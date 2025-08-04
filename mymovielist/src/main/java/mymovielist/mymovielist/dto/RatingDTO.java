package mymovielist.mymovielist.dto;

/**
 * Data transfer object for a rating
 * Each rating contains a rating out of 5 stars and a description associated with that rating
 * @author Andrew Gee
 */
public class RatingDTO {
    private Double rating; //rating for movie
    private String description; //description of the rating

    /**
     * Default constructor to create empty instance of class
     */
    public RatingDTO() {
    }

    /**
     * Parameterized constructor to created filled instance of the class
     * @param rating out of 5 stars
     * @param description review of the movie
     */
    public RatingDTO(Double rating, String description) {
        this.rating = rating;
        this.description = description;
    }

    /**
     * Gets the number of stars for the rating
     * @return the number of stars for the rating
     */
    public Double getRating() {
        return rating;
    }

    /**
     * Sets the number of stars for the rating
     * @param rating the number of stars for the rating
     */
    public void setRating(Double rating) {
        this.rating = rating;
    }

    /**
     * Gets the review associated with rating
     * @return the review associated with rating
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the review associated with rating
     * @param description the review associated with rating
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
