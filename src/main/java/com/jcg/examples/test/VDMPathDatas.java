package com.jcg.examples.test;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <pre>
 * structured data 를 만들기 위한
 * </pre>
 * @author Bong-Jin Kwon
 */
public class VDMPathDatas extends HashMap<String, List<ItemData>> {

    /**
     * <pre>
     * create VDMPathDatas instance
     * </pre>
     */
    public VDMPathDatas(){

    }

    /**
     * <pre>
     * add ItemData instance
     * </pre>
     * @param vdmFullPath
     * @param data
     */
    public void addData(String vdmFullPath, ItemData data){

        List<ItemData> datas = null;
        if (containsKey(vdmFullPath)) {
            datas = get(vdmFullPath);
        }else{
            datas = new ArrayList<ItemData>();
            put(vdmFullPath, datas);
        }

        datas.add(data);
    }


}