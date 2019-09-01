package com.interactions.logwriter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * FileWriter - writes Thread Buffer (List<String>) into the file (synch call) 
 * it creates file if file doesn't exists 
 * 
 * @author velit
 *
 */
public class FileWriter {
	
	public String fileName;
	public Lock lock = new ReentrantLock();
	
	public void writeFile(List<String> s) throws IOException {
		try {
				lock.lock();
				Path path = Paths.get(fileName);
				if(!Files.exists(path)) {
					Files.createFile(path);
				}
				Files.write(path, s, StandardCharsets.UTF_8,  StandardOpenOption.APPEND);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public FileWriter(String fileName) {
		this.fileName = fileName;
		try {
			Path path = Paths.get(fileName);
			if(Files.exists(path)) 
				Files.delete(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
