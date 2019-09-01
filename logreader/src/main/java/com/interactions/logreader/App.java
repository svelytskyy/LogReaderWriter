package com.interactions.logreader;

public class App 
{
    public static void main( String[] args ){
        Worker worker = new Worker();
        try {
	        if(args.length > 0) 
	        	worker.init(args[0]);
	        else {
	        	worker.init(null);
	        }
        }catch(Exception e) {
	    	e.printStackTrace();
	    }
    }
}