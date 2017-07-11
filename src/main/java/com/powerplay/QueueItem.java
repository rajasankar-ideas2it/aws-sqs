package com.powerplay;

import java.io.Serializable;

/**
 * <p>
 * Used to construct payload to Push data into SQS.
 * </p>
 * <p>
 * payload contains SQS message details to push given queueUrl in the created
 * queueRegion. If queueUrl and queueRegion is empty or null then if used
 * defaultQueueUrl and defaultQueueRegion to push SQS message.
 * </p>
 * 
 * @author powerplay
 *
 */
public class QueueItem {

	private Serializable payload;

	private String queueUrl;

	private String queueRegion;
	
	private Boolean encryptNeed = false;

	public Serializable getPayload() {
		return payload;
	}

	public void setPayload(Serializable payload) {
		this.payload = payload;
	}

	public String getQueueUrl() {
		return queueUrl;
	}

	public void setQueueUrl(String queueUrl) {
		this.queueUrl = queueUrl;
	}

	public String getQueueRegion() {
		return queueRegion;
	}

	public void setQueueRegion(String queueRegion) {
		this.queueRegion = queueRegion;
	}

	public Boolean getEncryptNeed() {
		return encryptNeed;
	}

	public void setEncryptNeed(Boolean encryptNeed) {
		this.encryptNeed = encryptNeed;
	}
}
