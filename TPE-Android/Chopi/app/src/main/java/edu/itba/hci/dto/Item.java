package edu.itba.hci.dto;

import java.io.Serializable;

/**
 * Created by kevinkraus on 18/11/14.
 */
public class Item implements Serializable {

    String id;
    int quantity;
    Double price;
    ProductFromOrder product;
    public void setProduct(ProductFromOrder product){
        this.product=product;
    }
    public ProductFromOrder getProduct(){
        return product;
    }


    public String getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "{quantity: " + quantity + ", price: " + price + "}";
    }
}
