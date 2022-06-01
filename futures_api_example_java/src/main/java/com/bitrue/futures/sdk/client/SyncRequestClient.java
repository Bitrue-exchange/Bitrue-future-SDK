package com.bitrue.futures.sdk.client;

import com.bitrue.futures.sdk.client.impl.BitrueApiInternalFactory;
import com.bitrue.futures.sdk.client.model.enums.Interval;
import com.bitrue.futures.sdk.client.model.market.ContractInfo;
import com.bitrue.futures.sdk.client.model.market.KlineBar;
import com.bitrue.futures.sdk.client.model.market.OrderBook;
import com.bitrue.futures.sdk.client.model.market.PriceChangeTicker;

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

}
