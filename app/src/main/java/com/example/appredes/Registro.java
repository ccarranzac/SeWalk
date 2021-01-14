package com.example.appredes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registro extends AppCompatActivity {

    TextInputLayout reg_name, reg_username, reg_phone, reg_email, reg_password;
    Button regButton, reg_toLoginButton;
    ImageView imagenlogoReg;
    TextView regSlogan, regMiniSlogan;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        reg_name = findViewById(R.id.name);
        reg_username = findViewById(R.id.username);
        reg_phone = findViewById(R.id.phonenumber);
        reg_email = findViewById(R.id.email);
        reg_password = findViewById(R.id.password);
        regButton = findViewById(R.id.regbutton);
        reg_toLoginButton = findViewById(R.id.toLoginbutton);
        imagenlogoReg=findViewById(R.id.reglogo_image);
        regSlogan=findViewById(R.id.reg_slogan);
        regMiniSlogan=findViewById(R.id.reg_minislogan);



    }


    public Boolean validateName(){
        String val=reg_name.getEditText().getText().toString();
        if(val.isEmpty()){
            reg_name.setError("Este campo no puede estar vacio");
            return false;
        }else{
            reg_name.setError(null);
            reg_name.setErrorEnabled(false);
            return true;
        }
    }

    public Boolean validateUsername(){
        String val=reg_username.getEditText().getText().toString();
        if(val.isEmpty()){
            reg_username.setError("Este campo no puede estar vacio");
            return false;
        }else if(val.length()>=15){
            reg_username.setError("username es muy largo");
            return false;
        }
        else{
            reg_username.setError(null);
            reg_username.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = reg_email.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            reg_email.setError("Este campo no puede estar vacio");
            return false;
        } else if (!val.matches(emailPattern)) {
            reg_email.setError("correo invalido");
            return false;
        } else {
            reg_email.setError(null);
            reg_email.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePhoneNo() {
        String val = reg_phone.getEditText().getText().toString();

        if (val.isEmpty()) {
            reg_phone.setError("Este campo no puede estar vacio");
            return false;
        } else {
            reg_phone.setError(null);
            reg_phone.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = reg_password.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            reg_password.setError("Este campo no puede estar vacio");
            return false;
        } else if (!val.matches(passwordVal)) {
            reg_password.setError("Contraseña es muy debil");
            return false;
        } else {
            reg_password.setError(null);
            reg_password.setErrorEnabled(false);
            return true;
        }
    }


    public void registerUser(View view) {

        if(!validateName() | !validatePassword() | !validatePhoneNo() | !validateEmail() | !validateUsername()){
            return;
        }

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("usuarios");

        String name = reg_name.getEditText().getText().toString();
        String username = reg_username.getEditText().getText().toString();
        String phone = reg_phone.getEditText().getText().toString();
        String email = reg_email.getEditText().getText().toString();
        String password = reg_password.getEditText().getText().toString();

        UserHelper helperclass = new UserHelper(name, username, email, phone, password);
        reference.child(phone).setValue(helperclass);
        Toast.makeText(this,"Registro Satisfactorio", Toast.LENGTH_LONG).show();
        Intent intent=new Intent(Registro.this,Login.class);
        startActivity(intent);
        finish();

    }

    public void volverLogin(View view){
        Intent intent=new Intent(Registro.this, Login.class);
        Pair[]pairs=new Pair[7];

        pairs[0]=new Pair<View,String>(imagenlogoReg,"logo_image");
        pairs[1]=new Pair<View,String>(regSlogan,"logo_text");
        pairs[2]=new Pair<View,String>(regMiniSlogan,"logo_minitext");
        pairs[3]=new Pair<View,String>(reg_phone,"phone_transition");
        pairs[4]=new Pair<View,String>(reg_password,"password_transition");
        pairs[5]=new Pair<View,String>(regButton,"button_transition");
        pairs[6]=new Pair<View,String>(reg_toLoginButton,"buttonloginReg_transition");

        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(Registro.this,pairs);
        startActivity(intent,options.toBundle());
        finish();
    }

}
