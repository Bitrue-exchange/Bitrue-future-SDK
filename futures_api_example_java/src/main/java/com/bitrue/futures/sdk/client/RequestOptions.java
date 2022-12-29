package com.bitrue.futures.sdk.client;

import com.bitrue.futures.sdk.client.constant.FuturesApiConstants;
import com.bitrue.futures.sdk.client.exception.BitrueApiException;

import java.net.URL;

/**
 * The configuration for the request APIs
 * @author superatom
 */
public class RequestOptions {

    private String url = FuturesApiConstants.API_BASE_URL;

    public RequestOptions() {
    }

    public RequestOptions(RequestOptions option) {
        this.url = option.url;
    }

    /**
     * Set the URL for request.
     *
     * @param url The URL name like "https://fapi.binance.com".
     */
    public void setUrl(String url) {
        try {
            URL u = new URL(url);
            this.url = u.toString();
        } catch (Exception e) {
            throw new BitrueApiException(BitrueApiException.INPUT_ERROR, "The URI is incorrect: " + e.getMessage());
        }
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
