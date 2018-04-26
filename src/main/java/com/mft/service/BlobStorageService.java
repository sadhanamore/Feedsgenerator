package com.mft.service;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.file.CloudFileClient;
import com.microsoft.azure.storage.file.CloudFileShare;

@Service
public class BlobStorageService {


	@Value("${blob.container.name}")
	private String containerName;

	@Value("${blob.account.name}")
	private String accountName;
	
	
	@Value("${blob.account.key}")
	private String accountKey;

	public CloudBlobContainer getCloudBlobContainerReference(String containerName) {
		System.out.println("getCloudBlobContainerReference--1");

		final String storageConnectionString = "DefaultEndpointsProtocol=https;" + "AccountName=" + accountName + ";"
				+ "AccountKey=" + accountKey;
		CloudBlobContainer container = null;

		try {

			CloudStorageAccount account = CloudStorageAccount.parse(storageConnectionString);
			CloudBlobClient blobClient = account.createCloudBlobClient();
			container = blobClient.getContainerReference(containerName);

		} catch (InvalidKeyException | URISyntaxException | StorageException e) {
			e.printStackTrace();
		}
		return container;
	}
}
