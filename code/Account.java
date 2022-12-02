/**
 * This class represents the account model of the program, it contains the data field name and password, so that it could be used for registration and login purposes 
 */
public abstract class Account {
    private String name;
    private String password;
    /**
     * No-arg constructor
     */
    public Account(){}

    /**
     * Constructs an account with a name and a password
     * @param name the name of the account
     * @param password the password of the account
     */
    public Account(String name, String password){
        this.name = name;
        this.password = password;
    }

    /**
     * returns the name of the account
     * @return the name of the account
     */
    public String getName(){
        return name;
    }
    /**
     * returns the password of the account
     * @return the password of the account
     */
    public String getPassword(){
        return password;
    }

    /**
     * Prints out the name and the password of the account
     */
    @Override
    public String toString(){
        return "Name: " + name + ", Password: " + password;
    }
}
