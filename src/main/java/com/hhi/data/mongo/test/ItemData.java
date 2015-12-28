package com.hhi.data.mongo.test;

/**
 * <pre>
 * structured data 의 leaf node Model class.
 * </pre>
 * @author Bong-Jin Kwon
 */
public class ItemData {

    private long timestamp;
    private String value;

    /**
     * 1: true, 0: false or daubt or NA
     */
    private int valid;

    /**
     * <pre>
     * create ItemData instance
     * </pre>
     * @param timestamp
     * @param value
     * @param valid
     */
    public ItemData(long timestamp, String value, int valid) {
        this.timestamp = timestamp;
        this.value = value;
        this.valid = valid;
    }

    /**
     * <pre>
     * get timestamp
     * </pre>
     * @return
     */
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * <pre>
     * get valid
     * </pre>
     * @return
     */
    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    /**
     * <pre>
     * get value
     * </pre>
     * @return
     */
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}