package com.example.msvs5;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;


public class profilenew extends Fragment {
    View view;
    TextView pronametxt, prodepttext, proidtxt,proleveltxt;
    TextInputLayout procgtxt,proemailtxt,prophonetxt;
    ImageView proimgview;
    DatabaseReference df;
    EditText searchedit;
    //Button probtn;
    String barcoderesult;
    String mybarcoderesult="051111407592";
    private static String studentid,proemail;

    public profilenew(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        view= inflater.inflate(R.layout.fragment_profilenew, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pronametxt = view.findViewById(R.id.pronametxt);
        prodepttext = view.findViewById(R.id.prodepttxt);
        proidtxt = view.findViewById(R.id.proidtxt);
        proleveltxt = view.findViewById(R.id.proleveltxt);
        procgtxt = view.findViewById(R.id.procgtxt);
        proemailtxt = view.findViewById(R.id.proemailtxt);
        prophonetxt = view.findViewById(R.id.prophonetxt);
        proimgview = view.findViewById(R.id.proimgview);
        searchedit = view.findViewById(R.id.searchedit);
        //probtn = findViewById(R.id.probtn);



        //pronametxt.setText(st.getName());
        //proidtxt.setText(st.getId());
        //prodepttext.setText(st.getDept());
        try {
            Class<?> cls=Class.forName("com.example.msvs5.MainActivity");
            Field field=cls.getDeclaredField("barcodeID");
            field.setAccessible(true);
            Object object=field.get(new MainActivity());
            barcoderesult=(String)object;
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        df = FirebaseDatabase.getInstance().getReference().child("Approveduser");
        Query query = df.orderByChild("barcodeid").equalTo(barcoderesult);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String name = "" + ds.child("name").getValue(String.class);
                    studentid = "" + ds.child("id").getValue(String.class);
                    String dept = "" + ds.child("dept").getValue(String.class);
                    String d=ds.child("imgurl").getValue(String.class);
                    proemail=ds.child("email").getValue(String.class);
                    String cg=ds.child("cg").getValue(String.class);
                    String level=ds.child("level").getValue(String.class);
                    String phone=ds.child("phone").getValue(String.class);
                    pronametxt.setText(name);
                    prodepttext.setText(dept);
                    proidtxt.setText(studentid);
                    proleveltxt.setText(level);
                    procgtxt.getEditText().setText(cg);
                    proemailtxt.getEditText().setText(proemail);
                    prophonetxt.getEditText().setText(phone);
                    Picasso.get().load(d).into(proimgview);

                    //Picasso.get().load(String.valueOf(ds.child("imgurl").getValue())).into(proimgview);
                    //try {
                    //} catch (Exception e) {
                    //  Picasso.get().load(R.drawable.mist).into(proimgview);
                    //}
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

