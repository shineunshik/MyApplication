package org.techtown.myapplication;

public class Ob_Bus_Select {

    String endnodenm; //종점
    String endvehicletime; //막차시간
    String routeid; //노선 id
    String routeno; //노선번호
    String routetp; //버스 종류
    String startnodenm; //기점
    String startvehicletime; //첫차시간
    String citycode; //도시 코드


    public Ob_Bus_Select(){

    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getEndnodenm() {
        return endnodenm;
    }

    public void setEndnodenm(String endnodenm) {
        this.endnodenm = endnodenm;
    }

    public String getEndvehicletime() {
        return endvehicletime;
    }

    public void setEndvehicletime(String endvehicletime) {
        this.endvehicletime = endvehicletime;
    }

    public String getRouteid() {
        return routeid;
    }

    public void setRouteid(String routeid) {
        this.routeid = routeid;
    }

    public String getRouteno() {
        return routeno;
    }

    public void setRouteno(String routeno) {
        this.routeno = routeno;
    }

    public String getRoutetp() {
        return routetp;
    }

    public void setRoutetp(String routetp) {
        this.routetp = routetp;
    }

    public String getStartnodenm() {
        return startnodenm;
    }

    public void setStartnodenm(String startnodenm) {
        this.startnodenm = startnodenm;
    }

    public String getStartvehicletime() {
        return startvehicletime;
    }

    public void setStartvehicletime(String startvehicletime) {
        this.startvehicletime = startvehicletime;
    }
}
