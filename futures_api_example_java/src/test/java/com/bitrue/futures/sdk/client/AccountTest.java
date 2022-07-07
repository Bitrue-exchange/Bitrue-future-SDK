package com.bitrue.futures.sdk.client;

import org.junit.Test;

public class AccountTest {

    @Test
    public void testAccount(){
        SyncRequestClient client = SyncRequestClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY, new RequestOptions());
        System.out.println(client.getAccount());
    }

    @Test
    public void testInverseAccount(){
        SyncRequestClient client = SyncRequestClient.createInverse(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY, new RequestOptions());
        System.out.println(client.getAccount());
    }

    @Test
    public void testPositions(){
        SyncRequestClient client = SyncRequestClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY, new RequestOptions());
        System.out.println(client.getPositions("E-BTC-USDT"));
    }

    @Test
    public void testInversePositions(){
        SyncRequestClient client = SyncRequestClient.createInverse(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY, new RequestOptions());
        System.out.println(client.getPositions("E-BTC-USD"));
    }
}
