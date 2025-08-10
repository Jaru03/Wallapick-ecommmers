package Wallapick.ModelsDTO;

import Wallapick.Models.Order;

import java.util.Date;

public class OrderDTO {

    private Long id;
    private String productName;
    private String buyerUsername;
    private String sellerUsername;
    private Date orderDate;
    private double finalPrice;

    public OrderDTO() {
    }

    public OrderDTO(Order c, boolean incluirProducto) {
        this.id = c.getId();
        this.productName = c.getProducto().getName();
        this.buyerUsername = c.getBuyer().getUsername();
        this.sellerUsername = c.getSeller().getUsername();
        this.orderDate = c.getOrderDate();
        this.finalPrice = c.getFinalPrice();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBuyerUsername() {
        return buyerUsername;
    }

    public void setBuyerUsername(String buyerUsername) {
        this.buyerUsername = buyerUsername;
    }

    public String getSellerUsername() {
        return sellerUsername;
    }

    public void setSellerUsername(String sellerUsername) {
        this.sellerUsername = sellerUsername;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String p) {
        this.productName = p;
    }
}