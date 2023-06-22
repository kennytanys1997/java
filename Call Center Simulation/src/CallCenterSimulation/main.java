/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CallCenterSimulation;
import java.text.DecimalFormat;
import java.util.*;

public class main {
    //For End Statistics
    int counter = 0;
    int cases = 0;
    int firstTry = 0;
    int secondTry = 0;
    int thirdTry = 0;
    int numberOfCalls = 0;
    double averageCalls = 0;
    double averageProcess = 0;
    DecimalFormat numberFormat = new DecimalFormat("#.00");
    
    // For Simulation Functions
    Random timeGen = new Random();
    int numberOfServers = 1;
    int callerID = 1;
    int time = timeGen.nextInt(301)+301;
    int secondspassed = 0;
    int numberOfProcessedCalls = 0;
    ArrayList callTimes = new ArrayList();
    List<Caller> callList = new ArrayList<>();
    Timer timer = new Timer();
    List<Server> servers = new ArrayList<>();
    ArrayList callQueue = new ArrayList();
    ArrayList callIDs = new ArrayList();
    ArrayList IDQueue = new ArrayList();
    
    //Timer to simulate functions each second
    TimerTask task = new TimerTask(){
        @Override
    public void run(){
        
        if((secondspassed % 60 == 0)){ 
        callGenerator();
        }
        //Generates calls with instances
        if(secondspassed < time){
            System.out.println("Time: " + secondspassed + "/" + (time-1));
            System.out.println("Queue: " + IDQueue);
            if (callTimes.contains(secondspassed)){
                callList.add(new Caller());
                callList.get(callerID-1).setID(callerID);
                callQueue.add(callerID);
                IDQueue.add(callList.get(callerID-1).genID);
                while(callIDs.contains(callList.get(callerID-1).genID)){
                    callList.get(callerID-1).randomizeGenID();
                }
                callIDs.add(callList.get(callerID-1).genID);
                if(callTimes.contains(secondspassed)){
                System.out.println("Call recieved: ID " + IDQueue.get(IDQueue.size()-1));
                }
                callerID++;
                numberOfCalls++;
                
            }
            
            
            for (int x = 0; x<numberOfServers; x++){
                
            //Assigns calls to servers
            if(servers.get(x).availability == 0 && !callQueue.isEmpty()){
                servers.get(x).availability++;
           
                servers.get(x).callID = (int) callQueue.get(0);
                callList.get((int) callQueue.get(0)-1).noOfEntries++;
                System.out.println("Call ID " + callIDs.get((int)callQueue.get(0)-1) + " Assigned to Server " + servers.get(x).serverNumber);
                callQueue.remove(0);
                IDQueue.remove(0);
            
            }
            }
            
            for (int x = 0; x<numberOfServers; x++){
           
            //Procecssing Calls
            if((servers.get(x).availability == 1)) {
                if(callList.get(servers.get(x).callID-1).timeNeeded<=0){
                    servers.get(x).time=7;
                callList.get(servers.get(x).callID-1).timeNeeded--;
                    
                }
                else{
                System.out.println("Server " + servers.get(x).serverNumber + " Processing Call ID: " + (callList.get(servers.get(x).callID-1).genID) 
                        + " (Maximum Time Left: " + (7-servers.get(x).time) + ") (Time Needed for Call: "
                        + (callList.get(servers.get(x).callID-1).timeNeeded) + ")");
                servers.get(x).time++;
                callList.get(servers.get(x).callID-1).timeNeeded--;
                }
            }
             //Checks for finished calls
            try{
            if(callList.get(servers.get(x).callID-1).timeNeeded <= 0 && callList.get(servers.get(x).callID-1).finished == 0){
                servers.get(x).time = 0;
                servers.get(x).availability = 0;
                numberOfProcessedCalls++;
                servers.get(x).processedCalls.add((callList.get(servers.get(x).callID-1).genID));
                callList.get(servers.get(x).callID-1).finished =1 ;
                System.out.println("Server " + servers.get(x).serverNumber + " Finished processing call: ID " + callList.get(servers.get(x).callID-1).genID);
            }
        
            }catch(ArrayIndexOutOfBoundsException e){}
           
            //Ends Calls if exceed Time Limit
            if(servers.get(x).time == 7){
                servers.get(x).availability = 0;
                servers.get(x).time = 10;
                
            }
            }
            
            for (int x = 0; x < numberOfServers; x++){
                if (servers.get(x).time == 10){
                    servers.get(x).time = 0;
                 if(callList.get(servers.get(x).callID-1).timeNeeded > 0){
                     callQueue.add(servers.get(x).callID);
                     IDQueue.add(callList.get(servers.get(x).callID-1).genID);
                     callList.get(servers.get(x).callID - 1).noOfEntries++;
                     System.out.println("Time left for Call ID " + (callList.get(servers.get(x).callID-1).genID) + ": "
                             + callList.get(servers.get(x).callID-1).timeNeeded );
                 }
                }
            }
               
            secondspassed++;
            System.out.println("_______________________________");
        }
        
        if(secondspassed == (time)){
            while(counter < callerID-1){
                if (callList.get(counter).finished == 1){
                cases = (callList.get(counter).noOfEntries)/2;
                }
                
               switch (cases){
                   case 1: firstTry++;
                            break;
                   
                   case 2: secondTry++;
                            break;
                            
                   case 3: thirdTry++;
                            break;
                   default: break;
               }
               cases = 0;
               counter++;
        }
            
            averageCalls = (double) numberOfCalls/((double)(time-1)/60);
            averageProcess = (double) numberOfProcessedCalls/((double)(time-1)/60);
            
            System.out.println("Total Calls: " + numberOfCalls);
            System.out.println("Average Calls per Minute: " + numberFormat.format(averageCalls));
            System.out.println("Processed Calls: " + numberOfProcessedCalls);
            System.out.println("Average Processed Calls per Minute: " + numberFormat.format(averageProcess));
            System.out.println("Number of Calls Processed on First Attempt: " + firstTry);
            System.out.println("Number of Calls Processed on Second Attempt: " + secondTry);
            System.out.println("Number of Calls Processed on Third Attempt: " + thirdTry);
            for(int x = 0; x<numberOfServers; x++){
                System.out.println("\nServer " + (x+1) + " processed calls [ID]:\n" + servers.get(x).processedCalls + "\nTotal: " + servers.get(x).processedCalls.size());
            }
            System.exit(0);
        }
    } 
    };
  
    // Generates servers based on user's input
    public void generateServers(){
        for (int x = 0; x <numberOfServers; x++){
            servers.add(new Server());
            servers.get(x).serverNumber = x + 1;
        }
    }
    
    //Generates the times that calls will be made within the period of 1 minute
    public void callGenerator(){
        Random callGen = new Random();
        int count = 0;
        callTimes.clear();
        
        while (count < 20){
            int n = callGen.nextInt(61) + secondspassed;
            if (!callTimes.contains(n)){
                count++;
                callTimes.add(n);
            }
        }
        
    }
    
    //Set timer rate
    public void start(){
        timer.scheduleAtFixedRate(task, 1000, 1000);
    }
    
    public static void main(String[] args){
        
        main simulator = new main();
        System.out.println("Enter number of Servers: ");
        Scanner sc = new Scanner(System.in);
        simulator.numberOfServers = sc.nextInt();
        simulator.generateServers();
        simulator.start();
       
        }
    
    
    
 
}
