package com.bitrue.futures.sdk.client.model.market;

import com.bitrue.futures.sdk.client.FuturesApiConstants;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.List;

public class OrderBook {

    private List<OrderBookEntry> bids;

    private List<OrderBookEntry> asks;

    public List<OrderBookEntry> getBids() {
        return bids;
    }

    public void setBids(List<OrderBookEntry> bids) {
        this.bids = bids;
    }

    public List<OrderBookEntry> getAsks() {
        return asks;
    }

    public void setAsks(List<OrderBookEntry> asks) {
        this.asks = asks;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, FuturesApiConstants.TO_STRING_BUILDER_STYLE)
                .append("bids", bids).append("asks", asks).toString();
    }
}
