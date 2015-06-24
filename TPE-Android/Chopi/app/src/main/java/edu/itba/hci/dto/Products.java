package edu.itba.hci.dto;

import java.io.Serializable;

/**
 * Created by kevinkraus on 8/11/14.
 */
public class Products implements Serializable {

    Integer page;
    Integer pageSize;
    Integer total;
    Product[] products;

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public void setProducts(Product[] products) {
        this.products = products;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getTotal() {
        return total;
    }

    public Product[] getProducts() {
        return products;
    }

    @Override
    public String toString() {
        String s= "";
        for(Product p: products) {
            s.concat(p.toString() + ", ");
        }
        return s;
    }
}
