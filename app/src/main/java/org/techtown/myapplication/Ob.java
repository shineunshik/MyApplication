package org.techtown.myapplication;

import java.io.Serializable;

public class Ob implements Serializable {

    String vehicleno;
    String plateNo;
    Double gpslati;
    Double gpslong;
    public Ob(){

    }

    public String getVehicleno() {
        return vehicleno;
    }

    public void setVehicleno(String vehicleno) {
        this.vehicleno = vehicleno;
    }

    public String getPlateNo() {
        return plateNo;
    }
    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public Double getGpslati() {
        return gpslati;
    }

    public void setGpslati(Double gpslati) {
        this.gpslati = gpslati;
    }

    public Double getGpslong() {
        return gpslong;
    }

    public void setGpslong(Double gpslong) {
        this.gpslong = gpslong;
    }
}
