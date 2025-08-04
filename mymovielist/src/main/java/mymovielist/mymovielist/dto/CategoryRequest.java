package mymovielist.mymovielist.dto;

/**
 * Data transfer object used to update the title and description of a category
 * @author Andrew gee
 */
public class CategoryRequest {
    private String title; //title of the category
    private String description; //description of the category

    /**
     * Default constructor to create empty instance of the class
     */
    public CategoryRequest() {
    }

    /**
     * parameterized constructor to create filled instance of the class
     * @param title the title of the category
     * @param description of the category
     */
    public CategoryRequest(String title, String description) {
        this.title = title;
        this.description = description;
    }

    /**
     * Gets the title of the category
     * @return the title of the category
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the category
     * @param title the title of the category
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description of the category
     * @return the description of the category
     */
    public String getDescription() {
        return description;
    }

    /**
     * sets the description of the category
     * @param description the description of the category
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
