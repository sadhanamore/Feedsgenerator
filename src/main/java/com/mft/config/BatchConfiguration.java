package com.mft.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.mft.batch.component.StudentItemProcessor;
import com.mft.factory.ReaderFactory;
import com.mft.factory.WriterFactory;
import com.mft.model.Marksheet;
import com.mft.model.Student;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	
	
	@Bean
    public ItemReader<Student> reader(){
		System.out.println("Inside reader");
		ReaderFactory<Student> readerFactory = new ReaderFactory<Student>();
		ItemReader<Student> reader = readerFactory.getReader("FileReader");
        
        return reader;
    }
	
	@Bean
    public ItemWriter<Marksheet> writer() {
		System.out.println("Inside writer");
    	WriterFactory<Marksheet> writerFactory = new WriterFactory<Marksheet>();
    	ItemWriter<Marksheet> writer = writerFactory.getWriter("AzureBlobWriter");
    	return writer;
    }
	@Bean
    public ItemProcessor<Student, Marksheet> processor() {
        return new StudentItemProcessor();
    }
	@Bean
    public Job createMarkSheet(JobBuilderFactory jobs, Step step) {
        return jobs.get("createMarkSheet6")
                .flow(step)
                .end()
                .build();
    }
	@Bean
    public Step step(StepBuilderFactory stepBuilderFactory, ItemReader<Student> reader,
            ItemWriter<Marksheet> writer, ItemProcessor<Student, Marksheet> processor) {
        return stepBuilderFactory.get("step")
                .<Student, Marksheet> chunk(2)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    
    }
	@Bean
	public DataSource dataSource(){
		return DataSourceBuilder.create()
				.url("jdbc:hsqldb:mem:mydb")
				.driverClassName("org.hsqldb.jdbcDriver")
				.username("sa")
				.password("")
				.build();
	}

}
