package mymovielist.mymovielist.entities;

import jakarta.persistence.*;

import java.util.List;

/**
 * Represents a user of the app
 * Users have categories containing movie reviews
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
    private List<Category> categories; //reference to the category table
    @OneToMany(mappedBy = "user")
    private List<Rating> rating; //reference to the Rating table

    /**
     * Default constructor to create empty user
     */
    public User(){}

    /**
     * Parameterized constructor that creates a filled user instance
     * @param email of the user
     * @param password of the user
     * @param name of the user
     * @param categories of movies that belong to the user
     * @param rating ratings that the user makes
     */
    public User(String email, String password, String name, List<Category> categories, List<Rating> rating) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.categories = categories;
        this.rating = rating;
    }

    /**
     * Gets the email associated with the user
     * @return the email associated with the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email associated with the user
     * @param email the email associated with the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the password associated with the user
     * @return the password associated with the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password associated with the user
     * @param password the password associated with the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the name associated with the user
     * @return the name associated with the user
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name associated with the user
     * @param name the name associated with the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the categories that belong to the user
     * @return the categories that belong to the user
     */
    public List<Category> getCategories() {
        return categories;
    }

    /**
     * Sets the categories that belong to the user
     * @param categories the categories that belong to the user
     */
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    /**
     * Gets the rating that belong to the user
     * @return the rating that belong to the user
     */
    public List<Rating> getRating() {
        return rating;
    }

    /**
     * Sets the rating that belong to the user
     * @param rating the rating that belong to the user
     */
    public void setRating(List<Rating> rating) {
        this.rating = rating;
    }
}
