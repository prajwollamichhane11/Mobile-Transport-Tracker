package org.anjelikasah.mtt2;

public class Artist
{
    private String BUS_NO;
    private String OWNER_NAME;
    private String EMAIL;
    private String PASSWORD;
    private String Longitude;
    private String Latitude;

    public Artist(){

    }
    public Artist(String BUS_NO, String OWNER_NAME, String EMAIL, String PASSWORD, String Longitude, String Latitude)
    {
        this.BUS_NO = BUS_NO;
        this.OWNER_NAME = OWNER_NAME;
        this.EMAIL = EMAIL;
        this.PASSWORD = PASSWORD;
        this.Longitude=Longitude;
        this.Latitude=Latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

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
}
