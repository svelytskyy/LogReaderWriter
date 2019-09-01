package com.interactions.logreader;

public class ThreadReader implements Runnable {

	private String cid;
	private String fileName;
	
	public void run() {
		
		FileReader reader = new FileReader(fileName, cid);
		reader.fileRead();
		
	}

	public ThreadReader(String cid, String fileName) {
		this.cid = cid;
		this.fileName = fileName;
	}
	
	

}
