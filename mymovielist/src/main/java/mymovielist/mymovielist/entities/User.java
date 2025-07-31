package mymovielist.mymovielist.entities;

import jakarta.persistence.*;

import java.util.List;

/**
 * Represents a user of the app
 * Contains essential info such as their email, password, and name
 * @author Andrew Gee
 */
@Entity
public class User {
    @Id
    private String email; //email associated with the user
    @Column(nullable = false)
    private String password; //password associated with the user
    @Column(nullable = false)
    private String name; //name associated with the user
    @OneToMany(mappedBy = "user")
    private List<Category> categories; //reference to Category table
    @OneToMany(mappedBy = "rating")
    private List<Rating> rating; //reference to the Rating table

    public User(){}

    public User(String email, String password, String name, List<Category> categories, List<Rating> rating) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.categories = categories;
        this.rating = rating;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Rating> getRating() {
        return rating;
    }

    public void setRating(List<Rating> rating) {
        this.rating = rating;
    }
}
