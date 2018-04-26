package com.mft.batch.component;

import org.springframework.batch.item.ItemProcessor;

import com.mft.model.Marksheet;
import com.mft.model.Student;
public class StudentItemProcessor implements ItemProcessor<Student, Marksheet> {
    
	@Override
    public Marksheet process(final Student student) throws Exception {
		System.out.println("Inside Process");
    	int totalMark = student.getSubMarkOne()+student.getSubMarkTwo();
    	System.out.println("student id:"+student.getStdId() +" and Total mark:"+ totalMark);
    	Marksheet marksheet = new Marksheet(student.getStdId(), totalMark);
        return marksheet;
    }

	} 
