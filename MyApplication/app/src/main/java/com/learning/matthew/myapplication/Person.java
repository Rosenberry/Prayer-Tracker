package com.learning.matthew.myapplication;

import java.util.ArrayList;

/**
 * Created by Matthew on 12/31/2014.
 * An object to contain a list of prayers and retain information for a single user.
 * Each Person has a list of Prayers and a name.
 */
public class Person {

    protected ArrayList<Prayer> prayers = new ArrayList<Prayer>();
    private String firstName;

    public Person(String n1){
        firstName = n1;
    }

    // add a new Prayer to the list
    public void createPrayer(String title, String message, Category category){
        prayers.add(new Prayer(title, message, category));
    }

    // return the list of prayers
    public ArrayList<Prayer> getPrayerList(){
        return prayers;
    }

    // remove a prayer at the given position from the list
    public void removePrayer(int position){ prayers.remove(position); }

    // return the Prayer at a given position
    public Prayer getPrayer(int position){
        return prayers.get(position);
    }

    // return a string array with the titles
    // of every prayer this person holds
    public ArrayList<String> getPrayerTitles(){
        ArrayList<String> prayerTitles = new ArrayList<String>();
        for(int i = 0; i < prayers.size(); i++){
            prayerTitles.add(prayers.get(i).getName());
        }
        return prayerTitles;
    }

    // return a Prayer[] of all Prayers in the given category
    public Prayer[] getPrayersByCategory(Category category){
        Prayer[] result = new Prayer[getNumPrayersByCategory(category)];
        int index = 0;
        int limit = prayers.size();
        for(int i = 0; i < limit; i++)
            if(prayers.get(i).getCategory() == category){
                result[index] = prayers.get(i);
                index++;
            }
        return result;
    }

    // return number of prayers in a given category
    public int getNumPrayersByCategory(Category category){
        int count = 0;
        int limit = prayers.size();
        for(int i = 0; i < limit; i++)
            if(prayers.get(i).getCategory() == category)
                count++;
        return count;
    }
}
