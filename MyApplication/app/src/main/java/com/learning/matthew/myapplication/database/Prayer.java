package com.learning.matthew.myapplication.database;
/**
 * Created by Matthew on 12/31/2014.
 * An object that contains the information for a single prayer.
 * Each Prayer has a name, message, category, and counter for
 * the number of times it has been prayed
 */
public class Prayer {

    private long id;
    private String name, message, category;
    private int counter;

    // constructor
    public Prayer(String n, String m, String cat){
        this.name = n;
        this.message = m;
        this.category = cat;
    }

    // constructor
    public Prayer(String n, String m, String cat, long id){
        this.name = n;
        this.message = m;
        this.category = cat;
        this.id = id;
    }

    // empty constructor
    public Prayer(){
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

    // return id
    public long getId() {
        return id;
    }

    // set id
    public void setId(long id){
        this.id = id;
    }

    // set name
    public void setName(String title){
        this.name = title;
    }

    // set message
    public void setMessage(String message){
        this.message = message;
    }

    // set category
    public void setCategory(String category){
        this.category = category;
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

