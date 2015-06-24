package edu.itba.hci.chopi.activity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import edu.itba.hci.chopi.R;
import edu.itba.hci.dto.Categories;
import edu.itba.hci.dto.Category;

/**
 * Created by kevinkraus on 8/11/14.
 */
public class CategoriesAdapter extends BaseAdapter {

    private Categories categories;
    private Context context;

    public CategoriesAdapter(Context context, Categories categories) {
        this.categories = categories;
        this.context = context;
    }

    @Override
    public int getCount() {
        return categories.getCategories().length;
    }

    @Override
    public Object getItem(int i) {
        Object[] items = categories.getCategories();
        return items[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder {
        TextView txtTitle;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            view = mInflater.inflate(R.layout.category_row, null);
            holder = new ViewHolder();
            holder.txtTitle = (TextView) view.findViewById(R.id.category_title);
            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        Category category = (Category) getItem(i);

        holder.txtTitle.setText(category.getName());

        return view;
    }
}
