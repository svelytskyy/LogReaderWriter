package com.interactions.logwriter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Worker {
	
	public void init(String rootAppPath) {
		long startTime = System.nanoTime();
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
		List<Future<String>> lstA = new ArrayList<Future<String>>();
		for(int i=0;i<typeA;i++){
			Future<String> task = typeAExecutor.submit(new ThreadWriter(writer,i,"A", typeA, iterations));
			lstA.add(task);
		}
		
		ExecutorService typeBExecutor = Executors.newFixedThreadPool(typeB); 
		List<Future<String>> lstB = new ArrayList<Future<String>>();
		for(int i=0;i<typeB;i++){
			Future<String> task = typeBExecutor.submit(new ThreadWriter(writer,i,"B", typeB, iterations));
			lstB.add(task);
		}
		
		typeAExecutor.shutdown();
		typeBExecutor.shutdown();
		
		for(Future<String> task : lstA) {
			try {
				System.out.println("Thread completed : " + task.get());
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for(Future<String> task : lstB) {
			try {
				System.out.println("Thread completed : " + task.get());
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		long endTime   = System.nanoTime();
		System.out.println("**********Total time execution : " + (double)(((double)endTime - (double)startTime)/1000000000.0) + " seconds");
		try {
		    if (!typeAExecutor.awaitTermination(10, TimeUnit.MINUTES)) {
		    	typeAExecutor.shutdownNow();
		    	//PropertyHolder.removeProperties();
		    }
		    if (typeBExecutor.awaitTermination(10, TimeUnit.MINUTES)) {
		    	typeBExecutor.shutdownNow();
		    	//PropertyHolder.removeProperties();
		    }		    
		} catch (InterruptedException e) {
			typeAExecutor.shutdownNow();
			typeBExecutor.shutdownNow();
			PropertyHolder.removeProperties();
		}
	}

}
