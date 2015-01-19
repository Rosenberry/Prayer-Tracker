package com.learning.matthew.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.learning.matthew.myapplication.database.PrayerDbHelper;

import java.util.ArrayList;

public class MainActivity extends Activity{


   // private ArrayList<Prayer> userPrayerList;
    private ArrayList<Long> prayer_ids;
    private ListView listView;
    private PrayerItemAdapter adapter;
    PrayerDbHelper db;
    final static int ADD_PRAYER_REQUEST_CODE = 1;
    final static int ADD_PRAYER_RESULT_CODE = 1;
    public static ArrayList<String> Categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prayer_ids = new ArrayList<Long>();
        listView = (ListView) findViewById(R.id.listview);
        adapter = new PrayerItemAdapter(this, R.layout.prayer_item, prayer_ids);
        listView.setAdapter(adapter);
        db = new PrayerDbHelper(getApplicationContext());
        Categories = new ArrayList<String>();
        Categories.add("Request");
        Categories.add("Praise");
        Categories.add("Protection");
        Categories.add("Strength");
        Categories.add("Courage");
        Categories.add("Healing");
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == ADD_PRAYER_REQUEST_CODE)
            if(resultCode == ADD_PRAYER_RESULT_CODE){

                // get attached information from intent
                String name = data.getStringExtra(AddNewPrayer.NAME);
                String message = data.getStringExtra(AddNewPrayer.MESSAGE);
                String category = data.getStringExtra(AddNewPrayer.CATEGORY);


                // add a new prayer to the users list and update adapter
                long newId = db.insertPrayer(new Prayer(name, message, category));
                prayer_ids.add(new Long(newId));
                adapter.notifyDataSetChanged();
                Log.e("MainActivity", "adapter notified of change");
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


    public class PrayerItemAdapter extends ArrayAdapter<Long> {
            Context context;
            int resourceId;
            ArrayList<Long> item_id;

            public PrayerItemAdapter(Context context, int resourceId, ArrayList<Long> items) {
                super(context, resourceId, items);
                this.context = context;
                this.resourceId = resourceId;
                this.item_id = items;
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

                // get the related prayer from database
                long currentPrayer_id = item_id.get(position);
                Prayer dbCurrentPrayer = db.getPrayer(currentPrayer_id);

                // set the text for each list item
                title.setText(dbCurrentPrayer.getName());
                category.setText(dbCurrentPrayer.getCategory());
                message.setText(dbCurrentPrayer.getMessage());

            // set onClick to count number of prayers
            increasePrayerCount.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    long id = item_id.get((Integer)v.getTag());
                    db.increaseCount(id);
                    increasePrayerCount.setText("" + db.getPrayerCount(id));
                }
            });

            // set onClick to remove prayers from the list
            delete.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int pos = (Integer)v.getTag();
                    long id = item_id.get(pos);
                    db.deletePrayer(id);
                    item_id.remove(pos);
                    adapter.notifyDataSetChanged();
                }
            });
            return v;
    }
}
}
