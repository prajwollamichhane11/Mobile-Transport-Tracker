package org.mobilett.version1;

public class Artist
{
    private String BUS_NO;
    private String OWNER_NAME;
    private String EMAIL;
    private String PASSWORD;
    private double Longitude;
    private double Latitude;
    private String id;
    private String msg;
    private String route;


    public Artist(){

    }
    public Artist(String BUS_NO, String OWNER_NAME, String EMAIL, String PASSWORD, double Longitude, double Latitude,String id,String msg, String route)
    {
        this.BUS_NO = BUS_NO;
        this.OWNER_NAME = OWNER_NAME;
        this.EMAIL = EMAIL;
        this.PASSWORD = PASSWORD;
        this.Longitude=Longitude;
        this.Latitude=Latitude;
        this.id = id;
        this.msg=msg;
        this.route = route;
    }
    public String getMsg(){return msg;}
    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public void setRoute(String route) { this.route=route;}

    public String getRoute(){return route;}

    public String getBUS_NO() {
        return BUS_NO;
    }

    public String getOWNER_NAME() {
        return OWNER_NAME;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setBUS_NO(String BUS_NO) {
        this.BUS_NO = BUS_NO;
    }

    public void setOWNER_NAME(String OWNER_NAME) {
        this.OWNER_NAME = OWNER_NAME;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public void setId(String id){this.id = id;}

    public String getId() {
        return id;
    }
}
