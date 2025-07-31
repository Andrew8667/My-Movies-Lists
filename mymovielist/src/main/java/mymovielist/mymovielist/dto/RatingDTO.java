package mymovielist.mymovielist.dto;

public class RatingDTO {
    private Double rating; //rating for movie
    private String description; //description of the rating

    public RatingDTO() {
    }

    public RatingDTO(Double rating, String description) {
        this.rating = rating;
        this.description = description;
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
