package Wallapick.ModelsDTO;

import Wallapick.Models.ItemEbay;
import Wallapick.Models.Product;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.util.Date;

public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private String category;
    private double price;
    private boolean forSale;
    private Date releaseDate;
    private String image;
    private String seller;
    private String status;
    private String urlEbayProduct;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private OrderDTO orderDTO;

    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, String description, String category, double price, boolean forSale, Date releaseDate,String image, UserDTO seller, OrderDTO orderDTO) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.forSale = forSale;
        this.releaseDate = releaseDate;
        this.image = image;
        this.seller = seller;
        this.status = status;
        this.urlEbayProduct = urlEbayProduct;
        this.orderDTO = orderDTO;
    }

    public ProductDTO(Product p) {
        this.id = p.getId();
        this.name = p.getName();
        this.description = p.getDescription();
        this.category = p.getCategory();
        this.price = p.getPrice();
        this.forSale = p.isForSale();
        this.releaseDate = p.getReleaseDate();
        this.image = p.getImage();
        if(p.getSeller() == null){
            this.seller = "EBAY_SELLER";
        }
        else{
            this.seller = p.getSeller().getUsername();

        }
        this.status = p.getStatus();

        // The order is only included if the product is not for sale
        if (!this.forSale && p.getOrder() != null) {
            this.orderDTO = new OrderDTO(p.getOrder(), false); // Avoid recursion
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

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public UserDTO getSeller() {
        return seller;
    }

    public void setSeller(UserDTO seller) {
        this.seller = seller;
    }

    public OrderDTO getOrderDTO() {
        return !this.forSale ? this.orderDTO : null;
    }

    public void setOrderDTO(OrderDTO orderDTO) {
        this.orderDTO = orderDTO;
    }

    public String getUrlEbayProduct() {
        return urlEbayProduct;
    }

    public void setUrlEbayProduct(String urlEbayProduct) {
        this.urlEbayProduct = urlEbayProduct;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
