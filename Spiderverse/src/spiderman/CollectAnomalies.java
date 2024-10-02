package spiderman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Steps to implement this class main method:
 * 
 * Step 1:
 * DimensionInputFile name is passed through the command line as args[0]
 * Read from the DimensionsInputFile with the format:
 * 1. The first line with three numbers:
 *      i.    a (int): number of dimensions in the graph
 *      ii.   b (int): the initial size of the cluster table prior to rehashing
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
 * Read from the HubInputFile with the format:
 * One integer
 *      i.    The dimensional number of the starting hub (int)
 * 
 * Step 4:
 * CollectedOutputFile name is passed in through the command line as args[3]
 * Output to CollectedOutputFile with the format:
 * 1. e Lines, listing the Name of the anomaly collected with the Spider who
 *    is at the same Dimension (if one exists, space separated) followed by 
 *    the Dimension number for each Dimension in the route (space separated)
 * 
 * @author Seth Kelley
 * 
 * 
 * 
 * 
 * javac -d bin src/spiderman/*.java
 * 
 * java -cp bin spiderman.CollectAnomalies dimension.in spiderverse.in hub.in collected.out
 * 
 * 
 * 
 */




 // get map, hub (int, dimension, linkedlist?)
 // do bfs from hub till we find a dimension with an anomaly in person[] (dimnum != dimsig)
 // booleans hasAnom and hasSpider!
 // get name of Anom (and Spider)
 // keep track of bfs path in ArrayList<Integer>
    // start at source
    // put all unvisited edges into queue 
    // then go to queue, add all unvisited edges into queue (get rid of them after)
    // repeat
    // ^ (are we putting them in PrintingList at that time? or BEGINNING of loop put?! so well unqueue)
    // stop when we get there! have a break point
 // return list and names and stuff
 // FOR ALL print anomName + " " (+ spiderName + " ")
 // for yes anom no spider --> forwards + backwards (ignoring first/last? --> source to destination to source)
 // for yes anom yes spider --> backwards (destination to source)

public class CollectAnomalies {

    private int hubNum;  // hub dimension number given
    private HashMap<Integer, LinkedList<Dimension>> map; // made my collider
    private HashMap<Integer, Integer> fullPath;   // keep track of order of dimensions
    private ArrayList<Integer> anomPath;  // path of specifically one anomaly
    private boolean hasAnom;
    private boolean hasSpider;
    private String outputFile;

    public CollectAnomalies(){
        this.hubNum = 0;
        this.map = new HashMap<Integer, LinkedList<Dimension>>(); 
        this.fullPath = new HashMap<Integer, Integer>();
        this.anomPath = new ArrayList<Integer>();
        this.hasAnom = false;
        this.hasSpider = false;
        this.outputFile = null;
    }

    public void BFS(ArrayList<LinkedList<Dimension>> collider){  

        map = makeHashMap(collider);
        fullPath.put(hubNum, null);  // nothing before it!
        Dimension source = map.get(hubNum).getFirst();

        Queue<Integer> queue = new LinkedList<>();
        ArrayList<Integer> visited = new ArrayList<>(); 

        queue.add(hubNum);      
        visited.add(hubNum);

        while (!queue.isEmpty()){  // while still dimensions to check branches
            Integer curNum = queue.poll();
            Dimension cur = map.get(curNum).getFirst();
            // 
            //anomPath.add(curNum);

            if (hasAnom){
                anomPath.clear();
                hasAnom = false;
                hasSpider = false;
                Integer next = fullPath.get(curNum); // what next anom path will end with for NOW (65)
                Integer now = fullPath.get(next);  // integer before ^ (1024)
                Stack<Integer> temp = new Stack<>();
                while (now != null){
                    temp.push(now);
                    now = fullPath.get(now);
                }
                while (!temp.empty()){
                    anomPath.add(temp.pop());
                }
                anomPath.add(next);
                anomPath.add(curNum);
            }

            //
            // anomPath.add(curNum);
            // visited.add(curNum);
            //

            ArrayList<Person> arr = cur.getPersonArray();

            if (curNum != hubNum){
                for (int i = 0; i < arr.size(); i++){
                    if (arr.get(i).getCurDim() != arr.get(i).getDimSig()){
                        hasAnom = true;
                        String anomName = arr.get(i).getName();
                        String spiderName = null;
                        for (int j = 0; j < arr.size(); j++){
                            if (arr.get(j).getCurDim() == arr.get(j).getDimSig()){
                                hasSpider = true;
                                spiderName = arr.get(j).getName();
                                break;
                            }
                        }
                        Integer next = fullPath.get(curNum); // what next anom path will end with for NOW (65)
                        
                        //
                        anomPath.clear();
                        Integer now = fullPath.get(next);  // integer before ^ (1024)
                        Stack<Integer> temp = new Stack<>();
                        while (now != null){
                            temp.push(now);
                            now = fullPath.get(now);
                        }
                        while (!temp.empty()){
                            anomPath.add(temp.pop());
                        }
                        anomPath.add(next);
                        anomPath.add(curNum);
                        //
                        if (hasAnom && hasSpider){
                            printPathSpider(anomPath, anomName, spiderName);
                        } else if (hasAnom){
                            printPathNoSpider(anomPath, anomName);
                        }
                        break;
                    }
                }
            }


            // Dimension current: map.getOrDefault(curNum, new LinkedList<Dimension>())


            for (Dimension current: map.getOrDefault(curNum, new LinkedList<Dimension>())){  // go through source edges

                int currentNum = current.getDimNum();

                if (!visited.contains(currentNum)){  // if not visited
                    visited.add(currentNum);  // make it visited
                    queue.add(currentNum);  // add to queue

                    fullPath.put(currentNum, curNum);  // get current node and its parent!
                }

            }

            // Dimension help = map.get(curNum).getFirst();

            
            
        }
        
        /* 
        int i = dimensions.keySet().size();  // amount of dimensions
        Integer[] keys = dimensions.keySet().toArray(new Integer[i]); // list of keys of the dimensions

        for (Integer j = 0; j < keys.length; j++){
            Integer key = keys[i];
            ArrayList<Integer> fullPath = new ArrayList<Integer>();

            for (Integer k = key; k != null; k = dimensions.get(k)){
                fullPath.add(0, k);
            }
            anomPath.add(key);

        }
        */


    }


