package com.learning.matthew.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.learning.matthew.myapplication.database.Person;
import com.learning.matthew.myapplication.database.Prayer;

import java.util.ArrayList;

public class MainActivity extends Activity{

    private Person user;
    private ArrayList<Prayer> userPrayerList;
    private ListView listView;
    private PrayerItemAdapter adapter;
    final static int ADD_PRAYER_REQUEST_CODE = 1;
    final static int ADD_PRAYER_RESULT_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = new Person("");
        userPrayerList = user.getPrayerList();
        listView = (ListView) findViewById(R.id.listview);
        adapter = new PrayerItemAdapter(this, R.layout.prayer_item, userPrayerList);
        listView.setAdapter(adapter);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == ADD_PRAYER_REQUEST_CODE)
            if(resultCode == ADD_PRAYER_RESULT_CODE){

                // get attached information from intent
                String name = data.getStringExtra(AddNewPrayer.NAME);
                String message = data.getStringExtra(AddNewPrayer.MESSAGE);
                String category = data.getStringExtra(AddNewPrayer.CATEGORY);

                // add a new prayer to the users list and update adapter
                user.createPrayer(name, message, category);
                adapter.notifyDataSetChanged();
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
     public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.add_prayer) {
            Intent intent = new Intent(this, AddNewPrayer.class);
            startActivityForResult(intent, ADD_PRAYER_REQUEST_CODE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public class PrayerItemAdapter extends ArrayAdapter<Prayer> {
            Context context;
            int resourceId;
            ArrayList<Prayer> item;

            public PrayerItemAdapter(Context context, int resourceId, ArrayList<Prayer> items) {
                super(context, resourceId, items);
                this.context = context;
                this.resourceId = resourceId;
                this.item = items;
            }

            public View getView(int position, View convertView, ViewGroup parent) {
                View v = convertView;
                if (v == null) {
                    LayoutInflater vi = getLayoutInflater();
                    v = vi.inflate(R.layout.prayer_item, null);
                }

                // instantiate the views for each list item
                TextView title = (TextView) v.findViewById(R.id.title);
                TextView category = (TextView) v.findViewById(R.id.category);
                TextView message = (TextView) v.findViewById(R.id.message);
                ImageButton delete = (ImageButton) v.findViewById(R.id.delete);
                final Button increasePrayerCount = (Button) v.findViewById(R.id.counterButton);

                // set tags, used to tell objects apart later
                delete.setTag(position);
                increasePrayerCount.setTag(position);

                // get the related prayer from user
                Prayer currentPrayer = user.getPrayer(position);

                // set the text for each list item
                title.setText(currentPrayer.getName());
                category.setText(currentPrayer.getCategory().toString());
                message.setText(currentPrayer.getMessage());

            // set onClick to count number of prayers
            increasePrayerCount.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int pos = (Integer)v.getTag();
                    user.getPrayer(pos).pray();
                    increasePrayerCount.setText("" + user.getPrayer(pos).getNumPrayers());
                }
            });

            // set onClick to remove prayers from the list
            delete.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int pos = (Integer)v.getTag();
                    user.removePrayer(pos);
                    adapter.notifyDataSetChanged();
                }
            });
            return v;
    }
}
}
