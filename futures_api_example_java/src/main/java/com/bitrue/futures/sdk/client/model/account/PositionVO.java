package com.bitrue.futures.sdk.client.model.account;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PositionVO {

    Integer contractId;
    String contractName;
    String contractSymbol;
    List<Position> positions;
}
