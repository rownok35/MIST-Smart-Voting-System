package com.example.msvs5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class finaledit extends AppCompatActivity {
    String finalname,finalid,finalcg,finaldept,finalphn,finalemail,finallevel,finalkey;
    TextInputLayout finalnameedit,finalidedit,finalemailedit,finalphnedit,finallvledit;
    Button finaleditbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_finaledit);
        finalnameedit=findViewById(R.id.finalnameedit);
        finalidedit=findViewById(R.id.finalidedit);
        finallvledit=findViewById(R.id.finalleveledit);
        finalemailedit=findViewById(R.id.finalemailedit);
        finalphnedit=findViewById(R.id.finalphnedit);
        finaleditbtn=findViewById(R.id.finaleditbtn);

        finalname=getIntent().getStringExtra("name");
        finalid=getIntent().getStringExtra("id");
        finallevel=getIntent().getStringExtra("level");
        finalemail=getIntent().getStringExtra("email");
        finalphn=getIntent().getStringExtra("phone");
        finalkey=getIntent().getStringExtra("key");

        finalnameedit.getEditText().setText(finalname);
        finalidedit.getEditText().setText(finalid);
        finallvledit.getEditText().setText(finallevel);
        finalemailedit.getEditText().setText(finalemail);
        finalphnedit.getEditText().setText(finalphn);

        finaleditbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Approveduser").child(finalkey);
                databaseReference.child("name").setValue(finalnameedit.getEditText().getText().toString().trim());
                databaseReference.child("id").setValue(finalidedit.getEditText().getText().toString().trim());
                databaseReference.child("level").setValue(finallvledit.getEditText().getText().toString().trim());
                databaseReference.child("email").setValue(finalemailedit.getEditText().getText().toString().trim());
                databaseReference.child("phone").setValue(finalphnedit.getEditText().getText().toString().trim());
            }
        });
    }
}
