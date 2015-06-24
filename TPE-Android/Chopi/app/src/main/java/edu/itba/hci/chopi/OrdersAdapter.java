package edu.itba.hci.chopi;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import edu.itba.hci.dto.Order;
import edu.itba.hci.dto.Orders;

/**
 * Created by alebezdjian on 13/11/14.
 */

public class OrdersAdapter extends BaseAdapter {

    private Orders orders;
    private Context context;

    public OrdersAdapter(Context context, Orders orders) {
        this.context = context;
        this.orders = orders;
    }

    @Override
    public int getCount() {
        return orders.getOrders().length;
    }

    @Override
    public Object getItem(int i) {
        Object[] items = orders.getOrders();
        return items[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder {
        TextView txtId;
        TextView txtAddress;
        TextView txtState;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            view = mInflater.inflate(R.layout.order_row, null);
            holder = new ViewHolder();
            holder.txtId = (TextView) view.findViewById(R.id.order_id);
            holder.txtAddress = (TextView) view.findViewById(R.id.order_address);
            holder.txtState = (TextView) view.findViewById(R.id.order_state);
            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }
        Order order = (Order)getItem(i);

        holder.txtId.setText(idToString(order));
        holder.txtAddress.setText(addressToString(order));
        holder.txtState.setText(stateToString(order));

        return view;
    }

    private String idToString(Order order){
        String aux;
        aux = context.getString(R.string.order1);
        aux += " ";
        aux += order.getId();
        return aux;
    }

    private String addressToString(Order order){
        String aux;
        aux = context.getString(R.string.order2);
        aux += " ";
        aux += order.getAddress();
        return aux;
    }

    private String stateToString(Order order){
        String aux;
        aux = context.getString(R.string.order3);
        aux += " ";
        String status = order.getStatus();
        if(status!=null){
            if(status.equals("1")){
                aux += context.getString(R.string.state1);
            }
            if(status.equals("2")){
                aux += context.getString(R.string.state2);
            }
            if(status.equals("3")){
                aux += context.getString(R.string.state3);
            }
            if(status.equals("4")){
                aux += context.getString(R.string.state4);
            }
        }
        return aux;
    }

}

