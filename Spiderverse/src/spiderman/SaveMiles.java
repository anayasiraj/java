package spiderman;

/**
 * Steps to implement this class main method:
 * 
 * Step 1:
 * DimensionInputFile name is passed through the command line as args[0]
 * Read from the DimensionsInputFile with the format:
 * 1. The first line with three numbers:
 *      i.    a (int): number of dimensions in the graph
 *      ii.   b (int): the initial size of the cluster table size prior to rehashing
 *      iii.  c (double): the capacity(threshold) used to rehash the cluster table 
 * 2. a lines, each with:
 *      i.    The dimension number (int)
 *      ii.   The number of canon events for the dimension (int)
 *      iii.  The dimension weight (int)
 * 
 * Step 2:
 * SpiderverseInputFile name is passed through the command line as args[1]
 * Read from the SpiderverseInputFile with the format:
 * 1. d (int): number of people in the file
 * 2. d lines, each with:
 *      i.    The dimension they are currently at (int)
 *      ii.   The name of the person (String)
 *      iii.  The dimensional signature of the person (int)
 * 
 * Step 3:
 * HubInputFile name is passed through the command line as args[2]
 * Read from the SpotInputFile with the format:
 * One integer
 *      i.    The dimensional number of the starting hub (int)
 * 
 * Step 4:
 * AnomaliesInputFile name is passed through the command line as args[3]
 * Read from the AnomaliesInputFile with the format:
 * 1. e (int): number of anomalies in the file
 * 2. e lines, each with:
 *      i.   The Name of the anomaly which will go from the hub dimension to their home dimension (String)
 *      ii.  The time allotted to return the anomaly home before a canon event is missed (int)
 * 
 * Step 5:
 * MeetupInputFile name is passed through the command line as args[4]
 * Read from the MeetupInputFile with the format:
 * 1. Same line:
 *      f (int): number of Spiders in the file
 *      g (int): number of people being gathered
 *      h (int): time given for them to arrive
 *      j (int): the Dimension they will gather at
 * 2. f lines, each with:
 *      i. The name of the Spider (String)
 * 
 * Step 6:
 * RescueOutputFile name is passed in through the command line as args[5]
 * Output to RescueOutputFile with the format:
 * 1. One Line, TRUE or FALSE
 * 
 * @author Seth Kelley
 * 
 * 
 * java -cp bin spiderman.GoHomeMachine dimension.in spiderverse.in hub.in anomalies.in meetup.in rescue.out 
 */

public class SaveMiles {
    
    public static void main(String[] args) {

        if ( args.length < 6 ) {
            StdOut.println(
                "Execute: java -cp bin spiderman.SaveMiles <dimension INput file> <spiderverse INput file> <hub INput file> <anomalies INput file> <meetup INput file> <rescue OUTput file>");
                return;
        }

        // WRITE YOUR CODE HERE
        
    }
}
