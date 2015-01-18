package com.learning.matthew.myapplication.database;
/**
 * Created by Matthew on 12/31/2014.
 * An object to contain a list of prayers and retain information for a single user.
 * Each Person has a list of Prayers and a name.
 */

import java.util.ArrayList;

public class Person {

    private ArrayList<Prayer> prayers;

    private String firstName;

    public Person(String n1){
        firstName = n1;
        prayers = new ArrayList<Prayer>();

    }

    public ArrayList<Prayer> getPrayerList(){
        return prayers;
    }

    // add a new Prayer to the list
    public void createPrayer(String title, String message, String category){
        //TODO: trim whitespace
        Prayer newPrayer = new Prayer(title, message, category);
        prayers.add(newPrayer);
    }

    // remove a prayer at the given position from the list
    public void removePrayer(int position){ prayers.remove(position); }

    // return the Prayer at a given position
    public Prayer getPrayer(int position){
        return prayers.get(position);
    }

    // return a string array with the titles
    // of every prayer this person holds
    public String[] getPrayerTitles(){
        int limit = prayers.size();
        String[] prayerTitles = new String[limit];
        for(int i = 0; i < limit; i++){
            prayerTitles[i] = prayers.get(i).getName();
        }
        return prayerTitles;
    }

    // return a Prayer[] of all Prayers in the given category
    public Prayer[] getPrayersByCategory(String category){
        Prayer[] result = new Prayer[getNumPrayersByCategory(category)];
        int index = 0;
        int limit = prayers.size();
        for(int i = 0; i < limit; i++)
            if(prayers.get(i).getCategory().equals(category)){
                result[index] = prayers.get(i);
                index++;
            }
        return result;
    }

    // return number of prayers in a given category
    public int getNumPrayersByCategory(String category){
        int count = 0;
        int limit = prayers.size();
        for(int i = 0; i < limit; i++)
            if(prayers.get(i).getCategory().equals(category))
                count++;
        return count;
    }
}
