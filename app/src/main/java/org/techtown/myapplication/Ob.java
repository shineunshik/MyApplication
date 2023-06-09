package org.techtown.myapplication;

import java.io.Serializable;

public class Ob implements Serializable {

    Double gpslati; //위도
    Double gpslong; //경도
    String vehicleno; //버스 번호판
    String nodenm; //정류소
    String nodeord;//정류소 순번
    String routenm; //노선번호
    String nodeid; //정류소ID
    String endnodenm; //종점
    String startnodenm; //기점

    public Ob(Double gpslati,Double gpslong,String vehicleno,String nodenm,String routenm,String nodeid){
        this.gpslati = gpslati;
        this.gpslong = gpslong;
        this.vehicleno = vehicleno;
        this.nodenm = nodenm;
        this.routenm = routenm;
        this.nodeid = nodeid;
    }
    public Ob(){

    }

    public String getEndnodenm() {
        return endnodenm;
    }

    public void setEndnodenm(String endnodenm) {
        this.endnodenm = endnodenm;
    }

    public String getStartnodenm() {
        return startnodenm;
    }

    public void setStartnodenm(String startnodenm) {
        this.startnodenm = startnodenm;
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


    public String getNodeord() {
        return nodeord;
    }

    public void setNodeord(String nodeord) {
        this.nodeord = nodeord;
    }


    public String getNodeid() {
        return nodeid;
    }

    public void setNodeid(String nodeid) {
        this.nodeid = nodeid;
    }
}
