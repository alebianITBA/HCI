package edu.itba.hci.chopi.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.Locale;

import edu.itba.hci.chopi.R;
import edu.itba.hci.chopi.api.ProductsByGenderRequest;
import edu.itba.hci.chopi.api.ProductsByCategoryRequest;
import edu.itba.hci.chopi.api.ProductsByNameRequest;
import edu.itba.hci.dto.Categories;
import edu.itba.hci.dto.Products;

public class MainActivity extends BaseActivity {
    public final static String EXTRA_MESSAGE = "edu.itba.hci.chopi";
    TextToSpeech ttobj;
    private EditText write;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setDrawer();
        write = (EditText) findViewById(R.id.search);
        ttobj = new TextToSpeech(getApplicationContext(),
                new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {

                        if (status != TextToSpeech.ERROR) {
                            ttobj.setLanguage(Locale.getDefault());
                        }
                    }
                });

    }

    @Override
    public void onPause() {
        if (ttobj != null) {
            ttobj.stop();
        }
        super.onPause();
    }

    public void speakText(View view) {
        write = (EditText) findViewById(R.id.search);
        String toSpeak = write.getText().toString();
        Toast.makeText(getApplicationContext(), toSpeak,
                Toast.LENGTH_SHORT).show();
        ttobj.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

    }




    public void startHombreProductsActivity(View view) {
        // Do something in response to button
        // getProductsInGender("[{\"id\":1,\"value\":\"Masculino\"}]");
        Intent intent = new Intent(this, GenderCategoriesActivity.class);
        intent.putExtra(GenderCategoriesActivity.man,true);
        startActivity(intent);
    }

    private void getProductsInGender(String s) {
        ProductsByGenderRequest req = new ProductsByGenderRequest(s);
        getBaseSpiceManager().execute(req, new ProductsByGenderRequestListener());
    }

    private final class ProductsByGenderRequestListener implements RequestListener<Products> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(MainActivity.this, "Fallo el request.", Toast.LENGTH_SHORT);
        }

        @Override
        public void onRequestSuccess(Products products) {
            Log.d(MainActivity.class.getSimpleName(), products.toString());
            onProductsByGenderSuccess(products);
        }

    }

    public void onProductsByGenderSuccess(Products products) {
        Intent intent = new Intent(this, ProductsListActivity.class);
        intent.putExtra(ProductsListActivity.PRODUCTS_LIST, products);
        startActivity(intent);
    }


    public void startMujerProductsActivity(View view) {
        // Do something in response to button
        //getProductsInGender("[{\"id\":1,\"value\":\"Femenino\"}]");
        Intent intent = new Intent(this, GenderCategoriesActivity.class);
        intent.putExtra(GenderCategoriesActivity.woman,true);
        startActivity(intent);
    }

    public void startBebeProductsActivity(View view) {
        // Do something in response to button
        // getProductsInGender("[{\"id\":2,\"value\":\"Bebe\"}]");
        Intent intent = new Intent(this, GenderCategoriesActivity.class);
        intent.putExtra(GenderCategoriesActivity.baby_face,true);
        startActivity(intent);
    }

    public void startInfantilProductsActivity(View view) {
        // Do something in response to button
        //getProductsInGender("[{\"id\":2,\"value\":\"Infantil\"}]");
        Intent intent = new Intent(this,GenderCategoriesActivity.class );
        intent.putExtra(GenderCategoriesActivity.infantil_face,true);
        startActivity(intent);
    }

    public void startOfertasProductsActivity(View view) {
        getProductsInGender("[{\"id\":5,\"value\":\"Oferta\"}]");
    }

    public void startAccesoriosProductsActivity(View view) {
        // Do something in response to button
        getProductsByCategory(3);
    }


    public void getProductsByCategory(int s) {
        ProductsByCategoryRequest req = new ProductsByCategoryRequest(s);
        getBaseSpiceManager().execute(req, new ProductsByCategoryRequestListener());
    }

    private final class ProductsByCategoryRequestListener implements RequestListener<Products> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(MainActivity.this, "Fallo el request.", Toast.LENGTH_SHORT);
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


    public void searchButtonMainActivity(View view) {
        EditText name = (EditText) findViewById(R.id.search);
        write = (EditText) findViewById(R.id.search);
        speakText(view);
        SystemClock.sleep(1000);
        String s = name.getText().toString();
        getProductsByName(s);
    }

    private void getProductsByName(String s) {
        ProductsByNameRequest req = new ProductsByNameRequest(s);
        getBaseSpiceManager().execute(req, new ProductsByNameRequestListener());
    }

    private final class ProductsByNameRequestListener implements RequestListener<Products> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(MainActivity.this, "Fallo el request.", Toast.LENGTH_SHORT);
        }

        @Override
        public void onRequestSuccess(Products products) {
            Log.d(MainActivity.class.getSimpleName(), products.toString());
            onProductsByNameSuccess(products);
        }
    }

    public void onProductsByNameSuccess(Products products) {
        if(products.getTotal()==0){
            Intent intent = new Intent(this, NoResultsActivity.class);

            startActivity(intent);
        }else{
            Intent intent = new Intent(this, ProductsListActivity.class);
            intent.putExtra(ProductsListActivity.PRODUCTS_LIST, products);
            startActivity(intent);
        }

    }

}