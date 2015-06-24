package edu.itba.hci.dto;

import java.io.Serializable;

/**
 * Created by kevinkraus on 19/11/14.
 */
public class Subcategory implements Serializable {
    private int id;
    private String name;
    private Object category;
    private Attribute[] attributes;

    public void setName(String name) {
        this.name = name;

    }

    public void setCategory(Object category) {
        this.category = category;
    }

    public Object getCategory() {
        return category;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAttributes(Attribute[] attributes) {
        this.attributes = attributes;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Attribute[] getAttributes() {
        return attributes;
    }

}
