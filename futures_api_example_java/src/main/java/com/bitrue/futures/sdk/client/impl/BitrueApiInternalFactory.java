package com.bitrue.futures.sdk.client.impl;

import com.bitrue.futures.sdk.client.RequestOptions;
import com.bitrue.futures.sdk.client.SyncRequestClient;

public class BitrueApiInternalFactory {

    private static final BitrueApiInternalFactory instance = new BitrueApiInternalFactory();

    private BitrueApiInternalFactory(){
    }

    public static BitrueApiInternalFactory getInstance() {
        return instance;
    }

    public SyncRequestClient createSyncRequestClient(String apiKey, String secKey, RequestOptions options) {
        RequestOptions requestOptions = new RequestOptions(options);
        RestApiRequestImpl requestImpl = new RestApiRequestImpl(apiKey, secKey, requestOptions);
        return new SyncRequestImpl(requestImpl);
    }

    public SyncRequestClient createSyncRequestInverseClient(String apiKey, String secKey, RequestOptions options){
        RequestOptions requestOptions = new RequestOptions(options);
        InverseRestApiRquestImpl inverseRequestImpl = new InverseRestApiRquestImpl(apiKey, secKey, options);
        return new SyncRequestImpl(inverseRequestImpl);
    }
}