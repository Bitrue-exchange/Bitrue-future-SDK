package com.bitrue.futures.sdk.client.impl;

import com.bitrue.futures.sdk.client.utils.JsonWrapper;

@FunctionalInterface
public interface RestApiJsonParser<T> {

    T parseJson(JsonWrapper json);
}
