package com.example.msvs5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.squareup.picasso.Picasso;

import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class MainActivity extends AppCompatActivity {
    Button userbtn,adminbtn,signbtn,fdbtn;
    Button regbtn;
    private ZXingScannerView scannerView;
    DatabaseReference dref;
    private static String barcodeID;
    private static String mybarcodeID="201714055";
    String resultcode;
    private int CAMERA_PERMISSION_CODE=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        dref= FirebaseDatabase.getInstance().getReference("Barcode");

        userbtn=findViewById(R.id.userbtn);
        signbtn=findViewById(R.id.signinbtn);
        //regbtn=findViewById(R.id.regbtn);
        adminbtn=findViewById((R.id.adminbtn));
        fdbtn=findViewById(R.id.fdbtn);
        userbtn.setVisibility(View.VISIBLE);
        fdbtn.setVisibility(View.VISIBLE);
        adminbtn.setVisibility(View.VISIBLE);
        userbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,user_new.class);
                startActivity(intent);
            }
        });
        adminbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,admin_new.class);
                startActivity(intent);
            }
        });

        fdbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,RegPage.class);
                startActivity(intent);
            }
        });

    }
    public void scanCode(View view){


                scannerView = new ZXingScannerView(MainActivity.this);
                scannerView.setResultHandler(new ZXingScannerResultHandler());
                setContentView(scannerView);
                scannerView.startCamera();

        }

    @Override
    public void onPause(){
        super.onPause();
        scannerView.stopCamera();

    }




    class ZXingScannerResultHandler implements ZXingScannerView.ResultHandler {

        @Override
        public void handleResult(final Result result) {
            //dref= new Firebase(Config.https://console.firebase.google.com/u/1/project/diabeticdiary-ebc55/database/diabeticdiary-ebc55/data)
            String resultCode = result.getText();
            barcodeID=resultCode;
            //System.out.println(barcodeID);
            DatabaseReference usersRef ;
            usersRef = FirebaseDatabase.getInstance().getReference().child("Approveduser");

            //usersRef.push().child("value").setValue(resultCode);



            usersRef.orderByChild("barcodeid").equalTo(resultCode).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override

                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int flag=0;
                    for(DataSnapshot data: dataSnapshot.getChildren()) {
                        if (!dataSnapshot.exists()) {

                            //Toast.makeText(MainActivity.this, "Not valid", Toast.LENGTH_SHORT).show();
                        } else {


                            flag=1;
                        }
                    }
                    if(flag==0)
                    {
                        Toast.makeText(MainActivity.this, "Not valid", Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                        builder.setTitle("Invalid User");
                        builder.setMessage("Have you already registered?");


                        builder.setPositiveButton(R.string.YES, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                // Do nothing but close the dialog

                                dialog.dismiss();
                                //Intent intent=new Intent(MainActivity.this,registration.class);
                                //startActivity(intent);
                                Toast.makeText(MainActivity.this, "You are still not approved by admin", Toast.LENGTH_SHORT).show();

                            }
                        });

                        builder.setNegativeButton(R.string.NO, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                // Do nothing
                                dialog.dismiss();
                                Intent intent=new Intent(MainActivity.this,RegPage.class);
                                startActivity(intent);


                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();
                    }

                    else
                    {


                        if(barcodeID.equals("055")){
                            Toast.makeText(MainActivity.this, "Welcome Admin!", Toast.LENGTH_SHORT).show();
                            Intent intent= new Intent(MainActivity.this,admin_new.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(MainActivity.this, "valid barcode", Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(MainActivity.this,user_new.class);
                        startActivity(intent);
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            setContentView(R.layout.activity_main);
            scannerView.stopCamera();
        }
    }

}