package com.example.android.forzahomeassignmentteams.Teams;

/**
 * Created by Eleftherios Ch. on 5/2/2018.
 */

public class Team {

    //region Fields

    private String name;
    private boolean national;
    private String country_name;

    //endregion

    //region Constructors

    public Team() {
    }

    public Team(String name, boolean national, String country_name) {
        this.name = name;
        this.national = national;
        this.country_name = country_name;
    }

    //endregion

    //region Getters & Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNational() {
        return national;
    }

    public void setNational(boolean national) {
        this.national = national;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }


    //endregion

}
