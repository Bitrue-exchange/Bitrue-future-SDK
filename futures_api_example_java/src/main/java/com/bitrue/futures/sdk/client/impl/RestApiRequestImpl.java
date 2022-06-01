package com.bitrue.futures.sdk.client.impl;

import com.bitrue.futures.sdk.client.FuturesApiConstants;
import com.bitrue.futures.sdk.client.RequestOptions;
import com.bitrue.futures.sdk.client.exception.BitrueApiException;
import com.bitrue.futures.sdk.client.model.enums.ContractSide;
import com.bitrue.futures.sdk.client.model.enums.Interval;
import com.bitrue.futures.sdk.client.model.market.*;
import com.bitrue.futures.sdk.client.utils.JsonWrapperArray;
import com.bitrue.futures.sdk.client.utils.UrlParamsBuilder;
import okhttp3.Request;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RestApiRequestImpl {

    private String apiKey;
    private String secretKey;
    private String serverUrl;

    RestApiRequestImpl(String apiKey, String secretKey, RequestOptions options) {
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        this.serverUrl = options.getUrl();
    }


    private Request createRequestByGet(String address, UrlParamsBuilder builder) {
//        System.out.println(serverUrl);
        return createRequestByGet(serverUrl, address, builder);
    }

    private Request createRequestByGet(String url, String address, UrlParamsBuilder builder) {
        return createRequest(url, address, builder);
    }

    private Request createRequest(String url, String address, UrlParamsBuilder builder) {
        String requestUrl = url + address;
        System.out.print(requestUrl);
        if (builder != null) {
            if (builder.hasPostParam()) {
                return new Request.Builder().url(requestUrl).post(builder.buildPostBody())
                        .addHeader("Content-Type", "application/json")
                        .addHeader("client_SDK_Version", "bitrue_futures-0.9.1-java").build();
            } else {
                return new Request.Builder().url(requestUrl + builder.buildUrl())
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .addHeader("client_SDK_Version", "bitrue_futures-0.9.1-java").build();
            }
        } else {
            return new Request.Builder().url(requestUrl).addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("client_SDK_Version", "bitrue_futures-0.9.1-java")
                    .build();
        }
    }

    private Request createRequestWithSignature(String url, String address, UrlParamsBuilder builder) {
        if (builder == null) {
            throw new BitrueApiException(BitrueApiException.RUNTIME_ERROR,
                    "[Invoking] Builder is null when create request with Signature");
        }
        String requestUrl = url + address;
        new ApiSignature().createSignature(apiKey, secretKey, builder);
        if (builder.hasPostParam()) {
            requestUrl += builder.buildUrl();
            return new Request.Builder().url(requestUrl).post(builder.buildPostBody())
                    .addHeader("Content-Type", "application/json")
                    .addHeader(FuturesApiConstants.API_KEY_HEADER, apiKey)
                    .addHeader("client_SDK_Version", "binance_futures-1.0.1-java")
                    .build();
        } else if (builder.checkMethod("PUT")) {
            requestUrl += builder.buildUrl();
            return new Request.Builder().url(requestUrl)
                    .put(builder.buildPostBody())
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader(FuturesApiConstants.API_KEY_HEADER, apiKey)
                    .addHeader("client_SDK_Version", "binance_futures-1.0.1-java")
                    .build();
        } else if (builder.checkMethod("DELETE")) {
            requestUrl += builder.buildUrl();
            return new Request.Builder().url(requestUrl)
                    .delete()
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("client_SDK_Version", "binance_futures-1.0.1-java")
                    .addHeader(FuturesApiConstants.API_KEY_HEADER, apiKey)
                    .build();
        } else {
            requestUrl += builder.buildUrl();
            return new Request.Builder().url(requestUrl)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("client_SDK_Version", "binance_futures-1.0.1-java")
                    .addHeader(FuturesApiConstants.API_KEY_HEADER, apiKey)
                    .build();
        }
    }

    private Request createRequestByPostWithSignature(String address, UrlParamsBuilder builder) {
        return createRequestWithSignature(serverUrl, address, builder.setMethod("POST"));
    }

    private Request createRequestByGetWithSignature(String address, UrlParamsBuilder builder) {
        return createRequestWithSignature(serverUrl, address, builder);
    }

    private Request createRequestByPutWithSignature(String address, UrlParamsBuilder builder) {
        return createRequestWithSignature(serverUrl, address, builder.setMethod("PUT"));
    }

    private Request createRequestByDeleteWithSignature(String address, UrlParamsBuilder builder) {
        return createRequestWithSignature(serverUrl, address, builder.setMethod("DELETE"));
    }

    private Request createRequestWithApikey(String url, String address, UrlParamsBuilder builder) {
        if (builder == null) {
            throw new BitrueApiException(BitrueApiException.RUNTIME_ERROR,
                    "[Invoking] Builder is null when create request with Signature");
        }
        String requestUrl = url + address;
        requestUrl += builder.buildUrl();
        if (builder.hasPostParam()) {
            return new Request.Builder().url(requestUrl)
                    .post(builder.buildPostBody())
                    .addHeader("Content-Type", "application/json")
                    .addHeader(FuturesApiConstants.API_KEY_HEADER, apiKey)
                    .addHeader("client_SDK_Version", "binance_futures-1.0.1-java")
                    .build();
        } else if (builder.checkMethod("DELETE")) {
            return new Request.Builder().url(requestUrl)
                    .delete()
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader(FuturesApiConstants.API_KEY_HEADER, apiKey)
                    .addHeader("client_SDK_Version", "binance_futures-1.0.1-java")
                    .build();
        } else if (builder.checkMethod("PUT")) {
            return new Request.Builder().url(requestUrl)
                    .put(builder.buildPostBody())
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader(FuturesApiConstants.API_KEY_HEADER, apiKey)
                    .addHeader("client_SDK_Version", "binance_futures-1.0.1-java")
                    .build();
        } else {
            return new Request.Builder().url(requestUrl)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader(FuturesApiConstants.API_KEY_HEADER, apiKey)
                    .addHeader("client_SDK_Version", "binance_futures-1.0.1-java")
                    .build();
        }
    }

    private Request createRequestByGetWithApikey(String address, UrlParamsBuilder builder) {
        return createRequestWithApikey(serverUrl, address, builder);
    }

    RestApiRequest<List<ContractInfo>> getContractList() {
        RestApiRequest<List<ContractInfo>> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build();
        request.request = createRequestByGet("/fapi/v1/contracts", builder);


        request.jsonParser = (jsonWraper -> {
            List<ContractInfo> result = new ArrayList<>();
            JsonWrapperArray arr = jsonWraper.getJsonArray("data");
            arr.forEach(wrapper -> {
                ContractInfo contractInfo = ContractInfo.builder()
                        .name(wrapper.getString("symbol"))
                        .pricePrecision(wrapper.getInteger("pricePrecision"))
                        .side(ContractSide.forInt(wrapper.getInteger("side")))
                        .maxMarketVolume(wrapper.getBigDecimal("maxMarketVolume"))
                        .multiplier(wrapper.getBigDecimal("multiplier"))
                        .minOrderVolume(wrapper.getBigDecimal("minOrderVolume"))
                        .maxMarketMoney(wrapper.getBigDecimal("maxMarketMoney"))
                        .type(wrapper.getString("type"))
                        .maxLimitVolume(wrapper.getBigDecimal("maxLimitVolume"))
                        .maxValidOrder(wrapper.getBigDecimal("maxValidOrder"))
                        .multiplierCoin(wrapper.getString("multiplierCoin"))
                        .minOrderMoney(wrapper.getBigDecimal("minOrderMoney"))
                        .maxLimitMoney(wrapper.getBigDecimal("maxLimitMoney"))
                        .status(wrapper.getInteger("status"))
                        .build();
                result.add(contractInfo);
            });
            return result;
        });

        return request;
    }

    RestApiRequest<OrderBook> getOrderBook(String contractName, Integer limit) {
        RestApiRequest<OrderBook> request = new RestApiRequest<OrderBook>();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("contractName", contractName)
                .putToUrl("limit", limit);
        request.request = createRequestByGet("/fapi/v1/depth", builder);

        request.jsonParser = (jsonWrapper -> {
            OrderBook result = new OrderBook();
            List<OrderBookEntry> list = new LinkedList<>();
            JsonWrapperArray dataArray = jsonWrapper.getJsonArray("bids");
            dataArray.forEachAsArray(item -> {
                OrderBookEntry entry = OrderBookEntry.builder()
                        .price(new BigDecimal(item.getToStringAt(0)))
                        .qty(new BigDecimal(item.getToStringAt(1)))
                        .build();
                list.add(entry);
            });
            result.setBids(list);

            List<OrderBookEntry> asks = new LinkedList<>();
            JsonWrapperArray askArray = jsonWrapper.getJsonArray("asks");
            askArray.forEachAsArray(item -> {
                OrderBookEntry entry = OrderBookEntry.builder()
                        .price(new BigDecimal(item.getToStringAt(0)))
                        .qty(new BigDecimal(item.getToStringAt(1)))
                        .build();
                asks.add(entry);
            });
            result.setAsks(asks);
            return result;
        });

        return request;
    }

    RestApiRequest<PriceChangeTicker> get24HrTickerPriceChange(String contractName){
        RestApiRequest<PriceChangeTicker> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("contractName", contractName);

        request.request = createRequestByGet("/fapi/v1/ticker", builder);

        request.jsonParser = (jsonWrapper ->{
            PriceChangeTicker result = PriceChangeTicker.builder()
                    .high(jsonWrapper.getBigDecimal("high"))
                    .low(jsonWrapper.getBigDecimal("low"))
                    .last(jsonWrapper.getBigDecimal("last"))
                    .vol(jsonWrapper.getBigDecimal("vol"))
                    .rose(jsonWrapper.getBigDecimal("rose"))
                    .time(jsonWrapper.getLong("time"))
                    .build();
            return result;
        });
        return request;
    }


    RestApiRequest<List<KlineBar>> getKlines(String contractName, Interval interval, Integer limit){
        RestApiRequest<List<KlineBar>> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("contractName", contractName)
                .putToUrl("interval", interval.getValue())
                .putToUrl("limit", limit);
        request.request = createRequestByGet("/fapi/v1/klines", builder);

        request.jsonParser = (jsonWrapper -> {
            List<KlineBar> result = new LinkedList<>();
            JsonWrapperArray arr = jsonWrapper.getJsonArray("data");
            arr.forEach(e -> {
                KlineBar bar = KlineBar.builder()
                        .high(e.getBigDecimal("high"))
                        .low(e.getBigDecimal("low"))
                        .open(e.getBigDecimal("open"))
                        .close(e.getBigDecimal("close"))
                        .vol(e.getBigDecimal("vol"))
                        .index(e.getLong("idx")).build();
                result.add(bar);
            });
            return result;
        });
        return request;
    }



}
