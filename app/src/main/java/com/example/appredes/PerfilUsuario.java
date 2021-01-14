package com.example.appredes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.Inet4Address;

public class PerfilUsuario extends AppCompatActivity {

    TextInputLayout fullname, email, phoneNumber, password;
    TextView fullnameLabel, usernameLabel;
    String user_username, user_name, user_email, user_phoneNumber,user_password;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);

        reference= FirebaseDatabase.getInstance().getReference("usuarios");
        fullname=findViewById(R.id.profile_fullname);
        email=findViewById(R.id.profile_email);
        phoneNumber=findViewById(R.id.profile_phonenumber);
        password=findViewById(R.id.profile_password);
        fullnameLabel=findViewById(R.id.nombre_completo);
        usernameLabel=findViewById(R.id.profile_username);

        showData();


    }

    public void showData(){
        Intent intent=getIntent();
        user_email=intent.getStringExtra("email");
        user_username=intent.getStringExtra("username");
        user_name=intent.getStringExtra("name");
        user_phoneNumber=intent.getStringExtra("phoneNo");
        user_password=intent.getStringExtra("password");

        fullnameLabel.setText(user_name);
        usernameLabel.setText(user_username);
        fullname.getEditText().setText(user_name);
        email.getEditText().setText(user_email);
        phoneNumber.getEditText().setText(user_phoneNumber);
        password.getEditText().setText(user_password);

    }



}
