package Wallapick.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.Date;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    @NotBlank
    private String name;

    //@NotBlank
    @Column(length = 1000)
    private String description;

    @NotNull
    private String category;

    @Positive
    private double price;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean forSale = true;

    private String status;

    @PastOrPresent
    @Temporal(TemporalType.DATE)
    private Date releaseDate = new Date();

    @ManyToOne
    @JoinColumn(name = "id_seller", nullable = true)
    private User seller;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private Order order;

    @NotNull
    @Column(length = 1000)
    private String image;

    @Column(length = 1000)
    private String urlEbayProduct;

    private String idEbay;

    public Product() {
    }

    public Product(Long id, String name, String description, String category, double price, boolean forSale, String status, Date releaseDate, User seller, String image, String idEbay) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.idEbay = idEbay;
        this.forSale = true;
        this.status = status;
        this.releaseDate = releaseDate;
        this.seller = seller;
        this.image = image;
    }
    public Product(ItemEbay e, String category) {
        this.name = e.getTitle();
        if(e.getShortDescription() == null){
            this.description = "No description available";
        }
        else{
            this.description = e.getShortDescription();
        }
        this.category = category;
        this.price = e.getPrice() != null ? e.getPrice().value : 0.0;
        this.forSale = true;
        this.status = e.getCondition();
        this.urlEbayProduct = e.getItemWebUrl();
        this.idEbay = e.getItemId();

        // Manejar el caso de imagen nula
        if (e.getImage() != null) {
            this.image = e.getImage().imageUrl;
        } else {
            this.image = "https://res.cloudinary.com/dpntbtjej/image/upload/v1758185535/404_mcewsz.jpg"; // o null si prefieres
        }
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

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

    public String getUrlEbayProduct() {
        return urlEbayProduct;
    }

    public void setUrlEbayProduct(String urlEbayProduct) {
        this.urlEbayProduct = urlEbayProduct;
    }
}
