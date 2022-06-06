package com.bitrue.futures.sdk.client.impl;

import com.bitrue.futures.sdk.client.FuturesApiConstants;
import com.bitrue.futures.sdk.client.RequestOptions;
import com.bitrue.futures.sdk.client.exception.BitrueApiException;
import com.bitrue.futures.sdk.client.model.account.Account;
import com.bitrue.futures.sdk.client.model.account.Position;
import com.bitrue.futures.sdk.client.model.account.PositionVO;
import com.bitrue.futures.sdk.client.model.enums.*;
import com.bitrue.futures.sdk.client.model.market.*;
import com.bitrue.futures.sdk.client.model.trade.Order;
import com.bitrue.futures.sdk.client.utils.JsonWrapperArray;
import com.bitrue.futures.sdk.client.utils.UrlParamsBuilder;
import com.sun.tools.corba.se.idl.constExpr.Or;
import okhttp3.Request;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

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
        String ts = String.valueOf(System.currentTimeMillis());
        String signature = new ApiSignature().createSignature(ts, address, apiKey, secretKey, builder);
        if (builder.hasPostParam()) {
            requestUrl += builder.buildUrl();
            return new Request.Builder().url(requestUrl).post(builder.buildPostBody())
                    .addHeader("Content-Type", "application/json")
                    .addHeader(FuturesApiConstants.API_KEY_HEADER, apiKey)
                    .addHeader(FuturesApiConstants.TS_KEY_HEADER, ts)
                    .addHeader(FuturesApiConstants.SIGN_KEY_HEADER, signature)
                    .addHeader("client_SDK_Version", "bitrue_futures-0.9.1-java")
                    .build();
        } else if (builder.checkMethod("PUT")) {
            requestUrl += builder.buildUrl();
            return new Request.Builder().url(requestUrl)
                    .put(builder.buildPostBody())
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader(FuturesApiConstants.API_KEY_HEADER, apiKey)
                    .addHeader(FuturesApiConstants.TS_KEY_HEADER, ts)
                    .addHeader(FuturesApiConstants.SIGN_KEY_HEADER, signature)
                    .addHeader("client_SDK_Version", "bitrue_futures-0.9.1-java")
                    .build();
        } else if (builder.checkMethod("DELETE")) {
            requestUrl += builder.buildUrl();
            return new Request.Builder().url(requestUrl)
                    .delete()
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("client_SDK_Version", "bitrue_futures-0.9.1-java")
                    .addHeader(FuturesApiConstants.TS_KEY_HEADER, ts)
                    .addHeader(FuturesApiConstants.API_KEY_HEADER, apiKey)
                    .addHeader(FuturesApiConstants.SIGN_KEY_HEADER, signature)
                    .build();
        } else {
            requestUrl += builder.buildUrl();
            return new Request.Builder().url(requestUrl)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("client_SDK_Version", "bitrue_futures-0.9.1-java")
                    .addHeader(FuturesApiConstants.TS_KEY_HEADER, ts)
                    .addHeader(FuturesApiConstants.API_KEY_HEADER, apiKey)
                    .addHeader(FuturesApiConstants.SIGN_KEY_HEADER, signature)
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
//                    .addHeader(FuturesApiConstants.TS_KEY_HEADER, System.currentTimeMillis()/1000)
                    .addHeader("client_SDK_Version", "bitrue_futures-0.9.1-java")
                    .build();
        } else if (builder.checkMethod("DELETE")) {
            return new Request.Builder().url(requestUrl)
                    .delete()
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader(FuturesApiConstants.API_KEY_HEADER, apiKey)
//                    .addHeader(FuturesApiConstants.TS_KEY_HEADER, System.currentTimeMillis()/1000)
                    .addHeader("client_SDK_Version", "bitrue_futures-0.9.1-java")
                    .build();
        } else if (builder.checkMethod("PUT")) {
            return new Request.Builder().url(requestUrl)
                    .put(builder.buildPostBody())
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader(FuturesApiConstants.API_KEY_HEADER, apiKey)
//                    .addHeader(FuturesApiConstants.TS_KEY_HEADER, System.currentTimeMillis()/1000)
                    .addHeader("client_SDK_Version", "bitrue_futures-0.9.1-java")
                    .build();
        } else {
            return new Request.Builder().url(requestUrl)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader(FuturesApiConstants.API_KEY_HEADER, apiKey)
//                    .addHeader(FuturesApiConstants.TS_KEY_HEADER, System.currentTimeMillis()/1000)
                    .addHeader("client_SDK_Version", "bitrue_futures-0.9.1-java")
                    .build();
        }
    }

    private Request createRequestByGetWithApikey(String address, UrlParamsBuilder builder) {
        return createRequestWithApikey(serverUrl, address, builder);
    }

    RestApiRequest<ServerTime> getServerTime(){
        RestApiRequest<ServerTime> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build();
        request.request = createRequestByGet("/fapi/v1/time", builder);
        request.jsonParser = (jsonWrapper -> {
            return ServerTime.builder().serverMillis(jsonWrapper.getLong("serverTime")).timeZone(jsonWrapper.getString("timezone")).build();
        });
        return request;
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

    RestApiRequest<Order> postOrder(String contractName, String price, String volume, OrderType orderType,
                                    OrderSide side, PositionActiion action, PositionType positionType, String clientOrdId,
                                    TimeInForce timeInForce){
        RestApiRequest<Order> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToPost("volume", volume)
                .putToPost("price", price)
                .putToPost("contractName", contractName)
                .putToPost("type", orderType.name())
                .putToPost("side", side.name())
                .putToPost("open", action.name())
                .putToPost("positionType", positionType.getValue())
                .putToPost("clientOrderId", clientOrdId)
                .putToPost("timeInForce", timeInForce.name());

        request.request = createRequestByPostWithSignature("/fapi/v1/order", builder);

        request.jsonParser = (jsonWrapper -> {
            Order result = Order.builder()
                    .clientOrdId(clientOrdId).orderId(jsonWrapper.getLong("orderId")).price(new BigDecimal(price))
                    .orgiQty(new BigDecimal(volume)).type(orderType.name()).contractName(contractName).side(side.name())
                    .postionAction(action.name()).timeInForce(timeInForce.name())
                    .build();
            return result;
        });

        return request;
    }

    public RestApiRequest<Order> cancelOrder(String contractName, Long orderId, String clientOrdId) {
        RestApiRequest<Order> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToPost("orderId", String.valueOf(orderId))
                .putToPost("contractName", contractName)
                .putToPost("clientOrderId", clientOrdId);
        request.request = createRequestByPostWithSignature("/fapi/v1/cancel", builder);

        request.jsonParser = (jsonWrapper -> {
            Order result = Order.builder()
                    .clientOrdId(clientOrdId).orderId(jsonWrapper.getLong("orderId")).contractName(contractName)
                    .build();
            return result;
        });
        return request;

    }

    public RestApiRequest<List<Order>> getOpenOrder(String contractName) {
        RestApiRequest<List<Order>> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build();
        builder.putToUrl("contractName", contractName);
        request.request = createRequestByGetWithSignature("/fapi/v1/openOrders", builder);

        request.jsonParser = (jsonWrapper->{
            List<Order> result = new ArrayList<>();
            JsonWrapperArray arr = jsonWrapper.getJsonArray("data");
            arr.forEach(wrapper -> {
                Order order = Order.builder().orderId(wrapper.getLong("orderId")).side(wrapper.getString("side"))
                        .executeQty(wrapper.getBigDecimalOrDefault("executedQty", BigDecimal.ZERO))
                        .price(wrapper.getBigDecimal("price")).orgiQty(wrapper.getBigDecimal("origQty"))
                        .avgPrice(wrapper.getBigDecimalOrDefault("avgPrice", BigDecimal.ZERO))
                        .type(wrapper.getString("type")).status(wrapper.getString("status"))
                        .postionAction(wrapper.getString("action")).ctime(wrapper.getLong("transactTime")).build();
                result.add(order);
            });
            return result;
        });
        return request;
    }

    public RestApiRequest<Order> queryOrder(String contractName, Long orderId){
        RestApiRequest<Order> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build();
        builder.putToUrl("contractName", contractName);
        builder.putToUrl("orderId", String.valueOf(orderId));

        request.request = createRequestByGetWithSignature("/fapi/v1/order", builder);
        request.jsonParser = (wrapper -> {
            return Order.builder().orderId(wrapper.getLong("orderId")).side(wrapper.getString("side"))
                    .executeQty(wrapper.getBigDecimalOrDefault("executedQty", BigDecimal.ZERO))
                    .price(wrapper.getBigDecimal("price")).orgiQty(wrapper.getBigDecimal("origQty"))
                    .avgPrice(wrapper.getBigDecimalOrDefault("avgPrice", BigDecimal.ZERO))
                    .type(wrapper.getString("type")).status(wrapper.getString("status"))
                    .postionAction(wrapper.getString("action")).ctime(wrapper.getLong("transactTime")).build();
        });
        return request;
    }

    public RestApiRequest<List<Account>> getAccount() {
        RestApiRequest<List<Account>> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build();

        request.request = createRequestByGetWithSignature("/fapi/v1/account", builder);
        request.jsonParser = (rootWrapper ->{
            List<Account> result = new ArrayList<>();
            JsonWrapperArray arr = rootWrapper.getJsonArray("account");
            arr.forEach(accJson -> {
                Account acct = Account.builder()
                        .marginCoin(accJson.getString("marginCoin"))
                        .accountNormal(accJson.getBigDecimal("accountNormal"))
                        .accountLock(accJson.getBigDecimal("accountLock"))
                        .isolatedNormal(accJson.getBigDecimal("partPositionNormal"))
                        .crossNormal(accJson.getBigDecimal("totalPositionNormal"))
                        .realizedAmount(accJson.getBigDecimal("achievedAmount"))
                        .unrealizedAmount(accJson.getBigDecimal("unrealizedAmount"))
                        .totalMarginRate(accJson.getBigDecimal("totalMarginRate"))
                        .totalEquity(accJson.getBigDecimal("totalEquity"))
                        .isolatedEquity(accJson.getBigDecimal("partEquity"))
                        .totalCost(accJson.getBigDecimal("totalCost"))
                        .sumMarginRate(accJson.getBigDecimal("sumMarginRate")).build();

                List<PositionVO> positionVoList = new ArrayList<>();
                JsonWrapperArray jsPosVos = accJson.getJsonArray("positionVos");
                jsPosVos.forEach(voJson ->{
                    PositionVO vo = PositionVO.builder()
                            .contractId(voJson.getInteger("contractId"))
                            .contractName(voJson.getString("contractName"))
                            .contractSymbol(voJson.getString("contractSymbol")).build();

                    List<Position> posList = new ArrayList<>();
                    JsonWrapperArray jsonPosList = voJson.getJsonArray("positions");
                    jsonPosList.forEach(jsonPos ->{
                        Position pos = Position.builder()
                                .id(jsonPos.getInteger("id"))
                                .uid(jsonPos.getInteger("uid"))
                                .positionType(PositionType.forInt(jsonPos.getInteger("positionType")))
                                .side(OrderSide.valueOf(jsonPos.getString("side").toUpperCase()))
                                .volume(jsonPos.getBigDecimal("volume"))
                                .openPrice(jsonPos.getBigDecimal("openPrice"))
                                .closePrice(jsonPos.getBigDecimal("closePrice"))
                                .leverageLevel(new BigDecimal(jsonPos.getInteger("leverageLevel")))
                                .holdAmount(jsonPos.getBigDecimal("holdAmount"))
                                .closeVolume(jsonPos.getBigDecimal("closeVolume"))
                                .pendingCloseVolume(jsonPos.getBigDecimal("pendingCloseVolume"))
                                .realizedAmount(jsonPos.getBigDecimal("realizedAmount"))
                                .historyRealizedAmount(jsonPos.getBigDecimal("historyRealizedAmount"))
                                .tradeFee(jsonPos.getBigDecimal("tradeFee"))
                                .fundingFee(jsonPos.getBigDecimal("capitalFee"))
                                .closeProfit(jsonPos.getBigDecimal("closeProfit"))
                                .shareAmount(jsonPos.getBigDecimal("shareAmount"))
                                .freezeLock(jsonPos.getInteger("freezeLock"))
                                .status(jsonPos.getInteger("status"))
                                .ctime(jsonPos.getDateTime("ctime"))
                                .mtime(jsonPos.getDateTime("mtime"))
                                .brokerId(jsonPos.getInteger("brokerId"))
                                .maintenanceMarginRatio(jsonPos.getBigDecimal("marginRate"))
                                .liquidationPrice(jsonPos.getBigDecimal("reducePrice"))
                                .returnRate(jsonPos.getBigDecimal("returnRate"))
                                .unrealizedAmount(jsonPos.getBigDecimal("unRealizedAmount"))
                                .openRealizedAmount(jsonPos.getBigDecimal("openRealizedAmount"))
                                .notional(jsonPos.getBigDecimal("positionBalance"))
                                .indexPrice(jsonPos.getBigDecimal("indexPrice"))
                                .maintenanceMarginRatio(jsonPos.getBigDecimal("keepRate"))
                                .maxFeeRate(jsonPos.getBigDecimal("maxFeeRate"))
                                .build();
                        posList.add(pos);
                    });
                    vo.setPositions(posList);
                    positionVoList.add(vo);
                });
                acct.setPositions(positionVoList);
                result.add(acct);
            });
            return result;
        });
        return request;
    }

    public RestApiRequest<List<Position>> getPositions(String contractName) {
        RestApiRequest<List<Position>> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build();
        builder.putToUrl("contractName", contractName);

        request.request = createRequestByGetWithSignature("/fapi/v1/positions", builder);
        request.jsonParser = (rootWrapper -> {
            List<Position> result = new ArrayList<>();
            JsonWrapperArray jsonPosList = rootWrapper.getJsonArray("positions");
            jsonPosList.forEach(jsonPos ->{
                Position pos = Position.builder()
//                        .id(jsonPos.getInteger("id"))
                        .contractName(jsonPos.getString("symbol"))
                        .positionType(PositionType.forInt(jsonPos.getInteger("positionType")))
                        .side(OrderSide.valueOf(jsonPos.getString("positionSide").toUpperCase()))
                        .volume(jsonPos.getBigDecimal("positionVolume"))
                        .openPrice(jsonPos.getBigDecimal("openPrice"))
                        .leverageLevel(new BigDecimal(jsonPos.getInteger("leverage")))
                        .holdAmount(jsonPos.getBigDecimal("holdAmount"))
                        .realizedAmount(jsonPos.getBigDecimal("realizedAmount"))
                        .ctime(jsonPos.getDateTime("ctime"))
                        .mtime(jsonPos.getDateTime("mtime"))
                        .build();
                result.add(pos);
            });
            return result;
        });
        return request;
    }

//    public static void main(String[] args){
//        ZoneId utc = ZoneId.of("Etc/UTC");
//        DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern(
//                "MM/dd/yyyy hh:mm:ss a zzz", Locale.ENGLISH);
//
//        String itsAlarmDttm = "2013-10-22T01:37:56";
//        ZonedDateTime utcDateTime = LocalDateTime.parse(itsAlarmDttm)
//                .atZone(ZoneId.systemDefault())
//                .withZoneSameInstant(utc);
//        String formatterUtcDateTime = utcDateTime.format(targetFormatter);
//        System.out.println(formatterUtcDateTime);
//    }
}
