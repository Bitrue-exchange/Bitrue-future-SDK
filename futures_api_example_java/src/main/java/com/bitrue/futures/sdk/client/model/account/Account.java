package com.bitrue.futures.sdk.client.model.account;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class Account {

    String marginCoin;
    BigDecimal accountNormal;
    BigDecimal accountLock;
    BigDecimal isolatedNormal;
    BigDecimal crossNormal;
    BigDecimal realizedAmount;
    BigDecimal unrealizedAmount;
    BigDecimal totalMarginRate;
    BigDecimal totalEquity;
    BigDecimal isolatedEquity;
    BigDecimal totalCost;
    BigDecimal sumMarginRate;
    List<PositionVO> positions;
}
