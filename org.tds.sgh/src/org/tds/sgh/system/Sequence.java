package org.tds.sgh.system;

import java.util.concurrent.atomic.AtomicInteger;

public class Sequence {
    private AtomicInteger atomicInteger;
    private static Sequence obj = null;
    private Sequence(int initialValue){
        this.atomicInteger = new AtomicInteger(initialValue); 
    }
     
    public static Sequence getInstance(){
        if(obj == null){
            obj = new Sequence(0);
        }
        return obj;
    }
 
    public int getCounter() {
        return atomicInteger.getAndIncrement();
    }   
}