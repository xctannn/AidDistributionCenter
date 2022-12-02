import java.io.File;
import java.util.ArrayList;

/**
 * A class created to manage account, capable of registering a new NGO/Donor account, login to an existing NGO/Donor account.
 */
public class AccountManagement {
    /**
     * Returns the index of the user account in dataFile, indicating whether the registration success
     * 
     * @param isNGO the account is a NGO account or Donor account
     * @param dataFile the file that contains all the NGO/Donor accounts
     * @param name the name of the account that user wants to register
     * @param password the password of the account that user wants to register
     * @param detail the detail of the account that user wants to register
     * @return the index of the new account created in dataFile
     */
    public int register(boolean isNGO, File dataFile, String name, String password, String detail){
        int userIndex = checkUserIndex(dataFile, isNGO, name, password, false);
        if(userIndex != -1)   // account exists
            return -1;  //registration fails, return -1 
        userIndex = createAccount(name, password, detail, dataFile, isNGO); //acount not exists, create account
        return userIndex;
    } 

    /**
     * Returns the index of the user account in dataFile after creating a new account
     * 
     * @param name the name of the account that user wants to register
     * @param password the password of the account that user wants to register
     * @param detail the detail of the account that user wants to register
     * @param dataFile the file that contains all the NGO/Donor accounts
     * @param isNGO the account is a NGO account or Donor account
     * @return the index of the new account created in dataFile
     */
    public int createAccount(String name, String password, String detail, File dataFile, boolean isNGO){
        if (isNGO){
            ArrayList<NGO> ngoList = DataManagement.readAllData(dataFile, NGO.class);  //create an arraylist which contains all NGO accounts
            int manpower = Integer.parseInt(detail); // convert String to int
            NGO ngoAccount = new NGO(name, password, manpower, new ArrayList<Aid>()); // create a new ngo object
            ngoList.add(ngoAccount);  // add the new created ngo object into arraylist
            DataManagement.writeAllData(ngoList, dataFile); //write the arrraylist into NGOFile
            return ngoList.size()-1;
        }
        else{
            ArrayList<Donor> donorList = DataManagement.readAllData(dataFile, Donor.class);  //create an arraylist which contains all Donor accounts
            Donor DonorAcc = new Donor(name, password, detail, new ArrayList<Aid>()); // create a new donor object
            donorList.add(DonorAcc);    // add the new created donor object into arraylist
            DataManagement.writeAllData(donorList,dataFile);  //write the arrraylist into donorFile
            return donorList.size()-1;
        }
    }

    /**
     * Returns the index of the user account in dataFile for login/register
     * 
     * @param dataFile the file that contains all the NGO/Donor accounts
     * @param isNGO the account is a NGO account or Donor account
     * @param name the name of the account to be checked 
     * @param password the password of the account to be checked
     * @param checkPassword checking password: true when user want to login, false when user want to register
     * @return the index of the account found in dataFile
     */
    public int checkUserIndex(File dataFile, boolean isNGO, String name, String password, boolean checkPassword){
        ArrayList<NGO> ngoList = new ArrayList<>();
        ArrayList<Donor> donorList = new ArrayList<>();
        if (isNGO)
            return NgoExists(name, dataFile, ngoList, checkPassword, password);
        else
            return DonorExists(name, dataFile, donorList, checkPassword, password);
    }

    /**
     * Returns the index of the NGO account in NGO data file, indicating whether the NGO exists
     * 
     * @param user the username of NGO account
     * @param ngoFile the file that contains all the NGO accounts
     * @param ngoList the arraylist of NGO objects
     * @param checkPassword checking password: true when user want to login, false when user want to register
     * @param password the password of the NGO account to be checked
     * @return the index of the NGO account in NGO data file
     */
    private int NgoExists(String user, File ngoFile, ArrayList<NGO> ngoList, boolean checkPassword, String password){
        ngoList = DataManagement.readAllData(ngoFile, NGO.class);
        for (int i = 0; i < ngoList.size(); i++){
            if (user.equals(ngoList.get(i).getName())){
                if (checkPassword){ 
                    if (password.equals(ngoList.get(i).getPassword())) // check if username and password is correct
                        return i;
                }
                else {return i;} // only check if username exists 
            }
        }
        return -1; //return -1 if fail to find username in NGO File
    }

    /**
     * Returns the index of the donor account in donor data file, indicating whether the donor exists
     * 
     * @param user the username of donor account
     * @param donorFile the file that contains all the donor accounts
     * @param donorList the arraylist of donor objects
     * @param checkPassword checking password: true when user want to login, false when user want to register
     * @param password the password of the Donor account to be checked
     * @return the index of the Donor account in Donor data file
     */
    private int DonorExists(String user, File donorFile, ArrayList<Donor> donorList, boolean checkPassword, String password){
        donorList = DataManagement.readAllData(donorFile, Donor.class);
        for (int i = 0; i < donorList.size(); i++){
            if (user.equals(donorList.get(i).getName())){
                if (checkPassword){
                    if (password.equals(donorList.get(i).getPassword())) // check if username and password is correct
                        return i;
                }
                else {return i;} // only check if username exists 
            }
        }
        return -1; //return -1 if fail to find username in donor File
    }
}