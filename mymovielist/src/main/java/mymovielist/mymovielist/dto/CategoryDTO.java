package mymovielist.mymovielist.dto;

public class CategoryDTO {
    private long id;
    private String name;
    private String description;
    private String email;

    public CategoryDTO(long id, String name, String description, String email) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