    public void printPathNoSpider(ArrayList<Integer> list, String name1){  // names
        StdOut.print(name1 + " ");
        for (int i = 0; i < list.size(); i++){
            StdOut.print(list.get(i) + " ");
        }
        for (int i = list.size()-2; i >= 0 ; i--){
            StdOut.print(list.get(i) + " ");
        }
        System.out.println(name1);
        System.out.println(list);
        StdOut.println();
    }

    public void printPathSpider(ArrayList<Integer> list, String name1, String name2){
        StdOut.print(name1 + " " + name2 + " ");
        
        for (int i = list.size()-1; i >= 0 ; i--){
            StdOut.print(list.get(i) + " ");
        }

        System.out.println(name1);
        System.out.println(name2);
        System.out.println(list);
        StdOut.println();
    }



    public HashMap<Integer, LinkedList<Dimension>> makeHashMap(ArrayList<LinkedList<Dimension>> collider){
        map = new HashMap<Integer, LinkedList<Dimension>>();

        for (Integer i = 0; i < collider.size(); i++){
            LinkedList<Dimension> cur = collider.get(i);
            map.put(cur.getFirst().getDimNum(), cur);    // instead of i, cur
        }

        //System.out.println(map);

        return map;
    }


    
    public static void main(String[] args) {

        if ( args.length < 4 ) {
            StdOut.println(
                "Execute: java -cp bin spiderman.CollectAnomalies <dimension INput file> <spiderverse INput file> <hub INput file> <collected OUTput file>");
                return;
        }

        //trackSpotObject.makeHashMap(collider);
        //anomObject.map = trackSpotObject.getMap();
        //System.out.println(anomObject.map);

        String inputFileDim = args[0];
        String inputFilePpl = args[1];
        String inputFileHub = args[2];
        String outputFile = args[3];

        //System.out.println(inputFileHub);
        StdOut.setFile(outputFile);

        

        CollectAnomalies anomObject = new CollectAnomalies();
        anomObject.outputFile = outputFile;

        TrackSpot trackSpotObject = new TrackSpot();
        Collider colliderObject = new Collider();
        Clusters clusterObject = new Clusters();
        
        clusterObject.createClust(inputFileDim);

        LinkedList<Dimension>[] arr = clusterObject.getCluster();
        colliderObject.sendPeopleHomeHaha(arr, inputFilePpl);

        clusterObject.wrap();
        colliderObject.createCollider(arr);

        ArrayList<LinkedList<Dimension>> collider = colliderObject.getCollider();
        //HashMap<Integer, LinkedList<Dimension>> map3 = anomObject.makeHashMap(collider);

        // anomObject.map = map3;
        // anomObject.makeHashMap(collider);

        StdIn.setFile(inputFileHub);
        anomObject.hubNum = StdIn.readInt();
        //System.out.println(anomObject.hubNum);

        anomObject.BFS(collider);

        //anomObject.printPathNoSpider(list, outputFile);
        // colliderObject.printCollider();


        
    }
}
