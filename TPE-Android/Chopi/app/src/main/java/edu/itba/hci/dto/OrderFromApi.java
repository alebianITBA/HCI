package edu.itba.hci.dto;

import java.io.Serializable;

/**
 * Created by kevinkraus on 19/11/14.
 */
public class OrderFromApi implements Serializable{
    private Order order;
    public Order getOrder(){
        return order;
    }
    public void setOrder(Order order){
        this.order=order;
    }
}
