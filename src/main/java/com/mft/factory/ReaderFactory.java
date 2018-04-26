package com.mft.factory;
import org.springframework.batch.item.ItemReader;

import com.mft.batch.component.ReaderRepo;
import com.mft.model.Student;
public class ReaderFactory<T> {
	
	ReaderRepo reader = new ReaderRepo();
	

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
