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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class addcandidate extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<DataSetFire> arrayList;
    private FirebaseRecyclerOptions<DataSetFire> options;
    private FirebaseRecyclerAdapter<DataSetFire,FirebaseViewHolder> adapter;
    private DatabaseReference df;
    String votecan,postcan;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_addcandidate);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        votecan=getIntent().getStringExtra("event");
        postcan=getIntent().getStringExtra("post");
        recyclerView=findViewById(R.id.recyclerview2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList=new ArrayList<DataSetFire>();
        df= FirebaseDatabase.getInstance().getReference().child("student").child(votecan).child(postcan);
        df.keepSynced(true);
        options=new FirebaseRecyclerOptions.Builder<DataSetFire>().setQuery(df,DataSetFire.class).build();

        adapter=new FirebaseRecyclerAdapter<DataSetFire, FirebaseViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseViewHolder firebaseViewHolder, final int i, @NonNull final DataSetFire model) {
                firebaseViewHolder.teamone.setText(model.getName());
                firebaseViewHolder.teamthree.setText(model.getDept());
                firebaseViewHolder.teamtwo.setText(model.getId());
                Picasso.get().load(model.getImgurl()).into(firebaseViewHolder.candidateimg);
                firebaseViewHolder.deletebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("student").child(getRef(i).getKey());
                        databaseReference.removeValue();
                    }
                });
                firebaseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(addcandidate.this,approvecandidate.class);
                        intent.putExtra("name",model.getName());
                        intent.putExtra("cg",model.getCg());
                        intent.putExtra("id",model.getId());
                        intent.putExtra("level",model.getLevel());
                        intent.putExtra("department",model.getDept());
                        intent.putExtra("propaganda",model.getPropaganda());
                        intent.putExtra("event",model.getEvent());
                        intent.putExtra("post",model.getPost());
                        intent.putExtra("imgurl",model.getImgurl());
                        startActivity(intent);
                    }
                });
            }



            @NonNull
            @Override
            public FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                return new FirebaseViewHolder(LayoutInflater.from(addcandidate.this).inflate(R.layout.row,viewGroup,false));
            }
        };

        recyclerView.setAdapter(adapter);

    }
}
