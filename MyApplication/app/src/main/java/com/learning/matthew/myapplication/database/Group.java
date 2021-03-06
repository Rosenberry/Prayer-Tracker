package com.learning.matthew.myapplication.database;

/**
 * Created by Matthew on 1/19/2015.
 */
public class Group {

    private String name, description;
    private long id;

    // empty constructor
    public Group(){}

    // constructor
    public Group(String name){setName(name);}

    // constructor
    public Group(String name, String description){
        setName(name);
        setDescription(description);
    }

    // set name
    public void setName(String name){this.name = name;}

    // set description
    public void setDescription(String description){this.description = description;}

    // set id
    public void setId(long id){this.id = id;}

    // return name
    public String getName(){return name;}

    // return description
    public String getDescription() {return description;}

    // return id
    public long getId() {return id;}
}
