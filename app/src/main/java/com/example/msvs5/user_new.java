package com.example.msvs5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.WindowManager;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class user_new extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_new);

        chipNavigationBar=findViewById(R.id.bottom_nav_menu_user);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_user,new votevents()).commit();
        bottomMenu();
    }

    private void bottomMenu() {
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment=null;
                switch (i){
                    case R.id.bottom_nav_event:
                        fragment=new votevents();
                        break;
                    case R.id.bottom_nav_result:
                        fragment=new result();
                        break;
                    case R.id.bottom_nav_profile:
                        fragment=new profilenew();
                        break;
                    case R.id.bottom_nav_candidate:
                        fragment=new candidate();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container_user,fragment).commit();
            }
        });
    }
}
