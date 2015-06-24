package edu.itba.hci.dto;

import java.io.Serializable;

/**
 * Created by kevinkraus on 19/11/14.
 */
public class ProductFromApi implements Serializable {
    private Product product;
    public void setProduct(Product p){
        this.product=p;
    }
    public Product getProduct(){
        return product;
    }
}
