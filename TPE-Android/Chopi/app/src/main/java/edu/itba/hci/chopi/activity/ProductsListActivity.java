package edu.itba.hci.chopi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import edu.itba.hci.chopi.ProductsListAdapter;
import edu.itba.hci.chopi.R;
import edu.itba.hci.chopi.api.ProductByIdRequest;
import edu.itba.hci.dto.Product;
import edu.itba.hci.dto.ProductFromApi;
import edu.itba.hci.dto.Products;

public class ProductsListActivity extends BaseActivity {

    public static final String PRODUCTS_LIST = "PRODUCTS_LIST";
    private Products products;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);

        setDrawer();
        listView = (ListView) findViewById(R.id.products_list);

        products = (Products) getIntent().getSerializableExtra(PRODUCTS_LIST);
        final ProductsListAdapter adapter = new ProductsListAdapter(this, products);
        Log.e("productListActivity error", products.toString());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Product product = (Product) adapter.getItem(position);
                Log.w(ProductsListActivity.class.getSimpleName(), product.toString() + ", id: " + product.getId());
                getProductById(Integer.valueOf(product.getId()));
            }
        });

    }

    private void getProductById(int id) {
        Log.w(ProductsListActivity.class.getSimpleName(), "id: " + id);
        ProductByIdRequest req = new ProductByIdRequest(id);
        getBaseSpiceManager().execute(req, new ProductByIdRequestListener());
    }

    private final class ProductByIdRequestListener implements RequestListener<ProductFromApi> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {

            Toast.makeText(ProductsListActivity.this, "Fallo el request.", Toast.LENGTH_SHORT);
        }

        @Override
        public void onRequestSuccess(ProductFromApi product) {
            onProductByIdSuccess(product);
        }
    }
    public void onProductByIdSuccess(ProductFromApi product) {
        Intent intent = new Intent(this, ProductActivity.class);
        intent.putExtra(ProductActivity.PRODUCT, product.getProduct());
        startActivity(intent);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
