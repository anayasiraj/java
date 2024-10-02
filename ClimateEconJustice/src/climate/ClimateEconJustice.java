package climate;

import java.util.ArrayList;

/**
 * This class contains methods which perform various operations on a layered 
 * linked list structure that contains USA communitie's Climate and Economic information.
 * 
 * @author Navya Sharma
 */

public class ClimateEconJustice {

    private StateNode firstState;
    
    /*
    * Constructor
    * 
    * **** DO NOT EDIT *****
    */
    public ClimateEconJustice() {
        firstState = null;
    }

    /*
    * Get method to retrieve instance variable firstState
    * 
    * @return firstState
    * 
    * **** DO NOT EDIT *****
    */ 
    public StateNode getFirstState () {
        // DO NOT EDIT THIS CODE
        return firstState;
    }

    /**
     * Creates 3-layered linked structure consisting of state, county, 
     * and community objects by reading in CSV file provided.
     * 
     * @param inputFile, the file read from the Driver to be used for
     * @return void
     * 
     * **** DO NOT EDIT *****
     */
    public void createLinkedStructure ( String inputFile ) {
        
        // DO NOT EDIT THIS CODE
        StdIn.setFile(inputFile);
        StdIn.readLine(); // Skips header
        
        // Reads the file one line at a time
        while ( StdIn.hasNextLine() ) {
            // Reads a single line from input file
            String line = StdIn.readLine();
            // IMPLEMENT these methods
            addToStateLevel(line);
            addToCountyLevel(line);
            addToCommunityLevel(line);
        }
    }

    /*
    * Adds a state to the first level of the linked structure.
    * Do nothing if the state is already present in the structure.
    * 
    * @param inputLine a line from the input file
    */
    public void addToStateLevel ( String inputLine ) {

        String[] array = inputLine.split(",");
        String state = array[2];

        if (firstState == null){ // if its the first node

            StateNode node = new StateNode(state, null, null);
            firstState = node; 

        } else {

            StateNode curState = firstState;
            boolean found = false;

            while (true) {

                if (curState.name.equals(state)) {
                    found = true;
                    break;
                }

                if (curState.next == null) break;

                curState = curState.next;

            }

            if (found) { return; }

            StateNode node = new StateNode(state, null, null);
            curState.next = node;  

            }

        } 

    /*
    * Adds a county to a state's list of counties.
    * 
    * Access the state's list of counties' using the down pointer from the State class.
    * Do nothing if the county is already present in the structure.
    * 
    * @param inputFile a line from the input file
    */
    public void addToCountyLevel ( String inputLine ) {

        String[] array = inputLine.split(",");
        String county = array[1];
        String state = array[2]; 

        // find state, (if not found, return?) if found, go to node.down
        StateNode curState = firstState;
        boolean foundState = true; // found state must be true for it to work!

        while (true){
            // if the state is the right state, leave
            if (curState.name.equals(state)) break;

            else if (curState.next == null) {
                foundState = false;
                break; // if not found!  MIGHT NOT BE NEEDED!!!!!!
            } 
            curState = curState.next;
        }

        if (foundState == false) { //   MIGHT NOT BE NEEDED!!!!!!
            return; 
        }

        // searching for county, if found, break, else once  null, put it there
        CountyNode curCounty;
        curCounty = curState.down; // if state has no nodes, itll be null
        CountyNode node = new CountyNode(county, null, null);

        if (curCounty == null){ // if theres no counties yet
            curCounty = node;
            curState.down = curCounty;  // IMPORTANT
            return;
        }

        boolean found = false;   
        while (true) {

            if (curCounty.name.equals(county)) {
                found = true;
                break;
            }

            if (curCounty.next == null) break; // last one

            curCounty = curCounty.next;

        }

        if (found) return; // if already present, return and do nothing
        
        curCounty.next = node;

    }

