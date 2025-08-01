package mymovielist.mymovielist.keys;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

/**
 * The rating key is the composite key used in the Rating table
 * Contains email from user table and id from movie table
 * @author Andrew Gee
 */
@Embeddable
public class RatingKey implements Serializable {
    private String email; //references the email of the user table
    private long id; //references the id of the movie table

    /**
     * Default constructor to create empty rating key
     */
    public RatingKey() {
    }

    /**
     * Parameterized constructor that creates populated instance of rating key
     * @param email of user
     * @param id of movie
     */
    public RatingKey(String email, long id) {
        this.email = email;
        this.id = id;
    }

    /**
     * Gets the email of the key
     * @return the email of the key
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the key
     * @param email the email of the key
     */
    public void setEmail(String email) {
        this.email = email;
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
     * Hashes the composite key for quick look up
     * @return the hashed rating key
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.getEmail(),this.getId());
    }

    /**
     * Checks if two rating keys are equal
     * @param obj rating key to check
     * @return true if the keys are the same, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null || obj.getClass() != this.getClass()){
            return false;
        }
        RatingKey ratingKey = (RatingKey) obj;
        return ratingKey.getId() == this.getId() && ratingKey.getEmail() == this.getEmail();
    }
}
