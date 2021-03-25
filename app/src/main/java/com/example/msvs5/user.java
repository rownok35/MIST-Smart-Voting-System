package com.example.msvs5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class user extends AppCompatActivity {
    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Button exitapp;
    private ImageView profileimg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        exitapp=findViewById(R.id.exitbtn);
        profileimg=findViewById(R.id.profileimg);

        Toolbar toolbar=findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //tabbed activity
        tabLayout=findViewById(R.id.tablayout);
        viewPager=findViewById(R.id.viewpager);
        viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragment(new votevents(),"event");
        viewPagerAdapter.addFragment(new result(),"result");
        viewPagerAdapter.addFragment(new candidate(),"candidate");
        //viewPagerAdapter.addFragment(new userprofile(),"profile");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        //tabLayout.getTabAt(0).setIcon(R.drawable.event);
        //tabLayout.getTabAt(1).setIcon(R.drawable.result);
        //tabLayout.getTabAt(2).setIcon(R.drawable.profile);
        tabLayout.getTabAt(0).setText("events");
        tabLayout.getTabAt(1).setText("result");
        tabLayout.getTabAt(2).setText("candidate");
        //tabLayout.getTabAt(3).setText("profile");

        exitapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(user.this,MainActivity.class);
                startActivity(intent);
            }
        });
        profileimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(user.this,Profile_of_user.class);
                startActivity(intent);
            }
        });


        }


    }

