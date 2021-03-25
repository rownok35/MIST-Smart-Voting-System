package com.example.msvs5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class detailedprofile extends AppCompatActivity {
    TextView candidatenametxt,candidateidtxt,candidatedepttxt,candidatelvltxt;
    TextInputLayout candidatecgtxt,candidateprotxt,passedit;
    Button votingbtn;
    ImageView candidateimage;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static final String TAG = "detailedprofile";
    private FingerprintManager fingerprintManager;
    private FingerprintManager.AuthenticationCallback authenticationCallback;
    String voterbarcode,namea,ida,depta;
    private DatabaseReference df,df2;
    int flag1=0;
    Long value;
    String teamevent,teampost,teamkey,teama,teamb,teamc,teamd,teame,teamf,teampass;
    String myvoterbarcode="051111407592";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_detailedprofile);
        candidatenametxt=findViewById(R.id.candidatenametxt);
        candidateidtxt=findViewById(R.id.candidateidtxt);
        candidatedepttxt=findViewById(R.id.candidatedepttxt);
        candidatecgtxt=findViewById(R.id.candidatecgtxt);
        candidatelvltxt=findViewById(R.id.candidatelvltxt);
        candidateprotxt=findViewById(R.id.candidateprotxt);
        votingbtn=findViewById(R.id.votingbtn2);
        passedit=findViewById(R.id.passedit);
        candidateimage=findViewById(R.id.candidateimage);
        teama=getIntent().getStringExtra("name");
        teamb=getIntent().getStringExtra("id");
        teamc=getIntent().getStringExtra("dept");
        teamd=getIntent().getStringExtra("cg");
        teame=getIntent().getStringExtra("lvl");
        teamf=getIntent().getStringExtra("propaganda");
        String teamg=getIntent().getStringExtra("imgurl");
        teamevent=getIntent().getStringExtra("event");
        teampost=getIntent().getStringExtra("post");
        teamkey= getIntent().getStringExtra("key");


        //Log.i("OUR VALUE",teamone);
        //Log.i("OUR VALUE 2",teamtwo);
        //Log.i("OUR VALUE 3",teamthree);
        Toast.makeText(this,""+teama,Toast.LENGTH_SHORT).show();
        candidatenametxt.setText(teama);
        candidateidtxt.setText(teamb);
        candidatedepttxt.setText(teamc);
        candidatecgtxt.getEditText().setText(teamd);
        candidatelvltxt.setText(teame);
        candidateprotxt.getEditText().setText(teamf);
        Picasso.get().load(teamg).into(candidateimage);
        try {
            Class<?> cls=Class.forName("com.example.msvs5.MainActivity");
            Field field=cls.getDeclaredField("barcodeID");
            field.setAccessible(true);
            Object object=field.get(new MainActivity());
            voterbarcode=(String)object;
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        df = FirebaseDatabase.getInstance().getReference().child("Approveduser");
        Query query = df.orderByChild("barcodeid").equalTo(voterbarcode);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    teampass=ds.child("passcode").getValue(String.class);

                }
                //Toast.makeText(detailedprofile.this,teampass,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        votingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass=passedit.getEditText().getText().toString().trim();
                if(pass.equals(teampass)){
                    DatabaseReference postRef = database.getReference().child("Approvedcandidate").child(teamevent).child(teampost).child(teamkey).child("vote");
                    postRef.runTransaction(new Transaction.Handler() {
                        @Override
                        public Transaction.Result doTransaction(MutableData mutableData) {
                            Long value = mutableData.getValue(Long.class);
                            if (value == null) {
                                mutableData.setValue(1);
                            }
                            else {
                                mutableData.setValue(value + 1);
                            }

                            return Transaction.success(mutableData);
                        }

                        @Override
                        public void onComplete(DatabaseError databaseError, boolean b,
                                               DataSnapshot dataSnapshot) {
                            // Transaction completed
                            Log.d(TAG, "postTransaction:onComplete:" + databaseError);
                        }
                    });
                    Toast.makeText(detailedprofile.this,"Success",Toast.LENGTH_LONG).show();

                    DatabaseReference postRef2 = database.getReference().child("VoteCheck").child(teamevent).child(teampost).child(voterbarcode);
                    postRef2.setValue(voterbarcode);

                    Intent i=new Intent(detailedprofile.this,user.class);
                    startActivity(i);
                }
            }
        });




        fingerprintManager=(FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

        authenticationCallback=new FingerprintManager.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                //textView.setText("Error");
                Toast.makeText(detailedprofile.this,"Error",Toast.LENGTH_SHORT).show();
                candidateimage.setImageResource(R.drawable.img2);
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                //textView.setText("Help");
                Toast.makeText(detailedprofile.this,"Help",Toast.LENGTH_SHORT).show();
                candidateimage.setImageResource(R.drawable.img3);
                super.onAuthenticationHelp(helpCode, helpString);
            }

            @Override
            public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {

                super.onAuthenticationSucceeded(result);

                AlertDialog dialog = new AlertDialog.Builder(detailedprofile.this)
                        .setTitle("Confirm Vote")
                        .setMessage("Do you really want to give vote?")
                        .setPositiveButton(R.string.YES, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseReference postRef = database.getReference().child("Approvedcandidate").child(teamevent).child(teampost).child(teamkey).child("vote");
                                postRef.runTransaction(new Transaction.Handler() {
                                    @Override
                                    public Transaction.Result doTransaction(MutableData mutableData) {
                                        Long value = mutableData.getValue(Long.class);
                                        if (value == null) {
                                            mutableData.setValue(1);
                                        }
                                        else {
                                            mutableData.setValue(value + 1);
                                        }

                                        return Transaction.success(mutableData);
                                    }

                                    @Override
                                    public void onComplete(DatabaseError databaseError, boolean b,
                                                           DataSnapshot dataSnapshot) {
                                        // Transaction completed
                                        Log.d(TAG, "postTransaction:onComplete:" + databaseError);
                                    }
                                });
                                Toast.makeText(detailedprofile.this,"Success",Toast.LENGTH_LONG).show();

                                DatabaseReference postRef2 = database.getReference().child("VoteCheck").child(teamevent).child(teampost).child(voterbarcode);
                                postRef2.setValue(voterbarcode);

                                Intent i=new Intent(detailedprofile.this,user_new.class);
                                startActivity(i);
                            }
                        })
                        .setNegativeButton(R.string.NO, null)
                        .create();
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    private static final int AUTO_DISMISS_MILLIS = 6000;
                    @Override
                    public void onShow(final DialogInterface dialog) {
                        final Button defaultButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE);
                        final CharSequence negativeButtonText = defaultButton.getText();
                        new CountDownTimer(AUTO_DISMISS_MILLIS, 100) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                defaultButton.setText(String.format(
                                        Locale.getDefault(), "%s (%d)",
                                        negativeButtonText,
                                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) + 1 //add one so it never displays zero
                                ));
                            }
                            @Override
                            public void onFinish() {
                                if (((AlertDialog) dialog).isShowing()) {
                                    dialog.dismiss();
                                }
                            }
                        }.start();
                    }
                });
                dialog.show();


            }

            @Override
            public void onAuthenticationFailed() {
                //textView.setText("Failed");
                Toast.makeText(detailedprofile.this,"Failed",Toast.LENGTH_SHORT).show();
                candidateimage.setImageResource(R.drawable.img2);
                super.onAuthenticationFailed();
            }
        };


    }

    public void scanButton(View v)
    {
        fingerprintManager.authenticate(null,null,0,authenticationCallback,null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(detailedprofile.this,"On start method",Toast.LENGTH_LONG).show();


        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        Query query=rootRef.child("VoteCheck").child(teamevent).child(teampost).child(voterbarcode);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    //create new user
                    Toast.makeText(detailedprofile.this,"You have already given Vote in this event",Toast.LENGTH_LONG).show();
                    Intent i=new Intent(detailedprofile.this,user_new.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(detailedprofile.this,"You didn't give Vote in this event yet",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage()); //Don't ignore errors!
            }
        };
        query.addListenerForSingleValueEvent(eventListener);


    }

}
