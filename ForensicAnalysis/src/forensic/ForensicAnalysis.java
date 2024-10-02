package forensic;

import java.util.ArrayList;

/**
 * This class represents a forensic analysis system that manages DNA data using
 * BSTs.
 * Contains methods to create, read, update, delete, and flag profiles.
 * 
 * @author Kal Pandit
 */
public class ForensicAnalysis {

    private TreeNode treeRoot;            // BST's root
    private String firstUnknownSequence;
    private String secondUnknownSequence;

    public ForensicAnalysis () {
        treeRoot = null;
        firstUnknownSequence = null;
        secondUnknownSequence = null;
    }

    /**
     * Builds a simplified forensic analysis database as a BST and populates unknown sequences.
     * The input file is formatted as follows:
     * 1. one line containing the number of people in the database, say p
     * 2. one line containing first unknown sequence
     * 3. one line containing second unknown sequence
     * 2. for each person (p), this method:
     * - reads the person's name
     * - calls buildSingleProfile to return a single profile.
     * - calls insertPerson on the profile built to insert into BST.
     *      Use the BST insertion algorithm from class to insert.
     * 
     * DO NOT EDIT this method, IMPLEMENT buildSingleProfile and insertPerson.
     * 
     * @param filename the name of the file to read from
     */
    public void buildTree(String filename) {
        // DO NOT EDIT THIS CODE
        StdIn.setFile(filename); // DO NOT remove this line

        // Reads unknown sequences
        String sequence1 = StdIn.readLine();
        firstUnknownSequence = sequence1;
        String sequence2 = StdIn.readLine();
        secondUnknownSequence = sequence2;
        
        int numberOfPeople = Integer.parseInt(StdIn.readLine()); 

        for (int i = 0; i < numberOfPeople; i++) {
            // Reads name, count of STRs
            String fname = StdIn.readString();
            String lname = StdIn.readString();
            String fullName = lname + ", " + fname;
            // Calls buildSingleProfile to create
            Profile profileToAdd = createSingleProfile();
            // Calls insertPerson on that profile: inserts a key-value pair (name, profile)
            insertPerson(fullName, profileToAdd);
        }
    }

    /** 
     * Reads ONE profile from input file and returns a new Profile.
     * Do not add a StdIn.setFile statement, that is done for you in buildTree.
    */
    public Profile createSingleProfile() {

        int s = StdIn.readInt(); // amount of STRs
        
        STR[] STRs = new STR[s]; // array of our STRs

        int i = 0;
        while (i < s){  // s = 4 --> 0, 1, 2, 3

            String name = StdIn.readString();
            int numOcc = StdIn.readInt();
            STR newStr = new STR(name, numOcc);
            STRs[i] = newStr;  // this STR goes in the STRs array
            i++;

        }

        Profile profile = new Profile(STRs);  // the Profile info is the STRs array we made
        
        return profile; 
    }

    /**
     * Inserts a node with a new (key, value) pair into
     * the binary search tree rooted at treeRoot.
     * 
     * Names are the keys, Profiles are the values.
     * USE the compareTo method on keys.
     * 
     * @param newProfile the profile to be inserted
     */
    public void insertPerson(String name, Profile newProfile) {

        TreeNode newTree = new TreeNode(name, newProfile, null, null);

        if (treeRoot == null){   // if first node
            treeRoot = newTree;
            return;
        }

        TreeNode ptr = treeRoot;
        TreeNode prev = null;
        
        while (ptr != null){  // until we hit empty spot
            int comp = name.compareTo(ptr.getName());
            if (comp < 0) { // if our node is less than current node
                prev = ptr;
                ptr = ptr.getLeft();   // go left
            } else {
                prev = ptr;
                ptr = ptr.getRight();  // else go right
            }
        }

        if (name.compareTo(prev.getName()) < 0){  // if we wanna put our node left
            prev.setLeft(newTree);
        } else {
            prev.setRight(newTree);  // if we wanna put our node right
        }

    }

