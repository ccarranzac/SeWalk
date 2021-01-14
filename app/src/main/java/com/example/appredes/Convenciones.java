package com.example.appredes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Convenciones extends AppCompatActivity {

    TextView convenciones_username;
    String convencionesUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convenciones);

        convenciones_username=findViewById(R.id.username_convenciones);

        Bundle datos=this.getIntent().getExtras();
        convencionesUsername=datos.getString("username");

        convenciones_username.setText(convencionesUsername);
    }
}
