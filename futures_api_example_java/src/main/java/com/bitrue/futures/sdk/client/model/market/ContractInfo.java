package com.bitrue.futures.sdk.client.model.market;

import com.bitrue.futures.sdk.client.model.enums.ContractSide;
import com.oracle.webservices.internal.api.databinding.DatabindingMode;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 *
 * @author superatom
 */
@Data
@EqualsAndHashCode
@Builder
public class ContractInfo {

    String name;
    Integer pricePrecision;
    ContractSide side;
    BigDecimal maxMarketVolume;
    BigDecimal multiplier;
    BigDecimal minOrderVolume;
    BigDecimal maxMarketMoney;
    String type;
    BigDecimal maxLimitVolume;
    BigDecimal maxValidOrder;
    String multiplierCoin;
    BigDecimal minOrderMoney;
    BigDecimal maxLimitMoney;
    Integer status;

}
