/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airport.traffic.control;

import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kenny
 */
public class Runway implements Runnable{
    
    private boolean inUse;
    private String name;
    private int counter;
    private Date date = new Date();
    private Timestamp timestamp;
    
    public Runway(String name){
        this.name = name;
    }
    
    public void run(){
        while(true){
            timestamp = new Timestamp(System.currentTimeMillis());
            System.out.println("[" + timestamp + "] " + name + " is available!");
            inUse = false;
            synchronized(this){
                try {
                    //waits for aircraft to notify so it can change the inUse status to be true
                    wait();
                    inUse = true;
                } catch (InterruptedException ex) {
                    Logger.getLogger(Runway.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
    }

    public void useRunway(int time) throws InterruptedException{
        synchronized(this){
            //synchronized so that only one aircraft can access this method at one time
            timestamp = new Timestamp(System.currentTimeMillis());
            System.out.println("[" + timestamp + "] " + name + " is in use!");
            Thread.sleep(time);
            counter++;
            System.out.println("Number of uses for " + name + ": " + counter);
            
        }
    }
    
    public boolean isInUse() {
        return inUse;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
    
    
}