    /*
    * Adds a community to a county's list of communities.
    * 
    * Access the county through its state
    *      - search for the state first, 
    *      - then search for the county.
    * Use the state name and the county name from the inputLine to search.
    * 
    * Access the state's list of counties using the down pointer from the StateNode class.
    * Access the county's list of communities using the down pointer from the CountyNode class.
    * Do nothing if the community is already present in the structure.
    * 
    * @param inputFile a line from the input file
    */
    public void addToCommunityLevel ( String inputLine ) {

        // WRITE YOUR CODE HERE

        // Data object --> make a string with inputLine and use that as node?
        // each one is a community, which can be different races and advantage n whtv

        // find state, if no, return ?
        // go to state.down, find country, if no, return ?
        // go to community down, until null to add the CommunityNode to the end

        String[] array = inputLine.split(",");
        String community = array[0];
        String county = array[1];
        String state = array[2]; 

        StateNode curState = firstState;
        boolean foundState = true; // found state must be true for it to work!
        while (true){
            // if the state is the right state, leave
            if (curState.name.equals(state)) break;

            else if (curState.next == null) {
                foundState = false;
                break; // if not found!  MIGHT NOT BE NEEDED!!!!!!
            } 
            curState = curState.next;
        }

        if (foundState == false) { //   MIGHT NOT BE NEEDED!!!!!!
            return; 
        }

        CountyNode curCounty = curState.down;
        boolean foundCounty = true;
        // there SHOULD BE counties there if there r communities, but IDK!!!
        // if ^ check the null of the first one.
        while (true){
            // if the county is the right county, leave
            if (curCounty.name.equals(county)) break;

            else if (curCounty.next == null) {
                foundCounty = false;
                break; // if not found!  MIGHT NOT BE NEEDED!!!!!!
            } 
            curCounty = curCounty.next;
        }

        if (foundCounty == false) { //   MIGHT NOT BE NEEDED!!!!!!
            return; 
        }


        //MAKE DATA NODE
        Data data = new Data();
        data.setPrcntAfricanAmerican(Double.parseDouble(array[3]));
        data.setPrcntNative(Double.parseDouble(array[4]));
        data.setPrcntAsian(Double.parseDouble(array[5]));
        data.setPrcntWhite(Double.parseDouble(array[8]));
        data.setPrcntHispanic(Double.parseDouble(array[9]));
        data.setAdvantageStatus(array[19]);
        data.setPMlevel(Double.parseDouble(array[49]));
        data.setChanceOfFlood(Double.parseDouble(array[37]));
        data.setPercentPovertyLine(Double.parseDouble(array[121]));

        CommunityNode curComm;
        curComm = curCounty.down; // if state has no nodes, itll be null
        CommunityNode node = new CommunityNode(community, null, data);

        if (curComm == null){ // if theres no counties yet
            curComm = node;
            curCounty.down = curComm;  // IMPORTANT
            return;
        }

        boolean found = false;   
        while (true) {

            if (curComm.name.equals(community)) {
                found = true;
                break;
            }

            if (curComm.next == null) break; // last one

            curComm = curComm.next;

        }

        if (found) return; // if already present, return and do nothing
        
        curComm.next = node;



    }

    /**
     * Given a certain percentage and racial group inputted by user, returns
     * the number of communities that have that said percentage or more of racial group  
     * and are identified as disadvantaged
     * 
     * Percentages should be passed in as integers for this method.
     * 
     * @param userPrcntage the percentage which will be compared with the racial groups
     * @param race the race which will be returned
     * @return the amount of communities that contain the same or higher percentage of the given race
     */
    public int disadvantagedCommunities ( double userPrcntage, String race ) {

        // for this racial demographic
        // if getrace is race
        // if (curcommunity percent >= percent && is getAdvantageStatus (method)) count ++;
        // of getAdvantageStatus method!!!!!
        // MULTIPLY VALUE IN CSV FILE BY 100
        // parameter = 52%, csv value = 0.52 so compare to csv * 100!

        // curState = firstState
        // while curState != null
        // curCounty = curState.down;
        // while curCount != null
        // curCommunity = curCounty.down;
        // if ( NO!(curCommunity.getRace) = race && percent >= percent && disadvantaged)
        // count ++
        // then curCounty = curCounty.next;
        // loop again!
        // once that loops done, curState = curState.next;

        // race.toLowerCase() == "african american"
                        // race.toLowerCase() == "native american"
                        // race.toLowerCase() == "asian american"
                        // race.toLowerCase() == "white american"
                        // race.toLowerCase() == "hispanic american"
    
                        // && curComm.getInfo().getPrcntAfricanAmerican()*100 >= userPrcntage


        int count = 0;
        StateNode curState = firstState;
        CountyNode curCounty;
        CommunityNode curComm;

        while (curState != null){
            curCounty = curState.down;

            while (curCounty != null){
                curComm = curCounty.down;
                while (curComm != null) {

                    if (curComm.getInfo().getAdvantageStatus().equalsIgnoreCase("true")) { // if it IS DISadvantaged
    
                        if (race.equalsIgnoreCase("african american") && curComm.getInfo().getPrcntAfricanAmerican()*100 >= userPrcntage){
                            count++;
                        }
                        else if (race.equalsIgnoreCase("native american")  && curComm.info.getPrcntNative()*100 >= userPrcntage){
                            count++;
                        } 
                        else if (race.equalsIgnoreCase("asian mmerican") && curComm.info.getPrcntAsian()*100 >= userPrcntage){
                            count++;
                        }
                        else if (race.equalsIgnoreCase("white american") && curComm.info.getPrcntWhite()*100 >= userPrcntage){
                            count++;
                        }
                        else if (race.equalsIgnoreCase("hispanic american") && curComm.info.getPrcntHispanic()*100 >= userPrcntage){
                            count++;
                        }
                    }
                    curComm = curComm.next;
                }
                curCounty = curCounty.next;
            }
            curState = curState.next;
        }

        return count; // update this line

    }

