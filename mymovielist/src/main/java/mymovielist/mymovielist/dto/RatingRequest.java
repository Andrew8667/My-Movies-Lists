package mymovielist.mymovielist.dto;

public class RatingRequest {
    private String title;
    private Double rating;
    private String description;

    public RatingRequest(String title, Double rating, String description) {
        this.title = title;
        this.rating = rating;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
