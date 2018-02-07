package com.example.android.forzahomeassignmentteams;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.forzahomeassignmentteams.Teams.Team;

import java.util.ArrayList;

/**
 * Created by Eleftherios Ch. on 7/2/2018.
 * Software Developer
 * lefterisxris@gmail.com
 */

public class ResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //region Fields

    private static final String TAG = ResultAdapter.class.getSimpleName(); // Για Logging.

    private LayoutInflater inflater;
    private Context context;
    private TextView teamName;
    private TextView isNational;
    private TextView teamNationality;
    Team current;
    private int currentPos = 0;

    private ArrayList<Team> teams = new ArrayList<>();

    //endregion

    public ResultAdapter(Context context, ArrayList<Team> teams){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.teams = teams;
    }




    public ResultAdapter(ArrayList<Team> teams){
        this.teams = teams;
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.result_item, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }


    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder = (MyHolder) holder;
        Team current = teams.get(position);
        myHolder.teamName.setText(current.getName());
        String nat = (current.isNational()) ? "(National Team)" : "(Club Team)";
        myHolder.isNational.setText(nat);
        myHolder.teamNationality.setText("" + current.getCountry_name());

    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        TextView teamName;
        TextView isNational;
        TextView teamNationality;


        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            teamName = itemView.findViewById(R.id.teamName);
            isNational = itemView.findViewById(R.id.isNational);
            teamNationality = itemView.findViewById(R.id.teamNationality);
        }
    }
    /*
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.result_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
//        VH vh = new VH(view, teams.get(0).getName(), teams.get(0).isNational(), teams.get(0).getCountry_name());
        VH vh = new VH(view);
        vh.teamName.setText("xaxaxa");

        return vh;
    }*/



    /*
        Team currentTeam = getItem(position);

        teamName = rView.findViewById(R.id.teamName);
        isNational = rView.findViewById(R.id.isNational);
        teamNationality = rView.findViewById(R.id.teamNationality);*/

      class VH extends RecyclerView.ViewHolder{

        TextView teamName;
        TextView isNational;
        TextView teamNationality;

        public VH(View itemView) {
            super(itemView);
        }
/*
        public VH(View itemView, String name, boolean isNationalS, String nationality){
//            super(itemView);

            teamName = itemView.findViewById(R.id.teamName);
            isNational = itemView.findViewById(R.id.isNational);
            teamNationality = itemView.findViewById(R.id.teamNationality);

            teamName.setText(name);
            isNational.setText(Boolean.toString(isNationalS));
            teamNationality.setText(nationality);
        }*/
    }

}
