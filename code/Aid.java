/**
 * This class represents the aid model, that contains the data fields name, quantity, donor, ngo, and status
 */
public class Aid {
    private String name;
    private int quantity;
    private String donor;
    private String ngo;
    private String status;

    /**
     * No-arg constructor
     */
    public Aid(){}

    /**
     * Constructs an aid object
     * 
     * @param name the name of the aid
     * @param quantity the quantity of the aid
     * @param donor the name of the donor that donated the aid
     * @param ngo the name of the NGO that requested the aid
     * @param status the status of the aid item
     */
    public Aid(String name, int quantity, String donor, String ngo, String status){
        this.name = name;
        this.quantity = quantity;
        this.donor = donor;
        this.ngo = ngo;
        this.status = status;
    }

    /**
     * Returns the name of the aid
     * 
     * @return the name of the aid
     */
    public String getName(){
        return name;
    }

    /**
     * Returns the quantity of the aid 
     * 
     * @return the quantity of the aid
     */
    public int getQuantity(){
        return quantity;
    }

    /**
     * Returns the name of the donor that donated the aid
     * 
     * @return the name of the donor that donated the aid
     */
    public String getDonor(){
        return donor;
    }

    /**
     * Returns the name of the NGO that requested the aid
     * 
     * @return the name of the NGO that requested the aid
     */
    public String getNgo(){
        return ngo;
    }

    /**
     * Returns the status of the aid item
     * 
     * @return the status of the aid item
     */
    public String getStatus(){
        return status;
    }

    /**
     * Changes the quantity of the aid to the argument "quantity"
     * 
     * @param quantity the new quantity of the aid
     */
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    /**
     * Changes the status of the aid 
     * 
     * @param status the new status of the aid
     */
    public void setStatus(String status){
        this.status = status;
    }
    
    /** 
     * Prints out the aid's name, quantity, donor's name, NGO's name and aid's status
     */
    @Override
    public String toString(){
        return "Name: " + name + ", Quantity: " + quantity 
                + ", Donor: " + donor + ", Ngo: " + ngo + ", Status: " + status;
    }
}