    /**
     * Finds the number of profiles in the BST whose interest status matches
     * isOfInterest.
     *
     * @param isOfInterest the search mode: whether we are searching for unmarked or
     *                     marked profiles. true if yes, false otherwise
     * @return the number of profiles according to the search mode marked
     */
    public int getMatchingProfileCount(boolean isOfInterest) {
        
        Queue<TreeNode> q = new Queue<TreeNode>();
        makeQueue(q, treeRoot);

        int count = 0;

        if (isOfInterest){
            while (!q.isEmpty()){
                TreeNode curNode = q.dequeue();
                if (curNode.getProfile().getMarkedStatus()) count++;
            }
        }
        if (!isOfInterest){
            while (!q.isEmpty()){
                TreeNode curNode = q.dequeue();
                if (!curNode.getProfile().getMarkedStatus()) count++;
            }
        }

        return count; // update this line
    }

    /**
     * Helper method that counts the # of STR occurrences in a sequence.
     * Provided method - DO NOT UPDATE.
     * 
     * @param sequence the sequence to search
     * @param STR      the STR to count occurrences of
     * @return the number of times STR appears in sequence
     */
    private int numberOfOccurrences(String sequence, String STR) {
        
        // DO NOT EDIT THIS CODE
        
        int repeats = 0;
        // STRs can't be greater than a sequence
        if (STR.length() > sequence.length())
            return 0;
        
            // indexOf returns the first index of STR in sequence, -1 if not found
        int lastOccurrence = sequence.indexOf(STR);
        
        while (lastOccurrence != -1) {
            repeats++;
            // Move start index beyond the last found occurrence
            lastOccurrence = sequence.indexOf(STR, lastOccurrence + STR.length());
        }
        return repeats;
    }

    /**
     * Traverses the BST at treeRoot to mark profiles if:
     * - For each STR in profile STRs: at least half of STR occurrences match (round
     * UP)
     * - If occurrences THROUGHOUT DNA (first + second sequence combined) matches
     * occurrences, add a match
     */

     private Queue<TreeNode> makeQueue(Queue<TreeNode> queue, TreeNode node){
        
        if (node != null){
            queue.enqueue(node);
            makeQueue(queue, node.getLeft());
            makeQueue(queue, node.getRight());
        }
        return queue;
    }
    

    public void flagProfilesOfInterest() {

        Queue<TreeNode> q = new Queue<TreeNode>();
        makeQueue(q, treeRoot);

        String sequence = firstUnknownSequence + secondUnknownSequence;
        

        while (!q.isEmpty()) {
            int count = 0;
            TreeNode curNode = q.dequeue();
            STR[] arr = curNode.getProfile().getStrs();
            int min = (arr.length)/2;
            if (arr.length % 2 == 1) min++;

            for (int i = 0; i < arr.length; i++){
                int occ = numberOfOccurrences(sequence, arr[i].getStrString());
                if (arr[i].getOccurrences() == occ){
                    count++;
                }
            }

            if (count >= min){
                curNode.getProfile().setInterestStatus(true);
            }

        }
        
    }

    /**
     * Uses a level-order traversal to populate an array of unmarked Strings representing unmarked people's names.
     * - USE the getMatchingProfileCount method to get the resulting array length.
     * - USE the provided Queue class to investigate a node and enqueue its
     * neighbors.
     * 
     * @return the array of unmarked people
     */
    public String[] getUnmarkedPeople() {

        String[] arr = new String[getMatchingProfileCount(false)];

        Queue<TreeNode> q = new Queue<TreeNode>();
        q.enqueue(treeRoot);
        int i = 0;

        while (!q.isEmpty()) {
            TreeNode cur = q.dequeue();
            if(cur.getLeft() != null){
                q.enqueue(cur.getLeft());
            }
            if(cur.getRight() != null){
                q.enqueue(cur.getRight());
            }
            boolean mark = cur.getProfile().getMarkedStatus();
            if(!mark){
                arr[i] = cur.getName();
                i++;
            }
        }

        return arr; 
    }

