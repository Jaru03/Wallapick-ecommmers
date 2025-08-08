package Wallapick.Modelos;

import lombok.Data;

import java.util.List;

public class ItemSummary {
    private String id;
    private String title;
    private double price;
    private String principalImage;
    private List<String> images;
    private List<String> categories;

    public ItemSummary() {
    }

    public ItemSummary(List<String> categories, List<String> images, double price, String title, String id, String principalImage) {
        this.categories = categories;
        this.images = images;
        this.price = price;
        this.title = title;
        this.id = id;
        this.principalImage = principalImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getPrincipalImage() {
        return principalImage;
    }

    public void setPrincipalImage(String principalImage) {
        this.principalImage = principalImage;
    }
}