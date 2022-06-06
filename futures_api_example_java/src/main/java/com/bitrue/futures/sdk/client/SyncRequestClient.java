package com.bitrue.futures.sdk.client;

import com.bitrue.futures.sdk.client.impl.BitrueApiInternalFactory;
import com.bitrue.futures.sdk.client.model.account.Account;
import com.bitrue.futures.sdk.client.model.account.Position;
import com.bitrue.futures.sdk.client.model.enums.*;
import com.bitrue.futures.sdk.client.model.market.*;
import com.bitrue.futures.sdk.client.model.trade.Order;

import java.util.List;

public interface SyncRequestClient {

    static SyncRequestClient create(){
        return create("", "", new RequestOptions());
    }

    static SyncRequestClient create(String apiKey, String secKey){
        return create(apiKey, secKey, new RequestOptions());
    }

    static SyncRequestClient create(String apiKey, String secKey, RequestOptions requestOptions) {
        return BitrueApiInternalFactory.getInstance().createSyncRequestClient(apiKey, secKey, requestOptions);
    }

    List<ContractInfo> getContractList();

    OrderBook getOrderBook(String contractName, Integer limit);

    List<KlineBar> getKlines(String contractName, Interval interval, Integer limit);

    PriceChangeTicker get24HrTickerPriceChange(String contractName);

    Order placeOrder(String contractName, OrderSide side, PositionActiion action, OrderType orderType,
                     PositionType positionType, TimeInForce timeInForce, String price, String volume, String clientOrdId);

    Order cancelOrder(String contractName, Long orderId, String clientOrdId);

    ServerTime getServerTime();

    List<Order> getOpenOrder(String contractName);

    Order queryOrder(String s, long l);

    List<Account> getAccount();

    List<Position> getPositions(String contractName);
}
