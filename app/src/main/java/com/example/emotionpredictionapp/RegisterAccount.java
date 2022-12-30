package com.example.emotionpredictionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterAccount extends AppCompatActivity {

    EditText firstName;
    EditText lastName;
    EditText username;
    EditText password;
    EditText cnfrmPass;
    TextView msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);

        firstName = findViewById(R.id.fistName);
        lastName = findViewById(R.id.lastName);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password1);
        cnfrmPass = findViewById(R.id.password2);
        msg = findViewById(R.id.message);

    }

    public void registerAccount(View view){

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("first_name", firstName.getText().toString());
            jsonObject.put("last_name", lastName.getText().toString());
            jsonObject.put("username", username.getText().toString());
            jsonObject.put("password", password.getText().toString());

            ApiHandler apiHandler = new ApiHandler();
            JSONObject response = apiHandler.postReuquest(this, "/registerPsychatrist", jsonObject);

//            if(response!=null && response.getString("status").equals("success")){
//
//            }else{
//                msg.setText("Error in creating account");
//            }

//                Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                finish();


        } catch (JSONException e) {

            Toast.makeText(this, "Error in sending information", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }
}
