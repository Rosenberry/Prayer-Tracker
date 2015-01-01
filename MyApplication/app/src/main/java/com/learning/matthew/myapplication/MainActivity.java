package com.learning.matthew.myapplication;

import android.app.Activity;
import android.content.Context;
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

import java.util.ArrayList;

public class MainActivity extends Activity {

    private Person user;
    private ListView listView;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = new Person("Matthew");
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
            Log.d("Main Activity", "Prayer Added");

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public class CustomAdapter extends ArrayAdapter<String> {
        Context context;
        int resourceId;
        ArrayList<String> item;

        public CustomAdapter(Context context, int resourceId, ArrayList<String> items) {
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

            TextView title = (TextView) v.findViewById(R.id.title);
            TextView category = (TextView) v.findViewById(R.id.category);
            TextView message = (TextView) v.findViewById(R.id.message);
            ImageButton delete = (ImageButton) v.findViewById(R.id.delete);
            Button increasePrayerCount = (Button) v.findViewById(R.id.counterButton);

            // TODO: set tags for buttons
            // TODO: delete button
            increasePrayerCount.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int pos = (Integer)v.getTag();
                    // TODO: increase the prayer count for the prayer at pos
                }
            });

            return v;
        }
    }
}
