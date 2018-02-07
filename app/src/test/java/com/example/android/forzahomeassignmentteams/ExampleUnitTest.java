package com.example.android.forzahomeassignmentteams;

import com.example.android.forzahomeassignmentteams.Teams.Team;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest{

    //region Fields

    private ArrayList<Team> teams;
    private String json;

    //endregion

    //region Constructor

    public ExampleUnitTest() {

        this.json = "[ { \"name\": \"Arsenal FC\", \"national\": false, \"country_name\": \"England\" }, { \"name\": \"FC Barcelona\", \"national\": false, \"country_name\": \"Spain\" }, { \"name\": \"Sweden\", \"national\": true, \"country_name\": \"Sweden\" }, { \"name\": \"Inter Milan\", \"national\": false, \"country_name\": \"Italy\" } ]";

        this.teams = new Gson().fromJson(this.json, new TypeToken<ArrayList<Team>>(){}.getType());
    }

    //endregion

    //region Tests

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void filledWithBools(){

        // For each team if it has any other value than logic, in isNational field, fails.
        for (Team team: teams){
            assertTrue( "Team type (if isNational or not) has to be defined.", (team.isNational() || !team.isNational()));
        }
    }

    @Test
    public void natiaonalityAgreement(){

        // For each team if it is national team, the name should be equal to the national name.
        for (Team team: teams){
            if (team.isNational())
                assertEquals("A national team name should have the country's name.", team.getName(), team.getCountry_name());
        }
    }

    @Test
    public void notNullTeam(){

        // Each team should have all these 3 fields filled. Empty or null fields could cause problems.
        for (Team team: teams){
            assertTrue("Team name should be defined properly.", (team.getName()!= null || !team.getName().equals("")));
            assertTrue( "Team origin country should be defined properly.", (team.getCountry_name()!= null || !team.getCountry_name().equals("")));
        }
        filledWithBools();
    }

    @Test
    public void canDeserializeJson(){

        boolean flag;

        // If we can't get the arraylist of Teams from the json, we got a problem.
        try {
            this.teams = new Gson().fromJson(this.json, new TypeToken<ArrayList<Team>>(){}.getType());
            flag = true;
        }catch (Exception ex){
            flag = false;
        }

        assertTrue("Cannot deserialize Json..", flag);
    }

    @Test
    public void isJsonValid(){

        boolean flag;

        // What if the json response is not valid?
        try{
            new Gson().fromJson(this.json, Object.class);
            flag = true;
        } catch (com.google.gson.JsonSyntaxException ex){
            flag = false;
        }

        assertTrue("Not valid Json information.", flag);
    }

    //endregion

}