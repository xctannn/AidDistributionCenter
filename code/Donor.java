import java.util.ArrayList;

/**
 * This class represents the Donor account in the system, in addition to its name and password, the account also contains the data field phonenumber, and aidDonate
 */
public class Donor extends Account{
    private String phoneNumber;
    private ArrayList<Aid> aidDonate = new ArrayList<Aid>();

    /**
     * No arg constructor
     */
    public Donor(){}

    /**
     * 
     * @param name the name of the donor account
     * @param password the password of the donor account     
     * @param phoneNumber the phone number of the donor
     * @param aidDonate the arraylist of donated aids by the donor
     */
    public Donor(String name, String password, String phoneNumber, ArrayList<Aid> aidDonate){
        super(name, password);
        this.phoneNumber = phoneNumber;
        this.aidDonate = aidDonate;
    }

    /**
     * Returns the phone number of the donor
     * @return the phone number of the donor
     */
    public String getphoneNumber(){
        return phoneNumber;
    }

    /**
     * Returns the arraylist of donated aids by the donor
     * @return the arraylist of donated aids by the donor
     */
    public ArrayList<Aid> getAidsList(){
        return aidDonate;
    }

    /**
     * Prints out the donor's name, password, phonenumber and arraylist of donated aids
     */
    @Override
    public String toString(){
        return "Donor Name: " + getName() + ", Password: " + getPassword()
                + ", Phone Number: " + phoneNumber + ", Aids Donated: " + aidDonate.toString();
    }
}
