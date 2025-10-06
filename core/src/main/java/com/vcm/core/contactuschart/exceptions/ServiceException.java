package com.vcm.core.contactuschart.exceptions;

public class ServiceException extends Exception {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final String errorKey;

    private final String errorMessage;

    public ServiceException(String errorKey, String errorMessage, Throwable cause){
        super(errorMessage,cause);
        this.errorKey = errorKey;
        this.errorMessage= errorMessage;
    }
    public ServiceException(String errorKey, String errorMessage) {
        super(errorMessage);
        this.errorKey = errorKey;
        this.errorMessage = errorMessage;
    }
	/**
	 * @return the errorKey
	 */
	public String getErrorKey() {
		return errorKey;
	}
	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

}
