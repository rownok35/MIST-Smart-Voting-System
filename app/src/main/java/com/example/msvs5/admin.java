package com.example.msvs5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class admin extends AppCompatActivity {
    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar=findViewById(R.id.toolBar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //tabbed activity
        tabLayout=findViewById(R.id.tablayout2);
        viewPager=findViewById(R.id.viewpager2);
        viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragment(new createvotevent(),"");
        viewPagerAdapter.addFragment(new edituser(),"");
        viewPagerAdapter.addFragment(new history(),"");
        viewPagerAdapter.addFragment(new Requestlist(),"");
        //viewPagerAdapter.addFragment(new addcandidatebyadmin(),"");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        //tabLayout.getTabAt(0).setIcon(R.drawable.event);
        //tabLayout.getTabAt(1).setIcon(R.drawable.result);
        //tabLayout.getTabAt(2).setIcon(R.drawable.profile);
        tabLayout.getTabAt(0).setText("Create Event");
        tabLayout.getTabAt(1).setText("Edit user");
        tabLayout.getTabAt(2).setText("history");
        tabLayout.getTabAt(3).setText("Request");
        //tabLayout.getTabAt(4).setText("Add Candidates");


    }
}
