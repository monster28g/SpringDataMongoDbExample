package com.jcg.examples.test;

import com.mongodb.*;
import com.mongodb.util.JSON;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class MongoDbSamplingClient {
    MongoClient mongoClient;
    SimpleDateFormat sdf;

    final static String timeStamp = "timestamp2";

    final static String map = " function () {"
            + "	emit(this.vdmpath, this);"
            + " }";

    final static String reduce = " function (key, values) { "
            + " 	lastDt = values[0].timestamp2; "
            + " 	lastValue = values[0]; "
            + " 	for (var i in values) { "
            + " 		if(lastDt < values[i].timestamp2) { "
            + " 			lastDt = values[i].timestamp2; "
            + " 			lastValue = values[i]; "
            + " 		} "
            + " 	} "
            + " 	return lastValue; "
            + " }";

    public MongoDbSamplingClient(String address, int port) {
        sdf = new SimpleDateFormat("MM-dd-yyyy_HH-mm-ss");
        try {
            mongoClient = new MongoClient( address , port );
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public void setMongoClient(String address, int port) throws UnknownHostException {
        this.mongoClient = new MongoClient(address, port);
    }

    public void doDump(DUMP_TYPE type) {
        // an hour
        int endTime = 1440;

        DB db = mongoClient.getDB( "hhivaasDevDB" );
        DBCollection collection = db.getCollection("SENSOR_DATA");
        // init start time
//        long start = getStartTime(timeStamp, collection);
//        long start = 1449014400000L;
        long start = 1448928000000L;
        long next = 0;


        for(int i = 0; i < endTime; i++) {
            next = periodOneMinute(start);
            BasicDBObject query = new BasicDBObject(timeStamp, new BasicDBObject("$gt", start).append("$lt", next));
//                    .append("vdmpath", new BasicDBObject("$ne", "AIS/GenAIS0.Msg.data"));

            System.out.println(query.toString());

//            MapReduceCommand cmd = new MapReduceCommand(collection, map, reduce,
//                    null, MapReduceCommand.OutputType.INLINE, query);
//            MapReduceOutput out = collection.mapReduce(cmd);
//            List<DBObject> results = new ArrayList<>();

            List<DBObject> results = collection.find(query).toArray();

            //convert list string to json array
//            for (DBObject o : out.results()) {
//                results.add((DBObject) o.get("value"));
//            }

            System.out.println(getDate(start, i));
            String s = getResultFormat(results);
            if(DUMP_TYPE.CONSOLE.equals(type)) {
                System.out.println(s);
            }else if(DUMP_TYPE.FILE.equals(type)) {
                try {
                    out(convertDate(start), s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            start = next;
        }


    }

    private String convertDate(long start) {
        return getSimpleDateFormatWithGMT().format(new java.util.Date (start));
    }

    private SimpleDateFormat getSimpleDateFormatWithGMT() {

        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf;
    }


    public String getResultFormat(List<DBObject> results) {

        String resultStr = JSON.serialize(results);


        return getStructuredJson(resultStr);

    }

    public String createStructuredData(JsonNode jsonNode) throws Exception {

        if (!jsonNode.isArray()) {
            throw new Exception();
        }

        StructureCreator creator = new StructureCreator();
        VDMPathDatas vdmPathDatas = new VDMPathDatas();

        for (int i = 0; i < jsonNode.size(); i++) {

            ObjectNode objNode = (ObjectNode) jsonNode.get(i);

            addItemData(objNode, vdmPathDatas);
        }

        JsonNode structured = creator.create(vdmPathDatas);

        return structured.toString();

    }

    private void addItemData(ObjectNode objNode, VDMPathDatas vdmPathDatas) {


        String vdmFullPath = objNode.get("vdmpath").asText();

        JsonNode timeNode = objNode.get("timestamp2");
        if (timeNode == null) {
            timeNode = objNode.get("receivedTime");
        }
        long timestamp = timeNode.asLong();
        String value = objNode.get("value").asText();
        String valid = objNode.get("valid").asText();
        int iValid = 0;
        if ("true".compareTo(valid) == 0) {
            iValid = 1;
        }

        vdmPathDatas.addData(vdmFullPath, new ItemData(timestamp, value, iValid));
    }

    public String getStructuredJson(String results){

        String structuredJson = "";
        try {
            JsonNode jsonNode = JSONUtil.readTree(results.toString());

            //use the vdm-handler bundle
            structuredJson = createStructuredData(jsonNode);
        } catch (Exception e) {
            // TODO Auto-generated catch block

        }

        return structuredJson;
    }


    private String getDate(long start, int i) {
        return "start priod "+ i + " : " + getSimpleDateFormatWithGMT().format(new java.util.Date (start));
    }

    private long getStartTime(String timeStamp, DBCollection coll) {
        DBObject obj = coll.findOne();
        return (long) obj.get(timeStamp);
    }


    private void out(String fileName, String x) throws IOException {
        BufferedWriter writer = new BufferedWriter( new FileWriter( fileName));
        writer.write( x);
// do stuff
        writer.close();

    }

    private long periodOneMinute(long start) {
        return start + 60000L;
    }


}
