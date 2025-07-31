package mymovielist.mymovielist.dto;

import java.util.List;

public class CategoryMovieRatingDTO {
    private long id;//id for category
    private String title; //title of category
    private String description; //description of purpose of category
    private List<MovieDTO> movies; //list of movies for the category

    public CategoryMovieRatingDTO() {
    }

    public CategoryMovieRatingDTO(long id, String title, String description, List<MovieDTO> movies) {
        this.id = id;
        this.title = title;
        this.description = description;
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

    public List<MovieDTO> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieDTO> movies) {
        this.movies = movies;
    }
}
