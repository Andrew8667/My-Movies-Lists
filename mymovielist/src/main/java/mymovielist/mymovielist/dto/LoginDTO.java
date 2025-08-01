package mymovielist.mymovielist.dto;

/**
 * Contains the credentials needed for a user login
 * @author Andrew Gee
 */
public class LoginDTO {
    private String email; //email of the login
    private String password; //password of the login

    /**
     * Default constructor to create an empty instance of login
     */
    public LoginDTO() {
    }

    /**
     * parameterized constructor to create populate login credentials
     * @param email of the login
     * @param password of the login
     */
    public LoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Gets the email of the login
     * @return the email of the login
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the login
     * @param email the email of the login
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the password of the login
     * @return the password of the login
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the login
     * @param password the password of the login
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
