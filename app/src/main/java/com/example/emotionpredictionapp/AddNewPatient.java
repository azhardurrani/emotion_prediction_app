package com.example.emotionpredictionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class AddNewPatient extends AppCompatActivity {

    EditText name;
    EditText desease;
    EditText description;
    TextView msg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_patient);

        name = findViewById(R.id.name);
        desease = findViewById(R.id.desease);
        description = findViewById(R.id.description);
        msg = findViewById(R.id.message);

    }

    public void addPatient(View view){

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("name", name.getText().toString());
            jsonObject.put("disease", desease.getText().toString());
            jsonObject.put("description", description.getText().toString());

            ApiHandler apiHandler = new ApiHandler();
            JSONObject response = apiHandler.postReuquest(this, "/addPatient", jsonObject);

            finish();

        } catch (JSONException e) {

            Toast.makeText(this, "Error in sending information", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }
}