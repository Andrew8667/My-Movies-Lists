package mymovielist.mymovielist.dto;

import java.util.List;

public class MovieDTO {
    private long id;//id of movie
    private String img; //image for movie
    private String title; //title of movie
    private RatingDTO rating; //rating for that movie

    public MovieDTO() {
    }

    public MovieDTO(long id, String img, String title, RatingDTO rating) {
        this.id = id;
        this.img = img;
        this.title = title;
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

    public RatingDTO getRating() {
        return rating;
    }

    public void setRating(RatingDTO rating) {
        this.rating = rating;
    }
}
