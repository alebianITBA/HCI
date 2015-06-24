package edu.itba.hci.chopi.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import edu.itba.hci.chopi.R;
import edu.itba.hci.dto.Attribute;
import edu.itba.hci.dto.Product;

/**
 * Created by kevinkraus on 9/11/14.
 */
public class ProductActivity extends BaseActivity {
    public static final String PRODUCT = "PRODUCT";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        Product product = (Product) getIntent().getSerializableExtra(PRODUCT);
        Log.e(ProductActivity.class.getSimpleName(), product.toString());

        TextView title = (TextView) findViewById(R.id.item_title);
        if (product.getName() != null) {
            title.setText(product.getName());
        }

        TextView price = (TextView) findViewById(R.id.item_price);
        if (product.getPrice() != null) {
            price.setText(getString(R.string.price) + ": $" + product.getPrice());
        }

        TextView d1 = (TextView) findViewById(R.id.description1);
        TextView d2 = (TextView) findViewById(R.id.description2);
        TextView d3 = (TextView) findViewById(R.id.description3);
        TextView d4 = (TextView) findViewById(R.id.description4);
        TextView d5 = (TextView) findViewById(R.id.description5);
        TextView d6 = (TextView) findViewById(R.id.description6);
        TextView d7 = (TextView) findViewById(R.id.description7);
        TextView d8 = (TextView) findViewById(R.id.description8);
        TextView d9 = (TextView) findViewById(R.id.description9);


        List<String> list = new ArrayList<String>();


        boolean var1 = false;
        boolean var2 = false;
        boolean var3 = false;
        boolean var4 = false;
        boolean var5 = false;
        boolean var6 = false;
        boolean var7 = false;
        boolean var8 = false;
        boolean var9 = false;

        for (int i = 0; i < product.getAttributes().length; i++) {

            if (product.getAttributes()[i].getName().equals("Marca") && !var1) {
                String string = "";
                var1 = true;
                string += (getString(R.string.marca) + " ");

                for (int j = 0; j < product.getAttributes()[i].getValues().length; j++) {
                    string += (product.getAttributes()[i].getValues()[j] + ", ");
                }
                list.add(0, string);
            }
            if (product.getAttributes()[i].getName().contains("Ocasion") && !var2) {
                String string = "";
                var2 = true;
                Log.e("OCASION NOT FOUNT", "ocasionessss");
                string += (getString(R.string.ocasion_problem) + " ");
                for (int j = 0; j < product.getAttributes()[i].getValues().length; j++) {
                    Log.e("OCASION NOT FOUNT", product.getAttributes()[i].getValues()[j]);
                    string += (product.getAttributes()[i].getValues()[j] + ", ");
                }
                list.add(0, string);
            }
            if (product.getAttributes()[i].getName().contains("Genero") && !var3) {
                String string = "";
                var3 = true;
                Log.e("GENERO NOT FOUNT", "generoooo");
                string += (getString(R.string.genero_problem) + " ");
                for (int j = 0; j < product.getAttributes()[i].getValues().length; j++) {
                    Log.e("genero NOT FOUNT", product.getAttributes()[i].getValues()[j]);
                    string += (product.getAttributes()[i].getValues()[j] + ", ");
                }
                list.add(0, string);
            }
            if (product.getAttributes()[i].getName().contains("Material") && !var4) {
                String string = "";
                var4 = true;
                string += (getString(R.string.material) + " ");
                for (int j = 0; j < product.getAttributes()[i].getValues().length; j++) {
                    string += (product.getAttributes()[i].getValues()[j] + ", ");
                }
                list.add(0, string);
            }
            if (product.getAttributes()[i].getName().contains("Oferta") && !var5) {
                String string = "";
                var5 = true;
                string += (getString(R.string.oferta) + " ");
                for (int j = 0; j < product.getAttributes()[i].getValues().length; j++) {
                    string += ("Si");
                }
                list.add(0, string);
            }
            if (product.getAttributes()[i].getName().contains("Edad") && !var6) {
                String string = "";
                var6 = true;
                string += (getString(R.string.edad) + " ");
                for (int j = 0; j < product.getAttributes()[i].getValues().length; j++) {
                    string += (product.getAttributes()[i].getValues()[j] + ", ");
                }
                list.add(0, string);
            }
            if (product.getAttributes()[i].getName().contains("Nuevo") && !var7) {
                String string = "";
                var7 = true;
                string += (getString(R.string.nuevo) + " ");
                for (int j = 0; j < product.getAttributes()[i].getValues().length; j++) {
                    string += (product.getAttributes()[i].getValues()[j] + ", ");
                }
                list.add(0, string);
            }
            if (product.getAttributes()[i].getName().equals("Color") && !var8) {
                String string = "";
                var8 = true;
                string += (getString(R.string.color) + " ");
                String[] s = product.getAttributes()[i].getValues();
                for (int j = 0; j < product.getAttributes()[i].getValues().length; j++) {
                    string += (product.getAttributes()[i].getValues()[j] + ", ");
                }
                list.add(0, string);
            }
            if (product.getAttributes()[i].getName().contains("Talle") && !var9) {
                String string = "";
                var9 = true;

                string += (getString(R.string.talle) + " ");
                String[] s = product.getAttributes()[i].getValues();
                for (int j = 0; j < product.getAttributes()[i].getValues().length; j++) {
                    string += (product.getAttributes()[i].getValues()[j] + ", ");
                }
                list.add(0, string);
            }


        }
        for(String str: list){
            Log.e("--->",str);
        }
        int cont = 1;
        for (String r : list) {

            switch (cont) {
                case 1:
                    addDescription(r, d1);
                    cont++;
                    break;
                case 2:
                    addDescription(r, d2);
                    cont++;
                    break;
                case 3:
                    addDescription(r, d3);
                    cont++;
                    break;
                case 4:
                    addDescription(r, d4);
                    cont++;
                    break;
                case 5:
                    addDescription(r, d5);
                    cont++;
                    break;
                case 6:
                    addDescription(r, d6);
                    cont++;
                    break;
                case 7:
                    addDescription(r, d7);
                    cont++;
                    break;
                case 8:
                    addDescription(r, d8);
                    cont++;
                    break;
                case 9:
                    addDescription(r, d9);
                    cont++;
                    break;
            }

        }


