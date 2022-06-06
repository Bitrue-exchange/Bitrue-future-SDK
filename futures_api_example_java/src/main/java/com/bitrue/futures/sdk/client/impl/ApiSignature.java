package com.bitrue.futures.sdk.client.impl;

import com.bitrue.futures.sdk.client.FuturesApiConstants;
import com.bitrue.futures.sdk.client.exception.BitrueApiException;
import com.bitrue.futures.sdk.client.utils.UrlParamsBuilder;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

class ApiSignature {

//    static final String op = "op";
//    static final String opValue = "auth";
    private static final String signatureMethodValue = "HmacSHA256";
    public static final String signatureVersionValue = "2";

    String createSignature(String ts, String path, String accessKey, String secretKey, UrlParamsBuilder builder) {

        if (accessKey == null || "".equals(accessKey) || secretKey == null || "".equals(secretKey)) {
            throw new BitrueApiException(BitrueApiException.KEY_MISSING, "API key and secret key are required");
        }

//        if(builder.getMethod().equals("GET")){
//            builder.putToUrl("recvWindow", Long.toString(FuturesApiConstants.DEFAULT_RECEIVING_WINDOW));
//        }
//        else if(builder.getMethod().equals("POST")){
//            builder.putToPost("recvWindow",Long.toString(FuturesApiConstants.DEFAULT_RECEIVING_WINDOW));
//        }

        Mac hmacSha256;
        try {
            hmacSha256 = Mac.getInstance(signatureMethodValue);
            SecretKeySpec secKey = new SecretKeySpec(secretKey.getBytes(), signatureMethodValue);
            hmacSha256.init(secKey);
        } catch (NoSuchAlgorithmException e) {
            throw new BitrueApiException(BitrueApiException.RUNTIME_ERROR,
                    "[Signature] No such algorithm: " + e.getMessage());
        } catch (InvalidKeyException e) {
            throw new BitrueApiException(BitrueApiException.RUNTIME_ERROR,
                    "[Signature] Invalid key: " + e.getMessage());
        }
        String payload = builder.buildSignature(ts, path);

        String actualSign = new String(Hex.encodeHex(hmacSha256.doFinal(payload.getBytes())));
        System.out.println(payload + "|" + secretKey + "|" + actualSign);
        return actualSign;

    }

}
