package mymovielist.mymovielist.dto;

public class MovieCategoryRequest {
    private String title;
    private String img;
    private String date;
    private long category;

    public MovieCategoryRequest(String title, String img, String date,  long category) {
        this.title = title;
        this.img = img;
        this.date = date;
        this.category = category;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getCategory() {
        return category;
    }

    public void setCategory(long category) {
        this.category = category;
    }
}
