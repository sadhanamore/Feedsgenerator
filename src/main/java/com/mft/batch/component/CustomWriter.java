package com.mft.batch.component;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mft.model.Marksheet;
import com.mft.service.AzureWriterService;


@Component
public class CustomWriter implements ItemWriter<Marksheet>{
	
	@Autowired
	AzureWriterService writerService;

	@Override
	public void write(List<? extends Marksheet> items) throws Exception {
		
		writerService.writeToBlob(items.toString());
	}
	

}
