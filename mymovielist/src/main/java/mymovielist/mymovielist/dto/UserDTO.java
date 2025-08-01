package mymovielist.mymovielist.dto;

/**
 * Data transfer object that contains the fields specific to users
 * This includes their email, password, and name
 * @author Andrew Gee
 */
public class UserDTO {
    private String email; //email of user
    private String password; //pasword of user
    private String name; //name of user

    /**
     * Default constructor to create empty instance of UserDTO
     */
    public UserDTO() {
    }

    /**
     * Parameterized constructor to create a filled instance of UserDTO
     * @param email of the user
     * @param password of the user
     * @param name of the user
     */
    public UserDTO(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    /**
     * Gets the email of user
     * @return the email of user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of user
     * @param email the email of user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the password of user
     * @return the password of user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of user
     * @param password the password of user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the name of user
     * @return the name of user
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of user
     * @param name the name of user
     */
    public void setName(String name) {
        this.name = name;
    }
}
