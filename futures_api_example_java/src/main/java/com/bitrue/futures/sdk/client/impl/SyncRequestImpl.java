package com.bitrue.futures.sdk.client.impl;

import com.bitrue.futures.sdk.client.SyncRequestClient;
import com.bitrue.futures.sdk.client.model.enums.*;
import com.bitrue.futures.sdk.client.model.market.*;
import com.bitrue.futures.sdk.client.model.trade.Order;

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
    public ServerTime getServerTime() {
        return RestApiInvoker.callSync(requestImpl.getServerTime());
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

    @Override
    public Order placeOrder(String contractName, OrderSide side, PositionActiion action, OrderType orderType, PositionType positionType, TimeInForce timeInForce, String price, String volume, String clientOrdId) {
        return RestApiInvoker.callSync(requestImpl.postOrder(contractName, price, volume, orderType, side, action, positionType,
                clientOrdId, timeInForce));
    }

    @Override
    public Order cancelOrder(String contractName, Long orderId, String clientOrdId) {
        return RestApiInvoker.callSync(requestImpl.cancelOrder(contractName, orderId, clientOrdId));
    }

    @Override
    public List<Order> getOpenOrder(String contractName) {
        return RestApiInvoker.callSync(requestImpl.getOpenOrder(contractName));
    }

    @Override
    public Order queryOrder(String contractName, long orderId) {
        return RestApiInvoker.callSync(requestImpl.queryOrder(contractName, orderId));
    }
}
