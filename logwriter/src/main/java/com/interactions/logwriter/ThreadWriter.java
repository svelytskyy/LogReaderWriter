package com.interactions.logwriter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadWriter implements Callable<Integer>{
	
	private FileWriter writer;
	private ThreadLocal<List<String>> buffer = new ThreadLocal<List<String>>();
	private volatile boolean running = true;
	private int threadId;
	private String cid;
	private int poolCount;
	private long totalLineCount;
	private ConcurrentHashMap<String,String> map = new ConcurrentHashMap<String,String>();
	
	@Override
	public Integer call() throws Exception {
		List<String> lst = new ArrayList<String>();
		buffer.set(lst);
		if("A".equals(cid))Thread.sleep(threadId*poolCount);
		else Thread.sleep(threadId*2*poolCount);
		while(running) {
			lst = buffer.get();
			long uid = Counter.incrementAndGet();
			//long uid = count.num++;
			running = uid <= totalLineCount;
			LogEntry entry = new LogEntry(cid, uid, "was writen by writer", threadId);
			if(running) {
				String uniqueKey = entry.getCid() + entry.getUid();
				if(map.get(uniqueKey) == null)
					map.put(uniqueKey, "");
				else {
					entry.setUid(new Date().getTime());
				}
				lst.add(entry.toString());
			}
			buffer.set(lst);
			if(lst != null && lst.size() == Integer.parseInt(PropertyHolder.getProperty("writer.buffer.size"))){
				writer.writeFile(buffer.get());
				System.out.println("writing into log :" + buffer.get());
				lst.clear();
			}
			if(!running) System.out.println("Thread " + cid + threadId + " completed" );
		}
		return null;

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
