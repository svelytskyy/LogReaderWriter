package com.interactions.logreader;

public class Worker {
	
	public void init(String rootAppPath) throws InterruptedException {
		String path = null;
		if(rootAppPath == null) path = "reader.properties";
		else path = rootAppPath;
		PropertyHolder.getInstance(path);
		String commitFile = (PropertyHolder.getProperty("log.commit.file")==null)?"commit.log":PropertyHolder.getProperty("log.commit.file");
		System.out.println(commitFile);
		Thread readerA = new Thread(new ThreadReader("A", commitFile));
		Thread readerB = new Thread(new ThreadReader("B", commitFile));
		
		readerA.start();
		readerB.start();
		
		readerA.join();
		readerB.join();
		
	}

}
