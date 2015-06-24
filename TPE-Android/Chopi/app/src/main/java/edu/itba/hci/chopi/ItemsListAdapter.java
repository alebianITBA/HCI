package edu.itba.hci.chopi;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import edu.itba.hci.dto.Item;
import edu.itba.hci.dto.Product;
import edu.itba.hci.dto.Products;

/**
 * Created by kevinkraus on 19/11/14.
 */
public class ItemsListAdapter extends BaseAdapter {

    private Context context;
    private Item[] items;

    public ItemsListAdapter(Context context, Item[] items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int i) {

        return items[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        TextView subtitle;
        TextView txtPrice;
        TextView available;
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


        Item item = (Item) getItem(i);

        holder.txtTitle.setText(item.getProduct().getName());
        holder.txtPrice.setText(context.getString(R.string.price)+": $" + item.getPrice().toString() +"     "+ context.getString(R.string.quantity) +" " +item.getQuantity());

        if(item.getProduct().getImageUrl() != null) {
            Picasso.with(context).load(item.getProduct().getImageUrl()).into(holder.imageView);
        } else {
//            holder.imageView.setImageResource(R.drawable.imagequeued);
        }

        return view;
    }
}
