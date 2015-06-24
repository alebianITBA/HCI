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
import edu.itba.hci.chopi.api.GetAllSubcategoriesFiltered;
import edu.itba.hci.chopi.api.ProductsByCategoryIdFiltered;
import edu.itba.hci.chopi.api.ProductsByCategoryRequest;
import edu.itba.hci.chopi.api.ProductsBySubcategoryIdRequest;
import edu.itba.hci.dto.Categories;
import edu.itba.hci.dto.Category;
import edu.itba.hci.dto.Product;
import edu.itba.hci.dto.Products;
import edu.itba.hci.dto.Subcategories;
import edu.itba.hci.dto.Subcategory;
import edu.itba.hci.dto.SubcategoryFromApi;

public class GenderCategoriesActivity extends BaseActivity {

    public final static String EXTRA_MESSAGE = "edu.itba.hci.chopi";
    public final static String baby_face = "flag_for_baby";
    public final static String infantil_face = "flag_for_infantil";
    public final static String man = "flag_for_man";
    public final static String woman = "flag_for_woman";
    private static ListView listView;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    List<String> category1;
    List<String> category2;
    List<String> category3;
    private boolean bebe = false;
    private boolean infantil = false;
    private boolean hombre = false;
    private boolean mujer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_categories_exp);
        setDrawer();
        bebe = (Boolean) getIntent().getBooleanExtra(baby_face, false);
        infantil = (Boolean) getIntent().getBooleanExtra(infantil_face, false);
        hombre = (Boolean) getIntent().getBooleanExtra(man, false);
        mujer = (Boolean) getIntent().getBooleanExtra(woman, false);
        if(bebe){
            setTitle(getApplicationContext().getString(R.string.by_age_baby));
        }else if(hombre ){
            setTitle(getApplicationContext().getString(R.string.by_gender_male));
        }else if(infantil){
            setTitle(getApplicationContext().getString(R.string.by_age_infantil));
        }else if(mujer){
            setTitle(getApplicationContext().getString(R.string.by_gender_female));
        }

        Log.e("HOMBRE", String.valueOf(hombre));
        Log.e("INFANTIL", String.valueOf(infantil));
        Log.e("BEBE", String.valueOf(bebe));
        Log.e("MUJER", String.valueOf(mujer));

        expListView = (ExpandableListView) findViewById(R.id.expandable_list);
        final String see_all = this.getString(R.string.see_all);
        prepareListData();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String o = (String) listAdapter.getChild(groupPosition, childPosition);
                if (bebe) {
                    if (o.equals(see_all)) {
                        getProductsInCategoryFiltered(1, "[{\"id\":2,\"value\":\"Bebe\"}]");
                    } else {
                        getProductsInSubcategory(childPosition, "[{\"id\":2,\"value\":\"Bebe\"}]");

                    }
                } else if (infantil) {
                    if (o.equals(see_all)) {
                        getProductsInCategoryFiltered(1, "[{\"id\":2,\"value\":\"Infantil\"}]");
                    } else {
                        getProductsInSubcategory(childPosition, "[{\"id\":2,\"value\":\"Infantil\"}]");
                    }

                } else if (hombre) {
                    if (o.equals(see_all)) {
                        switch (groupPosition) {
                            case 0:
                                getProductsInCategoryFiltered(1, "[{\"id\":1,\"value\":\"Masculino\"}]");
                                break;
                            case 1:
                                getProductsInCategoryFiltered(2, "[{\"id\":1,\"value\":\"Masculino\"}]");
                                break;
                            case 2:
                                getProductsInCategoryFiltered(3, "[{\"id\":1,\"value\":\"Masculino\"}]");
                                break;
                            default:
                                break;
                        }

                    } else {
                        switch (groupPosition) {
                            case 0:
                                switch (childPosition) {
                                    case 0:
                                        break;
                                    case 1:
                                        getProductsInSubcategory(childPosition, "[{\"id\":1,\"value\":\"Masculino\"}]");
                                        break;
                                    case 2:
                                        getProductsInSubcategory(3, "[{\"id\":1,\"value\":\"Masculino\"}]");
                                        break;
                                    case 3:
                                        getProductsInSubcategory(5, "[{\"id\":1,\"value\":\"Masculino\"}]");
                                        break;
                                    case 4:
                                        getProductsInSubcategory(6, "[{\"id\":1,\"value\":\"Masculino\"}]");
                                        break;
                                    case 5:
                                        getProductsInSubcategory(7, "[{\"id\":1,\"value\":\"Masculino\"}]");
                                        break;
                                    case 6:
                                        getProductsInSubcategory(8, "[{\"id\":1,\"value\":\"Masculino\"}]");
                                        break;
                                    case 7:
                                        getProductsInSubcategory(9, "[{\"id\":1,\"value\":\"Masculino\"}]");
                                        break;
                                    case 8:
                                        getProductsInSubcategory(10, "[{\"id\":1,\"value\":\"Masculino\"}]");
                                        break;
                                    default:
                                        break;
                                }
                                break;
                            case 1:

                                switch (childPosition) {
                                    case 0:
                                        break;
                                    case 1:
                                        getProductsInSubcategory(12, "[{\"id\":1,\"value\":\"Masculino\"}]");
                                        break;
                                    case 2:
                                        getProductsInSubcategory(13, "[{\"id\":1,\"value\":\"Masculino\"}]");
                                        break;
                                    case 3:
                                        getProductsInSubcategory(15, "[{\"id\":1,\"value\":\"Masculino\"}]");
                                        break;
                                    case 4:
                                        getProductsInSubcategory(16, "[{\"id\":1,\"value\":\"Masculino\"}]");
                                        break;
                                    case 5:
                                        getProductsInSubcategory(19, "[{\"id\":1,\"value\":\"Masculino\"}]");
                                        break;
                                    case 6:
                                        getProductsInSubcategory(20, "[{\"id\":1,\"value\":\"Masculino\"}]");
                                        break;
                                    case 7:
                                        getProductsInSubcategory(21, "[{\"id\":1,\"value\":\"Masculino\"}]");
                                        break;
                                    case 8:
                                        break;
                                    default:
                                        break;

                                }

                                break;
                            case 2:
                                switch (childPosition) {
                                    case 0:
                                        break;
                                    case 1:
                                        getProductsInSubcategory(29, "[{\"id\":1,\"value\":\"Masculino\"}]");
                                        break;
                                    case 2://cinturones
                                        Intent intent12 = new Intent(GenderCategoriesActivity.this, NoResultsActivity.class);
                                        startActivity(intent12);
                                        break;
                                    case 3://gorras
                                        Intent intent1 = new Intent(GenderCategoriesActivity.this, NoResultsActivity.class);
                                        startActivity(intent1);
                                        break;
                                    case 4://mochilas
                                        Intent intent2 = new Intent(GenderCategoriesActivity.this, NoResultsActivity.class);
                                        startActivity(intent2);
                                        break;
                                    case 5://relojes
                                        Intent intent3 = new Intent(GenderCategoriesActivity.this, NoResultsActivity.class);
                                        startActivity(intent3);
                                        break;
                                    default:
                                        break;
                                }
                                break;
                            default:
                                break;
                        }
                    }

                } else if (mujer) {

                    if (o.equals(see_all)) {
                        switch (groupPosition) {
                            case 0:
                                getProductsInCategoryFiltered(1, "[{\"id\":1,\"value\":\"Femenino\"}]");
                                break;
                            case 1:
                                getProductsInCategoryFiltered(2, "[{\"id\":1,\"value\":\"Femenino\"}]");
                                break;
                            case 2:
                                getProductsInCategoryFiltered(3, "[{\"id\":1,\"value\":\"Femenino\"}]");
                                break;
                            default:
                                break;
                        }

                    } else {
                        switch (groupPosition) {
                            case 0:
                                switch (childPosition) {
                                    case 0:
                                        break;
                                    case 1:
                                        getProductsInSubcategory(1, "[{\"id\":1,\"value\":\"Femenino\"}]");
                                        break;
                                    case 2:
                                        getProductsInSubcategory(2, "[{\"id\":1,\"value\":\"Femenino\"}]");
                                        break;
                                    case 3:
                                        getProductsInSubcategory(3, "[{\"id\":1,\"value\":\"Femenino\"}]");
                                        break;
                                    case 4:
                                        getProductsInSubcategory(4, "[{\"id\":1,\"value\":\"Femenino\"}]");
                                        break;
                                    case 5:
                                        getProductsInSubcategory(5, "[{\"id\":1,\"value\":\"Femenino\"}]");
                                        break;
                                    case 6:
                                        getProductsInSubcategory(6, "[{\"id\":1,\"value\":\"Femenino\"}]");
                                        break;
                                    case 7:
                                        getProductsInSubcategory(7, "[{\"id\":1,\"value\":\"Femenino\"}]");
                                        break;
                                    case 8:
                                        getProductsInSubcategory(8, "[{\"id\":1,\"value\":\"Femenino\"}]");
                                        break;
                                    case 9:
                                        getProductsInSubcategory(9, "[{\"id\":1,\"value\":\"Femenino\"}]");
                                        break;
                                    case 10:
                                        getProductsInSubcategory(10, "[{\"id\":1,\"value\":\"Femenino\"}]");
                                        break;
                                    case 11:
                                        getProductsInSubcategory(11, "[{\"id\":1,\"value\":\"Femenino\"}]");
                                        break;

                                    default:
                                        break;
                                }
                                break;
                            case 1:

                                switch (childPosition) {
                                    case 0:
                                        break;
                                    case 1:
                                        getProductsInSubcategory(12, "[{\"id\":1,\"value\":\"Femenino\"}]");
                                        break;
                                    case 2:
                                        getProductsInSubcategory(13, "[{\"id\":1,\"value\":\"Femenino\"}]");
                                        break;
                                    case 3:
                                        getProductsInSubcategory(14, "[{\"id\":1,\"value\":\"Femenino\"}]");
                                        break;
                                    case 4:
                                        getProductsInSubcategory(15, "[{\"id\":1,\"value\":\"Femenino\"}]");
                                        break;
                                    case 5:
                                        getProductsInSubcategory(16, "[{\"id\":1,\"value\":\"Femenino\"}]");
                                        break;
                                    case 6:
                                        getProductsInSubcategory(17, "[{\"id\":1,\"value\":\"Femenino\"}]");
                                        break;
                                    case 7:
                                        getProductsInSubcategory(18, "[{\"id\":1,\"value\":\"Femenino\"}]");
                                        break;
                                    case 8:
                                        getProductsInSubcategory(19, "[{\"id\":1,\"value\":\"Femenino\"}]");
                                        break;
                                    case 9:
                                        getProductsInSubcategory(20, "[{\"id\":1,\"value\":\"Femenino\"}]");
                                        break;
                                    case 10:
                                        getProductsInSubcategory(21, "[{\"id\":1,\"value\":\"Femenino\"}]");
                                        break;
                                    case 11:
                                        getProductsInSubcategory(22, "[{\"id\":1,\"value\":\"Femenino\"}]");
                                        break;
                                    case 12:
                                       //remeras
                                        Intent intent = new Intent(GenderCategoriesActivity.this, NoResultsActivity.class);
                                        startActivity(intent);
                                        break;
                                    case 13:
                                        Intent intent1 = new Intent(GenderCategoriesActivity.this, NoResultsActivity.class);
                                        startActivity(intent1);
                                        //sacos
                                        break;
                                    case 14:
                                        Intent intent2 = new Intent(GenderCategoriesActivity.this, NoResultsActivity.class);
                                        startActivity(intent2);
                                        //sweaters
                                        break;
                                    case 15:
                                        Intent intent3 = new Intent(GenderCategoriesActivity.this, NoResultsActivity.class);
                                        startActivity(intent3);
                                        //vestidos
                                    default:
                                        break;

                                }

                                break;
                            case 2:
                                switch (childPosition) {
                                    case 0:
                                        break;
                                    case 1:
                                        getProductsInSubcategory(27, "[{\"id\":1,\"value\":\"Femenino\"}]");
                                        break;
                                    case 2:
                                        getProductsInSubcategory(28, "[{\"id\":1,\"value\":\"Femenino\"}]");
                                        break;
                                    case 3:
                                        getProductsInSubcategory(29, "[{\"id\":1,\"value\":\"Femenino\"}]");
                                        break;
                                    case 4:
                                        getProductsInSubcategory(30, "[{\"id\":1,\"value\":\"Femenino\"}]");
                                        break;
                                    case 5:
                                        //cinturones
                                        Intent intent2 = new Intent(GenderCategoriesActivity.this, NoResultsActivity.class);
                                        startActivity(intent2);
                                        break;
                                    case 6:
                                        //collares
                                        Intent intent = new Intent(GenderCategoriesActivity.this, NoResultsActivity.class);
                                        startActivity(intent);
                                        break;
                                    case 7:
                                        Intent intent3 = new Intent(GenderCategoriesActivity.this, NoResultsActivity.class);
                                        startActivity(intent3);
                                        //llaveros
                                        break;
                                    case 8:
                                        Intent intent4 = new Intent(GenderCategoriesActivity.this, NoResultsActivity.class);
                                        startActivity(intent4);
                                        //mochilas
                                        break;
                                    case 9:
                                        Intent intent5 = new Intent(GenderCategoriesActivity.this, NoResultsActivity.class);
                                        startActivity(intent5);
                                        //pulseras
                                        break;
                                    case 10:
                                        Intent intent12 = new Intent(GenderCategoriesActivity.this, NoResultsActivity.class);
                                        startActivity(intent12);
                                        //relojes
                                        break;
                                    default:
                                        break;
                                }
                                break;
                            default:
                                break;
                        }
                    }

                } else {


                    if (o.equals(see_all) && groupPosition == 0) {
                        getProductsInCategory(1);

                    } else if (o.equals(see_all) && groupPosition == 1) {
                        getProductsInCategory(2);

                    } else if (o.equals(see_all) && groupPosition == 2) {
                        getProductsInCategory(3);

                    } else {
                        Log.e("que producto es", o);
                        if (groupPosition == 0) {
                            getProductsInSubcategory(childPosition, "");

                        } else if (groupPosition == 1) {
                            getProductsInSubcategory(childPosition + 11, "");

                        } else {
                            getProductsInSubcategory(childPosition + 11 + 15, "");

                        }
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
        if (bebe || infantil) {
            listDataHeader.add(this.getString(R.string.category1));
        } else {
            listDataHeader.add(this.getString(R.string.category1));
            listDataHeader.add(this.getString(R.string.category2));
            listDataHeader.add(this.getString(R.string.category3));
        }
        getSubs();

        // Adding child data
        category1 = new ArrayList<String>();
        category2 = new ArrayList<String>();
        category3 = new ArrayList<String>();
        category1.add(this.getString(R.string.see_all));
        category2.add(this.getString(R.string.see_all));
        category3.add(this.getString(R.string.see_all));

        if (bebe || infantil) {
            listDataChild.put(listDataHeader.get(0), category1); // Header, Child data
        } else {
            Log.e("sshould cargar todas", "las subcategorias");
            listDataChild.put(listDataHeader.get(0), category1); // Header, Child data
            listDataChild.put(listDataHeader.get(1), category2);
            listDataChild.put(listDataHeader.get(2), category3);
        }

    }

    public void getSubs() {
        if (bebe) {
            getBaseSpiceManager().execute(new GetAllSubcategoriesFiltered(1, "[{\"id\":2,\"value\":\"Bebe\"}]"), new SubcategoriesFilteredRequestListener());
        } else if (infantil) {
            getBaseSpiceManager().execute(new GetAllSubcategoriesFiltered(1, "[{\"id\":2,\"value\":\"Infantil\"}]"), new SubcategoriesFilteredRequestListener());
        } else if (hombre) {
            getBaseSpiceManager().execute(new GetAllSubcategoriesFiltered(1, "[{\"id\":1,\"value\":\"Masculino\"}]"), new SubcategoriesFilteredRequestListener());
            getBaseSpiceManager().execute(new GetAllSubcategoriesFiltered(2, "[{\"id\":1,\"value\":\"Masculino\"}]"), new SubcategoriesFilteredRequestListener());
            getBaseSpiceManager().execute(new GetAllSubcategoriesFiltered(3, "[{\"id\":1,\"value\":\"Masculino\"}]"), new SubcategoriesFilteredRequestListener());
        } else if (mujer) {
            getBaseSpiceManager().execute(new GetAllSubcategoriesFiltered(1, "[{\"id\":1,\"value\":\"Femenino\"}]"), new SubcategoriesFilteredRequestListener());
            getBaseSpiceManager().execute(new GetAllSubcategoriesFiltered(2, "[{\"id\":1,\"value\":\"Femenino\"}]"), new SubcategoriesFilteredRequestListener());
            getBaseSpiceManager().execute(new GetAllSubcategoriesFiltered(3, "[{\"id\":1,\"value\":\"Femenino\"}]"), new SubcategoriesFilteredRequestListener());
        } else {
            getBaseSpiceManager().execute(new GetAllSubcategories(1), new SubcategoriesRequestListener());
            getBaseSpiceManager().execute(new GetAllSubcategories(2), new SubcategoriesRequestListener());
            getBaseSpiceManager().execute(new GetAllSubcategories(3), new SubcategoriesRequestListener());
        }
    }



    private final class CategoriesRequestListener implements RequestListener<Categories> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(GenderCategoriesActivity.this, "Fallo el request.", Toast.LENGTH_SHORT);
        }

        @Override
        public void onRequestSuccess(Categories categories) {
            onCategoriesSuccess(categories);
        }

    }

    private final class SubcategoriesRequestListener implements RequestListener<Subcategories> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(GenderCategoriesActivity.this, "Fallo el request.", Toast.LENGTH_SHORT);
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

    private void getProductsInCategoryFiltered(int id, String filter) {
        ProductsByCategoryIdFiltered req = new ProductsByCategoryIdFiltered(id, filter);
        getBaseSpiceManager().execute(req, new ProductsByCategoryIdFilteredListener());
    }

    private final class ProductsByCategoryIdFilteredListener implements RequestListener<Products> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(GenderCategoriesActivity.this, "Fallo el request.", Toast.LENGTH_SHORT);
        }

        @Override
        public void onRequestSuccess(Products products) {
            Log.d(CategoriesActivity.class.getSimpleName(), products.toString());
            onProductsByCategoryFilteredSuccess(products);
        }

    }

    public void onProductsByCategoryFilteredSuccess(Products products) {
        if(products.getTotal()==0){
            Intent intent = new Intent(this, NoResultsActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this, ProductsListActivity.class);
            intent.putExtra(ProductsListActivity.PRODUCTS_LIST, products);
            startActivity(intent);
        }

    }

    private void getProductsInSubcategory(int id, String filter) {
        ProductsBySubcategoryIdRequest req = new ProductsBySubcategoryIdRequest(id, filter);
        getBaseSpiceManager().execute(req, new ProductsBySubcategoryIdRequestListener());
    }

    private final class ProductsBySubcategoryIdRequestListener implements RequestListener<Products> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(GenderCategoriesActivity.this, "Fallo el request.", Toast.LENGTH_SHORT);
        }

        @Override
        public void onRequestSuccess(Products products) {
            Log.d(CategoriesActivity.class.getSimpleName(), products.toString());
            onProductsBySubcategoryIdSuccess(products);
        }

    }

    public void onProductsBySubcategoryIdSuccess(Products products) {
        if(products.getTotal()==0){
            Intent intent = new Intent(this, NoResultsActivity.class);

            startActivity(intent);
        }else{
            Intent intent = new Intent(this, ProductsListActivity.class);
            intent.putExtra(ProductsListActivity.PRODUCTS_LIST, products);
            startActivity(intent);
        }

    }

    private final class ProductsByCategoryRequestListener implements RequestListener<Products> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(GenderCategoriesActivity.this, "Fallo el request.", Toast.LENGTH_SHORT);
        }

        @Override
        public void onRequestSuccess(Products products) {
            Log.d(CategoriesActivity.class.getSimpleName(), products.toString());
            onProductsByCategorySuccess(products);
        }

    }


    public void onProductsByCategorySuccess(Products products) {
        if(products.getTotal()==0){
            Intent intent = new Intent(this, NoResultsActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this, ProductsListActivity.class);
            intent.putExtra(ProductsListActivity.PRODUCTS_LIST, products);
            startActivity(intent);
        }

    }

    private final class SubcategoriesFilteredRequestListener implements RequestListener<Subcategories> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(GenderCategoriesActivity.this, "Fallo el request.", Toast.LENGTH_SHORT);
        }

        @Override
        public void onRequestSuccess(Subcategories subcategories) {
            onSubcategoriesFilteredSuccess(subcategories);
        }

    }

    public void onSubcategoriesFilteredSuccess(Subcategories subcategories) {
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

            Log.d("Cargando subcategorias de la categoria: ", nombre_cat);
            if (nombre_cat.equals("Calzado")) {
                category1.add(subcategories1[i].getName());
            } else if (nombre_cat.equals("Indumentaria")) {
                Log.d("Cargando subcategorias de", "Indumentaria");
                category2.add(subcategories1[i].getName());
            } else if (nombre_cat.equals("Accesorios")) {
                Log.d("Cargando subcategorias de ", "Accesorios");
                category3.add(subcategories1[i].getName());
            }
        }


    }

}
