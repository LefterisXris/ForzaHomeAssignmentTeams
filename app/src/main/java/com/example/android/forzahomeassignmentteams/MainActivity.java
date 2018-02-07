package com.example.android.forzahomeassignmentteams;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.forzahomeassignmentteams.Teams.Team;
import com.example.android.forzahomeassignmentteams.Utils.AppStatus;
import com.example.android.forzahomeassignmentteams.Utils.NetworkUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Eleftherios Ch. on 7/2/2018.
 * Software Developer
 * lefterisxris@gmail.com
 */

public class MainActivity extends AppCompatActivity {

    //region Fields

    private static final String TAG = MainActivity.class.getSimpleName(); // For Logging.

    private LinearLayout linlaHeaderProgress;
    private TextView mTvHeaderProgress;
    private TextView mTvErorr;
    private ProgressBar mPbar;
    private RecyclerView teamsRView;

    private ArrayList<Team> teams = new ArrayList<>();
    private ResultAdapter resultAdapter;

    private Toast mToast;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linlaHeaderProgress = findViewById(R.id.linlaHeaderProgress);
        mTvHeaderProgress = findViewById(R.id.tvHeaderProgress);//pbHeaderProgress
        mPbar = findViewById(R.id.pbHeaderProgress);
        mTvErorr = findViewById(R.id.error_01);

        // My Recycler View.
        teamsRView = findViewById(R.id.teamsRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        teamsRView.setLayoutManager(layoutManager);
        teamsRView.setHasFixedSize(true);

        // Check internet connection.
        if (AppStatus.getInstance(this).isOnline()) {

            // Get the data and load RecyclerView.
            fetchData();
        } else {
            Toast.makeText(this,"You are not online!!!!",Toast.LENGTH_LONG).show();
            Log.v(TAG, "############################You are not online!!!!");

            // Hide Loader.
            changeLoaderVisibility(false);
            mTvHeaderProgress.setText("Check your internet connection...");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (teams.size() == 0)
            fetchData();
        else {
            createAndAddRecyclerView();
        }
    }

    private void fetchData(){
        new FetchTeamsDataTask().execute();
    }

    //region Options

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Options handling.
        switch (item.getItemId()){
            case R.id.action_refresh:
                //refresh;
                fetchData();
                Toast toast = Toast.makeText(this.getBaseContext(), "refreshing...", Toast.LENGTH_LONG );
                toast.show();
                break;

            case R.id.addData:

                // Add data multiple times for testing the recycler view.
                teams.addAll(teams);
                createAndAddRecyclerView();
                Toast toast1 = Toast.makeText(this.getBaseContext(), "Adding teams..", Toast.LENGTH_LONG );
                toast1.show();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);

    }

    //endregion

    //region AsyncTask

    public class FetchTeamsDataTask extends AsyncTask<Void, Void, String>{

        @Override
        protected void onPreExecute() {

            // Loader visible.
            changeLoaderVisibility(true);

        }

        @Override
        protected String doInBackground(Void... voids) {

            String response = "";

            // Try to fetch the response as a String. (Actually a Json)
            try {
                response = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.buildUrl());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error: " + e.getMessage());
            }

            Log.v(TAG, "Response is: " + response);

            return response;
        }

        @Override
        protected void onPostExecute(String response) {

            if (response.equals("")){
                Log.v(TAG, "Error, empty response ");
                return;
            }

            teams = new Gson().fromJson(response, new TypeToken<ArrayList<Team>>(){}.getType());

            // Adapter set.
            createAndAddRecyclerView();

            // Hide Loader.
            changeLoaderVisibility(false);
        }
    }

    //endregion

    private void changeLoaderVisibility(boolean visible){

        if (visible){
            this.mTvHeaderProgress.setVisibility(View.VISIBLE);
            this.mPbar.setVisibility(View.VISIBLE);
            mTvErorr.setVisibility(View.GONE);
            linlaHeaderProgress.setVisibility(View.VISIBLE);
        }
        else{
            this.mTvHeaderProgress.setVisibility(View.GONE);
            this.mPbar.setVisibility(View.GONE);
            mTvErorr.setVisibility(View.GONE);
            linlaHeaderProgress.setVisibility(View.GONE);
        }

    }

    private void createAndAddRecyclerView(){

        resultAdapter = new ResultAdapter(MainActivity.this, teams, new ResultAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Team team) {
                Toast.makeText(MainActivity.this, team.getName() + " Clicked", Toast.LENGTH_LONG).show();
            }
        });

        teamsRView.setAdapter(resultAdapter);

    }

}
