package com.mft.factory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemWriter;

import com.mft.batch.component.CustomWriter;
import com.mft.batch.component.WriterRepo;
import com.mft.model.Marksheet;


public class WriterFactory <T>{

	

	public ItemWriter<Marksheet> getWriter(String writerType) {
		
		ItemWriter<Marksheet> writer = null;
		
		if (StringUtils.isNotBlank(writerType)) {
			switch (writerType) {
			case "FileWriter":
				WriterRepo flateFileWriter = new WriterRepo();
				writer = flateFileWriter.getFlatfileWriter();
				break;

			case "AzureBlobWriter":
				writer = new CustomWriter();
				break;
			}
		}

		return writer;
	}

}
