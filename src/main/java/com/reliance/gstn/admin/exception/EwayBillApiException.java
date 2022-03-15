/**
 * 
 */
package com.reliance.gstn.admin.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * @auth
 *
 */
public class EwayBillApiException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6125976525557460700L;

	private Map<String, Object> excObj = new HashMap<String, Object>();

	public EwayBillApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
			Map<String, Object> excObj) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.excObj = excObj;
	}

	public EwayBillApiException(String message) {
		super(message);
	}

	public Map<String, Object> getExcObj() {
		return excObj;
	}

}
