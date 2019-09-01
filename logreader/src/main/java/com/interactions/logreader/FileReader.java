package com.interactions.logreader;

import java.io.File;
import java.util.Scanner;

public class FileReader {
	
	private String fileName;
	private String cid;

	public FileReader(String fileName, String cid) {
		this.fileName = fileName;
		this.cid = cid;
	}
	
	public void fileRead() {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(fileName));
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				String[] s = line.split(":");
				if(s == null || s.length== 0) throw new Exception("Wrong file format");
				if(s[0].equals(cid)) {
					System.out.println(line);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
        }finally {
			if(scanner != null)	scanner.close();
		}
	}

}
