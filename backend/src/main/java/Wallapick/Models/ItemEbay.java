package Wallapick.Models;

import java.util.List;

public class ItemEbay {

    private String id;
    private String title;
    private double price;
    private String mainImage;
    private List<String> images;
    private List<String> categories;

    public ItemEbay() {
    }

    public ItemEbay(String id, String title, double price, String mainImage, List<String> images, List<String> categories) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.mainImage = mainImage;
        this.images = images;
        this.categories = categories;
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

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }
}