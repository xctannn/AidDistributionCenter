import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class that is used for extracting out every single aid that has been donated, requested, or distributed
 */
public class DistributionView{
    private File donorFile;
    private File ngoFile;
    private File distributedFile;

    /**
     * No-arg constructor
     */
    public DistributionView(){}

    /**
     * Constructs a distributionView object with all the files as its data fields, so that it can be used to updating the database.
     * 
     * @param donorFile the file object of the file that contains all the available donations
     * @param ngoFile the file object of the file that contains all the incomplete requests
     * @param distributedFile the file object of the file that contains all the distributed aids     
     */
    public DistributionView(File donorFile, File ngoFile, File distributedFile){
        this.donorFile = donorFile;
        this.ngoFile = ngoFile;
        this.distributedFile = distributedFile;
    }

    /**
     * Returns an ArrayList that contains all the aids that has been distributed, and all the remaining available donations and incomplete requests
     * 
     * @return an ArrayList that contains all the aids that has been donated, requested and distributed 
     */
   public ArrayList<Distribution> viewAllAids(){
       ArrayList<Distribution> allAids = new ArrayList<>();
       ArrayList<Distribution> distributions = getDistributions();
       ArrayList<Distribution> donations =  getDonations();
       ArrayList<Distribution> requests =  getRequests();
       
       allAids.addAll(distributions);        
       allAids.addAll(donations);
       allAids.addAll(requests);

       return allAids;
    }

    /**
     * Returns an ArrayList that contains all the aids that has been distribued
     * 
     * @return an ArrayList that contains all the aids that has been distributed
     */
    public ArrayList<Distribution> getDistributions(){
        ArrayList<Distribution> distributionsView = new ArrayList<>();
        ArrayList<Aid> DistributedList = DataManagement.readAllData(distributedFile,Aid.class);
        HashMap<String, String> DonorPhoneMap = buildDonorPhoneNumberMap();
        HashMap<String, String> NGOManpowerMap = buildNGOManpowerMap();
        for(int i = 0; i < DistributedList.size(); i++){
            String donor = DistributedList.get(i).getDonor();
            String phoneNumber = DonorPhoneMap.get(donor);
            String aidName = DistributedList.get(i).getName();
            String aidQuantity = Integer.toString(DistributedList.get(i).getQuantity());
            String ngo = DistributedList.get(i).getNgo();
            String manpower = NGOManpowerMap.get(ngo);
            String status = DistributedList.get(i).getStatus();

            Distribution distribution = new Distribution(donor, phoneNumber, aidName, aidQuantity, ngo, manpower, status);
            distributionsView.add(distribution);
        }
        return distributionsView;
    }

    /**
     * Returns an ArrayList that contains all the donations that has not been distributed to any NGOs
     * 
     * @return an ArrayList that contains all the aids that has been donated
     */
    public ArrayList<Distribution> getDonations(){
        ArrayList<Distribution> donations = new ArrayList<>();
        ArrayList<Donor> DonorList  = DataManagement.readAllData(donorFile, Donor.class);

        for (int i = 0; i < DonorList.size(); i++){ // Loop through every donor in donorFile to get its name and phone number
            String name = DonorList.get(i).getName();
            String phonenumber = DonorList.get(i).getphoneNumber();
            ArrayList<Aid> aidsList = DonorList.get(i).getAidsList();

            for (int j = 0; j < aidsList.size(); j++){ // Loop through every aid in the aidslist of the donor to get its aid name and quantity
                String aidName = aidsList.get(j).getName();
                String aidQuantity = Integer.toString(aidsList.get(j).getQuantity());
                String status = aidsList.get(j).getStatus();
                
                Distribution donation = new Distribution(name, phonenumber, aidName, aidQuantity, null, null, status);
                donations.add(donation);  
            }
        }

        return donations;
    }

    /**
     * Returns an ArrayList that contains all the requests that has not been met by any Donors
     * 
     * @return an ArrayList that contains all the aids that has been requested
     */
    public ArrayList<Distribution> getRequests(){
        ArrayList<Distribution> requests = new ArrayList<>();
        ArrayList<NGO> NGOList = DataManagement.readAllData(ngoFile, NGO.class);

        for (int i = 0; i < NGOList.size(); i++){ // Loop through every NGO in NGOFile to get its name and manpower
            String name = NGOList.get(i).getName();
            String manpower = Integer.toString(NGOList.get(i).getManpower());
            ArrayList<Aid> aidsList = NGOList.get(i).getAidsList();
            
            for (int j = 0; j < aidsList.size(); j++){ // Loop through every aid in the aidslist of the NGO to get its aid name and quantity
                String aidName = aidsList.get(j).getName();
                String aidQuantity = Integer.toString(aidsList.get(j).getQuantity());
                String status = aidsList.get(j).getStatus();

                Distribution request = new Distribution(null, null, aidName, aidQuantity, name, manpower, status);
                requests.add(request);      
            }
        }

        return requests;
    }

    // 
    /**
     * Builds a hash map that connects the key, Donor Name, to its value, Donor Phonenumber
     * 
     * @return a hash map that maps the donor's phone number to its name
     */
    public HashMap<String, String> buildDonorPhoneNumberMap(){
        ArrayList<Donor> DonorList  = DataManagement.readAllData(donorFile, Donor.class);
        HashMap<String, String> DonorPhoneMap = new HashMap<>();

        for (int i = 0; i < DonorList.size(); i++){
            String name = DonorList.get(i).getName();
            String phoneNumber = DonorList.get(i).getphoneNumber();

            DonorPhoneMap.put(name,phoneNumber); // Assign value phoneNumber to key name
        }
        return DonorPhoneMap;
    }


    /**
     * Builds a hash map that connects the key, NGO name, to its value, NGO Manpower
     * 
     * @return a hash map that maps the NGO's manpower to its name
     */
    public HashMap<String, String> buildNGOManpowerMap(){
        ArrayList<NGO> NGOList = DataManagement.readAllData(ngoFile, NGO.class);
        HashMap<String, String> NGOManpowerMap = new HashMap<>();

        for (int i = 0; i < NGOList.size(); i++){
            String name = NGOList.get(i).getName();
            String manpower = Integer.toString(NGOList.get(i).getManpower());

            NGOManpowerMap.put(name, manpower); // Assign value manpower to key name
        }
        return NGOManpowerMap;
    }
}