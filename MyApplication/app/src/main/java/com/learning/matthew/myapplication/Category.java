package com.learning.matthew.myapplication;

/**
 * Created by Matthew on 12/31/2014.
 * Enumerated type to separate prayers into categories
 */
public enum Category {
    NO_CATEGORY, REQUEST, PRAISE, PROTECTION, STRENGTH, COURAGE;

    private static String[] names = {"No Category","Request","Praise","Protection","Strength","Courage"};

    public String toString(){
        return names[this.ordinal()];
    }
}
