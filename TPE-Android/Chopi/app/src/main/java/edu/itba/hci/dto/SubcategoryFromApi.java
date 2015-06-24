package edu.itba.hci.dto;

import java.io.Serializable;

/**
 * Created by kevinkraus on 19/11/14.
 */
public class SubcategoryFromApi  implements Serializable {
    private Subcategory subcategory;
    public void setSubcategory(Subcategory p){
        this.subcategory=p;
    }
    public Subcategory getSubcategory(){
        return subcategory;
    }
}
