package com.learning.matthew.myapplication;

/**
 * Created by Matthew on 12/31/2014.
 * An object that contains the information for a single prayer.
 * Each Prayer has a name, message, category, and counter for
 * the number of times it has been prayed
 */

public class Prayer {

    private String name;
    private String message;
    private Category category;
    private int numPrayers;

    // Creates a Prayer
    // default category is NO_CATEGORY
    public Prayer(String n, String m){
        this.name = n;
        this.message = m;
        this.category = Category.NO_CATEGORY;
    }

    // Creates a Prayer
    public Prayer(String n, String m, Category cat){
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
    public Category getCategory(){
        return category;
    }

    // increases the count by one
    public void pray(){
        this.numPrayers++;
    }

    // returns the count
    public int getNumPrayers(){
        return numPrayers;
    }

    // In format:
    // [CATEGORY]
    // [numPrayers] : [name], [message]
    // TODO: reformat toString
    public String toString(){
        return category.toString() + "\n" + numPrayers + " : " + name + ", " + message;
    }
}

