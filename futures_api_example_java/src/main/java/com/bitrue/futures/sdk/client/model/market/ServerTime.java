package com.bitrue.futures.sdk.client.model.market;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServerTime {

    Long serverMillis;
    String timeZone;
}
