package com.example.emotionpredictionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ArrayAdapter adapter;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(listener);

        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, new DashboardFragment()).commit();
        context = this.getApplicationContext();

    }

    BottomNavigationView.OnNavigationItemSelectedListener listener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectdFragment = null;

                    switch (item.getItemId()) {
                        case R.id.home:
                            selectdFragment = new DashboardFragment();
                            break;
                        case R.id.patientList:
                            selectdFragment = new PatientListFragment();
                            break;
                        case R.id.settings:
                            selectdFragment = new SettingsFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, selectdFragment).commit();

                    return true;
                }
            };

    public void onPredict(View view){
        Intent intent = new Intent(this, PredictionScreen.class);
        startActivity(intent);
    }

    public void onAddPatient(View view){
        Intent intent = new Intent(this, AddNewPatient.class);
        startActivity(intent);
    }
}
