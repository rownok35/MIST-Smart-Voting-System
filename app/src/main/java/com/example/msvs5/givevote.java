package com.example.msvs5;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class givevote extends AppCompatActivity {

    private TextView textView;
    private ImageView imageView;
    private FingerprintManager fingerprintManager;
    private FingerprintManager.AuthenticationCallback authenticationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_givevote);

        textView=findViewById(R.id.textview);
        imageView=findViewById(R.id.imageView);

        imageView.setImageResource(R.drawable.img3);
        fingerprintManager=(FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

        authenticationCallback=new FingerprintManager.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                textView.setText("Error");
                imageView.setImageResource(R.drawable.img2);
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                textView.setText("Help");
                imageView.setImageResource(R.drawable.img3);
                super.onAuthenticationHelp(helpCode, helpString);
            }

            @Override
            public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                textView.setText("Success");
                imageView.setImageResource(R.drawable.img1);
                super.onAuthenticationSucceeded(result);
            }

            @Override
            public void onAuthenticationFailed() {
                textView.setText("Failed");
                imageView.setImageResource(R.drawable.img2);
                super.onAuthenticationFailed();
            }
        };


    }

    public void scanButton(View v)
    {
        fingerprintManager.authenticate(null,null,0,authenticationCallback,null);
    }
}
