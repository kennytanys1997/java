/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CallCenterSimulation;

import java.util.ArrayList;

/**
 *
 * @author kenny
 */
public class Server {
    //availbility 0 = available, 1 = not available
    int serverNumber = 0;
    int availability = 0;
    int numberOfCalls = 0;
    int callID = 0;
    int time = 0;
    ArrayList processedCalls = new ArrayList();
}
