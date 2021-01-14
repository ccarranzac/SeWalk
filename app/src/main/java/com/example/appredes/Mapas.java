package com.example.appredes;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static java.lang.Integer.parseInt;


public class Mapas extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseReference mDataBase;
    TextView map_username;
    String mapUsername;
    Button posicion;
    private FusedLocationProviderClient ubicacion_mapa;
    int num_pos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapas);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mDataBase = FirebaseDatabase.getInstance().getReference();

        map_username = findViewById(R.id.username_maps);
        posicion=findViewById(R.id.posicion_button);
        num_pos=0;

        Bundle datos = this.getIntent().getExtras();
        mapUsername = datos.getString("username");

        map_username.setText(mapUsername);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mDataBase.child("ubicacion").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                LatLng bogota = new LatLng(4.653421, -74.145150);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bogota, 11.1f));
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    UbicacionHelper ubi=snapshot.getValue(UbicacionHelper.class);
                    Double latitud=ubi.getLatitud();
                    Double longitud=ubi.getLongitud();
                    String numero=ubi.getUbicacion();
                    if (numero.equals("0") || numero.equals("1")){
                        mMap.addMarker(new MarkerOptions().position(new LatLng(latitud,longitud)).icon(BitmapDescriptorFactory.fromResource(R.drawable.adn1)));
                    }
                    else if(numero.equals("2") || numero.equals("3")){
                        mMap.addMarker(new MarkerOptions().position(new LatLng(latitud,longitud)).icon(BitmapDescriptorFactory.fromResource(R.drawable.adn2)));
                    }
                    else if(numero.equals("4") || numero.equals("5")){
                        mMap.addMarker(new MarkerOptions().position(new LatLng(latitud,longitud)).icon(BitmapDescriptorFactory.fromResource(R.drawable.adn3)));
                    }
                    else if(numero.equals("6") || numero.equals("7")){
                        mMap.addMarker(new MarkerOptions().position(new LatLng(latitud,longitud)).icon(BitmapDescriptorFactory.fromResource(R.drawable.adn4)));
                    }
                    else if(numero.equals("8") || numero.equals("9")){
                        mMap.addMarker(new MarkerOptions().position(new LatLng(latitud,longitud)).icon(BitmapDescriptorFactory.fromResource(R.drawable.adn5)));
                    }
                    else{
                        System.out.println("dato perdido");
                    }

                }
                if(num_pos==1){
                    if(ContextCompat.checkSelfPermission(Mapas.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(Mapas.this,"Tenemos Permiso",Toast.LENGTH_SHORT).show();
                    }else{
                        ActivityCompat.requestPermissions(Mapas.this,new String[]{
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION},2);
                    }
                    ubicacion_mapa= LocationServices.getFusedLocationProviderClient(Mapas.this);
                    ubicacion_mapa.getLastLocation().addOnSuccessListener(Mapas.this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if(location!=null){
                                Double latitudUser=location.getLatitude();
                                Double longitudUser=location.getLongitude();
                                mMap.addMarker(new MarkerOptions().position(new LatLng(latitudUser,longitudUser)).icon(BitmapDescriptorFactory.fromResource(R.drawable.yo)));
                            }
                        }
                    });
                    num_pos=0;
                    System.out.println(num_pos);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void tokenUbicacion(View view){
        num_pos=1;
        System.out.println(num_pos);
        onMapReady(mMap);
    }
}
