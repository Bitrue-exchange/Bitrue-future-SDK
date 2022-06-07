package com.bitrue.futures.sdk.client;

import org.apache.commons.lang.builder.ToStringStyle;

public class FuturesApiConstants {
    /**
     * REST API base URL.
     */
    public static final String API_BASE_URL = "https://fapi.bitrue.com";
    
    /**
     * HTTP Header to be used for API-KEY authentication.
     */
    public static final String API_KEY_HEADER = "X-CH-APIKEY";

    /**
     * Decorator to indicate that an endpoint requires an API key.
     */
    public static final String ENDPOINT_SECURITY_TYPE_APIKEY = "APIKEY";
    public static final String ENDPOINT_SECURITY_TYPE_APIKEY_HEADER = ENDPOINT_SECURITY_TYPE_APIKEY + ": #";

    /**
     * Decorator to indicate that an endpoint requires a signature.
     */
    public static final String ENDPOINT_SECURITY_TYPE_SIGNED = "SIGNED";
    public static final String ENDPOINT_SECURITY_TYPE_SIGNED_HEADER = ENDPOINT_SECURITY_TYPE_SIGNED + ": #";

    /**
     * Default receiving window.
     */
    public static final long DEFAULT_RECEIVING_WINDOW = 60_000L;
    public static final String TS_KEY_HEADER = "X-CH-TS";
    public static final String SIGN_KEY_HEADER = "X-CH-SIGN";

    /**
     * Default ToStringStyle used by toString methods. Override this to change the
     * output format of the overridden toString methods. - Example
     * ToStringStyle.JSON_STYLE
     */
    public static ToStringStyle TO_STRING_BUILDER_STYLE = ToStringStyle.SHORT_PREFIX_STYLE;
}
