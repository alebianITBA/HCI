package edu.itba.hci.chopi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.itba.hci.chopi.ExpandableListAdapter;
import edu.itba.hci.chopi.R;
import edu.itba.hci.chopi.api.CategoriesRequest;
import edu.itba.hci.chopi.api.GetAllSubcategories;
import edu.itba.hci.chopi.api.ProductsByCategoryRequest;
import edu.itba.hci.chopi.api.ProductsBySubcategoryIdRequest;
import edu.itba.hci.dto.Categories;
import edu.itba.hci.dto.Category;
import edu.itba.hci.dto.Product;
import edu.itba.hci.dto.Products;
import edu.itba.hci.dto.Subcategories;
import edu.itba.hci.dto.Subcategory;
import edu.itba.hci.dto.SubcategoryFromApi;

public class CategoriesActivity extends BaseActivity {

    public final static String EXTRA_MESSAGE = "edu.itba.hci.chopi";
    private static ListView listView;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    List<String> category1;
    List<String> category2;
    List<String> category3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getApplicationContext().getString(R.string.categories));


        setContentView(R.layout.activity_categories_exp);
        setDrawer();

        expListView = (ExpandableListView) findViewById(R.id.expandable_list);
        final String see_all = this.getString(R.string.see_all);
        prepareListData();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String o = (String) listAdapter.getChild(groupPosition, childPosition);
                if (o.equals(see_all) && groupPosition == 0) {
                    getProductsInCategory(1);

                } else if (o.equals(see_all) && groupPosition == 1) {
                    getProductsInCategory(2);

                } else if (o.equals(see_all) && groupPosition == 2) {
                    getProductsInCategory(3);

                } else {
                    Log.e("que producto es", o);
                    if (groupPosition == 0) {
                        getProductsInSubcategory(childPosition,"");

                    } else if (groupPosition == 1) {
                        getProductsInSubcategory(childPosition + 11,"");

                    } else {
                        getProductsInSubcategory(childPosition + 11 + 15,"");

                    }
                }
                return true;
            }
        });
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add(this.getString(R.string.category1));
        listDataHeader.add(this.getString(R.string.category2));
        listDataHeader.add(this.getString(R.string.category3));

        getSubs();

        // Adding child data
        category1 = new ArrayList<String>();
        category2 = new ArrayList<String>();
        category3 = new ArrayList<String>();
        category1.add(this.getString(R.string.see_all));
        category2.add(this.getString(R.string.see_all));
        category3.add(this.getString(R.string.see_all));


        listDataChild.put(listDataHeader.get(0), category1); // Header, Child data
        listDataChild.put(listDataHeader.get(1), category2);
        listDataChild.put(listDataHeader.get(2), category3);
    }

    public void getSubs() {
        getBaseSpiceManager().execute(new GetAllSubcategories(1), new SubcategoriesRequestListener());
        getBaseSpiceManager().execute(new GetAllSubcategories(2), new SubcategoriesRequestListener());
        getBaseSpiceManager().execute(new GetAllSubcategories(3), new SubcategoriesRequestListener());
    }



    private final class CategoriesRequestListener implements RequestListener<Categories> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(CategoriesActivity.this, "Fallo el request.", Toast.LENGTH_SHORT);
        }

        @Override
        public void onRequestSuccess(Categories categories) {
            onCategoriesSuccess(categories);
        }

    }

    private final class SubcategoriesRequestListener implements RequestListener<Subcategories> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(CategoriesActivity.this, "Fallo el request.", Toast.LENGTH_SHORT);
        }

        @Override
        public void onRequestSuccess(Subcategories subcategories) {
            onSubcategoriesSuccess(subcategories);
        }

    }

    public void onSubcategoriesSuccess(Subcategories subcategories) {
        Subcategory[] subcategories1 = subcategories.getSubcategories();
        String[] s;

        String nombre_cat = "";


        for (int i = 0; i < subcategories1.length; i++) {
            String categoryName = subcategories1[i].getCategory().toString();
            if (categoryName != null) {
                s = categoryName.split("name=");
                String p;
                p = s[1];
                nombre_cat = p.replace("}", "");
            }
            if (nombre_cat.equals("Calzado")) {
                category1.add(subcategories1[i].getName());
            } else if (nombre_cat.equals("Indumentaria")) {
                category2.add(subcategories1[i].getName());
            } else if (nombre_cat.equals("Accesorios")) {
                category3.add(subcategories1[i].getName());
            }
        }


    }


    public void onCategoriesSuccess(Categories categories) {
        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(getApplicationContext(), categories);
        listView.setAdapter(categoriesAdapter);
    }

    private void getProductsInCategory(int id) {
        ProductsByCategoryRequest req = new ProductsByCategoryRequest(id);
        getBaseSpiceManager().execute(req, new ProductsByCategoryRequestListener());
    }

    private void getProductsInSubcategory(int id,String filter) {
        ProductsBySubcategoryIdRequest req = new ProductsBySubcategoryIdRequest(id,filter);
        getBaseSpiceManager().execute(req, new ProductsBySubcategoryIdRequestListener());
    }

    private final class ProductsBySubcategoryIdRequestListener implements RequestListener<Products> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(CategoriesActivity.this, "Fallo el request.", Toast.LENGTH_SHORT);
        }

        @Override
        public void onRequestSuccess(Products products) {
            Log.d(CategoriesActivity.class.getSimpleName(), products.toString());
            onProductsBySubcategoryIdSuccess(products);
        }

    }

    public void onProductsBySubcategoryIdSuccess(Products products) {
        if(products.getTotal()!=0){
            Intent intent = new Intent(this, ProductsListActivity.class);
            intent.putExtra(ProductsListActivity.PRODUCTS_LIST, products);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this, NoResultsActivity.class);
            startActivity(intent);
        }

    }

    private final class ProductsByCategoryRequestListener implements RequestListener<Products> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(CategoriesActivity.this, "Fallo el request.", Toast.LENGTH_SHORT);
        }

        @Override
        public void onRequestSuccess(Products products) {
            Log.d(CategoriesActivity.class.getSimpleName(), products.toString());
            onProductsByCategorySuccess(products);
        }

    }


    public void onProductsByCategorySuccess(Products products) {
        Intent intent = new Intent(this, ProductsListActivity.class);
        intent.putExtra(ProductsListActivity.PRODUCTS_LIST, products);
        startActivity(intent);
    }

}
