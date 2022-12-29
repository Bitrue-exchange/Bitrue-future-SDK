package com.bitrue.futures.sdk.client.model.market;

import lombok.Builder;
import lombok.Data;

/**
 * Server time details
 */
@Data
@Builder
public class ServerTime {

    Long serverMillis;
    String timeZone;
}
