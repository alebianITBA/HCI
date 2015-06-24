package edu.itba.hci.chopi;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import edu.itba.hci.dto.Attribute;
import edu.itba.hci.dto.Product;
import edu.itba.hci.dto.Products;

/**
 * Created by kevinkraus on 8/11/14.
 */
public class ProductsListAdapter extends BaseAdapter {

    private Context context;
    private Products products;

    public ProductsListAdapter(Context context, Products products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.getProducts().length;
    }

    @Override
    public Object getItem(int i) {
        Object[] items = products.getProducts();
        return items[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;

        TextView txtPrice;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = mInflater.inflate(R.layout.product_row, null);
            holder = new ViewHolder();
            holder.txtTitle = (TextView) view.findViewById(R.id.row_title);
            holder.txtPrice = (TextView) view.findViewById(R.id.row_price);

            holder.imageView = (ImageView) view.findViewById(R.id.row_thumbnail);
            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        Product product = (Product) getItem(i);
        String modifiedName;
        String [] marca={};
        Attribute[] attributes= product.getAttributes();
        for(int k =0;k<attributes.length;k++){
            if(attributes[k].getName().equals("Marca")){
                marca= attributes[k].getValues();
            }
            Log.e("ATTR", marca[0]);
        }
        Log.e("NAME",product.toString());
        String name = product.getName();
        if(name.contains(marca[0])){
            modifiedName = name.replace(marca[0],"");
        }else{
            modifiedName = product.getName();
        }
        holder.txtTitle.setText(modifiedName);
        holder.txtTitle.append(" - ");
        holder.txtTitle.append(marca[0]);
        holder.txtPrice.setText("$" + product.getPrice().toString());


        if(product.getImageUrl() != null && product.getImageUrl().length > 0) {
            Picasso.with(context).load(product.getImageUrl()[0]).into(holder.imageView);
        } else {
//            holder.imageView.setImageResource(R.drawable.imagequeued);
        }

        return view;
    }
}
