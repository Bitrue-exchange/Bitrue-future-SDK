package com.bitrue.futures.sdk.client.model.market;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Price Change Ticker
 */
@Builder
@Data
public class PriceChangeTicker {

    private BigDecimal high;
    private BigDecimal vol;
    private BigDecimal last;
    private BigDecimal low;
    private BigDecimal rose;
    private long time;
}
