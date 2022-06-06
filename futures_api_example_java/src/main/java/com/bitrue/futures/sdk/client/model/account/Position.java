package com.bitrue.futures.sdk.client.model.account;

import com.bitrue.futures.sdk.client.model.enums.OrderSide;
import com.bitrue.futures.sdk.client.model.enums.PositionType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Date;

@Data
@Builder
public class Position {

    Integer id;
    Integer uid;
    String contractName;
    PositionType positionType;
    OrderSide side;
    BigDecimal volume;
    BigDecimal openPrice;
    BigDecimal avgPrice;
    BigDecimal closePrice;
    BigDecimal leverageLevel;
    BigDecimal holdAmount;
    BigDecimal closeVolume;
    BigDecimal pendingCloseVolume;
    BigDecimal realizedAmount;
    BigDecimal historyRealizedAmount;
    BigDecimal tradeFee;
    BigDecimal fundingFee;
    BigDecimal closeProfit;
    BigDecimal shareAmount;
    Integer freezeLock;
    Integer status;
    ZonedDateTime ctime;
    ZonedDateTime mtime;
    Integer brokerId;
    ZonedDateTime lockTime;
    BigDecimal marginRate;
    BigDecimal liquidationPrice;
    BigDecimal returnRate;
    BigDecimal unrealizedAmount;
    BigDecimal openRealizedAmount;
    BigDecimal notional;
    BigDecimal indexPrice;
    BigDecimal maintenanceMarginRatio;
    BigDecimal maxFeeRate;

}
