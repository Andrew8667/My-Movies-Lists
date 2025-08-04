package mymovielist.mymovielist.dto;

import java.util.List;

/**
 * Data transfer object that contains the info for the category, the movies inside the category, and those movies ratings
 * @author Andrew Gee
 */
public class CategoryMovieRatingDTO {
    private long id;//id for category
    private String title; //title of category
    private String description; //description of purpose of category
    private List<MovieDTO> movies; //list of movies for the category

    /**
     * Default constructor to create empty instance of the class
     */
    public CategoryMovieRatingDTO() {
    }

    /**
     * Parameterized constructor to create populated instance of the class
     * @param id identification number of the category
     * @param title of the category
     * @param description of the category
     * @param movies the movies in that category
     */
    public CategoryMovieRatingDTO(long id, String title, String description, List<MovieDTO> movies) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.movies = movies;
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
     * Sets the description of the category
     * @param description the description of the category
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the movies in the category
     * @return the movies in the category
     */
    public List<MovieDTO> getMovies() {
        return movies;
    }

    /**
     * sets the movies in the category
     * @param movies the movies in the category
     */
    public void setMovies(List<MovieDTO> movies) {
        this.movies = movies;
    }
}
