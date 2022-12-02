import java.io.File;
import java.util.ArrayList;

/**
 * A class representing the distribution center, capable of distributing the newly added aids.
 */
public class DistributionCenter{
    private File donorFile;
    private File ngoFile;
    private File distributedFile;

    /**
     * No-arg constructor
     */
    public DistributionCenter(){}

    /**
     * Constructs a DistributionCenter object with the all the files as its data fields, so that it can be used to updating the database.
     * 
     * @param donorFile the file object of the file that contains all the available donations
     * @param ngoFile the file object of the file that contains all the incomplete requests
     * @param distributedFile the file object of the file that contains all the distributed aids
     */
    public DistributionCenter(File donorFile, File ngoFile, File distributedFile){
        this.donorFile = donorFile;
        this.ngoFile = ngoFile;
        this.distributedFile = distributedFile;
    }

    /**
     * Returns the file object of the file that contains all the available donations
     * 
     * @return the file object of the file that contains all the available donations
     */
    public File getDonorFile(){
        return donorFile;
    }

    /**
     * Returns the file object of the file that contains all the incomplete requests
     * 
     * @return the file object of the file that contains all the incomplete requests
     */
    public File getNgoFile(){
        return ngoFile;
    }

    /**
     * Returns the file object of the file that contains all the distributed aids
     * 
     * @return the file object of the file that contains all the distributed aids
     */
    public File getDistributedFile(){
        return distributedFile;
    }

    /**
     * Takes in a newly added aid, goes through the database to see if there are corresponding aids to complete a distribution
     * 
     * @param aidsList the list in which the newly added aid is located at
     * @param aid the newly added aid
     * @param isNGO the aid is an NGO request or donor donation
     */
    public void updateSupplies(ArrayList<Aid> aidsList, Aid aid, boolean isNGO){   
        ArrayList<Aid> distributedList = DataManagement.readAllData(distributedFile,Aid.class);
        String aidName = aid.getName();
        int aidQuantity = aid.getQuantity();
        int searchListSize = getSearchListSize(isNGO);

        for (int i = 0; i < searchListSize; i++){ // looping through the accounts in searchList
            ArrayList<Aid> searchAidsList = getSearchAidsList(isNGO, i);
            boolean updateDistributed = false;

            for(int j = 0; j < searchAidsList.size(); j++){  // looping through aids in the searchlist aidslist
                Aid searchListAid = searchAidsList.get(j);
                int searchListAidQuantity = searchListAid.getQuantity();

                if (searchListAid.getName().equals(aidName)){ 
                    if (searchListAidQuantity >= aidQuantity){
                        searchListAidQuantity -= aidQuantity;
                        if(searchListAidQuantity == 0) // removes aid from aidlist and update the file if aidquantity matches exactly with the searchAidQuantity
                            removeAidFromSearchAidsList(isNGO, i, j);
                        else // else change the quantity of the searchAid and update the file
                            changeSearchListAidQuantity(isNGO, i, j, searchListAidQuantity);
                        
                        searchListAid.setQuantity(searchListAidQuantity);
                        if(isNGO){ // if searchListAid is ngoList
                            distributedList.add(new Aid(searchListAid.getName(), aidQuantity, searchListAid.getDonor(), aid.getNgo(), "Reserved"));
                        }
                        else{ // if searchListAid is donorList
                            distributedList.add(new Aid(searchListAid.getName(), aidQuantity, aid.getDonor(), searchListAid.getNgo(), "Reserved"));
                        }
                        updateDistributed = true; // Update Distributed, aid has been completely processed
                        aidsList.remove(aid); // Aid has been completely removed from aidsList
                        break;
                    }
                    else{
                        aidQuantity -= searchListAidQuantity;
                        aid.setQuantity(aidQuantity);
                        if(isNGO){ // if searchListAid is ngoList
                            distributedList.add(new Aid(searchListAid.getName(), searchListAidQuantity, searchListAid.getDonor(), aid.getNgo(), "Reserved"));
                        }
                        else{ // if searchListAid is donorList
                            distributedList.add(new Aid(searchListAid.getName(), searchListAidQuantity, aid.getDonor(), searchListAid.getNgo(), "Reserved"));
                        }
                        // deletion of Aid object from search json file
                        searchAidsList.remove(searchListAid); // searchListAid has been completely removed from searchAidsList
                        removeAidFromSearchAidsList(isNGO, i, j); 
                        j--;
                    } 
                }
            }
            if (updateDistributed) break;
        }
        // overwrite Distributed.json with Distributed arraylist
        DataManagement.writeAllData(distributedList, distributedFile);
    }
 
    /**
     * Returns the aidslist of the index-th account in SearchList
     * 
     * @param isNGO the aid is an NGO request or donor donation
     * @param index the index of the account
     * @return the aidlist to be searched through for distribution of the newly added aid
     */
    public ArrayList<Aid> getSearchAidsList(boolean isNGO, int index){
        if (isNGO){
            ArrayList<Donor> donorList  = DataManagement.readAllData(donorFile, Donor.class);
            return donorList.get(index).getAidsList();
        }
        else{
            ArrayList<NGO> ngoList = DataManagement.readAllData(ngoFile, NGO.class);
            return ngoList.get(index).getAidsList();
        }
    }

    /**
     * Returns the size of the searchList
     * 
     * @param isNGO the aid is an NGO request or donor donation
     * @return the size of the arraylist that has to be searched through for distribution of the newly added aid
     */
    public int getSearchListSize(boolean isNGO){
        if (isNGO) 
            return DataManagement.readAllData(donorFile, Donor.class).size();
        else
            return DataManagement.readAllData(ngoFile, NGO.class).size();
    }

    /**
     * Changes the quantity of the aidIndex-th Aid in the aidsList of the account-th Account in the SearchList
     * 
     * @param isNGO the aid is an NGO request or donor donation
     * @param account the index of the account
     * @param aidIndex the index of the aid in the account's aidslist
     * @param newQuantity the new quantity of the the aid
     */
    public void changeSearchListAidQuantity(boolean isNGO, int account, int aidIndex, int newQuantity){
        if (isNGO){
            ArrayList<Donor> donorList  = DataManagement.readAllData(donorFile, Donor.class);
            donorList.get(account).getAidsList().get(aidIndex).setQuantity(newQuantity);
            DataManagement.writeAllData(donorList, donorFile);
        }   
        else{
            ArrayList<NGO> ngoList = DataManagement.readAllData(ngoFile, NGO.class);
            ngoList.get(account).getAidsList().get(aidIndex).setQuantity(newQuantity);
            DataManagement.writeAllData(ngoList, ngoFile);
        } 
    }

    /**
     * Removes the aidIndex-th Aid from its aidsList in the SearchList
     *
     * @param isNGO  the aid is an NGO request or donor donation
     * @param account the index of the account
     * @param aidIndex the index of the aid in the account's aidslist
     */
    public void removeAidFromSearchAidsList(boolean isNGO, int account, int aidIndex){
        if (isNGO){
            ArrayList<Donor> donorList  = DataManagement.readAllData(donorFile, Donor.class);
            donorList.get(account).getAidsList().remove(aidIndex);
            DataManagement.writeAllData(donorList, donorFile);
        }
        else{
            ArrayList<NGO> ngoList = DataManagement.readAllData(ngoFile, NGO.class);
            ngoList.get(account).getAidsList().remove(aidIndex);
            DataManagement.writeAllData(ngoList, ngoFile);
        }   
    }
}
