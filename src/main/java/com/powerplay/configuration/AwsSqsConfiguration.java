package com.powerplay.configuration;

/**
 * <p>
 * Contains AWS SQS Configuration details.
 * </p>
 * <p>
 * They details are accessKey, secretKey, encryptionKey, encryptionId,
 * encryptionRegion, defaultQueueUrl, defaultQueryRegion
 * </p>
 * 
 * <p>
 * defaultQueueUrl and defaultQueryRegion is used if only one queue used in
 * project then no need to add Queue Item. Please configure here only.
 * </p>
 * 
 * @author powerplay
 */
public class AwsSqsConfiguration {

	private String accessKey;

	private String secretKey;

	private String encryptionKey;

	private String encryptionName;

	private String encryptionId;

	private String encryptionRegion;

	private String defaultQueueUrl;

	private String defaultQueryRegion;

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getEncryptionKey() {
		return encryptionKey;
	}

	public void setEncryptionKey(String encryptionKey) {
		this.encryptionKey = encryptionKey;
	}

	public String getEncryptionName() {
		return encryptionName;
	}

	public void setEncryptionName(String encryptionName) {
		this.encryptionName = encryptionName;
	}

	public String getEncryptionId() {
		return encryptionId;
	}

	public void setEncryptionId(String encryptionId) {
		this.encryptionId = encryptionId;
	}

	public String getEncryptionRegion() {
		return encryptionRegion;
	}

	public void setEncryptionRegion(String encryptionRegion) {
		this.encryptionRegion = encryptionRegion;
	}

	public String getDefaultQueueUrl() {
		return defaultQueueUrl;
	}

	public void setDefaultQueueUrl(String defaultQueueUrl) {
		this.defaultQueueUrl = defaultQueueUrl;
	}

	public String getDefaultQueryRegion() {
		return defaultQueryRegion;
	}

	public void setDefaultQueryRegion(String defaultQueryRegion) {
		this.defaultQueryRegion = defaultQueryRegion;
	}
}
