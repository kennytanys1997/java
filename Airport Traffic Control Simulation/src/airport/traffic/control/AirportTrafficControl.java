
package airport.traffic.control;
import static java.lang.Thread.sleep;
import java.util.ArrayList;

public class AirportTrafficControl {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        ArrayList<Aircraft> aircrafts = new ArrayList<>();
        ArrayList<Thread> threads = new ArrayList<>();
        Runway runway1 = new Runway("Runway 1");
        Runway runway2 = new Runway("Runway 2");
        Runway runway3 = new Runway("Runway 3");
        int counter = 0;
        
        Thread r1 = new Thread(runway1);
        Thread r2 = new Thread(runway2);
        Thread r3 = new Thread(runway3);
        
        r1.start();
        r2.start();
        r3.start();
        
        while(true){
            //adds new aircraft to arraylist
            aircrafts.add(new Aircraft(runway1, runway2, runway3));
            //adds the new aircraft to a thread arraylist
            threads.add(new Thread(aircrafts.get(counter)));
            //starts the thread of the aircraft
            threads.get(counter).start();
            //iterates through the arraylist
            counter++;
            //waits for 3 seconds before generating a new aircraft
            sleep(3000);
        }
    }
    
}
