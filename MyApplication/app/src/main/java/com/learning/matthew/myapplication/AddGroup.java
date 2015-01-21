package com.learning.matthew.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class AddGroup extends Activity {

    protected final static String TITLE = "t";
    protected final static String DESCRIPTION = "d";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_group, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    // send the information needed for a new qroup back to main
    public void sendGroupInformation(View view){
        EditText group_title = (EditText)findViewById(R.id.edit_group_title);
        EditText group_message = (EditText)findViewById(R.id.edit_group_description);

        // get the intent
        Intent returnIntent = getIntent();

        // attach values to intent
        returnIntent.putExtra(TITLE, group_title.getText().toString());
        returnIntent.putExtra(DESCRIPTION, group_message.getText().toString());


        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
