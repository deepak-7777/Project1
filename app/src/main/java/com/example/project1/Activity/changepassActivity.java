package com.example.project1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project1.R;


public class changepassActivity extends AppCompatActivity {

    public void backBtn1 (View view) {
        startActivity(new Intent(changepassActivity.this,  LoginActivity.class));
    }

    Button changeEmail, changeGoogle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_changepass);

        changeEmail = findViewById(R.id.changeEmail);
        changeGoogle = findViewById(R.id.changeGoogle);

        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(changepassActivity.this, ForgotPassword.class));
            }
        });
        changeGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(changepassActivity.this, "Wait", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
