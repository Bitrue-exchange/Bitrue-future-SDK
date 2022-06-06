package com.bitrue.futures.sdk.client.model.enums;

/**
 * @author superatom
 */

public enum PositionType {
    CROSS(1), ISOLATED(2);

    private int value;
    PositionType(int i){
        this.value = i;
    }

    public static PositionType forInt(int i){
        switch (i){
            case 1:
                return CROSS;
            case 2:
                return ISOLATED;
            default:
                return null;
        }
    }

    public Integer getValue() {
        return value;
    }
}
