package com.example.msvs5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;

public class Profile_of_user extends AppCompatActivity {
    TextView pronametxt, prodepttext, proidtxt,proleveltxt;
    TextInputLayout procgtxt,proemailtxt,prophonetxt;
    ImageView proimgview;
    DatabaseReference df;
    EditText searchedit;
    //Button probtn;
    String barcoderesult;
    private static String studentid,proemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_of_user);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //student st=(student)getApplicationContext();
        pronametxt = findViewById(R.id.pronametxt);
        prodepttext = findViewById(R.id.prodepttxt);
        proidtxt = findViewById(R.id.proidtxt);
        proleveltxt = findViewById(R.id.proleveltxt);
        procgtxt = findViewById(R.id.procgtxt);
        proemailtxt = findViewById(R.id.proemailtxt);
        prophonetxt = findViewById(R.id.prophonetxt);
        proimgview = findViewById(R.id.proimgview);
        searchedit = findViewById(R.id.searchedit);
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
        /*probtn.setOnClickListener(new View.OnClickListener() {
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
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });*/