    /**
     * Removes a SINGLE node from the BST rooted at treeRoot, given a full name (Last, First)
     * This is similar to the BST delete we have seen in class.
     * 
     * If a profile containing fullName doesn't exist, do nothing.
     * You may assume that all names are distinct.
     * 
     * @param fullName the full name of the person to delete
     */
    public void removePerson(String fullName) {  
        
        TreeNode cur = treeRoot;
        TreeNode prev = null;

        boolean lastLeft = false;
        while(cur != null){
            String nodeName = cur.getName();
            int cmp = fullName.compareTo(nodeName);

            if (cmp < 0){
                prev = cur;
                cur = cur.getLeft();
                lastLeft = true;
            }
            else if (cmp > 0){
                prev = cur;
                cur = cur.getRight();
                lastLeft = false;
            }
            else if (cmp == 0){
                if (cur == treeRoot){
                    if (cur.getRight() == null){ // nothing on right
                        treeRoot = cur.getLeft();
                    }
                    else {

                        TreeNode newNode = inOrder(cur);
                        newNode.setLeft(cur.getLeft());
                        newNode.setRight(cur.getRight());
                        treeRoot = newNode;
                    }
                }
                else if (cur.getLeft() != null && cur.getRight() != null){ // 2 children

                    TreeNode newNode = inOrder(cur);
                    if (lastLeft){
                        prev.setLeft(newNode);
                    } else {
                        prev.setRight(newNode);
                    }
                    newNode.setLeft(cur.getLeft());
                    newNode.setRight(cur.getRight());
                    // cur = newNode?
                    cur.setLeft(null);
                    cur.setRight(null);

                } else if (cur.getLeft() != null){
                    if (lastLeft){
                        prev.setLeft(cur.getLeft());
                    } else {
                        prev.setRight(cur.getLeft());
                    }
                    
                } else if (cur.getRight() != null){
                    if (lastLeft){
                        prev.setLeft(cur.getRight());
                    } else {
                        prev.setRight(cur.getRight());
                    }
                } else if (cur.getLeft() == null && cur.getRight() == null){
                    if (lastLeft){  // cur node is to left of prev
                        prev.setLeft(null);
                    } else { // cur node is to right of prev
                        prev.setRight(null);
                    }
                }

                return;
            }
            
        }
        
    }
    private TreeNode inOrder(TreeNode node){
        TreeNode prev = node;
        TreeNode newNode = null;  
        node = node.getRight();

        if (node.getLeft() == null){
            newNode = node; 
            prev.setRight(node.getRight());      ///////
            return newNode;
        }

        while (node.getLeft() != null){
            prev = node;
            node = node.getLeft();
        }
        newNode = node;  
        prev.setLeft(node.getRight());           ///////
        return newNode; 
    }

   /*private TreeNode inOrder(TreeNode node){ 
        TreeNode prev = node;
        TreeNode newNode = null;  
        node = node.getRight();

        if (node.getLeft() == null){
            newNode = node;   
            prev.setRight(null);   //  
            return newNode;  
        }

        while (node.getLeft() != null){
            prev = node;
            node = node.getLeft();
        }
        newNode = node;   
        prev.setLeft(null);
        return newNode; 

    }*/ 

    /**
     * Clean up the tree by using previously written methods to remove unmarked
     * profiles.
     * Requires the use of getUnmarkedPeople and removePerson.
     */
    public void cleanupTree() {

        String[] arr = getUnmarkedPeople();

        for (int i = 0; i < arr.length; i++){
            removePerson(arr[i]);
        }

    }

    /**
     * Gets the root of the binary search tree.
     *
     * @return The root of the binary search tree.
     */
    public TreeNode getTreeRoot() {
        return treeRoot;
    }

    /**
     * Sets the root of the binary search tree.
     *
     * @param newRoot The new root of the binary search tree.
     */
    public void setTreeRoot(TreeNode newRoot) {
        treeRoot = newRoot;
    }

    /**
     * Gets the first unknown sequence.
     * 
     * @return the first unknown sequence.
     */
    public String getFirstUnknownSequence() {
        return firstUnknownSequence;
    }

    /**
     * Sets the first unknown sequence.
     * 
     * @param newFirst the value to set.
     */
    public void setFirstUnknownSequence(String newFirst) {
        firstUnknownSequence = newFirst;
    }

    /**
     * Gets the second unknown sequence.
     * 
     * @return the second unknown sequence.
     */
    public String getSecondUnknownSequence() {
        return secondUnknownSequence;
    }

    /**
     * Sets the second unknown sequence.
     * 
     * @param newSecond the value to set.
     */
    public void setSecondUnknownSequence(String newSecond) {
        secondUnknownSequence = newSecond;
    }

}
