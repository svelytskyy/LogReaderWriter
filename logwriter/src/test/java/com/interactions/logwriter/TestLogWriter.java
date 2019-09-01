package com.interactions.logwriter;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

//@RunWith(Parameterized.class)
public class TestLogWriter {
	
//	@Parameterized.Parameters
//	    public static Object[][] data() {
//	        return new Object[1][0];
//	}
	
	
	String fileName = null;
	
    @Before
    public void setUp() throws Exception {
//    	Worker worker = new Worker();
//    	worker.init(null);
//    	fileName = PropertyHolder.getProperty("log.commit.file");
    }
    
    @Test
    public void testRule3() throws InterruptedException {
    	Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				
		      	Worker worker = new Worker();
		    	worker.init(null);
		    	fileName = PropertyHolder.getProperty("log.commit.file");	
				
			}
    		
    	});
    	
    	t1.start();
    	t1.join();
    	
    	Thread.sleep(5000);
    	System.out.println("******************** Start check **************************");
    	Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {

				Scanner scanner = null;
				try {
					scanner = new Scanner(new File(fileName));
					Map<String,String> map = new HashMap<String,String>();
					int counter = 0;
					boolean isDuplicate = false;
					while (scanner.hasNext()) {
						String line = scanner.nextLine();
						String[] s = line.split(":");
						String key = s[0]+s[1];
						if(counter != 0) {
							if(map.get(key)  != null) {
								System.out.println("Duplicate : " + key);
								isDuplicate = true;
							}
						}
						map.put(key, "");
						counter++;
					}
					assertEquals(isDuplicate, false);
				}catch (Exception e) {
					e.printStackTrace();
		        }finally {
					if(scanner != null)	scanner.close();
				}

				
				
			}
    		
    	});
    	
    	
    	t2.start();
		
		System.out.println("******************** Finished check **************************");

    }
	
}
