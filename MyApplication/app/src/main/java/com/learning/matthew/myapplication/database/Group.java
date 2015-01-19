package com.learning.matthew.myapplication.database;

/**
 * Created by Matthew on 1/19/2015.
 */
public class Group {

    private String name;
    private long id;

    // empty constructor
    public Group(){}

    // constructor
    public Group(String name){
        this.name = name;
    }

    // set name
    public void setName(String name){
        this.name = name;
    }

    // set id
    public void setId(long id){
        this.id = id;
    }

    // return name
    public String getName(){
        return name;
    }

    // return id
    public long getId() {
        return id;
    }
}
