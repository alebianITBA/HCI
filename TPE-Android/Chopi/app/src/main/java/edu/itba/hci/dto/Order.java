package edu.itba.hci.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * Created by alebezdjian on 13/11/14.
 */
public class Order implements Serializable {
    String id;
    Object address;
    String status;
    String creationDate;
    String deliveryDate;
    Double price;
    Item[] items;
    String latitude;
    String longitude;

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    public Item[] getItems() {
        return items;
    }

    public Object getAddress() {
        if (address != null) {
            String add = address.toString();
            String[] s = add.split("name=", add.length());
            String answer = s[1].replace("}", "");
            return answer;
        } else {
            return address;
        }
    }

    public String getStatus() {
        return status;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public Double getPrice() {
        return price;
    }

}
