package mymovielist.mymovielist.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.util.List;

/**
 * Users can create different categories to store their movies
 * For example, they may create a category called 'Favorite Action Movies' or 'Unpopular favorites'
 * @author Andrew Gee
 */
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; //identification number of the custom category
    @Column(nullable = false)
    private String title; //title given to category
    @Column(nullable = false)
    private String description; //description given to category
    @ManyToOne
    @JoinColumn(name = "user_email")
    private User user; //reference to user table
    @ManyToMany(mappedBy = "categories")
    private List<Movie> movies; //reference to Movie table

    public Category(){

    }

    public Category(long id, String title, String description,User user, List<Movie> movies) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.user = user;
        this.movies = movies;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
