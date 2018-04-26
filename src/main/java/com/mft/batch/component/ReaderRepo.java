package com.mft.batch.component;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;

import com.mft.model.Student;



public class ReaderRepo {

	
public FlatFileItemReader<Student> getFlatfileReader() {
		
		FlatFileItemReader<Student> reader = new FlatFileItemReader<Student>();
        reader.setResource(new ClassPathResource("student-data.csv"));
        reader.setLineMapper(new DefaultLineMapper<Student>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] {"stdId", "subMarkOne", "subMarkTwo" });
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Student>() {{
                setTargetType(Student.class);
            }});
        }});
		
		return  reader;
	}
}
