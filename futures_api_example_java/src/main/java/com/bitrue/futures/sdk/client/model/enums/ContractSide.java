package com.bitrue.futures.sdk.client.model.enums;

/**
 * @author superatom
 */

public enum ContractSide {

    FORWARD(), REVERSE();

    ContractSide(){
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