        ImageView imageViewPrincipal = (ImageView) findViewById(R.id.item_image_principal);
        ImageView imageViewSecond = (ImageView) findViewById(R.id.item_image_second);
        ImageView imageViewThird = (ImageView) findViewById(R.id.item_image_third);
        String imageToLoad = product.getImageUrl()[0];
        if (imageToLoad != null && !imageToLoad.isEmpty()) {
            Picasso.with(this).load(imageToLoad).into(imageViewPrincipal);
        }
        if (product.getImageUrl().length > 1) {
            String imageToLoad2 = product.getImageUrl()[1];
            if (imageToLoad2 != null && !imageToLoad2.isEmpty()) {
                Picasso.with(this).load(imageToLoad2).into(imageViewSecond);
            }
        }
        if (product.getImageUrl().length > 2) {
            String imageToLoad3 = product.getImageUrl()[2];
            if (imageToLoad3 != null && !imageToLoad3.isEmpty()) {
                Picasso.with(this).load(imageToLoad3).into(imageViewThird);
            }
        }
        String marca="";
        String modifiedName="";
        for (int k = 0; k < product.getAttributes().length; k++) {
            if (product.getAttributes()[k].getName().equals("Marca")) {


                for (int j = 0; j < product.getAttributes()[k].getValues().length; j++) {
                    marca = (product.getAttributes()[k].getValues()[j]);
                }

            }
        }
        String name = product.getName();
        if(name.contains(marca)){
            modifiedName = name.replace(marca,"");
        }else{
            modifiedName = product.getName();
        }
        setTitle(modifiedName);

    }

    private void addDescription(String r, TextView c) {
        if (r.contains(getString(R.string.color))) {
            c.append(r);
            return;
        }
        if (r.contains(getString(R.string.material))) {
            c.append(r);
            return;
        }
        if (r.contains(getString(R.string.nuevo))) {
            c.append(r);
            return;
        }
        if (r.contains(getString(R.string.genero_problem))) {
            c.append(r);
            return;
        }
        if (r.contains(getString(R.string.ocasion_problem))) {
            c.append(r);
            return;
        }
        if (r.contains(getString(R.string.oferta))) {
            c.append(r);
            return;
        }
        if (r.contains(getString(R.string.marca))) {
            c.append(r);
            return;
        }
        if (r.contains(getString(R.string.talle))) {
            c.append(r);
            return;
        }
        if (r.contains(getString(R.string.edad))) {
            c.append(r);
            return;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
