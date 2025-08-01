package mymovielist.mymovielist.entities;

import jakarta.persistence.*;

import java.util.List;

/**
 * Movie entity contains the movies from the OMDb movie database that users want to review
 * These movies will be saved with an id, movie title, and poster image
 * @author Andrew Gee
 */
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;//identification number of the movie
    @Column(nullable = false)
    private String img; //contains the link to the image of the movie
    @Column(nullable = false)
    private String title; //the title of the movie
    @ManyToMany
    @JoinTable(
            name = "movie_category",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories; //the joined table with category table
    @OneToMany(mappedBy = "movie")
    private List<Rating> rating; //reference to the Rating table

    /**
     * Default constructor to create an empty instance of movie
     */
    public Movie(){

    }

    /**
     * Parameterized constructor to create a populated instance of movie
     * @param id identification number of the movie
     * @param img poster of the movie
     * @param title of the movie
     * @param categories the categories that contain the movie
     * @param rating the ratings given to the movie
     */
    public Movie(long id, String img, String title, List<Category> categories, List<Rating> rating) {
        this.id = id;
        this.img = img;
        this.title = title;
        this.categories = categories;
        this.rating = rating;
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
     * Gets the poster of the movie
     * @return the poster of the movie
     */
    public String getImg() {
        return img;
    }

    /**
     * Sets the poster of the movie
     * @param img the poster of the movie
     */
    public void setImg(String img) {
        this.img = img;
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
     * Gets the categories the movie belongs to
     * @return the categories the movie belongs to
     */
    public List<Category> getCategories() {
        return categories;
    }

    /**
     * Sets the categories the movie belongs to
     * @param categories the categories the movie belongs to
     */
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    /**
     * Gets the ratings given to the movie
     * @return the ratings given to the movie
     */
    public List<Rating> getRating() {
        return rating;
    }

    /**
     * Sets the ratings given to the movie
     * @param rating the ratings given to the movie
     */
    public void setRating(List<Rating> rating) {
        this.rating = rating;
    }
}
