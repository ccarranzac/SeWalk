package com.example.appredes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.LocaleList;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Reportar extends AppCompatActivity {

    private FusedLocationProviderClient ubicacion;
    TextView report_username,report_name,report_advertising;
    String reportUsername,reportName,advertencia;
    Button report_button;
    TextInputLayout nivel_reporte;
    String numero;

    FirebaseDatabase database;
    DatabaseReference refubicacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportar);

        database=FirebaseDatabase.getInstance();
        refubicacion=database.getReference("ubicacion");

        report_username=findViewById(R.id.username_report);
        report_name=findViewById(R.id.fullname_report);
        report_advertising=findViewById(R.id.advertising_label);
        report_button=findViewById(R.id.sendreport_button);
        nivel_reporte=findViewById(R.id.number_report);

        advertencia="Al enviar un reporte, aceptas que reconoces algun tipo de peligro presente en la zona que transitas." +
                " Recuerda que la informacion que reportas puede ser usada por las autoridades. Cualquier tipo de intento de" +
                " generar un reporte falso puede terminar en un baneo permanente de la plataforma.";

        Bundle datos=this.getIntent().getExtras();
        reportUsername=datos.getString("username");
        reportName=datos.getString("name");

        report_username.setText(reportUsername);
        report_name.setText(reportName);
        report_advertising.setText(advertencia);

        report_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                darUbicacion();
            }
        });

    }
    private void darUbicacion(){
        if(ContextCompat.checkSelfPermission(Reportar.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            Toast.makeText(Reportar.this,"Tenemos Permiso",Toast.LENGTH_SHORT).show();
        }else{
            ActivityCompat.requestPermissions(Reportar.this,new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        numero=nivel_reporte.getEditText().getText().toString(); //arreglar esto para evitar enviar reportes vacios
        ubicacion= LocationServices.getFusedLocationProviderClient(Reportar.this);
        ubicacion.getLastLocation().addOnSuccessListener(Reportar.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    Double latitud=location.getLatitude();
                    Double longitud=location.getLongitude();

                    UbicacionHelper ubi=new UbicacionHelper(latitud,longitud,numero);
                    refubicacion.push().setValue(ubi);
                    Toast.makeText(Reportar.this,"Reporte agregado!",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}
