package com.mft.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mft.core.InjectableLogger;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudAppendBlob;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

@Service
public class AzureWriterService {

	@Value("${blob.container.name}")
	private String containerName;

	@Autowired
	BlobStorageService blobStorageService;
	CloudBlobContainer container;
	
	@InjectableLogger
	Logger logger;
	
	@PostConstruct
	public void initialize() {
		container = blobStorageService.getCloudBlobContainerReference(containerName);
	}

	public boolean uploadFileToBlobStorage(String containerName, File file, String fileName, String messageContent) {
		try {
			
			CloudBlockBlob blob = container.getBlockBlobReference(fileName);
			blob.upload(new FileInputStream(file), file.length());
			logger.info("File: {} is uploaded to Blob", fileName );

		} catch (StorageException | IOException | URISyntaxException e) {
			logger.info("Error occured while uploading the file to blob", e );
			return false;
		}

		return true;

	}
	
	public boolean appendToFileInBlobStorage(String containerName, File file, String fileName, String messageContent) {
		try {
			
			CloudAppendBlob blob = container.getAppendBlobReference(fileName);
			
			if(!blob.exists()) {
				blob.createOrReplace();
			}
			blob.appendText(messageContent);
			logger.info("Content appended to File : {} in Blob", fileName );

		} catch (StorageException | IOException | URISyntaxException e) {
			e.printStackTrace();
			return false;
		}

		return true;

	}

	public File createTempLocalFile(String tempFileNamePrefix, String tempFileNameSuffix, byte[] bytesToWrite)
			throws IOException {

		File tempFile = File.createTempFile(tempFileNamePrefix, tempFileNameSuffix);
		try (FileOutputStream tempFileOutputStream = new FileOutputStream(tempFile)) {
			tempFileOutputStream.write(bytesToWrite);

			if (tempFile != null) {
				tempFile.deleteOnExit();
			}
		}

		return tempFile;
	}

	public boolean writeToBlob(String content) throws IOException {

		byte[] bytesToWrite = content.getBytes();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmss");
		String fileName = "BatchWriterFile.csv";
		return appendToFileInBlobStorage(containerName, createTempLocalFile("batchWriterFile", ".temp", bytesToWrite),
				fileName, content);

	}
}
