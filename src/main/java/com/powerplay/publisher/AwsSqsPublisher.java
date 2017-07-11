package com.powerplay.publisher;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kms.AWSKMSClient;
import com.amazonaws.services.kms.model.EncryptRequest;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.powerplay.Publisher;
import com.powerplay.QueueItem;
import com.powerplay.configuration.AwsSqsConfiguration;
import com.powerplay.exception.PublishException;

public class AwsSqsPublisher implements Publisher<QueueItem> {

	private final AmazonSQS sqs;

	private final AWSCredentials credentials;

	private final AwsSqsConfiguration config;

	public AwsSqsPublisher(final AwsSqsConfiguration config) {
		super();
		this.config = config;
		credentials = new BasicAWSCredentials(config.getAccessKey(), config.getSecretKey());
		sqs = new AmazonSQSClient(credentials);
	}

	/**
	 * <p>
	 * Used to publish the payload as message in SQS.
	 * </p>
	 */
	public UUID publish(QueueItem item) throws PublishException {
		UUID uuid = null;
		try {
			setQueueRegion(item);
			setQueueURL(item);
			// Check encryption need or not. Default is encryption not needed.
			// If encryption is need then encrpt item Payload message and form
			// payload with uuid.
			// Otherwise without encrypt payload message and form payload wih
			// uuid
			final String payload = item.getEncryptNeed() ? formPayload(encrypt(item))
					: formPayload(item.getPayload().toString());
			final SendMessageResult messageResult = sqs
					.sendMessage(new SendMessageRequest(item.getQueueUrl(), payload));
			uuid = UUID.fromString(messageResult.getMessageId());
		} catch (final Exception e) {
			throw new PublishException(String.format("Failed to publish item.url: %s, region: %s, payload:%s.",
					item.getQueueUrl(), item.getQueueRegion(), item.getPayload()), e);
		}
		return uuid;
	}

	private String formPayload(String encrypt) {
		final ObjectMapper mapper = new ObjectMapper();
		final ObjectNode payloadJson = mapper.createObjectNode();
		payloadJson.put("uuid", UUID.randomUUID().toString());
		payloadJson.put("data", encrypt);
		return payloadJson.toString();
	}

	/**
	 * <p>
	 * Used to encrypt a given Message using AWS KMS key
	 * </p>
	 *
	 * @param payload
	 *            - contains payload data for ciphered using AWS KMS
	 * @return encrypted data using AWSKMS encryption technique
	 */
	private String encrypt(final QueueItem item) {
		final AWSKMSClient kms = new AWSKMSClient(credentials);
		final ByteBuffer bufferData = ByteBuffer.wrap(item.getPayload().toString().getBytes());

		// AWS KMS encryption key configured in property file
		final EncryptRequest request = new EncryptRequest().withKeyId(config.getEncryptionId())
				.withPlaintext(bufferData);

		// Region has been configured by property file
		kms.setRegion(Region.getRegion(Regions.fromName(config.getEncryptionRegion())));
		final ByteBuffer encryptedData = kms.encrypt(request).getCiphertextBlob();

		// Return encrypted text
		return new String(Base64.getEncoder().encodeToString(encryptedData.array()));
	}

	/**
	 * <p>
	 * Used to set Queue Region in SQS.
	 * </p>
	 * <p>
	 * If {@link QueueItem} contains queue region then that region is set in
	 * SQS. Otherwise default Queue region is set.
	 * </p>
	 * 
	 * @param item
	 *            - {@link QueueItem} entity used to set Default Queue Region
	 */
	private void setQueueRegion(QueueItem item) {
		if (!isNullOrEmpty(item.getQueueRegion())) {
			sqs.setRegion(Region.getRegion(Regions.fromName(item.getQueueRegion())));
		} else {
			sqs.setRegion(Region.getRegion(Regions.fromName(config.getDefaultQueryRegion())));
		}
	}

	/**
	 * <p>
	 * Used to set Queue URL if {@link QueueItem} does not contain queueUrl it
	 * set default Queue URL.
	 * </p>
	 * 
	 * @param item
	 *            - Used to set defaultQueueUrl as queueUrl in {@link QueueItem}
	 *            if queueUrl is empty.
	 */
	private void setQueueURL(QueueItem item) {
		if (isNullOrEmpty(item.getQueueUrl())) {
			item.setQueueUrl(config.getDefaultQueueUrl());
		}
	}

	/**
	 * @param str
	 *            The string
	 * @return {@code true} if the argument string is null or
	 *         {@link java.lang.String#isEmpty() empty}
	 */
	private static boolean isNullOrEmpty(final String str) {
		return (str == null || str.isEmpty());
	}
}
