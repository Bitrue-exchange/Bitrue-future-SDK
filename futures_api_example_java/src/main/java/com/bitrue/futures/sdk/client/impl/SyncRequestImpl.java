package com.bitrue.futures.sdk.client.impl;

import com.bitrue.futures.sdk.client.SyncRequestClient;
import com.bitrue.futures.sdk.client.model.enums.Interval;
import com.bitrue.futures.sdk.client.model.market.ContractInfo;
import com.bitrue.futures.sdk.client.model.market.KlineBar;
import com.bitrue.futures.sdk.client.model.market.OrderBook;
import com.bitrue.futures.sdk.client.model.market.PriceChangeTicker;

import java.util.List;

/**
 * @author superatom
 */
public class SyncRequestImpl implements SyncRequestClient {

    private final RestApiRequestImpl requestImpl;

    SyncRequestImpl(RestApiRequestImpl requestImpl) {
        this.requestImpl = requestImpl;
    }

    @Override
    public List<ContractInfo> getContractList() {
        return RestApiInvoker.callSync(requestImpl.getContractList());
    }

    @Override
    public OrderBook getOrderBook(String contractName, Integer limit) {
        return RestApiInvoker.callSync(requestImpl.getOrderBook(contractName, limit));
    }

    @Override
    public List<KlineBar> getKlines(String contractName, Interval interval, Integer limit) {
        return RestApiInvoker.callSync(requestImpl.getKlines(contractName, interval, limit));
    }

    @Override
    public PriceChangeTicker get24HrTickerPriceChange(String contractName) {
        return RestApiInvoker.callSync(requestImpl.get24HrTickerPriceChange(contractName));
    }
}
