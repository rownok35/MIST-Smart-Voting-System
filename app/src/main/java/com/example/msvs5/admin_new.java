package com.example.msvs5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.WindowManager;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class admin_new extends AppCompatActivity {
    ChipNavigationBar chipNavigationBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_admin_new);

        chipNavigationBar=findViewById(R.id.bottom_nav_menu);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_admin,new createvotevent()).commit();
        bottomMenu();


    }

    private void bottomMenu() {
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment=null;
                switch (i){
                    case R.id.bottom_nav_votevent:
                        fragment=new createvotevent();
                        break;
                    case R.id.bottom_nav_edit:
                        fragment=new edituser();
                        break;
                    case R.id.bottom_nav_history:
                        fragment=new history();
                        break;
                    case R.id.bottom_nav_request:
                        fragment=new Requestlist();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container_admin,fragment).commit();
            }
        });
    }
}
