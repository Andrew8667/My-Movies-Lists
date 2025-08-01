package mymovielist.mymovielist.entities;

import jakarta.persistence.*;
import mymovielist.mymovielist.keys.RatingKey;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Users can leave a rating on a movie
 * Ratings include a rating out of 5 and also a description for their rating
 * @author Andrew Gee
 */
@Entity
public class Rating {
    @EmbeddedId
    private RatingKey ratingKey; //contains fields email and id
    @Column(nullable = false)
    private Double rating; //rating of movie out of 5
    @Column(nullable = false)
    private String description; //description associated with rating
    @ManyToOne
    @MapsId("email")
    @JoinColumn(name = "user_email")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user; //reference to the user table
    @ManyToOne
    @MapsId("id")
    @JoinColumn(name = "movie_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Movie movie; //reference to the movie table

    /**
     * Default constructor to create empty instance of rating
     */
    public Rating(){

    }

    /**
     * Parameterized constructor to create filled instance of rating
     * @param ratingKey composite key containing email from user and id from movie
     * @param rating of the movie out of 5
     * @param description associated with the rating
     * @param user that owns the rating
     * @param movie being rated
     */
    public Rating(RatingKey ratingKey, Double rating, String description, User user, Movie movie) {
        this.ratingKey = ratingKey;
        this.rating = rating;
        this.description = description;
        this.user = user;
        this.movie = movie;
    }

    /**
     * Gets the rating key
     * @return the rating key
     */
    public RatingKey getRatingKey() {
        return ratingKey;
    }

    /**
     * Sets the rating key
     * @param ratingKey the rating key
     */
    public void setRatingKey(RatingKey ratingKey) {
        this.ratingKey = ratingKey;
    }

    /**
     * Gets the rating for the movie
     * @return the rating for the movie
     */
    public Double getRating() {
        return rating;
    }

    /**
     * Sets the rating for the movie
     * @param rating the rating for the movie
     */
    public void setRating(Double rating) {
        this.rating = rating;
    }

    /**
     * Gets the description associated with the rating
     * @return the description associated with the rating
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description associated with the rating
     * @param description the description associated with the rating
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the user making the rating
     * @return the user making the rating
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user making the rating
     * @param user the user making the rating
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the movie the rating is for
     * @return the movie the rating is for
     */
    public Movie getMovie() {
        return movie;
    }

    /**
     * Sets the movie the rating is for
     * @param movie the movie the rating is for
     */
    public void setMovie(Movie movie) {
        this.movie = movie;
    }

}
