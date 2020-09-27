package com.app.feish.application.Patient;

public class WorkoutPlanIdName {
    public int id = 0;
    public String name = "";

    public WorkoutPlanIdName( int _id, String _name)
    {
        id = _id;
        name = _name;

    }
    public String toString()
    {
        return( name );
    }
}
