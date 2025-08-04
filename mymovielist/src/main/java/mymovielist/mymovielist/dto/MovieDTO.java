package mymovielist.mymovielist.dto;

import java.util.List;

/**
 * Data transfer object for movies
 * Used to represent a user's rating for a movie
 * @author Andrew Gee
 */
public class MovieDTO {
    private long id;//id of movie
    private String img; //image for movie
    private String title; //title of movie
    private RatingDTO rating; //rating for that movie

    /**
     * Default constructor to create empty instance of class
     */
    public MovieDTO() {
    }

    /**
     * Parameterized constructor to create filled instance of the class
     * @param id identification number of the movie
     * @param img link to the poster of the movie
     * @param title of the movie
     * @param rating of the movie. Contains stars and review
     */
    public MovieDTO(long id, String img, String title, RatingDTO rating) {
        this.id = id;
        this.img = img;
        this.title = title;
        this.rating = rating;
    }

    /**
     * Gets the identification number of the movie
     * @return the identification number of the movie
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the identification number of the movie
     * @param id the identification number of the movie
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the poster link of the movie
     * @return the poster link of the movie
     */
    public String getImg() {
        return img;
    }

    /**
     * Sets the poster link of the movie
     * @param img the poster link of the movie
     */
    public void setImg(String img) {
        this.img = img;
    }

    /**
     * Gets the title of the movie
     * @return the title of the movie
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the movie
     * @param title the title of the movie
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the rating for the movie
     * @return the rating for the movie
     */
    public RatingDTO getRating() {
        return rating;
    }

    /**
     * Sets the rating for the movie
     * @param rating the rating for the movie
     */
    public void setRating(RatingDTO rating) {
        this.rating = rating;
    }
}
