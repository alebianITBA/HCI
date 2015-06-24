package edu.itba.hci.dto;

import java.io.Serializable;

/**
 * Created by alebezdjian on 13/11/14.
 */

public class Orders implements Serializable{
    private Order[] orders;


    public Order[] getOrders() {
        return orders;
    }

    public void setOrders(Order[] orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        String s= "";
        for(Order o: orders) {
            s.concat(o.toString() + ", ");
        }
        return s;
    }
}
