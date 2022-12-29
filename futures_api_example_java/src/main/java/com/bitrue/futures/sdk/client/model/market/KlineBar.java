package com.bitrue.futures.sdk.client.model.market;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * KlineBar details
 */
@Data
@Builder
public class KlineBar {

    private long index;
    private BigDecimal open;
    private BigDecimal close;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal vol;
}
