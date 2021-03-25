package com.example.msvs5;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.zxing.Result;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class registration extends AppCompatActivity {
    Spinner spinner;
    ImageView img;
    Button scanbtn,addimgbtn;
    EditText nameedit1, idedit, emailedit;
    private StorageReference folder;
    private static final int image_pick_code = 1000;
    private static final int permission_code = 1001;
    private ZXingScannerView scannerView;
    DatabaseReference dref;
    student s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        //dref= FirebaseDatabase.getInstance().getReference("Barcode");
        spinner = findViewById(R.id.spinner3);
        nameedit1=findViewById(R.id.nameedit1);
        idedit=findViewById(R.id.idedit);
        emailedit=findViewById(R.id.emailedit);
        img=findViewById(R.id.regimg);
        addimgbtn=findViewById(R.id.regimgbtn);
        folder= FirebaseStorage.getInstance().getReference().child("Imagefolder");
        addimgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    //==PackageManager.PERMISSION_DENIED){
                    //String[] permissions={Manifest.permission.READ_EXTERNAL_STORAGE};
                    //requestPermissions(permissions,permission_code);
                    //}
                    //else {
                    PickImageGallery();
                } else {
                    PickImageGallery();
                }
            }
        });
    }

    public void PickImageGallery() {
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,image_pick_code);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case permission_code:{
                if(grantResults.length>0 && grantResults[0]==
                        PackageManager.PERMISSION_GRANTED){
                    PickImageGallery();
                }
                else{
                    Toast.makeText(this,"permission denied",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == image_pick_code) {
            Uri imagedata1 = data.getData();
            img.setImageURI(imagedata1);
        }

    }

    public void scanCode(View view){
        scannerView = new ZXingScannerView(this);
        scannerView.setResultHandler(new registration.ZXingScannerResultHandler());
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
        public void handleResult(Result result) {
            //dref= new Firebase(Config.https://console.firebase.google.com/u/1/project/diabeticdiary-ebc55/database/diabeticdiary-ebc55/data)
            final String resultCode = result.getText();
            //DatabaseReference usersRef ;
            dref = FirebaseDatabase.getInstance().getReference().child("Info");
            final String name=nameedit1.getText().toString().trim();
            final String id=idedit.getText().toString().trim();
            final String email=emailedit.getText().toString().trim();
            final String dept = spinner.getSelectedItem().toString();
            s=new student();
            s.setName(name);
            s.setId(id);
            s.setEmail(email);
            s.setDept(dept);
            s.setBarcodeid(resultCode);
            AlertDialog.Builder builder = new AlertDialog.Builder(registration.this);

            builder.setTitle("Confirmation");
            builder.setMessage("Confirm registration?");


            builder.setPositiveButton(R.string.YES, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing but close the dialog

                    dialog.dismiss();
                    //Intent intent=new Intent(registration.this,registration.class);
                    //startActivity(intent);
                    dref.push().setValue(s);
                    Toast.makeText(registration.this,"wait for admin approval",Toast.LENGTH_LONG).show();
                }
            });

            builder.setNegativeButton(R.string.NO, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // Do nothing
                    dialog.dismiss();

                }
            });

            AlertDialog alert = builder.create();
            alert.show();




            setContentView(R.layout.activity_registration);
            scannerView.stopCamera();
        }
    }
}
