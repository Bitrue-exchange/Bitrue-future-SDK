package com.bitrue.futures.sdk.client.exception;

public class BitrueApiException extends RuntimeException {

    private static final long serialVersionUID = 4360108982268949009L;
    public static final String RUNTIME_ERROR = "RuntimeError";
    public static final String INPUT_ERROR = "InputError";
    public static final String KEY_MISSING = "KeyMissing";
    public static final String ENV_ERROR = "EnvironmentError";
    public static final String EXEC_ERROR = "ExecuteError";
    private final String errCode;

    public BitrueApiException(String errType, String errMsg) {
        super(errMsg);
        this.errCode = errType;
    }

    public BitrueApiException(String errType, String errMsg, Throwable e) {
        super(errMsg, e);
        this.errCode = errType;
    }

    public String getErrType() {
        return this.errCode;
    }
}