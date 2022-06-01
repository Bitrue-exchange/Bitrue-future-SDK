package com.bitrue.futures.sdk.client.model.enums;

/**
 *
 * @author superatom
 */
public enum Interval {

    ONE_MIN("1min"), FIVE_MIN("5min"), FIFTEEN_MIN("15min"), THIRTY_MIN("30min"), ONE_HOUR("1h"), ONE_DAY("1day"), ONE_WEEK("1week"), ONE_MONTH("1month");

    /**
     * value
     */
    private String value;

    Interval(String v){
        this.value = v;
    }

    public String getValue(){
        return value;
    }

    public static Interval forValue(String v){
        switch (v){
            case "1min":
                return ONE_MIN;
            case "5min":
                return FIVE_MIN;
            case "15min":
                return FIFTEEN_MIN;
            case "30min":
                return THIRTY_MIN;
            case "1h":
                return ONE_HOUR;
            case "1day":
                return ONE_DAY;
            case "1week":
                return ONE_WEEK;
            case "1month":
                return ONE_MONTH;
            default:
                return null;
        }
    }
}
