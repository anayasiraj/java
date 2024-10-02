package spiderman;
import java.util.ArrayList;
import java.util.LinkedList;

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
 * 
 * Step 2:
 * ClusterOutputFile name is passed in through the command line as args[1]
 * Output to ClusterOutputFile with the format:
 * 1. n lines, listing all of the dimension numbers connected to 
 *    that dimension in order (space separated)
 *    n is the size of the cluster table.
 * 
 * @author Seth Kelley
 */


 // javac -d bin src/spiderman/*.java
 // java -cp bin spiderman.Clusters dimension.in clusters.out

public class Clusters {

    // instance variables
    private LinkedList<Dimension>[] cluster;

    // default constructor 
    public Clusters(){
        cluster = null;
    }

    // getter
    public LinkedList<Dimension>[] getCluster() {
        // returns the cluster
        return cluster;
    }

    // setter
    public void setCluster(LinkedList<Dimension>[] arr){
        this.cluster = arr;
    }


    // methods

    public void createClust(String inputFile){
        StdIn.setFile(inputFile);  

        // LinkedList<Dimension> list = new LinkedList<Dimension>();

        int numDim = StdIn.readInt();
        int hashSize = StdIn.readInt();
        double cap = StdIn.readDouble();

        //double count = 0.0;  // keep track so we know when to rehash

        //LinkedList<Dimension>[] arr = (LinkedList<Dimension>[]) new LinkedList<?>[hashSize];     //
        //setCluster(arr);

        cluster = new LinkedList[hashSize];   // hashtable set!

        for (int i = 0; i < numDim; i++){
            
            if (i/hashSize >= cap){   // if, starting next addition, resize and rehash cluster!
                hashSize = hashSize*2;
                rehash(hashSize, cluster);
            }

            StdIn.readLine();
            int dimNum = StdIn.readInt();
            int numCanonEvents = StdIn.readInt();
            int dimWeight = StdIn.readInt();
            ArrayList<Person> hehe = new ArrayList<Person>();

            Dimension node = new Dimension(dimNum, numCanonEvents, dimWeight, hehe);
            int hash = hash(node, hashSize);
            if (cluster[hash] == null) cluster[hash] = new LinkedList<Dimension>();
            cluster[hash].addFirst(node);

            
        }
    }

    public int hash(int dimNum, int size){
        return dimNum % size;
    }

    public int hash(Dimension node, int size){
        int dimNum = node.getDimNum();
        return hash(dimNum, size);
    }


    public void rehash(int newSize, LinkedList<Dimension>[] arr){
        LinkedList<Dimension>[] newArr = new LinkedList[newSize];

        //LinkedList<Dimension>[] newArr = (LinkedList<Dimension>[]) new LinkedList<>[newSize];

        int oldSize = cluster.length;

        for (int i = 0; i < oldSize; i++){  // go through cluster n move each thing to newArr

            while (cluster[i].size() != 0){
                Dimension node = cluster[i].remove();  // take out last and put into as first into newArr
                int hash = hash(node, newSize);
                if (newArr[hash] == null) newArr[hash] = new LinkedList<Dimension>();
                newArr[hash].addFirst(node);
            }

        }

        cluster = newArr;

    }


    public void printClusters(String outputFile){
       StdOut.setFile(outputFile);
       //StdOut.print("hello");

       for(int i = 0; i < cluster.length; i++){
            for (int j = 0; j < cluster[i].size(); j++){
                StdOut.print(cluster[i].get(j).getDimNum()+" ");
            }
            if (i<(cluster.length-1)) StdOut.println();
       }

       /*Dimension ptr;
       for (int i = 0; i < cluster.length; i++){
            ptr = cluster[i];
            while (ptr != null){
              StdOut.print(ptr.getDimNum());
                ptr = ptr.getNextNode();
           }
        }*/

    }

    public void wrap (){
        for (int i = 0; i < cluster.length; i++){

            if (i == 0) {  // first 
                Dimension node1 = cluster[cluster.length-1].getFirst();
                cluster[i].addLast(node1);
                Dimension node2 = cluster[cluster.length-2].getFirst();
                cluster[i].addLast(node2);
            }

            else if (i == 1) {  // second
                Dimension node1 = cluster[i-1].getFirst();
                cluster[i].addLast(node1);
                Dimension node2 = cluster[cluster.length-1].getFirst();
                cluster[i].addLast(node2);
            }

            else {
                Dimension node1 = cluster[i-1].getFirst();
                cluster[i].addLast(node1);
                Dimension node2 = cluster[i-2].getFirst();
                cluster[i].addLast(node2);
            }
        }
    }

    public static void main(String[] args) {

        if ( args.length < 2 ) {
            StdOut.println(
                "Execute: java -cp bin spiderman.Clusters <dimension INput file> <collider OUTput file>");
                return;
        }

        String inputFile = args[0];
        String outputFile = args[1];

        StdOut.setFile(outputFile);

        Clusters clusterObject = new Clusters();

        clusterObject.createClust(inputFile);

        clusterObject.wrap();

        clusterObject.printClusters(outputFile);

    }
}
