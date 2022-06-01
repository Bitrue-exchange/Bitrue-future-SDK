package com.bitrue.futures.sdk.client.utils;

@FunctionalInterface
public interface Handler<T> {

    void handle(T t);
}
