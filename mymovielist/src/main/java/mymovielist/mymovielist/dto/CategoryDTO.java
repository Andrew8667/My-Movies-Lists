package mymovielist.mymovielist.dto;

/**
 * Data transfer object containing the vital info for category entity
 * @author Andrew Gee
 */
public class CategoryDTO {
    private long id; //identification number of the category
    private String name; //title of the category
    private String description; //description of the category
    private String email; //email of user that owns the category

    /**
     * Default constructor to create empty instance of category
     */
    public CategoryDTO() {
    }

    /**
     * Parameterized constructor to create filled instance of category
     * @param id identification number of the category
     * @param name the name of the category
     * @param description of the category
     * @param email of user who owns the category
     */
    public CategoryDTO(long id, String name, String description, String email) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.email = email;
    }

    /**
     * Gets the identification number of the category
     * @return the identification number of the category
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the identification number of the category
     * @param id the identification number of the category
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the name of the category
     * @return the name of the category
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the category
     * @param name the name of the category
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the description of the category
     * @return the description of the category
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the category
     * @param description the description of the category
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the email of the user who owns the category
     * @return the email of the user who owns the category
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user who owns the category
     * @param email the email of the user who owns the category
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
