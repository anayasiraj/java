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
 * ColliderOutputFile name is passed in through the command line as args[2]
 * Output to ColliderOutputFile with the format:
 * 1. e lines, each with a different dimension number, then listing
 *       all of the dimension numbers connected to that dimension (space separated)
 * 
 * @author Seth Kelley
 * 
 * 
 * 
 * 
 * javac -d bin src/spiderman/*.java
 * 
 * java -cp bin spiderman.Collider dimension.in spiderverse.in collider.out
 * 
 * 
 */

public class Collider {

    // instance
    private ArrayList<LinkedList<Dimension>> collider;

    // constructor
    public Collider(){
        this.collider = new ArrayList<LinkedList<Dimension>>();
    }

    public Collider (ArrayList<LinkedList<Dimension>> arr){
        this.collider = arr;
    }

    // getter
    public ArrayList<LinkedList<Dimension>> getCollider() {
        return collider;
    }

    // setter
    public void setCollider(ArrayList<LinkedList<Dimension>> arr){
        this.collider = arr;
    }


    // methods

    public void createCollider(LinkedList<Dimension>[] cluster){
        collider = new ArrayList<LinkedList<Dimension>>();
        
        for (int i = 0; i < cluster.length; i++){   // for every cluster (index in array)
            
            LinkedList<Dimension> oldList = cluster[i];   // all nodes have to connect to this first

            Dimension root = oldList.getFirst();

            if (root.getHasList() == true){   // if we DO already have an edge-list
                int index = -1;
                for (int j = 0; j < collider.size(); j++){    // find index of the edge-list
                    if (collider.get(j).getFirst() == root){
                        index = j;
                        break;
                    }
                }

                for (int j = 1; j < oldList.size(); j++){  // add rest nodes to root's edge-list (with index found)
                    
                    Dimension cur = oldList.get(j);
                    collider.get(index).addLast(cur);

                }
            }

            else {   // if we dont have an edge-list, make one!
                LinkedList<Dimension> newRow = new LinkedList<Dimension>();
                newRow.addFirst(root);
                collider.add(newRow);
                root.setHasList(true);
                for (int j = 1; j < oldList.size(); j++){  // add rest nodes to root's edge-list
                    Dimension cur = oldList.get(j);
                    newRow.addLast(cur);
                }
            }

            // for each node in that chain, add root to rest nodes' edge-lists
            for (int j = 1; j < oldList.size(); j++){  
                Dimension cur = oldList.get(j);
                if (cur.getHasList() == true){     // IF NODE ALREADY HAS LIST find the list and put it there

                    int index2 = -1;
                    for (int k = 0; k < collider.size(); k++){
                        if (collider.get(k).getFirst() == cur){
                            index2 = k;
                            break;
                        }
                    }

                    collider.get(index2).addLast(root);

                } else {   // IF NOT make an edge list for cur node
                    LinkedList<Dimension> newRow2 = new LinkedList<Dimension>();   
                    newRow2.addFirst(cur);
                    newRow2.addLast(root);
                    collider.add(newRow2);
                    cur.setHasList(true);
                }

        }
        }
        
    }

    public void sendPeopleHomeHaha(LinkedList<Dimension>[] cluster, String file){
        StdIn.setFile(file);
        int numPpl = StdIn.readInt();

        for (int i = 0; i < numPpl; i++){
            StdIn.readLine();
            int curDim = StdIn.readInt();
            StdIn.readChar();
            String name = StdIn.readString();
            int dimSig = StdIn.readInt();
            Person person = new Person(curDim, name, dimSig);

            int index = curDim % cluster.length;   
            LinkedList<Dimension> list = cluster[index];
            
            for (int j = 0; j < list.size(); j++){
                Dimension node = cluster[index].get(j);
                if (node.getDimNum() == curDim) {
                    if (node.getPersonArray().contains(person) != true){ // if person not already in array! JUSTT in case!
                        node.getPersonArray().add(person);
                    } else {
                        break;
                    }
                }
            }

        }
    }


    public void printCollider(String outputFile) {
        StdOut.setFile(outputFile);
        for(int i = 0; i < collider.size(); i++){
            for (int j = 0; j < collider.get(i).size(); j++){
                StdOut.print(collider.get(i).get(j).getDimNum() + " ");
            }
            StdOut.println();
       }
    }


    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println(
                "Execute: java -cp bin spiderman.Collider <dimension INput file> <spiderverse INput file> <collider OUTput file>");
                return;
        }

        String inputFileDim = args[0];
        String inputFilePpl = args[1];
        String outputFile = args[2];

        StdOut.setFile(outputFile);

        Collider colliderObject = new Collider();
        Clusters clusterObject = new Clusters();
        clusterObject.createClust(inputFileDim);

        LinkedList<Dimension>[] arr = clusterObject.getCluster();
        colliderObject.sendPeopleHomeHaha(arr, inputFilePpl);

        clusterObject.wrap();

        colliderObject.createCollider(arr);
        colliderObject.printCollider(outputFile);
        
    }
}