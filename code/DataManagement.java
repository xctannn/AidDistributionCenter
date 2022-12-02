import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * A class to manage all data, capable of writing data into json file and read all data from json file using Jaskson
 */
public class DataManagement {
    /**
     * A generic method to write data into json file
     * 
     * @param dataList the list contains all data to be writed into data file
     * @param dataFile the file that data will be writen in
     */
    public static <T> void writeAllData(T dataList, File dataFile){
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter()); // enable pretty print of json output

        try {
            writer.writeValue(dataFile, dataList); // serializing a Java object into JSON
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error in writing data to file.");
        }
    }

    /**
     * Returns an arraylist that contains all data read from json file
     * 
     * @param dataFile the file which contains data to be read
     * @param elementClass the classtype of the data object read
     */
    public static <T> ArrayList<T> readAllData(File dataFile, Class<T> elementClass){
        ArrayList<T> dataList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        JavaType type = mapper.getTypeFactory().constructCollectionType(ArrayList.class,elementClass);

        try {
            dataList = mapper.readValue(dataFile,type); // converting a JSON String to a Java object
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error in reading data from file.");
        }
        return dataList;
    }
}
