package mymovielist.mymovielist.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

/**
 * Users can create different categories to store their movie reviews
 * For example, they can create a category called 'Favorite Action Movies' or 'Unpopular favorites'
 * @author Andrew Gee
 */
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; //identification number of the category
    @Column(nullable = false)
    private String title; //title of the category
    @Column(nullable = false)
    private String description; //description of what the category is
    @ManyToOne
    @JoinColumn(name = "user_email")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user; //fk connecting to user table that has pk of email
    @ManyToMany(mappedBy = "categories")
    private List<Movie> movies; //used to join table for movie_category

    /**
     * Default constructor that creates an empty instance of category
     */
    public Category(){

    }

    /**
     * Parameterized constructor that creates a filled instance of category
     * @param id identification number of the category
     * @param title of the category
     * @param description of the category
     * @param user the category belongs to
     * @param movies in the category
     */
    public Category(long id, String title, String description, User user, List<Movie> movies) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.user = user;
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
     * Gets the user that owns the category
     * @return the user that owns the category
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user that owns the category
     * @param user the user that owns the category
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the movies in the category
     * @return the movies in the category
     */
    public List<Movie> getMovies() {
        return movies;
    }

    /**
     * Sets the movies in the category
     * @param movies the movies in the category
     */
    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
