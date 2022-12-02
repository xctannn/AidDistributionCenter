import java.io.File;
import java.util.ArrayList;

/**
 * A class created for NGO/Donor to perform their tasks in the system which are requesting aids/donating aids, viewing their list of aid
 */
public class UserInterface{
    private File donorFile;
    private File ngoFile;
    private File distributedFile;

    /**
     * No-arg constructor
     */
    public UserInterface(){}

    /**
     * Constructs a UserInterface object with the all the files as its data fields, so that it can be used to updating the database.
     * 
     * @param donorFile the file object of the file that contains all Donor data
     * @param ngoFile the file object of the file that contains all the NGO data
     * @param distributedFile the file object of the file that contains all the distributed aids
     */
    public UserInterface(File donorFile, File ngoFile, File distributedFile){
        this.donorFile = donorFile;
        this.ngoFile = ngoFile;
        this.distributedFile = distributedFile;
    }

    /**
     * For NGO to request new aid and to be added into the database
     * 
     * @param isNGO the aid is an NGO request or donor donation
     * @param userIndex the index of user located in data file
     * @param aidName the name of the requested aid
     * @param aidQuantity the quantity of requested aid 
     */
    public void requestAid(boolean isNGO, int userIndex, String aidName, int aidQuantity){
        ArrayList<NGO> ngoList = DataManagement.readAllData(ngoFile, NGO.class);
        ArrayList<Aid> aidsList = ngoList.get(userIndex).getAidsList(); // get the aids list of the NGO
        String user = ngoList.get(userIndex).getName(); //get the NGO username
        getInputAid(aidsList, user, isNGO, aidName, aidQuantity);
        DataManagement.writeAllData(ngoList, ngoFile);
    }

    /**
     * For Donor to donate new aid and to be added into the database
     * 
     * @param isNGO the aid is an NGO request or donor donation
     * @param userIndex the index of user located in data file
     * @param aidName the name of the donated aid
     * @param aidQuantity the quantity of donated aid 
     */
    public void donateAid(boolean isNGO, int userIndex, String aidName, int aidQuantity){
        ArrayList<Donor> donorList = DataManagement.readAllData(donorFile, Donor.class);
        ArrayList<Aid> aidsList = donorList.get(userIndex).getAidsList();   // get the aids list of the Donor
        String user = donorList.get(userIndex).getName();   //get the Donor username
        getInputAid(aidsList, user, isNGO, aidName, aidQuantity);
        DataManagement.writeAllData(donorList, donorFile);
    }

    /**
     * Create a new requested or donated aid and match in distribution center
     * 
     * @param aidsList the list of aids of NGO/Donor
     * @param username the username of NGO/Donor
     * @param isNGO the aid is an NGO request or donor donation
     * @param aidName the name of the donated aid
     * @param aidQuantity the quantity of donated aid 
     */
    private void getInputAid(ArrayList<Aid> aidsList, String username, boolean isNGO, String aidName, int aidQuantity) {
        DistributionCenter distributionCenter = new DistributionCenter(donorFile, ngoFile, distributedFile);      
        Aid aid = new Aid();
        if (isNGO)
            aid = new Aid(aidName, aidQuantity, "", username, "Available");    
        else 
            aid = new Aid(aidName, aidQuantity, username, "", "Available");           
        aidsList.add(aid);
        distributionCenter.updateSupplies(aidsList, aid, isNGO);    // call distribution center to match the aid
    }

    /**
     * Returns an arraylist of aid of the particular NGO/donor
     * 
     * @param isNGO the task is performed by a NGO or Donor
     * @param userIndex the index of user located in data file
     * @return an arraylist of aid 
     */
    public ArrayList<Aid> listAids(boolean isNGO, int userIndex){
        ArrayList<Aid> distrbutedAids = DataManagement.readAllData(distributedFile, Aid.class);
        if (isNGO){
            ArrayList<NGO> ngoList = DataManagement.readAllData(ngoFile, NGO.class);
            ArrayList<Aid> ngoAids = ngoList.get(userIndex).getAidsList();
            String username = ngoList.get(userIndex).getName();
            for (int i = 0; i < distrbutedAids.size(); i++){
                if (distrbutedAids.get(i).getNgo().equals(username)){   // get the aid requested (which has been distributed) of the particular NGO
                    ngoAids.add(0,distrbutedAids.get(i));
                }
            }
            return ngoAids;
        }
        else{
            ArrayList<Donor> donorList = DataManagement.readAllData(donorFile, Donor.class);
            ArrayList<Aid> donorAids = donorList.get(userIndex).getAidsList();
            String username = donorList.get(userIndex).getName();
            for (int i = 0; i < distrbutedAids.size(); i++){
                if (distrbutedAids.get(i).getDonor().equals(username)){  // get the aid donated (which has been distributed) of the particular Donor
                    donorAids.add(0,distrbutedAids.get(i));
                }
            }
            return donorAids;
        }
    }
}