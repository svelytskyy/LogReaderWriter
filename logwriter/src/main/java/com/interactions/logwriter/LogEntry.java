package com.interactions.logwriter;

/**
 * Data Model of the file Entry - line
 * @author velit
 *
 */
public class LogEntry {
	
	public String cid;
	public Long uid;
	public String data;
	public int threadId;

	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	public int getThreadId() {
		return threadId;
	}
	public void setThreadId(int threadId) {
		this.threadId = threadId;
	}
	public LogEntry(String cid, Long uid, String data, int threadId) {
		super();
		this.cid = cid;
		this.uid = uid;
		this.data = data;
		this.threadId = threadId;
	}
	public LogEntry() {
	}
	
	public String toString() {
		return cid + ": " +  uid+  ": " + data + ": " + cid + threadId;  
	}
	
}
