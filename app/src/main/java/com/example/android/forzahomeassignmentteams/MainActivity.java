package com.example.android.forzahomeassignmentteams;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.forzahomeassignmentteams.Teams.Team;
import com.example.android.forzahomeassignmentteams.Utils.NetworkUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    //region Fields

    private static final String TAG = MainActivity.class.getSimpleName(); // For Logging.

    private LinearLayout linlaHeaderProgress;
    private TextView mTvHeaderProgress;
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
        mTvHeaderProgress = findViewById(R.id.tvHeaderProgress);

        // Ο Recycler View μου.
        teamsRView = findViewById(R.id.teamsRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        teamsRView.setLayoutManager(layoutManager);
        teamsRView.setHasFixedSize(true);


        fetchData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (teams.size() == 0)
            fetchData();
        else {
            resultAdapter = new ResultAdapter(MainActivity.this, teams);
            teamsRView.setAdapter(resultAdapter);
        }
    }

    private void fetchData(){
        new FetchTeamsDataTask().execute();
    }

    public class FetchTeamsDataTask extends AsyncTask<Void, Void, String>{

        @Override
        protected void onPreExecute() {

            // Loader visible.
            linlaHeaderProgress.setVisibility(View.VISIBLE);
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
            resultAdapter = new ResultAdapter(MainActivity.this, teams);

            teamsRView.setAdapter(resultAdapter);

            // Hide Loader.
            linlaHeaderProgress.setVisibility(View.GONE);
            mTvHeaderProgress.setVisibility(View.GONE);
        }
    }

}

