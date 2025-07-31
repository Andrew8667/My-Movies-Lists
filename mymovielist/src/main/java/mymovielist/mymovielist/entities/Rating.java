package mymovielist.mymovielist.entities;

import jakarta.persistence.*;
import mymovielist.mymovielist.keys.RatingKey;

/**
 * Users can leave a rating on a movie
 * Ratings include a rating out of 5 and also a description for their rating
 */
@Entity
public class Rating {
    @Id
    @EmbeddedId
    private RatingKey ratingKey; //contains email and id
    @Column(nullable = false)
    private Double rating; //rating of movie out of 5
    @Column(nullable = false)
    private String description; //description left with rating
    @ManyToOne
    @MapsId("email")
    @JoinColumn(name = "user_email")
    private User user; //reference to the user table
    @ManyToOne
    @MapsId("id")
    @JoinColumn(name = "movie_id")
    private Movie movie; //reference to the movie table

    public Rating(){

    }

    public Rating(RatingKey ratingKey, Double rating, String description, User user, Movie movie) {
        this.ratingKey = ratingKey;
        this.rating = rating;
        this.description = description;
        this.user = user;
        this.movie = movie;
    }

    public RatingKey getRatingKey() {
        return ratingKey;
    }

    public void setRatingKey(RatingKey ratingKey) {
        this.ratingKey = ratingKey;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

}
