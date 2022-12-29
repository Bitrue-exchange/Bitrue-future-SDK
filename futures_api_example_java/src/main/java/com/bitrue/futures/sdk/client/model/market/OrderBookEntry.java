package com.bitrue.futures.sdk.client.model.market;

import com.bitrue.futures.sdk.client.constant.FuturesApiConstants;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.math.BigDecimal;

@Data
@Builder
public class OrderBookEntry {

    private BigDecimal price;

    private BigDecimal qty;

    @Override
    public String toString() {
        return new ToStringBuilder(this, FuturesApiConstants.TO_STRING_BUILDER_STYLE).append("price", price)
                .append("qty", qty).toString();
    }
}