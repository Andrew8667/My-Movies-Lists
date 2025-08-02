package mymovielist.mymovielist.dto;

import mymovielist.mymovielist.entities.Category;

import java.util.List;

public class AddMovieReviewDTO {
    private String title; //title of the movie
    private String img; //poster of the movie
    private Double stars; //rating out of 5 of the movie
    private String review; //review of the movie
    private List<Long> categories; //id of categories to add movie to

    public AddMovieReviewDTO() {
    }

    public AddMovieReviewDTO(String title, String img, Double stars, String review, List<Long> categories) {
        this.title = title;
        this.img = img;
        this.stars = stars;
        this.review = review;
        this.categories = categories;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Double getStars() {
        return stars;
    }

    public void setStars(Double stars) {
        this.stars = stars;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public List<Long> getCategories() {
        return categories;
    }

    public void setCategories(List<Long> categories) {
        this.categories = categories;
    }
}
