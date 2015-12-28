package com.hhi.data.mongo.test;


import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * json convert util class.
 *
 * @author BongJin Kwon
 */
public abstract class JSONUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * <pre>
     * convert json string to class Object.
     * </pre>
     *
     * @param <T>
     * @param json
     * @param valueType
     * @return
     */
    public static <T> T jsonToObj(String json, Class<T> valueType) {

        try {
            return MAPPER.readValue(json, valueType);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }


    }

    /**
     * <pre>
     * convert Object to json String.
     * </pre>
     *
     * @param obj
     * @return
     */
    public static String objToJson(Object obj) throws IOException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        JsonGenerator generator =
                MAPPER.getJsonFactory().createJsonGenerator(outputStream, JsonEncoding.UTF8);

        MAPPER.writeValue(generator, obj);

        return outputStream.toString(JsonEncoding.UTF8.getJavaName());
    }

    /**
     * Method to deserialize JSON content as tree expressed using set of JsonNode instances.
     * @param json JSON content
     * @return
     */
    public static JsonNode readTree(String json){

        try {
            return MAPPER.readTree(json);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
