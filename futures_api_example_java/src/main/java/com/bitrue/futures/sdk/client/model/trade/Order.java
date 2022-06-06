package com.bitrue.futures.sdk.client.model.trade;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author superatom
 */
@Data
@Builder
public class Order {

    private  String clientOrdId;
    private BigDecimal cumQuote;
    private BigDecimal executeQty;
    private Long orderId;
    private BigDecimal orgiQty;
    private BigDecimal price;
    private Boolean reduceOnly;
    private String side;
    private Integer positionType;
    private String postionAction;
    private String status;
    private String contractName;
    private String timeInForce;
    private String type;
    private long updateTime;
    private BigDecimal avgPrice;
    private long ctime;

}
