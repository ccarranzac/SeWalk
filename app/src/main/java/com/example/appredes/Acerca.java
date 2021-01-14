package com.example.appredes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Acerca extends AppCompatActivity {

    TextView acerca_username;
    String acercaUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca);

        acerca_username=findViewById(R.id.username_acerca);

        Bundle datos=this.getIntent().getExtras();
        acercaUsername=datos.getString("username");

        acerca_username.setText(acercaUsername);

    }
}
