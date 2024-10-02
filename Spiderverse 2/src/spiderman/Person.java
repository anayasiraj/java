package spiderman;
import java.util.ArrayList;

public class Person {

    // instance variables
    private int curDim;
    private String name;
    private int dimSig;

    // constructors
    
    public Person(){
        this.curDim = 0;
        this.name = null;
        this.dimSig = 0;
    }
    
    public Person(int curDim, String name, int dimSig){
        this.curDim = curDim;
        this.name = name;
        this.dimSig = dimSig;
    }


    // setters
    public void setCurDim(int curDim){
        this.curDim = curDim;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setDimSig(int dimSig){
        this.dimSig = dimSig;
    }

    // getters
    public int getCurDim(){
        return this.curDim;     // return dimNum?
    }
    public String getName(){
        return this.name;
    }
    public int getDimSig(){
        return this.dimSig;
    }

}
