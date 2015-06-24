package edu.itba.hci.chopi.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import edu.itba.hci.chopi.ItemsListAdapter;
import edu.itba.hci.chopi.ProductsListAdapter;
import edu.itba.hci.chopi.R;
import edu.itba.hci.dto.Item;
import edu.itba.hci.dto.Items;
import edu.itba.hci.dto.Order;
import edu.itba.hci.dto.Products;

public class OrderActivity extends BaseActivity {
    private Order order;
    private ListView listView;
    public static final String ORDER = "ORDER";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        order = (Order) getIntent().getSerializableExtra(ORDER);
        listView = (ListView) findViewById(R.id.order_product_list);
        Item[] items = order.getItems();
        final ItemsListAdapter adapter = new ItemsListAdapter(this, items);

        listView.setAdapter(adapter);
        Item[] items2 =order.getItems();
        int total=0;
        for(int i = 0; i<items2.length;i++){
            total+=items2[i].getPrice() * items2[i].getQuantity();
        }
        setTitle(getApplicationContext().getString(R.string.orderTitle) + " " + order.getId());
        this.getString(R.string.total_price);

        TextView total_price = (TextView) findViewById(R.id.final_price);
        total_price.setText(this.getString(R.string.total_price) + String.valueOf(total));

        TextView latitude = (TextView) findViewById(R.id.latitude);
        latitude.setText(this.getString(R.string.latitude) + order.getLatitude());

        TextView longitude = (TextView) findViewById(R.id.longitude);
        longitude.setText(this.getString(R.string.longitude) + order.getLongitude());

        TextView status = (TextView) findViewById(R.id.status);
        String aux="";
        if(order.getStatus().equals("1")){
            status.setText(this.getString(R.string.order3) + this.getString(R.string.state1));
        }
        if(order.getStatus().equals("2")){
            status.setText(this.getString(R.string.order3) + this.getString(R.string.state2));
        }
        if(order.getStatus().equals("3")){
            status.setText(this.getString(R.string.order3) + this.getString(R.string.state3));

        }
        if(order.getStatus().equals("4")){
            status.setText(this.getString(R.string.order3) + this.getString(R.string.state4));
        }


        TextView dir = (TextView) findViewById(R.id.direction1);
        dir.setText(this.getString(R.string.order2) +" "+ order.getAddress());
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
