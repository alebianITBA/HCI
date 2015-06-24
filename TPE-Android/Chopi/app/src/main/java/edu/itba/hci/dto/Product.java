package edu.itba.hci.dto;

import java.io.Serializable;

/**
 * Created by kevinkraus on 8/11/14.
 */
public class Product implements Serializable {

    String id;
    String name;
    Double price;
    String[] imageUrl;
    Category category;
    Category subcategory;
    Attribute[] attributes;
    String marca;
    public String getMarca(){
        return marca;

    }
    public void setMarca(String marca){
        this.marca= marca;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public String[] getImageUrl() {
        return imageUrl;
    }

    public Category getCategory() {
        return category;
    }

    public Category getSubcategory() {
        return subcategory;
    }

    public Attribute[] getAttributes() {
        return attributes;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setImageUrl(String[] imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setSubcategory(Category subcategory) {
        this.subcategory = subcategory;
    }

    public void setAttributes(Attribute[] attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "{Name: " + name + ", price: " + price + "}";
    }
}
