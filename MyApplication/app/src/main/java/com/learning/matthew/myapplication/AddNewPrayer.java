package com.learning.matthew.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.learning.matthew.myapplication.CustomObjects.Person;


public class AddNewPrayer extends Activity {

    private final int RESULT_CODE = MainActivity.ADD_ACTIVITY_RESULT_CODE;
    private EditText editName;
    private EditText editMessage;
    private Spinner categorySpinner;
    private Button sendButton;
    public static final String NAME = "n";
    public static final String MESSAGE = "m";
    public static final String CATEGORY = "c";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_prayer);

        categorySpinner = (Spinner) findViewById(R.id.add_spinner);
        addItemsOnCategorySpinner();
    }

    // gives the Spinner an adapter
    public void addItemsOnCategorySpinner() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Person.Categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(dataAdapter);
    }

    // return the data for a new prayer back to the main activity
    public void sendInformation(View view){

        editName = (EditText)findViewById(R.id.editName);
        editMessage = (EditText)findViewById(R.id.editMessage);

        // get the intent
        Intent returnIntent = getIntent();

        // attach values to intent
        returnIntent.putExtra(NAME, editName.getText().toString());
        returnIntent.putExtra(MESSAGE, editMessage.getText().toString());
        returnIntent.putExtra(CATEGORY, String.valueOf(categorySpinner.getSelectedItem()));

        // set the result
        setResult(RESULT_CODE, returnIntent);

        // leave the activity
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_new_prayer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

}
