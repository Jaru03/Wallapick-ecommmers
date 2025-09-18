package Wallapick.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ItemEbay {


    private String itemId;
    private String title;//name
    private String shortDescription;//description
    private Price price;
    private Image image;
    private String itemWebUrl;
    private String condition;//status


    public ItemEbay() {
    }

    public ItemEbay(String itemId, String title, String shortDescription, Price price, Image image, String itemWebUrl, String condition) {
        this.itemId = itemId;
        this.title = title;
        this.shortDescription = shortDescription;
        this.price = price;
        this.image = image;
        this.itemWebUrl = itemWebUrl;
        this.condition = condition;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public static class Price {
        public double value;
    }

    public static class Image {
        public String imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getItemWebUrl() {
        return itemWebUrl;
    }

    public void setItemWebUrl(String itemWebUrl) {
        this.itemWebUrl = itemWebUrl;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
