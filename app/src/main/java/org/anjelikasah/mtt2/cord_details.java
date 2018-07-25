package org.anjelikasah.mtt2;

public class cord_details {
    String lattitude, longitude,msg;
    public cord_details()
    {

    }

    public cord_details(String lattitude, String longitude, String msg) {
        this.lattitude = lattitude;
        this.longitude = longitude;
        this.msg = msg;
    }

    public String getLattitude() {
        return lattitude;
    }

    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
