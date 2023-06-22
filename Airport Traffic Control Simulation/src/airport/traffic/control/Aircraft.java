/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airport.traffic.control;
import java.sql.Timestamp;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;
/**
 *
 * @author kenny
 */
public class Aircraft implements Runnable{
    
    private Runway runway1;
    private Runway runway2;
    private Runway runway3;
    private Random rand = new Random();
    private Boolean landing = rand.nextBoolean();
    private String name = "N" + rand.nextInt(100000);
    private Boolean runwayFound = false;
    private Runway runway = null;
    private int time;
    private Date date = new Date();
    private Timestamp timestamp;


    
    public Aircraft(Runway runway1, Runway runway2, Runway runway3){
        this.runway1 = runway1;
        this.runway2 = runway2;
        this.runway3 = runway3;
        
        if(landing){
            time = 10000;
        }
        else{
            time = 5000;
        }
        
    }
    
    public void run(){
        //Landing or departing aircraft is generated on initialization 
        if(landing){
            timestamp = new Timestamp(System.currentTimeMillis());
            System.out.println("[" + timestamp + "] " + name + " is waiting for landing...");
            //loop to find runway until a runway is found
            while(!runwayFound){
                //First search if Runway 1 has less uses than Runway 2 and Runway 3. If it's not in use than the runway allocated is Runway 1.
                if((runway1.getCounter()<=runway2.getCounter()&&(runway1.getCounter()<=runway3.getCounter())&&(!runway1.isInUse()))){
                    runwayFound = true;
                    runway = runway1;
                }
                //Else search if runway 2 has less uses than runway 3. If it is true and it is not in use, the runway allocated is runway 2.
                else if((runway2.getCounter()<= runway3.getCounter()) &&(!runway2.isInUse())){
                    runwayFound = true;
                    runway = runway2;
                }
                //Else search if runway 3 is in use.
                else if(!runway3.isInUse()){
                    runwayFound = true;
                    runway = runway3;
                }
                //Else search runway 2 again if it is in use.
                else if(!runway2.isInUse()){
                    runwayFound = true;
                    runway = runway2;
                }
                //Else search runway 1 again if it is in use.
                else if(!runway1.isInUse()){
                    runwayFound = true;
                    runway = runway1;
                }
            }
            
            //Runway is selected from the previous block of code and assigned to the runway variable. This runway variable is then synchronized
            synchronized(runway){
                runway.notify();
                timestamp = new Timestamp(System.currentTimeMillis());
                System.out.println("[" + timestamp + "] " + name + " is landing on " + runway.getName() + "!");
                try {
                    runway.useRunway(time);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Aircraft.class.getName()).log(Level.SEVERE, null, ex);
                }
                timestamp = new Timestamp(System.currentTimeMillis());
                System.out.println("[" + timestamp + "] " + name + " has succesfully landed!");
                
            }
        }
        
        //This block of code is for departing aircraft
        else{
            timestamp = new Timestamp(System.currentTimeMillis());
            System.out.println("[" + timestamp + "] " + name + " is waiting for departure...");
            //loop to find runway until a runway is found
            while(!runwayFound){
                //First search if Runway 1 has less uses than Runway 2 and Runway 3. If it's not in use than the runway allocated is Runway 1.
                if((runway1.getCounter()<=runway2.getCounter()&&(runway1.getCounter()<=runway3.getCounter())&&(!runway1.isInUse()))){
                    runwayFound = true;
                    runway = runway1;
                }
                //Else search if runway 2 has less uses than runway 3. If it is true and it is not in use, the runway allocated is runway 2.
                else if((runway2.getCounter()<= runway3.getCounter()) &&(!runway2.isInUse())){
                    runwayFound = true;
                    runway = runway2;
                }
                //Else search if runway 3 is in use.
                else if(!runway3.isInUse()){
                    runwayFound = true;
                    runway = runway3;
                }
                //Else search runway 2 again if it is in use.
                else if(!runway2.isInUse()){
                    runwayFound = true;
                    runway = runway2;
                }
                //Else search runway 1 again if it is in use.
                else if(!runway1.isInUse()){
                    runwayFound = true;
                    runway = runway1;
                }
            }
            
            synchronized(runway){
                if(!runway1.isInUse() && !runway2.isInUse() && !runway3.isInUse()){
                    time = 8000;
                }
                runway.notify();
                timestamp = new Timestamp(System.currentTimeMillis());
                System.out.println("[" + timestamp + "] " + name + " is departing on " + runway.getName() + "!");
                try {
                    runway.useRunway(time);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Aircraft.class.getName()).log(Level.SEVERE, null, ex);
                }
                timestamp = new Timestamp(System.currentTimeMillis());
                System.out.println("[" + timestamp + "] " + name + " has succesfully departed!");
            }
        
        }
        
    }
}
