package org.techtown.myapplication;

import java.io.Serializable;

public class Ob implements Serializable {

    Double gpslati; //위도
    Double gpslong; //경도
    String vehicleno; //버스 번호판
    String nodenm; //정류소
    String routenm; //노선번호

    public Ob(Double gpslati,Double gpslong,String vehicleno,String nodenm,String routenm){
        this.gpslati = gpslati;
        this.gpslong = gpslong;
        this.vehicleno = vehicleno;
        this.nodenm = nodenm;
        this.routenm = routenm;
    }
    public Ob(){

    }

    public String getNodenm() {
        return nodenm;
    }

    public void setNodenm(String nodenm) {
        this.nodenm = nodenm;
    }

    public String getRoutenm() {
        return routenm;
    }

    public void setRoutenm(String routenm) {
        this.routenm = routenm;
    }

    public String getVehicleno() {
        return vehicleno;
    }

    public void setVehicleno(String vehicleno) {
        this.vehicleno = vehicleno;
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
