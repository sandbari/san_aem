package com.vcm.core.contactuschart.utils;

public enum ErrorCodeMapping {

    GENESYS_AUTHSERVICE_EXCEPTION(500, "Auth token service failed"),

    CURRENTWAITTIME_SERVICE_EXCEPTION(500, "current wait time service failed"),
    
    WAITTIME_CHART_SERVICE_EXCEPTION(500, "wait time chart service failed"),
    
    OPEN_HOURS_FINDER_SERVICE_EXCEPTION(500, "open hours schedule generator service failed");
    
    private final int errorCode;

    private final String errorMessage;

    private ErrorCodeMapping(int errorCode, String errorMessage) {
	this.errorCode = errorCode;
	this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
	return errorCode;
    }

    public String getErrorMessage() {
	return errorMessage;
    }
}
