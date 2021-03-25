package com.example.msvs5;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class approve extends AppCompatActivity {
    TextView usernametxt,useridtxt,userdepttxt,leveltxt;
    TextInputLayout useremailtxt,userbarcodeidtxt,phonetxt,cgtxtview;
    Button approvebtn;
    private DatabaseReference df;
    private DatabaseReference df1;
    String teama,teamb,teamc,teame,teamd,teamf,teamg,teamh,teami,teamj;
    ImageView profileimg;
    int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_approve);
        usernametxt=findViewById(R.id.nametxtview);
        useridtxt=findViewById(R.id.idtxtview);
        userdepttxt=findViewById(R.id.depttxtview);
        useremailtxt=findViewById(R.id.emailtxtview);
        phonetxt=findViewById(R.id.phonetxt);
        leveltxt=findViewById(R.id.leveltxt);
        cgtxtview=findViewById(R.id.cgviewtxt);
        profileimg=findViewById(R.id.profileimg);
        userbarcodeidtxt=findViewById(R.id.barcodeidtxtview);
        approvebtn=findViewById(R.id.approvebtn);
        teama=getIntent().getStringExtra("name");
        teamb=getIntent().getStringExtra("id");
        teamc=getIntent().getStringExtra("dept");
        teamd=getIntent().getStringExtra("email");
        teame=getIntent().getStringExtra("barcodeid");
        teamf=getIntent().getStringExtra("level");
        teamg=getIntent().getStringExtra("imgurl");
        teamh=getIntent().getStringExtra("phone");
        teami=getIntent().getStringExtra("cg");
        teamj=getIntent().getStringExtra("key");

        //Log.i("OUR VALUE",teamone);
        //Log.i("OUR VALUE 2",teamtwo);
        //Log.i("OUR VALUE 3",teamthree);
        Toast.makeText(this,""+teamb,Toast.LENGTH_SHORT).show();
        usernametxt.setText(teama);
        useridtxt.setText(teamb);
        userdepttxt.setText(teamc);
        useremailtxt.getEditText().setText(teamd);
        userbarcodeidtxt.getEditText().setText(teame);
        phonetxt.getEditText().setText(teamh);
        leveltxt.setText(teamf);
        cgtxtview.getEditText().setText(teami);
        Picasso.get().load(teamg).into(profileimg);
        approvebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                df= FirebaseDatabase.getInstance().getReference().child("Approveduser");
                /*Voter voter=new Voter();
                voter.setName(teama);
                voter.setId(teamb);
                voter.setDept(teamc);
                voter.setEmail(teamd);
                voter.setBarcodeid(teame);*/

                final HashMap<String,String> hashMap=new HashMap<>();
                hashMap.put("name",teama);
                hashMap.put("id",teamb);
                hashMap.put("barcodeid",teame);
                hashMap.put("cg",teami);
                hashMap.put("level",teamf);
                hashMap.put("dept",teamc);
                hashMap.put("email",teamd);
                hashMap.put("phone",teamh);
                hashMap.put("key",teamj);
                hashMap.put("passcode","0");
                hashMap.put("imgurl",teamg);
                Query query=df.orderByChild("barcodeid").equalTo(teame);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        if(dataSnapshot.hasChildren()){
                            flag=1;
                            break;
                        }
                        }
                        if(flag==0){
                            df.child(teamj).setValue(hashMap);

                            Toast.makeText(approve.this,"voter is approved",Toast.LENGTH_SHORT).show();
                            //df1=FirebaseDatabase.getInstance().getReference().child("feedback");

                            String Subject="Approval from Admin";
                            String feed="You are approved by Admin as a Voter. Congratulations!";
                            JavaMailAPI javaMailAPI= new JavaMailAPI(approve.this,teamd,Subject,feed);
                            javaMailAPI.execute();
                        }
                        else {
                            Toast.makeText(approve.this,"voter is already approved!!",Toast.LENGTH_SHORT).show();
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
