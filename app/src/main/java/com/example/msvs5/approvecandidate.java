package com.example.msvs5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
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

import java.io.InputStream;
import java.util.HashMap;

public class approvecandidate extends AppCompatActivity {
    TextView appcandidatenametxt,appcandidatedepttxt,appcandidateidtxt,appcandidateleveltxt;
    TextInputLayout appcandidatecgtxt,appcandidateprotxt,appcandidatephonetxt,appcandidateemailtxt;
    ImageView candidateImageview;
    Button canapprovebtn;
    private DatabaseReference df;
    String teama,teamb,teamc,teamd,teame,teamf,teamg,teamh,teami,x;
    DataSetFire candi;
    int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_approvecandidate);
        appcandidatenametxt = findViewById(R.id.appcandidatenametxt);
        appcandidatecgtxt = findViewById(R.id.appcandidatecgtxt);
        appcandidatedepttxt = findViewById(R.id.appcandidatedepttxt);
        appcandidateprotxt = findViewById(R.id.appcandidateprotxt);
        //appcandidatephonetxt = findViewById(R.id.appcandidatephonetxt);
        //appcandidateemailtxt = findViewById(R.id.appcandidateemailtxt);
        candidateImageview = findViewById(R.id.candidateimageView);
        appcandidateidtxt=findViewById(R.id.appcandidateidtxt);
        appcandidateleveltxt=findViewById(R.id.appcandidateleveltxt);
        canapprovebtn=findViewById(R.id.canapprovebtn);
        candi=new DataSetFire();
        teama =getIntent().getStringExtra("name");
        teamb =getIntent().getStringExtra("cg");
        teamc = getIntent().getStringExtra("department");
        teamd =getIntent().getStringExtra("propaganda");
        teame = getIntent().getStringExtra("imgurl");
        teamf =getIntent().getStringExtra("event");
        teamg =getIntent().getStringExtra("post");
        teamh= getIntent().getStringExtra("id");
        teami=getIntent().getStringExtra("level");
        //Log.i("OUR VALUE",teamone);
        //Log.i("OUR VALUE 2",teamtwo);
        //Log.i("OUR VALUE 3",teamthree);
        Toast.makeText(this, "" + teama, Toast.LENGTH_SHORT).show();
        appcandidatenametxt.setText(teama);
        appcandidatecgtxt.getEditText().setText(teamb);
        appcandidatedepttxt.setText(teamc);
        appcandidateprotxt.getEditText().setText(teamd);
        appcandidateidtxt.setText(teamh);
        appcandidateleveltxt.setText(teami);
        Picasso.get().load(teame).into(candidateImageview);
        canapprovebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                df = FirebaseDatabase.getInstance().getReference().child("Approvedcandidate").child(teamf).child(teamg);
                candi.setName(teama);
                candi.setId(teamh);
                candi.setDept(teamc);
                candi.setCg(teamb);
                candi.setPropaganda(teamd);
                candi.setVote(0);
                candi.setImgurl(teame);
                candi.setLevel(teami);
                x = df.push().getKey();
                candi.setKey(x);

                Query query = df.orderByChild("id").equalTo(teamh);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            if (dataSnapshot.hasChildren()) {
                                flag = 1;
                                break;
                            }
                        }
                        if (flag == 0) {

                            df.child(x).setValue(candi);
                            Toast.makeText(approvecandidate.this, "candidate is approved", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(approvecandidate.this, "candidate is already approved!!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

                });
                //final Uri image=Uri.parse(teame);
                //candidateImageview.setImageURI(image);
                //new DownloadImageTask(candidateImageview).execute(teame);
            }


        private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
            ImageView bmImage;

            public DownloadImageTask(ImageView bmImage) {
                this.bmImage = bmImage;
            }

            protected Bitmap doInBackground(String... urls) {
                String urldisplay = urls[0];
                Bitmap mIcon11 = null;
                try {
                    InputStream in = new java.net.URL(urldisplay).openStream();
                    mIcon11 = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
                return mIcon11;
            }

            protected void onPostExecute(Bitmap result) {
                bmImage.setImageBitmap(result);
            }
        }

    }

