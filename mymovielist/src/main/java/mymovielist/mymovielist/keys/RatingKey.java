package mymovielist.mymovielist.keys;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

/**
 * Key used in the rating table
 * email references email in user table and id references id in movie table
 */
@Embeddable
public class RatingKey implements Serializable {
    private String email; //email of the user
    private long id; //identification number of the movie

    public RatingKey() {
    }

    public RatingKey(String email, long id) {
        this.email = email;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getEmail(),this.getId());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null && obj.getClass() != this.getClass()){
            return false;
        }
        RatingKey ratingKey = (RatingKey) obj;
        return ratingKey.getId() == this.getId() && ratingKey.getEmail() == this.getEmail();
    }
}
