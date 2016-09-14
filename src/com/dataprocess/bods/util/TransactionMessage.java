package com.dataprocess.bods.util;

import java.io.Serializable;

public class TransactionMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5137275800815343389L;
	private String transactionMessage;
	private String reason;
	
	public TransactionMessage(String transactionMessage, String reason) {
		this.transactionMessage = transactionMessage;
		this.reason = reason;
	}
	
	public String getTransactionMessage() {
		return transactionMessage;
	}
	
	public void setTransactionMessage(String transactionMessage) {
		this.transactionMessage = transactionMessage;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
