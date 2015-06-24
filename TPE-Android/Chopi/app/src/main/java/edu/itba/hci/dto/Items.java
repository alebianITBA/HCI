package edu.itba.hci.dto;

import java.io.Serializable;

/**
 * Created by kevinkraus on 18/11/14.
 */
public class Items implements Serializable {
    private Item[] items;


    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    @Override
    public String toString() {
        String s= "";
        for(Item o: items) {
            s.concat(o.toString() + ", ");
        }
        return s;
    }
}
