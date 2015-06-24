package edu.itba.hci.chopi;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DrawerAdapter extends BaseAdapter {
	private Activity activity;
	ArrayList<DrawerItem> darray;
	
	public DrawerAdapter(Activity activity, ArrayList<DrawerItem> darray){
		super();
		this.activity=activity;
		this.darray=darray;
	}
	
	@Override
	public int getCount() {
		return darray.size();
	}

	@Override
	public Object getItem(int position) {
		return darray.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public static class Row {
		TextView itmTitle;
		ImageView itmIcon;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Row view;
		LayoutInflater inflator = activity.getLayoutInflater();
		if(convertView==null){
			view = new Row();
			DrawerItem itm = darray.get(position);
			convertView = inflator.inflate(R.layout.drawer_item, null);
			view.itmTitle = (TextView) convertView.findViewById(R.id.ditem_text);
			view.itmTitle.setText(itm.getTitulo());
			view.itmIcon = (ImageView) convertView.findViewById(R.id.icon);
			view.itmIcon.setImageResource(itm.getIcono());
			convertView.setTag(view);
		} else {
			view = (Row) convertView.getTag();
		}
		return convertView;
	}

}
