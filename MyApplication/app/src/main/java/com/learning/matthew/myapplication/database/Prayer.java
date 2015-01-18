package com.learning.matthew.myapplication.database;
/**
 * Created by Matthew on 12/31/2014.
 * An object that contains the information for a single prayer.
 * Each Prayer has a name, message, category, and counter for
 * the number of times it has been prayed
 */
public class Prayer {

    private String name, message, category;
    private int counter;

    // Creates a Prayer
    public Prayer(String n, String m, String cat){
        this.name = n;
        this.message = m;
        this.category = cat;
    }

    // returns name
    public String getName(){
        return name;
    }

    // returns message
    public String getMessage(){
        return message;
    }

    // returns category
    public String getCategory(){
        return category;
    }

    // increases the count by one
    public void pray(){
        this.counter++;
    }

    // returns the count
    public int getNumPrayers(){
        return counter;
    }
}

