package com.interactions.logwriter;

public class App 
{
    public static void main( String[] args ){
        Worker worker = new Worker();
        if(args.length > 0) 
        	worker.init(args[0]);
        else {
        	worker.init(null);
        }
    }
}
