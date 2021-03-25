package com.example.msvs5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;

import com.example.msvs5.DataSetFire;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Edituserlist extends AppCompatActivity {
    String editstdid;
    private RecyclerView recyclerView;
    private ArrayList<DataSetFire> arrayList;
    private FirebaseRecyclerOptions<DataSetFire> options;
    private FirebaseRecyclerAdapter<DataSetFire,FirebaseViewHolder> adapter;
    private DatabaseReference df;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edituserlist);
        editstdid=getIntent().getStringExtra("stdid");

        recyclerView = findViewById(R.id.recyclerview7);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        df = FirebaseDatabase.getInstance().getReference().child("Approveduser");
        Query query=df.orderByChild("level").equalTo(editstdid);
        query.keepSynced(true);
        options = new FirebaseRecyclerOptions.Builder<DataSetFire>().setQuery(query, DataSetFire.class).build();

        adapter = new FirebaseRecyclerAdapter<DataSetFire, FirebaseViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseViewHolder firebaseViewHolder, int i, @NonNull final DataSetFire model) {
                firebaseViewHolder.teamname.setText(model.getName());
                firebaseViewHolder.teamid.setText(model.getId());
                firebaseViewHolder.teamvoteno.setText(model.getDept());
                Picasso.get().load(model.getImgurl()).into(firebaseViewHolder.voterimg);
                firebaseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(Edituserlist.this,finaledit.class);
                        intent.putExtra("name",model.getName());
                        intent.putExtra("id",model.getId());
                        intent.putExtra("level",model.getLevel());
                        intent.putExtra("key",model.getKey());
                        intent.putExtra("email",model.getEmail());
                        intent.putExtra("phone",model.getPhone());
                        intent.putExtra("cg",model.getCg());
                        startActivity(intent);
                    }
                });

            }


            @NonNull
            @Override
            public FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                return new FirebaseViewHolder(LayoutInflater.from(Edituserlist.this).inflate(R.layout.rowforvote, viewGroup, false));
            }
        };

        recyclerView.setAdapter(adapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
