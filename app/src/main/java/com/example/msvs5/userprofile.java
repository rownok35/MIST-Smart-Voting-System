package com.example.msvs5;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class userprofile extends Fragment {
    View view;
    TextView pronametxt, prodepttext, proidtxt;
    ImageView proimgview;
    DatabaseReference df;
    FirebaseDatabase fr;
    EditText searchedit;
    Button probtn;

    public userprofile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        view= inflater.inflate(R.layout.fragment_userprofile, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        pronametxt=view.findViewById(R.id.pronametxt);
        prodepttext=view.findViewById(R.id.prodepttxt);
        proidtxt=view.findViewById(R.id.proidtxt);
        proimgview=view.findViewById(R.id.proimgview);
        searchedit=view.findViewById(R.id.searchedit);
        probtn=view.findViewById(R.id.probtn);
        df=FirebaseDatabase.getInstance().getReference().child("student");
        probtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=searchedit.getText().toString().trim();
                Query query=df.orderByChild("name").equalTo(name);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds: dataSnapshot.getChildren()){
                            String a=""+ds.child("name").getValue(String.class);
                            String b=""+ds.child("department").getValue(String.class);
                            String c=""+ds.child("cg").getValue(String.class);
                            pronametxt.setText(a);
                            prodepttext.setText(b);
                            proidtxt.setText(c);

                                Picasso.get().load(String.valueOf(ds.child("imgurl").getValue())).into(proimgview);
                                /*try {
                            }catch (Exception e){
                                Picasso.get().load(R.drawable.mist).into(proimgview);
                            }*/
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


    }
}
