package com.mtp.config;
import org.springframework.batch.item.ItemReader;

import com.mtp.model.Student;
public class ReaderFactory<T> {
	
	Reader reader = new Reader();
	

	public ItemReader<Student> getReader(String readerType){
	      if(readerType == null){
	         return null;
	      }		
	      else if(readerType.equalsIgnoreCase("FileReader")){
	         
	    	return  reader.getFlatfileReader(); 
	         
	      }
		
	      return null;
	   }
	
	
}
