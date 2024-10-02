package spiderman;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;

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
 * SpotInputFile name is passed through the command line as args[2]
 * Read from the SpotInputFile with the format:
 * Two integers (line seperated)
 *      i.    Line one: The starting dimension of Spot (int)
 *      ii.   Line two: The dimension Spot wants to go to (int)
 * 
 * Step 4:
 * TrackSpotOutputFile name is passed in through the command line as args[3]
 * Output to TrackSpotOutputFile with the format:
 * 1. One line, listing the dimenstional number of each dimension Spot has visited (space separated)
 * 
 * @author Seth Kelley
 * 
 * 
 * 
 * javac -d bin src/spiderman/*.java
 * 
 * java -cp bin spiderman.TrackSpot dimension.in spiderverse.in spot.in trackspot.out
 * 
 * 
 */

public class TrackSpot {

    private int source;
    private int dest;
    private ArrayList<Dimension> fullPath;
    private ArrayList<LinkedList<Dimension>> collider;
    private HashMap<Integer, LinkedList<Dimension>> map;
    private boolean found;

    public TrackSpot(){
        this.source = 0;
        this.dest = 0;
        this.fullPath = new ArrayList<Dimension>();
        this.collider = null;
        this.map = new HashMap<Integer, LinkedList<Dimension>>();
        this.found = false;
    }

    public HashMap<Integer, LinkedList<Dimension>> getMap(){
        return this.map;
    }


    public void makeHashMap(ArrayList<LinkedList<Dimension>> collider){
        map = new HashMap<Integer, LinkedList<Dimension>>();

        for (Integer i = 0; i < collider.size(); i++){
            LinkedList<Dimension> cur = collider.get(i);
            map.put(cur.getFirst().getDimNum(), cur);    // instead of i, cur
        }

        //System.out.println(map);
    }


    public void DFS () {
        fullPath = new ArrayList<Dimension>();
        LinkedList<Dimension> list = map.get(source);
        DFSrecursive(source, list);
    }

    public void DFSrecursive(Integer root, LinkedList<Dimension> list){
        list.getFirst().setVisitStatus(true);
        //System.out.println(root);
        fullPath.add(list.getFirst());

        for (Integer i = 0; i < list.size(); i++){
            if (root == dest){
                found=true;
                return;
            }
            else if (!list.get(i).getVisitStatus()){
                DFSrecursive(list.get(i).getDimNum(), map.get(list.get(i).getDimNum()));
                if (found) break;
            }
        }

    }


    public void printPath (String outputFile){
        StdOut.setFile(outputFile);

        for (int i = 0; i < fullPath.size(); i++){
            StdOut.print(fullPath.get(i).getDimNum() + " ");
        }
    }


    public static void main(String[] args) {

        if ( args.length < 4 ) {
            StdOut.println(
                "Execute: java -cp bin spiderman.TrackSpot <dimension INput file> <spiderverse INput file> <spot INput file> <trackspot OUTput file>");
                return;
        }

        String inputFileDim = args[0];
        String inputFilePpl = args[1];
        String inputFileSpot = args[2];
        String outputFile = args[3];

        StdOut.setFile(outputFile);

        TrackSpot trackSpotObject = new TrackSpot();
        Collider colliderObject = new Collider();
        Clusters clusterObject = new Clusters();
        
        clusterObject.createClust(inputFileDim);

        LinkedList<Dimension>[] arr = clusterObject.getCluster();
        colliderObject.sendPeopleHomeHaha(arr, inputFilePpl);

        clusterObject.wrap();
        colliderObject.createCollider(arr);
        ArrayList<LinkedList<Dimension>> collider = colliderObject.getCollider();

        

        StdIn.setFile(inputFileSpot);
        StdOut.setFile(outputFile);

        trackSpotObject.source = StdIn.readInt();
        StdIn.readLine();
        trackSpotObject.dest = StdIn.readInt();
        trackSpotObject.collider = collider;
        
        trackSpotObject.makeHashMap(collider);
        trackSpotObject.DFS();
        trackSpotObject.printPath(outputFile);



        
    }
}
