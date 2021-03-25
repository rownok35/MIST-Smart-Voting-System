package com.example.msvs5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.Random;

public class osmanyhallcapt extends AppCompatActivity {
    Button candidatepro,givevotebtn,passwordbtn;
    TextView maineventtxt,mainposttxt;
    TextInputLayout datea,starta,enda;
    String x,y,z,mainevent,mainpost,mainbar,mainemail,mainkey;
    String mymainbar="051111407592";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_osmanyhallcapt);

        try {
            Class<?> cls=Class.forName("com.example.msvs5.MainActivity");
            Field field=cls.getDeclaredField("barcodeID");
            field.setAccessible(true);
            Object object=field.get(new MainActivity());
            mainbar=(String)object;
            Toast.makeText(osmanyhallcapt.this,mainbar,Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        final Random rand=new Random();
        candidatepro=findViewById(R.id.candidatepro);
        givevotebtn=findViewById(R.id.givevotebtn);
        datea=findViewById(R.id.datetexta);
        starta=findViewById(R.id.starttexta);
        enda=findViewById(R.id.finishtexta);
        maineventtxt=findViewById(R.id.maineventtext);
        mainposttxt=findViewById(R.id.mainposttext);
        passwordbtn=findViewById(R.id.passwordbtn);

        x=getIntent().getStringExtra("date");
        y=getIntent().getStringExtra("start");
        z=getIntent().getStringExtra("end");
        mainevent=getIntent().getStringExtra("event");
        mainpost=getIntent().getStringExtra("post");
        datea.getEditText().setText(x);
        starta.getEditText().setText(y);
        enda.getEditText().setText(z);
        maineventtxt.setText(mainevent);
        mainposttxt.setText(mainpost);
        givevotebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(osmanyhallcapt.this, givevote.class);
                startActivity(intent);
            }
        });
        candidatepro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(osmanyhallcapt.this, candidateprofile.class);
                intent.putExtra("event",mainevent);
                intent.putExtra("post",mainpost);
                startActivity(intent);
            }
        });
        passwordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Approveduser").child(mainkey);
                int rand_int1 = rand.nextInt(1000000);
                //int rand_int2= (int) Math.round(rand_int1)+1000000;
                String pass= Integer.toString(rand_int1);
                databaseReference.child("passcode").setValue(pass);
                String Subject="Password for Voting";
                JavaMailAPI javaMailAPI= new JavaMailAPI(osmanyhallcapt.this,mainemail,Subject,pass);
                javaMailAPI.execute();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(osmanyhallcapt.this,"on start method",Toast.LENGTH_SHORT).show();
        DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("Approveduser");
        Query query = df.orderByChild("barcodeid").equalTo(mainbar);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String a = "" + ds.child("name").getValue(String.class);
                    String b = "" + ds.child("id").getValue(String.class);
                    String c = "" + ds.child("dept").getValue(String.class);

                    mainemail=ds.child("email").getValue(String.class);
                    mainkey=""+ds.child("key").getValue(String.class);


                }
                Toast.makeText(osmanyhallcapt.this,mainemail,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
