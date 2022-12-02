import java.util.ArrayList;

/**
 * This class represents the NGO account in the system, in addition to its name and password, the account also contains the data field manpower, and aidRequest
 */
public class NGO extends Account implements Comparable<NGO>{
    private int manpower;
    private ArrayList<Aid> aidRequest = new ArrayList<Aid>();

    /**
     * No arg constructor
     */
    public NGO(){}

    /**
     * Constructs an NGO object with a name, password, manpower, arraylist of aids
     * @param name the name of the NGO account
     * @param password the password of the NGO account
     * @param manpower the manpower of the NGO
     * @param aidRequest the arraylist of requested aids by the NGO
     */
    public NGO(String name, String password,int manpower, ArrayList<Aid> aidRequest){
        super(name, password);
        this.manpower = manpower;
        this.aidRequest = aidRequest;
    }
    
    /**
     * Returns the manpower of the NGO
     * 
     * @return the manpower of the NGO
     */
    public int getManpower(){
        return manpower;
    }

    /**
     * Returns the arraylist of requested aids by the NGO
     * 
     * @return the arraylist of requested aids by the NGO
     */
    public ArrayList<Aid> getAidsList(){
        return aidRequest;
    }

    /**
     * Prints out the NGO's name
     */
    @Override
    public String toString(){
        return getName();
    }

    /**
    * Compares the manpower of an NGO with the manpower of another NGO 
     */
    public int compareTo(NGO ngo){
        return manpower - ngo.getManpower();
    }
}
