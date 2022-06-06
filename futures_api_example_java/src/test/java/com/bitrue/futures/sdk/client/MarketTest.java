package com.bitrue.futures.sdk.client;

import com.bitrue.futures.sdk.client.model.enums.Interval;
import org.junit.Test;

public class MarketTest {

    @Test
    public void testContracts(){
        SyncRequestClient client = SyncRequestClient.create("", "", new RequestOptions());
        System.out.println(client.getContractList());
    }

    @Test
    public void testOrderBook(){
        SyncRequestClient client = SyncRequestClient.create();
        System.out.println(client.getOrderBook("E-BTC-USDT", 100));
    }

    @Test
    public void testTicker(){
        SyncRequestClient client = SyncRequestClient.create();
        System.out.println(client.get24HrTickerPriceChange("E-BTC-USDT"));
    }

    @Test
    public void testKlines(){
        SyncRequestClient client = SyncRequestClient.create();
        System.out.println(client.getKlines("E-BTC-USDT", Interval.forValue("1min"), 300));
    }

    @Test
    public void testServerTime(){
        SyncRequestClient client = SyncRequestClient.create();
        System.out.println(client.getServerTime());
    }
}
