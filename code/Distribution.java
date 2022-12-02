/**
 * This class represents the aids that has been donated, requested, and distributed in our distribution system, to be displayed for users
 */
public class Distribution{
    private String donor;
    private String phone;
    private String aid;
    private String quantity;
    private String ngo;
    private String manpower;
    private String status;

    /**
     * No-arg constructor
     */
    public Distribution() {}

    /**
     * Constructs an distribution object that contains all the non-sensitive information about an aid that has been entered into the distribution system
     *
     * @param donor the name of the donor that donated the aid
     * @param phone the phone number of the donor that donated the aid
     * @param aid the name of the aid
     * @param quantity the quantity of the aid in String data type
     * @param ngo the name of the NGO that requested the aid
     * @param manpower the manpower of the NGO that requested the aid
     * @param status the status of the aid item
     */
    public Distribution(String donor, String phone, String aid, String quantity, String ngo, String manpower, String status) {
        this.donor = donor;
        this.phone = phone;
        this.aid = aid;
        this.quantity = quantity;
        this.ngo = ngo;
        this.manpower = manpower;
        this.status = status;
    }

    /**
     * Returns the name of the donor that donated the aid
     * @return the name of the donor that donated the aid
     */
    public String getDonor() {
        return donor;
    }

    /**
     * Returns the phone number of the donor that donated the aid
     * @return the phone number of the donor that donated the aid
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Returns the name of the aid
     * @return the name of the aid
     */
    public String getAid() {
        return aid;
    }

    /**
     * Returns the quantity of the aid in String data type
     * @return the quantity of the aid in String data type
     */
    public String getQuantity() {
        return quantity;
    }

    /**
     * Returns the name of the NGO that requested the aid
     * @return the name of the NGO that requested the aid
     */
    public String getNgo() {
        return ngo;
    }

    /**
     * Returns the manpower of the NGO that requested the aid
     * @return the manpower of the NGO that requested the aid
     */
    public String getManpower() {
        return manpower;
    }

    /**
     * Returns the status of the aid item
     * @return the status of the aid item
     */
    public String getStatus(){
        return status;
    }

    /**
     * Changes the status of the aid item
     * @param status the new status of the aid item
     */
    public void setStatus(String status){
        this.status = status;
    }
}
