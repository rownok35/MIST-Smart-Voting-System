package com.example.msvs5;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FirebaseViewHolder extends RecyclerView.ViewHolder {
    public TextView teamone,teamtwo,teamthree;
    public TextView teamname,teamid,teamvoteno;
    public ImageView candidateimg,candimagex,voterimg;
    public Button deletebtn,deletecandbtn;
    public TextView teamname1,teamcg,teamdept;
    public TextView teamx,teamy,teamz;
    public FirebaseViewHolder(@NonNull View itemView) {
        super(itemView);
        teamone=itemView.findViewById(R.id.teamone);
        teamtwo=itemView.findViewById(R.id.teamtwo);
        teamthree=itemView.findViewById(R.id.teamthree);
        teamname=itemView.findViewById(R.id.teamname);
        teamid=itemView.findViewById(R.id.teamid);
        teamvoteno=itemView.findViewById(R.id.teamvoteno);
        teamname1=itemView.findViewById(R.id.teamname1);
        teamcg=itemView.findViewById(R.id.teamcg);
        teamdept=itemView.findViewById(R.id.teamdept);
        deletebtn=itemView.findViewById(R.id.deletebtn);
        deletecandbtn=itemView.findViewById(R.id.deletecandbtn);
        candidateimg=itemView.findViewById(R.id.candimage);
        teamx=itemView.findViewById(R.id.teamx);
        teamy=itemView.findViewById(R.id.teamy);
        teamz=itemView.findViewById(R.id.teamz);
        candimagex=itemView.findViewById(R.id.candimagex);
        voterimg=itemView.findViewById(R.id.voterimage);
    }
}
