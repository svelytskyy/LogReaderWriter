package com.interactions.logwriter;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Singleton class for generating counter used for uid in the file Entry
 * @author Sergiy Velytskyy
 * @version 1.0.0
 *
 */
public class Counter {
	
	 private static volatile int count = 0;
	 private final static Lock lock1 = new ReentrantLock();
	 private final static Lock lock2 = new ReentrantLock();
	 
	 private static volatile Counter counter;
	 
	 private Counter() {}
	 
	 public static Counter getInstance() {
		 	if(counter == null) {
		        boolean isAcquired = lock1.tryLock();
		        if(isAcquired) {
		            try {
		            	if(counter == null) counter = new Counter();
		            } finally {
		                lock1.unlock();
		            }
		        }
		 	}
		 	return counter;

	 }
	 
	 public static int incrementAndGet() {
 		lock2.lock();
        try {
              count = count + 1;
        } finally {
              lock2.unlock();
        }
        return count;
    }

}
