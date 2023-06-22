/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CallCenterSimulation;

import java.util.Random;

/**
 *
 * @author kenny
 */
public class Caller {
    
    Random generator = new Random();
    
    int noOfEntries = 0;
    int id = 0;
    int timeNeeded = generator.nextInt(14)+3;
    int finished = 0;
    int processing = 0;
    int genID = generator.nextInt(9000)+1000;
            
    
    
    public Caller(){
    noOfEntries++;
            }
    
    public void randomizeGenID(){
        genID = generator.nextInt(9000)+1000;
    }

    public void setID(int id) {
        this.id = id;
    }
    
    
}
