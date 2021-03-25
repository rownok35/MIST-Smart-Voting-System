package com.example.msvs5;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class addcandidatebyadmin extends Fragment {
    View view;
    private RecyclerView recyclerView;
    private ArrayList<DataSetFire> arrayList;
    private FirebaseRecyclerOptions<DataSetFire> options;
    private FirebaseRecyclerAdapter<DataSetFire,FirebaseViewHolder> adapter;
    private DatabaseReference df;
    public addcandidatebyadmin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_addcandidatebyadmin, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        recyclerView=view.findViewById(R.id.recylceview4);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        arrayList=new ArrayList<DataSetFire>();
        df= FirebaseDatabase.getInstance().getReference().child("student");
        df.keepSynced(true);
        options=new FirebaseRecyclerOptions.Builder<DataSetFire>().setQuery(df,DataSetFire.class).build();

        adapter=new FirebaseRecyclerAdapter<DataSetFire, FirebaseViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseViewHolder firebaseViewHolder, final int i, @NonNull final DataSetFire model) {
                firebaseViewHolder.teamone.setText(model.getName());
                firebaseViewHolder.teamthree.setText(model.getDepartment());
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
                        Intent intent=new Intent(getActivity(),approvecandidate.class);
                        intent.putExtra("name",model.getName());
                        intent.putExtra("cg",model.getCg());
                        intent.putExtra("id",model.getId());
                        intent.putExtra("department",model.getDept());
                        intent.putExtra("propaganda",model.getPropaganda());
                        intent.putExtra("imgurl",model.getImgurl());
                        intent.putExtra("event",model.getEvent());
                        intent.putExtra("post",model.getPost());
                        intent.putExtra("level",model.getLevel());
                        startActivity(intent);
                    }
                });
            }



            @NonNull
            @Override
            public FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                return new FirebaseViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.row,viewGroup,false));
            }
        };

        recyclerView.setAdapter(adapter);

    }
    @Override
    public void onStart() {
        super.onStart();

        adapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}