    /**
     * Given a certain percentage and racial group inputted by user, returns
     * the number of communities that have that said percentage or more of racial group  
     * and are identified as non disadvantaged
     * 
     * Percentages should be passed in as integers for this method.
     * 
     * @param userPrcntage the percentage which will be compared with the racial groups
     * @param race the race which will be returned
     * @return the amount of communities that contain the same or higher percentage of the given race
     */
    public int nonDisadvantagedCommunities ( double userPrcntage, String race ) {

        int count = 0;
        StateNode curState = firstState;
        CountyNode curCounty;
        CommunityNode curComm;

        while (curState != null){
            curCounty = curState.down;

            while (curCounty != null){
                curComm = curCounty.down;
                while (curComm != null) {

                    if (curComm.getInfo().getAdvantageStatus().equalsIgnoreCase("false")) { // if it IS DISadvantaged
    
                        if (race.equalsIgnoreCase("african american") && curComm.getInfo().getPrcntAfricanAmerican()*100 >= userPrcntage){
                            count++;
                        }
                        else if (race.equalsIgnoreCase("native american")  && curComm.info.getPrcntNative()*100 >= userPrcntage){
                            count++;
                        } 
                        else if (race.equalsIgnoreCase("asian mmerican") && curComm.info.getPrcntAsian()*100 >= userPrcntage){
                            count++;
                        }
                        else if (race.equalsIgnoreCase("white american") && curComm.info.getPrcntWhite()*100 >= userPrcntage){
                            count++;
                        }
                        else if (race.equalsIgnoreCase("hispanic american") && curComm.info.getPrcntHispanic()*100 >= userPrcntage){
                            count++;
                        }
                    }
                    curComm = curComm.next;
                }
                curCounty = curCounty.next;
            }
            curState = curState.next;
        }

        return count;

    }
    
    /** 
     * Returns a list of states that have a PM (particulate matter) level
     * equal to or higher than value inputted by user.
     * 
     * @param PMlevel the level of particulate matter
     * @return the States which have or exceed that level
     */ 
    public ArrayList<StateNode> statesPMLevels ( double PMlevel ) {

        // WRITE YOUR METHOD HERE

        // make arraylist of states, empty for now
        // go through each  community. each one that has PL >= pmlevel, put node in
        // arraylist
        // return arraylist

        ArrayList states = new ArrayList<>();

        StateNode curState = firstState;
        CountyNode curCounty;
        CommunityNode curComm;

        while (curState != null){
            curCounty = curState.down;

            while (curCounty != null){
                curComm = curCounty.down;
                while (curComm != null) {
                    if (curComm.getInfo().getPMlevel() >= PMlevel) { 

                        if (!states.contains(curState)){

                            states.add(curState);

                        }
                        
                    }
                    curComm = curComm.next;
                }
                curCounty = curCounty.next;
            }
            curState = curState.next;
        }

        return states;
	
    }

    /**
     * Given a percentage inputted by user, returns the number of communities 
     * that have a chance equal to or higher than said percentage of
     * experiencing a flood in the next 30 years.
     * 
     * @param userPercntage the percentage of interest/comparison
     * @return the amount of communities at risk of flooding
     */
    public int chanceOfFlood ( double userPercntage ) {

        int count = 0;

        StateNode curState = firstState;
        CountyNode curCounty;
        CommunityNode curComm;

        while (curState != null){
            curCounty = curState.down;

            while (curCounty != null){
                curComm = curCounty.down;
                while (curComm != null) {
                    if (curComm.getInfo().getChanceOfFlood() >= userPercntage) { 
                        count++;
                    }
                    curComm = curComm.next;
                }
                curCounty = curCounty.next;
            }
            curState = curState.next;
        }

        return count;
    }

    /** 
     * Given a state inputted by user, returns the communities with 
     * the 10 lowest incomes within said state.
     * 
     *  @param stateName the State to be analyzed
     *  @return the top 10 lowest income communities in the State, with no particular order
    */
    public ArrayList<CommunityNode> lowestIncomeCommunities ( String stateName ) {

        ArrayList<CommunityNode> communities = new ArrayList<CommunityNode>();
        
        // find state
        StateNode curState = firstState;

        while (curState != null) {
            if (curState.name.equalsIgnoreCase(stateName)){
                break;
            }
            else {
                curState = curState.next;
            }
        }

        CountyNode curCounty = curState.down;
            
        while (curCounty != null) {
            CommunityNode curComm = curCounty.down;

            while (curComm != null) {

                Data data = curComm.getInfo();
                if (communities.size() < 10){
                    communities.add(curComm);
                }
                else {

                    double low = Double.MAX_VALUE; // communities.get(0)
                    CommunityNode high = communities.get(0);

                    int lowIndex = -1;
                    for (int i = 0; i < communities.size(); i++) {
                        Data data2 = communities.get(i).getInfo(); /////////
                        if (data2.getPercentPovertyLine() < low){
                            low = data2.getPercentPovertyLine();
                            lowIndex = i;
                        }
                    }
                    if (data.getPercentPovertyLine() > low) {
                        communities.set(lowIndex, curComm);
                    }
                }

                curComm = curComm.next;

            }

            curCounty = curCounty.next;

        }

        

    // go to community

    // arraylist 10 lowest income  (lowest getPercentPovertyLine)
    // no repeat poverty level
    // 

        return communities; // update this line
    }
}
    
