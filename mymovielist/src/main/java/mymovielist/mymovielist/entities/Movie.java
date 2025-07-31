package mymovielist.mymovielist.entities;

import jakarta.persistence.*;

import java.util.List;

/**
 * Users can add movies to their categories
 * These movies will be chosen from an extensive list of movie titles
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
    private List<Category> categories; //reference to Category table
    @OneToMany(mappedBy = "movie")
    private List<Rating> rating; //reference to the Rating table

    public Movie(){

    }

    public Movie(long id, String img, String title, List<Category> categories, List<Rating> rating) {
        this.id = id;
        this.img = img;
        this.title = title;
        this.categories = categories;
        this.rating = rating;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
