package org.techtown.myapplication;

import java.io.Serializable;

public class Ob implements Serializable {

    String plateNo;
    String gpslati;
    String gpslong;
    public Ob(){

    }



    public String getPlateNo() {
        return plateNo;
    }
    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getGpslati() {
        return gpslati;
    }

    public void setGpslati(String gpslati) {
        this.gpslati = gpslati;
    }

    public String getGpslong() {
        return gpslong;
    }

    public void setGpslong(String gpslong) {
        this.gpslong = gpslong;
    }
}
