package com.mtp.config;
import org.springframework.batch.item.ItemWriter;
import com.mtp.model.Marksheet;


public class WriterFactory <T>{
Writer writer = new Writer();
	

	public ItemWriter<Marksheet> getWriter(String writerType){
	      if(writerType == null){
	         return null;
	      }		
	      else if(writerType.equalsIgnoreCase("FileWriter")){
	         
	    	return  writer.getFlatfileWriter();
	         
	      }
		
	      return null;
	   }
	
}
