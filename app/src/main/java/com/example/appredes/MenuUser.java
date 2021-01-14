package com.example.appredes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class MenuUser extends AppCompatActivity {

    TextView menu_username, menu_name, menu_text;
    ImageView menu_logo;
    Button reportButton, viewReportButton, profileButton, PanicButton, signoutButton,convencionButton,acercaButton;
    String userUsername, userName, userEmail, userPhoneNumber,userPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_user);

        menu_username=findViewById(R.id.username_menu);
        menu_name=findViewById(R.id.fullname_menu);
        menu_text=findViewById(R.id.text_menu);
        menu_logo=findViewById(R.id.logo_menu);
        reportButton=findViewById(R.id.report_button);
        viewReportButton=findViewById(R.id.reportView_button);
        profileButton=findViewById(R.id.profile_button);
        PanicButton=findViewById(R.id.panic_Button);
        signoutButton=findViewById(R.id.signout_Button);
        convencionButton=findViewById(R.id.convenciones_button);
        acercaButton=findViewById(R.id.acerca_button);


        Bundle datos=this.getIntent().getExtras();
        userEmail=datos.getString("email");
        userUsername=datos.getString("username");
        userName=datos.getString("name");
        userPhoneNumber=datos.getString("phoneNo");
        userPassword=datos.getString("password");

        menu_username.setText(userUsername);
        menu_name.setText(userName);
    }
     public void CerrarSesión(View view){
        Intent intent=new Intent(getApplicationContext(),Login.class);
         Pair[]pairs=new Pair[3];
         pairs[0]=new Pair<View,String>(menu_logo,"logo_image");
         pairs[1]=new Pair<View,String>(menu_text,"logo_text");
         pairs[2]=new Pair<View,String>(menu_name,"logo_minitext");
         ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(MenuUser.this,pairs);
         startActivity(intent,options.toBundle());
         finish();

     }

     public void IrAlPerfil(View view){
        Intent intent=new Intent(MenuUser.this,PerfilUsuario.class);
         intent.putExtra("name", userName);
         intent.putExtra("username", userUsername);
         intent.putExtra("email", userEmail);
         intent.putExtra("phoneNo", userPhoneNumber);
         intent.putExtra("password", userPassword);

         Pair[]pairs=new Pair[3];
         pairs[0]=new Pair<View,String>(menu_name,"logo_minitext");
         pairs[1]=new Pair<View,String>(menu_text,"logo_text");
         pairs[2]=new Pair<View,String>(profileButton,"button_perfil");
         ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(MenuUser.this,pairs);
         startActivity(intent,options.toBundle());

    }

    public void IrMapa(View view){
        Intent intent=new Intent(MenuUser.this,Mapas.class);
        intent.putExtra("username", userUsername);
        startActivity(intent);
    }

    public void Reportar(View view){
        Intent intent=new Intent(MenuUser.this,Reportar.class);
        intent.putExtra("name", userName);
        intent.putExtra("username", userUsername);
        startActivity(intent);
    }

    public void Convenciones(View view){
        Intent intent=new Intent(MenuUser.this,Convenciones.class);
        intent.putExtra("username",userUsername);
        startActivity(intent);
    }

    public void Acerca(View view){
        Intent intent=new Intent(MenuUser.this,Acerca.class);
        intent.putExtra("username",userUsername);
        startActivity(intent);
    }
}



