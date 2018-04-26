package com.mtp.config;

import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.core.io.ClassPathResource;

import com.mtp.model.Marksheet;


public class Writer {

	
	public FlatFileItemWriter<Marksheet> getFlatfileWriter() {
	FlatFileItemWriter<Marksheet> writer = new FlatFileItemWriter<Marksheet>();
	writer.setResource(new ClassPathResource("student-marks.csv"));
	DelimitedLineAggregator<Marksheet> delLineAgg = new DelimitedLineAggregator<Marksheet>();
	delLineAgg.setDelimiter(",");
	BeanWrapperFieldExtractor<Marksheet> fieldExtractor = new BeanWrapperFieldExtractor<Marksheet>();
	fieldExtractor.setNames(new String[] {"stdId", "totalSubMark"});
	delLineAgg.setFieldExtractor(fieldExtractor);
	writer.setLineAggregator(delLineAgg);
    return writer;
	}
}
