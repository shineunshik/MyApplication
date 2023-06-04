package org.techtown.myapplication;

import java.io.Serializable;

public class Ob_Station implements Serializable { //노선별 경유정류소 목록

    Double gpslati; //위도
    Double gpslong; //경도
    String nodeid; //정류소ID
    String nodenm; //정류소
    String nodeord;//정류소 순번
    String  routeid ; //노선ID  옵션
    String endnodenm; //종점
    String startnodenm; //기점

    public Ob_Station(Double gpslati, Double gpslong, String nodeid,String nodenm,String nodeord,String routeid){
        this.gpslati = gpslati;
        this.gpslong = gpslong;
        this.nodeid = nodeid;
        this.nodenm = nodenm;
        this.nodeord = nodeord;
        this.routeid = routeid;
    }

    public Ob_Station( String nodeid){
        this.nodeid = nodeid;
    }
    public Ob_Station(){

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

    public String getRouteid() {
        return routeid;
    }

    public void setRouteid(String routeid) {
        this.routeid = routeid;
    }

    public String getNodeid() {
        return nodeid;
    }

    public void setNodeid(String nodeid) {
        this.nodeid = nodeid;
    }
}
