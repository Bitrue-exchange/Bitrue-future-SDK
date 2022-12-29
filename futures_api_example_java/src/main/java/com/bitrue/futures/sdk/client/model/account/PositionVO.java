package com.bitrue.futures.sdk.client.model.account;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Position Value Object details
 */
@Data
@Builder
public class PositionVO {

    Integer contractId;
    String contractName;
    String contractSymbol;
    List<Position> positions;
}
