package com.example.msvs5;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class edituser extends Fragment {

View view;
Button editbtn;
TextInputLayout idedit;
String stdid;
    public edituser() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view= inflater.inflate(R.layout.fragment_edituser, container, false);
       return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        idedit=view.findViewById(R.id.idedit5);
        editbtn=view.findViewById(R.id.editidbtn);
        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stdid=idedit.getEditText().getText().toString().trim();
                Intent intent=new Intent(getActivity(),Edituserlist.class);
                intent.putExtra("stdid",stdid);
                startActivity(intent);
            }
        });

    }
}
