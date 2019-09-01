package com.interactions.logwriter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
/**
 *  This is implementation of each Thread
 *  Each Thread has a buffer for writing into the file. 
 *  The number of lines written into the buffer before writing into the commit.log file is configurable
 *  
 * @author Sergiy Velytskyy
 * @version 1.0.0
 *
 */
public class ThreadWriter implements Callable<String>{
	
	//shared FileWriter for each thread
	private FileWriter writer;
	
	//buffer of each Thread.
	private ThreadLocal<List<String>> buffer = new ThreadLocal<List<String>>();
	
	private volatile boolean running = true;
	
	private int threadId;
	//cid - A or B thread
	private String cid;
	
	private int poolCount;
	
	//total line count written into the commit.log file
	private long totalLineCount;
	
	//concurrent map - extra check if uid already exists in each file entry (line)
	//if exists - than overwrite uid with time in millisec
	private ConcurrentHashMap<String,String> map = new ConcurrentHashMap<String,String>();
	
	@Override
	public String call() throws Exception {
		
		//local var buffer for a thread
		List<String> lst = new ArrayList<String>();
		buffer.set(lst);
		//condition for starting threads
		if("A".equals(cid))Thread.sleep(threadId*poolCount);
		else Thread.sleep(threadId*2*poolCount);
		
		while(running) {
			lst = buffer.get();
			//increment counter for uid
			long uid = Counter.incrementAndGet();
			running = uid <= totalLineCount;
			LogEntry entry = new LogEntry(cid, uid, "was writen by writer", threadId);
			if(running) {
				//extra condition and check for uniqueness of uid written into commit.log
				String uniqueKey = entry.getCid() + entry.getUid();
				if(map.get(uniqueKey) == null)
					map.put(uniqueKey, "");
				else {
					//if already exists uid yhan overwrite entry uid with time in millisec
					entry.setUid(new Date().getTime());
				}
				lst.add(entry.toString());
			}
			buffer.set(lst);
			//flush thread buffer - write into the file
			if(lst != null && lst.size() == Integer.parseInt(PropertyHolder.getProperty("writer.buffer.size"))){
				writer.writeFile(buffer.get());
				System.out.println("writing into log :" + buffer.get());
				lst.clear();
			}
			//if(!running) System.out.println("Thread " + cid + threadId + " completed" );
		}
		return cid + threadId;

	}
	
	
	public ThreadWriter(FileWriter writer, int threadId, String cid, int poolCount, long totalLineCount) {
		super();
		this.writer = writer;
		this.threadId = threadId;
		this.cid = cid;
		this.poolCount = poolCount;
		this.totalLineCount = totalLineCount;
	}
	

}
