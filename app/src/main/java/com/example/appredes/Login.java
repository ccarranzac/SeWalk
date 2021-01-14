package com.example.appredes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    Button callRegistro, loginButton;
    ImageView image;
    TextView logoText, sloganText;
    TextInputLayout phone, password;
    ProgressBar loginProgress;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        callRegistro=findViewById(R.id.callRegistroButton);
        loginButton=findViewById(R.id.loginButton);
        image=findViewById(R.id.logologin);
        logoText=findViewById(R.id.loginTitle);
        sloganText=findViewById(R.id.loginSubtitle);
        phone=findViewById(R.id.phone);
        password=findViewById(R.id.password);
        loginProgress=findViewById(R.id.loginprogressBar);


        }

    public Boolean validateUsername(){
        String val=phone.getEditText().getText().toString();

        if(val.isEmpty()){
            phone.setError("Este campo no puede estar vacio");
            return false;

        }else {
            phone.setError(null);
            phone.setErrorEnabled(false);
            return true;

        }
    }

    public Boolean validatePassword(){
        String val=password.getEditText().getText().toString();

        if(val.isEmpty()){
            password.setError("Este campo no puede estar vacio");
            return false;

        }else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;

        }
    }

    public void loginUser(View view){
        if(!validatePassword() | !validateUsername()){

        }else{
            isRealUser();
        }
    }

    /*private void isUser() {

        final String userEnteredUsername = username.getEditText().getText().toString().trim();
        final String userEnteredPassword = password.getEditText().getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("usuarios");
        Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    username.setError(null);
                    username.setErrorEnabled(false);
                    String passwordFromDB = dataSnapshot.child(userEnteredUsername).child("password").getValue(String.class);
                    //Toast.makeText(getApplicationContext(),passwordFromDB,Toast.LENGTH_SHORT).show();

                    if (passwordFromDB.equals(userEnteredPassword)) {

                        username.setError(null);
                        username.setErrorEnabled(false);


                        String nameFromDB = dataSnapshot.child(userEnteredUsername).child("name").getValue(String.class);
                        String usernameFromDB = dataSnapshot.child(userEnteredUsername).child("username").getValue(String.class);
                        String phoneNoFromDB = dataSnapshot.child(userEnteredUsername).child("phoneNo").getValue(String.class);
                        String emailFromDB = dataSnapshot.child(userEnteredUsername).child("email").getValue(String.class);
                        Intent intent = new Intent(getApplicationContext(),PerfilUsuario.class);
                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("username", usernameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("phoneNo", phoneNoFromDB);
                        intent.putExtra("password", passwordFromDB);
                        startActivity(intent);
                    } else {
                        password.setError("Contraseña incorrecta");
                        password.requestFocus();
                    }
                } else {
                    username.setError("no existe el usuario");
                    username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });
    }*/

    private void isRealUser(){

        final String userEnteredPhone = phone.getEditText().getText().toString().trim();
        final String userEnteredPassword = password.getEditText().getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("usuarios");
        loginProgress.setVisibility(View.VISIBLE);
        reference.child(userEnteredPhone).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    phone.setError(null);
                    phone.setErrorEnabled(false);
                    String passwordDB=dataSnapshot.child("password").getValue(String.class);
                    if(passwordDB.equals(userEnteredPassword)){
                        String nameFromDB = dataSnapshot.child("name").getValue(String.class);
                        String usernameFromDB = dataSnapshot.child("username").getValue(String.class);
                        String phoneNoFromDB = dataSnapshot.child("phone").getValue(String.class);
                        String emailFromDB = dataSnapshot.child("email").getValue(String.class);
                        Intent intent = new Intent(getApplicationContext(),MenuUser.class);
                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("username", usernameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("phoneNo", phoneNoFromDB);
                        intent.putExtra("password", passwordDB);

                        Pair []pairs=new Pair[3];
                        pairs[0]=new Pair<View,String>(image,"logo_image");
                        pairs[1]=new Pair<View,String>(logoText,"logo_text");
                        pairs[2]=new Pair<View,String>(sloganText,"logo_minitext");

                        loginProgress.setVisibility(View.GONE);
                        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(Login.this,pairs);
                        startActivity(intent,options.toBundle());
                        finish();
                    }else {
                        password.setError("Contraseña incorrecta");
                        password.requestFocus();
                        loginProgress.setVisibility(View.GONE);
                    }
                }else{
                    phone.setError("no esta registrado este telefono");
                    phone.requestFocus();
                    loginProgress.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Fallo la lectura: " + databaseError.getCode());

            }
        });


    }


    public void callRegistro(View view){
        Intent intent=new Intent(Login.this,Registro.class);
        Pair []pairs=new Pair[7];

        pairs[0]=new Pair<View,String>(image,"logo_image");
        pairs[1]=new Pair<View,String>(logoText,"logo_text");
        pairs[2]=new Pair<View,String>(sloganText,"logo_minitext");
        pairs[3]=new Pair<View,String>(phone,"phone_transition");
        pairs[4]=new Pair<View,String>(password,"password_transition");
        pairs[5]=new Pair<View,String>(loginButton,"button_transition");
        pairs[6]=new Pair<View,String>(callRegistro,"buttonloginReg_transition");


        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(Login.this,pairs);
        startActivity(intent,options.toBundle());
        finish();
    }


}
