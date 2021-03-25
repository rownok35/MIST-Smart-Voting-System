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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Requestlist extends Fragment {

    View view;
    private RecyclerView recyclerView;
    private ArrayList<DataSetFire> arrayList;
    private FirebaseRecyclerOptions<DataSetFire> options;
    private FirebaseRecyclerAdapter<DataSetFire,FirebaseViewHolder> adapter;
    private DatabaseReference df;
    public Requestlist() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_requestlist, container, false);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView=view.findViewById(R.id.recylceview1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        //refreshbtn=view.findViewById(R.id.refreshbtn);

        arrayList=new ArrayList<DataSetFire>();


        df= FirebaseDatabase.getInstance().getReference().child("RequestedUser");
        //Query query=df.orderByChild("name").equalTo(text1);
        df.keepSynced(true);
        options=new FirebaseRecyclerOptions.Builder<DataSetFire>().setQuery(df,DataSetFire.class).build();
        adapter=new FirebaseRecyclerAdapter<DataSetFire, FirebaseViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseViewHolder firebaseViewHolder,final int i, @NonNull final DataSetFire dataSetFire) {
                firebaseViewHolder.teamone.setText(dataSetFire.getName());
                firebaseViewHolder.teamtwo.setText(dataSetFire.getId());
                firebaseViewHolder.teamthree.setText(dataSetFire.getDept());
                Picasso.get().load(dataSetFire.getImgurl()).into(firebaseViewHolder.candidateimg);
                firebaseViewHolder.deletebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("RequestedUser").child(getRef(i).getKey());
                        databaseReference.removeValue();
                    }
                });
                firebaseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(getActivity(),approve.class);
                        intent.putExtra("name",dataSetFire.getName());
                        intent.putExtra("id",dataSetFire.getId());
                        intent.putExtra("dept",dataSetFire.getDept());
                        intent.putExtra("email",dataSetFire.getEmail());
                        intent.putExtra("barcodeid",dataSetFire.getBarcodeid());
                        intent.putExtra("imgurl",dataSetFire.getImgurl());
                        intent.putExtra("phone",dataSetFire.getPhone());
                        intent.putExtra("level",dataSetFire.getLevel());
                        intent.putExtra("cg",dataSetFire.getCg());
                        intent.putExtra("key",dataSetFire.getKey());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new FirebaseViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.row,parent,false));
            }
        };
        //adapter.notifyDataSetChanged();
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
