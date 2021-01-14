package com.example.appredes;

public class UbicacionHelper {
    Double latitud;
    Double longitud;
    String ubicacion;

    public UbicacionHelper() {
    }

    public UbicacionHelper(Double latitud, Double longitud, String ubicacion) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.ubicacion =  ubicacion;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}
