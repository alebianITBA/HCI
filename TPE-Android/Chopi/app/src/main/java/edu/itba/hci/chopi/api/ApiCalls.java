package edu.itba.hci.chopi.api;

import edu.itba.hci.dto.Categories;
import edu.itba.hci.dto.Order;
import edu.itba.hci.dto.OrderFromApi;
import edu.itba.hci.dto.Orders;
import edu.itba.hci.dto.Product;
import edu.itba.hci.dto.ProductFromApi;
import edu.itba.hci.dto.Products;
import edu.itba.hci.dto.Subcategories;
import retrofit.http.GET;
import retrofit.http.Query;

public interface ApiCalls {

	@GET("/Catalog.groovy")
	Categories getCategories(@Query("method") String method);

    @GET("/Catalog.groovy")
    Subcategories getAllSubcategories(@Query("method") String method, @Query("id") int id);

    @GET("/Catalog.groovy")
    Subcategories getAllSubcategoriesFiltered(@Query("method") String method, @Query("id") int id, @Query("filters") String filter);

    @GET("/Catalog.groovy")
    Products getProductsBySubcategoryId(@Query("method") String method, @Query("id") int id,@Query("filters")String filter, @Query("page_size") int size);

    @GET("/Order.groovy")
    Orders getAllOrders(@Query("method") String method, @Query("username") String username, @Query("authentication_token") String authentication_token);

    @GET("/Order.groovy")
    OrderFromApi getOrderById(@Query("method") String method, @Query("username") String username, @Query("authentication_token") String authentication_token, @Query("id") int orderId);

    @GET("/Catalog.groovy")
    Products getProductsByCategoryId(@Query("method") String method, @Query("id") int categoryId, @Query("page_size") int size,@Query("filters") String filter);

    @GET("/Catalog.groovy")
    ProductFromApi getProductById(@Query("method") String method, @Query("id") int productId);

    @GET("/Catalog.groovy")
    Products getProductsByGender(@Query("method") String method, @Query("filters") String filter, @Query("page_size") int size);

    @GET("/Catalog.groovy")
    Products getProductsByName(@Query("method") String method, @Query("name") String name, @Query("page_size") int size);

}
