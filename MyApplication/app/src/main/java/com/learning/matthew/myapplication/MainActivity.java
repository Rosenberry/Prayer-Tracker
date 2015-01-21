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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

import com.learning.matthew.myapplication.database.Group;
import com.learning.matthew.myapplication.database.Prayer;
import com.learning.matthew.myapplication.database.PrayerDbHelper;

public class MainActivity extends Activity{



    private ArrayList<Long> group_ids;
    private ListView listView;
    private GroupItemAdapter adapter;
    PrayerDbHelper db;
    final static int ADD_GROUP_REQUEST_CODE = 25;
    final static int VIEW_PRAYERS_REQUEST_CODE = 50;
    public static ArrayList<String> Categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        group_ids = new ArrayList<Long>();
        listView = (ListView) findViewById(R.id.listview);
        adapter = new GroupItemAdapter(this, R.layout.prayer_item, group_ids);
        listView.setAdapter(adapter);
        setListViewOnItemClickListener();
        Categories = new ArrayList<String>();
        addItemsToCategories();

        // get the database
        db = new PrayerDbHelper(getApplicationContext());
    }

    // populate Categories
    public void addItemsToCategories(){
        Categories.add("Request");
        Categories.add("Praise");
        Categories.add("Protection");
        Categories.add("Strength");
        Categories.add("Courage");
        Categories.add("Healing");
    }

    // set onItemClickListener for the ListView
    public void setListViewOnItemClickListener(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Log.e("MainActivity", "listview item selected at position " + position);
                long group_id = group_ids.get((Integer) view.getTag());
                Intent intent = new Intent(getApplicationContext(), ViewPrayersByGroup.class);
                intent.putExtra("group_id", group_id);
                Log.e("MainActivity", "starting new activity");
                startActivityForResult(intent, VIEW_PRAYERS_REQUEST_CODE);

            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == ADD_GROUP_REQUEST_CODE)
            if(resultCode == RESULT_OK){


                // get attached information from intent
                String title = data.getStringExtra(AddGroup.TITLE);
                String description = data.getStringExtra(AddGroup.DESCRIPTION);

                // add a new prayer to the users list and update adapter
                long newId = db.insertGroup(new Group(title, description));
                group_ids.add(new Long(newId));
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
        if (id == R.id.add_group) {
            Intent intent = new Intent(this, AddGroup.class);
            startActivityForResult(intent, ADD_GROUP_REQUEST_CODE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public class GroupItemAdapter extends ArrayAdapter<Long> {
        Context context;
        int resourceId;
        ArrayList<Long> group_item_id;

        public GroupItemAdapter(Context context, int resourceId, ArrayList<Long> items) {
            super(context, resourceId, items);
            this.context = context;
            this.resourceId = resourceId;
            this.group_item_id = items;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = getLayoutInflater();
                v = vi.inflate(R.layout.group_item, null);
            }

            // instantiate the views for each list item
            TextView group_title = (TextView) v.findViewById(R.id.group_title);
            TextView group_description = (TextView) v.findViewById(R.id.group_description);
            ImageButton group_delete = (ImageButton) v.findViewById(R.id.group_delete);

            // set tags, used to tell objects apart later
            group_delete.setTag(position);
            v.setTag(position);
            group_delete.setFocusable(false);

            // get the related prayer from database
            long currentGroup_id = group_item_id.get(position);
            Group dbCurrentGroup = db.getGroup(currentGroup_id);

            // set the text for each list item
            group_title.setText(dbCurrentGroup.getName());
            group_description.setText(dbCurrentGroup.getDescription());

            // set onClick to remove prayers from the list
            group_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (Integer) v.getTag();
                    long id = group_item_id.get(pos);
                    db.deleteGroup(id);
                    group_item_id.remove(pos);
                    adapter.notifyDataSetChanged();
                }
            });
        return v;
        }
    }
}
