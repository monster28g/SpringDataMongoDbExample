package com.hhi.data.mongo.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * <pre>
 * create structured data
 * </pre>
 * @author Bong-Jin Kwon
 */
public class StructureCreator {

    private static final Logger LOGGER = LoggerFactory.getLogger(StructureCreator.class);

    private ObjectMapper om = new ObjectMapper();

    /**
     * <pre>
     * create StructureCreator instance
     * </pre>
     */
    public StructureCreator() {

    }
    /**
     * <pre>
     * create structured JsonNode by VDMPathDatas
     * </pre>
     * @param vdmPathDatas
     * @return
     */
    public JsonNode create(VDMPathDatas vdmPathDatas){

        ObjectNode rootNode = om.createObjectNode();

        for (Entry<String, List<ItemData>> entry : vdmPathDatas.entrySet()) {

            try {
                setStructuredNode(rootNode, entry.getKey(), entry.getValue());
            } catch (IllegalArgumentException e) {
                LOGGER.debug(e.toString());
                continue;
            }

        }

        return rootNode;
    }

    /**
     * <pre>
     * create structured JsonNode by VDMPathConfigDatas
     * </pre>
     * @param vdmPathConfigDatas
     * @return
     */
    public JsonNode create(Map<String, ObjectNode> vdmPathConfigDatas){

        ObjectNode rootNode = om.createObjectNode();

        for (Entry<String, ObjectNode> entry : vdmPathConfigDatas.entrySet()) {

            try {
                setStructuredNode(rootNode, entry.getKey(), entry.getValue());
            } catch (IllegalArgumentException e) {
                LOGGER.debug(e.toString());
                continue;
            }

        }

        return rootNode;
    }

    private void setStructuredNode(ObjectNode rootNode, String vdmFullPath, Object datas) {
        LOGGER.debug("vdmFullPath: {}", vdmFullPath);

        if (StringUtils.isBlank(vdmFullPath)) {
            throw new IllegalArgumentException("vdmFullPath is empty.");
        }

        String[] highTokens = StringUtils.split(vdmFullPath, "/");
        String[] lowTokens = StringUtils.split(highTokens[highTokens.length - 1], "."); // split highTokens last element (ESDR.DepthF.val.f)

        highTokens = (String[])ArrayUtils.remove(highTokens, highTokens.length - 1);// remove last element (ESDR.DepthF.val.f)

        String[] allTokens = (String[])ArrayUtils.addAll(highTokens, lowTokens);

        createChildren(rootNode, allTokens, 0, datas);

    }

    private void createChildren(ObjectNode parent, String[] allTokens, int tokenIndex, Object datas) {

        LOGGER.debug("token: {}", allTokens[tokenIndex]);

        if (allTokens.length == (tokenIndex + 1)) {

            // leaf node
            parent.put(allTokens[tokenIndex], convertJsonNode(datas));
            return;

        } else {

            ObjectNode child = (ObjectNode)parent.get(allTokens[tokenIndex]);

            if(child == null){
                child = om.createObjectNode();
                parent.put(allTokens[tokenIndex], child);
            }

            createChildren(child, allTokens, tokenIndex + 1, datas);
        }

    }

    /**
     * <pre>
     * convert to JsonNode
     * </pre>
     * @param data
     * @return
     */
    private JsonNode convertJsonNode(Object data) {

        JsonNode convertedObj = null;

        if (data instanceof List) {
            convertedObj = om.valueToTree( toArray((List)data) );
        } else if (data instanceof JsonNode) {
            convertedObj = (JsonNode)data;
        }

        return convertedObj;
    }

    /**
     * <pre>
     * convert to Object[]
     * </pre>
     * @param datas
     * @return
     */
    private Object[] toArray(List<ItemData> datas) {
        List<Object> list = new ArrayList<Object>();

        for (ItemData item : datas) {
            list.add( item.getTimestamp() );
            list.add( item.getValue() );
            list.add( item.getValid() );
        }

        return list.toArray();
    }

}