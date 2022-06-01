package com.bitrue.futures.sdk.client.model.enums;

import com.bitrue.futures.sdk.client.model.market.ContractInfo;

/**
 * @author superatom
 */

public enum ContractSide {

    FORWARD(1), REVERSE(2);

    private int value;

    ContractSide(int value){
        this.value = value;
    }

    public static ContractSide forInt(int v){
        switch (v){
            case 1:
                return FORWARD;
            case 2:
                return REVERSE;
            default:
                return null;
        }
    }

}
