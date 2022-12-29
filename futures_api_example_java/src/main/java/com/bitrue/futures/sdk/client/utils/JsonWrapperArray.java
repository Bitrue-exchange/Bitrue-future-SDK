package com.bitrue.futures.sdk.client.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bitrue.futures.sdk.client.exception.BitrueApiException;
import java.math.BigDecimal;
import java.util.List;
import java.util.LinkedList;

public class JsonWrapperArray {

    private JSONArray array;

    public JsonWrapperArray(JSONArray array) {
        this.array = array;
    }

    public JsonWrapper getJsonObjectAt(int index) {
        if (array != null && array.size() > index) {
            JSONObject object = (JSONObject) array.get(index);
            if (object == null) {
                throw new BitrueApiException(BitrueApiException.RUNTIME_ERROR,
                        "[Json] Cannot get object at index " + index + " in array");
            }
            return new JsonWrapper(object);
        } else {
            throw new BitrueApiException(BitrueApiException.RUNTIME_ERROR,
                    "[Json] Index is out of bound or array is null");
        }
    }

    public void add(JSON val) {
        this.array.add(val);
    }

    public JsonWrapperArray getArrayAt(int index) {
        if (array != null && array.size() > index) {
            JSONArray newArray = (JSONArray) array.get(index);
            if (newArray == null) {
                throw new BitrueApiException(BitrueApiException.RUNTIME_ERROR,
                        "[Json] Cannot get array at index " + index + " in array");
            }
            return new JsonWrapperArray(newArray);
        } else {
            throw new BitrueApiException(BitrueApiException.RUNTIME_ERROR,
                    "[Json] Index is out of bound or array is null");
        }
    }

    private Object getObjectAt(int index) {
        if (array != null && array.size() > index) {
            return array.get(index);
        } else {
            throw new BitrueApiException(BitrueApiException.RUNTIME_ERROR,
                    "[Json] Index is out of bound or array is null");
        }
    }

    public long getLongAt(int index) {
        try {
            return (Long) getObjectAt(index);
        } catch (Exception e) {
            throw new BitrueApiException(BitrueApiException.RUNTIME_ERROR,
                    "[Json] Cannot get long at index " + index + " in array: " + e.getMessage());
        }

    }

    public Integer getIntegerAt(int index) {
        try {
            return (Integer) getObjectAt(index);
        } catch (Exception e) {
            throw new BitrueApiException(BitrueApiException.RUNTIME_ERROR,
                    "[Json] Cannot get integer at index " + index + " in array: " + e.getMessage());
        }
    }

    public String getToStringAt(int index){
        try {
            Object obj = getObjectAt(index);
            if(obj instanceof  BigDecimal){
                return ((BigDecimal) obj).toPlainString();
            }
            return obj.toString();
        } catch (Exception e) {
            throw new BitrueApiException(BitrueApiException.RUNTIME_ERROR,
                    "[Json] Cannot get getToStringAt at index " + index + " in array: " + e.getMessage());
        }
    }

    public BigDecimal getOriginBigDecimalAt(int index){
        try {
            return (BigDecimal) getObjectAt(index);
        } catch (Exception e) {
            throw new BitrueApiException(BitrueApiException.RUNTIME_ERROR,
                    "[Json] Cannot get BigDecimal at index " + index + " in array: " + e.getMessage());
        }
    }

    public BigDecimal getBigDecimalAt(int index) {

        try {
            return new BigDecimal(new BigDecimal(getStringAt(index)).stripTrailingZeros().toPlainString());
        } catch (RuntimeException e) {
            throw new BitrueApiException(null, e.getMessage());
        }

    }

    public String getStringAt(int index) {

        try {
            return (String) getObjectAt(index);
        } catch (RuntimeException e) {
            throw new BitrueApiException(null, e.getMessage());
        }

    }

    public void forEach(Handler<JsonWrapper> objectHandler) {
        array.forEach((object) -> {
            if (!(object instanceof JSONObject)) {
                throw new BitrueApiException(BitrueApiException.RUNTIME_ERROR, "[Json] Parse array error in forEach");
            }
            objectHandler.handle(new JsonWrapper((JSONObject) object));
        });
    }

    public void forEachAsArray(Handler<JsonWrapperArray> objectHandler) {
        array.forEach((object) -> {
            if (!(object instanceof JSONArray)) {
                throw new BitrueApiException(BitrueApiException.RUNTIME_ERROR,
                        "[Json] Parse array error in forEachAsArray");
            }
            objectHandler.handle(new JsonWrapperArray((JSONArray) object));
        });
    }

    public void forEachAsString(Handler<String> objectHandler) {
        array.forEach((object) -> {
            if (!(object instanceof String)) {
                throw new BitrueApiException(BitrueApiException.RUNTIME_ERROR,
                        "[Json] Parse array error in forEachAsString");
            }
            objectHandler.handle((String) object);
        });
    }

    public List<String> convert2StringList() {
        List<String> result = new LinkedList<>();
        this.forEachAsString((item) -> {
            result.add(item);
        });
        return result;
    }

}