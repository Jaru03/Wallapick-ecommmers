package Wallapick.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.Date;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    @Column(length = 1000)
    private String description;

    @NotNull
    private String category; // Not ENUM since it will be received from the frontend as a checkbox

    @Positive
    private double price;

    private boolean forSale = true;

    private String status; // Not ENUM since it will be received from the frontend as a checkbox

    @PastOrPresent
    @Temporal(TemporalType.DATE)
    private Date releaseDate = new Date();

    @ManyToOne
    @JoinColumn(name = "id_seller", nullable = false)
    private User seller;

    // 1:1 relationship with order (a product can be ordered only once)
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private Order order;

    //private byte[] image;

    public Product() {
    }

    public Product(Long id, String name, String description, String category, double price, boolean forSale, String status, Date releaseDate, User seller /*,byte[] image */) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.forSale = true;
        this.status = status;
        this.releaseDate = releaseDate;
        this.seller = seller;
        // this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isForSale() {
        return forSale;
    }

    public void setForSale(boolean forSale) {
        this.forSale = forSale;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

/*
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
*/

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", forSale=" + forSale +
                ", status='" + status + '\'' +
                ", releaseDate=" + releaseDate +
                ", seller=" + seller +
                ", order=" + order +
                '}';
    }
}