package spiderman;
import java.util.ArrayList;

public class Dimension {

    // instance variables
    private int dimNum;
    private int numCanonEvents;
    private int dimWeight;
    private ArrayList<Person> personArr;
    private boolean hasList;
    private boolean visitStatus;


    // constructors
    
    public Dimension(){
        this.dimNum = 0;
        this.numCanonEvents = 0;
        this.dimWeight = 0;
        this.personArr = null;
        this.hasList = false;
        this.visitStatus = false;
    }
    
    public Dimension(int dimNum, int numCanonEvents, int dimWeight, ArrayList<Person> arr){
        this.dimNum = dimNum;
        this.numCanonEvents = numCanonEvents;
        this.dimWeight = dimWeight;
        this.personArr = arr;
        this.hasList = false;
        this.visitStatus = false;
    }


    // setters
    public void setDimNum(int dimNum){
        this.dimNum = dimNum;
    }
    public void setNumCanonEvents(int numCanonEvents){
        this.numCanonEvents = numCanonEvents;
    }
    public void setDimWeight(int dimWeight){
        this.dimWeight = dimWeight;
    }
    public void setPersonArray(ArrayList<Person> arr){
        this.personArr = arr;
    }
    public void addPerson(Person person){
        personArr.add(person);
    }
    public void setHasList(boolean hasList){
        this.hasList = hasList;
    }
    public void setVisitStatus(boolean visitStatus){
        this.visitStatus = visitStatus;
    }

    // getters
    public int getDimNum(){
        return this.dimNum;     // return dimNum?
    }
    public int getNumCanonEvents(){
        return this.numCanonEvents;
    }
    public int getDimWeight(){
        return this.dimWeight;
    }
    public ArrayList<Person> getPersonArray(){
        return this.personArr;
    }
    public boolean getHasList(){
        return this.hasList;
    }
    public boolean getVisitStatus(){
        return this.visitStatus;
    }

}
