package com.interactions.logwriter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Worker {
	
	public void init(String rootAppPath) {
		String path = null;
		if(rootAppPath == null) path = Constants.PROPERTY_FILE_WRITER;
		else path = rootAppPath;
		PropertyHolder.getInstance(path);
		Counter.getInstance();
		String commitFile = (PropertyHolder.getProperty("log.commit.file")==null)?"commit.log":PropertyHolder.getProperty("log.commit.file");
		FileWriter writer = new FileWriter(commitFile);
		int typeA = 0;
		int typeB = 0;
		long iterations = 0;
		try {
			typeA = Integer.parseInt(PropertyHolder.getProperty("a.writer.thread.pool"));
			typeB = Integer.parseInt(PropertyHolder.getProperty("b.writer.thread.pool"));
			iterations = Long.parseLong(PropertyHolder.getProperty("writer.total.iterations"));
		}catch(Exception e) {
			if(typeA == 0)typeA = 1;
			if(typeB == 0)typeB = 1;
			if(iterations ==0)iterations = 100;
			e.printStackTrace();
		}
		
		ExecutorService typeAExecutor = Executors.newFixedThreadPool(typeA); 
		for(int i=0;i<typeA;i++){
			typeAExecutor.submit(new ThreadWriter(writer,i,"A", typeA, iterations));
		}
		
		ExecutorService typeBExecutor = Executors.newFixedThreadPool(typeB); 
		for(int i=0;i<typeB;i++){
			typeBExecutor.submit(new ThreadWriter(writer,i,"B", typeB, iterations));
		}
		
		typeAExecutor.shutdown();
		typeBExecutor.shutdown();
		try {
		    if (!typeAExecutor.awaitTermination(100000, TimeUnit.MILLISECONDS) &&
		    		typeBExecutor.awaitTermination(100000, TimeUnit.MILLISECONDS)) {
		    	typeAExecutor.shutdownNow();
		    	typeBExecutor.shutdownNow();
		    	PropertyHolder.removeProperties();
		    } 
		} catch (InterruptedException e) {
			typeAExecutor.shutdownNow();
			typeBExecutor.shutdownNow();
			PropertyHolder.removeProperties();
		}
	}

}
