package com.example.timelineclassprofessor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNav);

        getSupportFragmentManager().beginTransaction().add(R.id.main_frame, new lecture_list()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.item_lecture_list:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new lecture_list()).commit();
                        break;
                    case R.id.item_time_list:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new time_list()).commit();
                        break;
                }
                return true;
            }
        });
    }
}