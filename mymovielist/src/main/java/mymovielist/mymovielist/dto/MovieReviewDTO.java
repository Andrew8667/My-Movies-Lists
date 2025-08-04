package mymovielist.mymovielist.dto;

import java.util.List;

/**
 * Data transfer object that contains info for the movie, its review, and its categories
 */
public class MovieReviewDTO {
    private String title; //title of the movie
    private String img; //poster of the movie
    private Double stars; //rating out of 5 of the movie
    private String review; //review of the movie
    private List<Long> categories; //id of categories to add movie to

    /**
     * Default constructor to create empty instance of the class
     */
    public MovieReviewDTO() {
    }

    /**
     *Parameterized constructor to create filled instance of the class
     * @param title of the movie
     * @param img link to the movie poster image
     * @param stars for the movie out of 5
     * @param review the review of the movie
     * @param categories that the movie is part of for the user
     */
    public MovieReviewDTO(String title, String img, Double stars, String review, List<Long> categories) {
        this.title = title;
        this.img = img;
        this.stars = stars;
        this.review = review;
        this.categories = categories;
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
     * Gets the movie post link
     * @return the movie post link
     */
    public String getImg() {
        return img;
    }

    /**
     * Sets the movie post link
     * @param img the movie post link
     */
    public void setImg(String img) {
        this.img = img;
    }

    /**
     * Gets the star rating for the movie
     * @return the star rating for the movie
     */
    public Double getStars() {
        return stars;
    }

    /**
     * Sets the star rating for the movie
     * @param stars the star rating for the movie
     */
    public void setStars(Double stars) {
        this.stars = stars;
    }

    /**
     * Gets the user's review for the movie
     * @return the user's review for the movie
     */
    public String getReview() {
        return review;
    }

    /**
     * Sets the user's review for the movie
     * @param review the user's review for the movie
     */
    public void setReview(String review) {
        this.review = review;
    }

    /**
     * Gets the user's categories the movie belongs to
     * @return the user's categories the movie belongs to
     */
    public List<Long> getCategories() {
        return categories;
    }

    /**
     * Sets the user's categories the movie belongs to
     * @param categories the user's categories the movie belongs to
     */
    public void setCategories(List<Long> categories) {
        this.categories = categories;
    }
}
