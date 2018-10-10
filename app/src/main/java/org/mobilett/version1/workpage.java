package org.mobilett.version1;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class workpage extends AppCompatActivity implements View.OnClickListener
{
    ConnectionDetector cd;
    public String id;

    private static final String TAGF = "MainActivity";

    public static final String TAG = "workpage";

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databseArtist;
    List<Artist> artistList;


    private static final int REQUEST_LOCATION = 1;
    Button button, logoutbtn, gotomap,sendmsg,delmsg,btnroute;
    TextView textView, edittext;
    EditText msg, route;
    LocationManager locationManager;
    String lattitude, longitude;
    DatabaseReference mref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workpage);

        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        textView = (TextView) findViewById(R.id.text_location);
        button = (Button) findViewById(R.id.button_location);

        //for connection check
        cd = new ConnectionDetector(this);

        //to update route
        route = (EditText)findViewById(R.id.update_route);
        btnroute = (Button)findViewById(R.id.btn_update_route);
        btnroute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateroute();
            }
        });

        //to send message-start
        msg = (EditText) findViewById(R.id.msg);
        sendmsg = (Button)findViewById(R.id.sendmsg);
        delmsg = (Button)findViewById(R.id.delmsg);
        sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addmsg();
            }
        });
        delmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletemsg();
            }
        });


        mref = FirebaseDatabase.getInstance().getReference("coordinates");
        id = getIntent().getStringExtra("id");


        //for database firebase
        databseArtist = FirebaseDatabase.getInstance().getReference("buses");
        artistList = new ArrayList<>();

        button.setOnClickListener(this);

        //for logout
        logoutbtn = (Button) findViewById(R.id.logout);
        gotomap = (Button) findViewById(R.id.gotomap);

        gotomap.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (cd.isConnected()) {
                            Intent mapintent = new Intent(workpage.this, MapsActivity.class);
                            startActivity(mapintent);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Check Internet Connection !!!",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        logoutbtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id = getIntent().getStringExtra("id");
                        deleteArtist(id);
                        finish();
                    }
                }
        );

        Button LogOut = (Button) findViewById(R.id.logout1);
        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefs.saveSharedSetting(workpage.this, "CaptainCode", "true");
                //And when you click on Logout button, You will set the value to True AGAIN
                Intent LogOut = new Intent(getApplicationContext(), Bus_signin.class);
                LogOut.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(LogOut);

                finish();
            }
        });

    }

    private boolean deleteArtist(String wow) {
        if (cd.isConnected()) {
            DatabaseReference longitude = FirebaseDatabase.getInstance().getReference("buses").child(wow).child("longitude");
            DatabaseReference latitude = FirebaseDatabase.getInstance().getReference("buses").child(wow).child("latitude");
            latitude.setValue(null);
            longitude.setValue(null);
            Toast.makeText(this, "Your Location is Turned off ", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Check Internet Connection !!!",Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private boolean updateArtist(double lon, double lat) {
        if (cd.isConnected()) {
            DatabaseReference longitude = FirebaseDatabase.getInstance().getReference("buses").child(id).child("longitude");
            DatabaseReference latitude = FirebaseDatabase.getInstance().getReference("buses").child(id).child("latitude");
            latitude.setValue(lon);
            longitude.setValue(lat);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Check Internet Connection !!!",Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public void onBackPressed() {
        Toast.makeText(this, "Press LOGOUT or GO TO MAP", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();
            cord_details cd = new cord_details(lattitude, longitude, "N/A");
            mref.setValue(cd);
        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(workpage.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (workpage.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(workpage.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Location location2 = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);
                double latitu = Double.parseDouble(lattitude);
                double longitu = Double.parseDouble(longitude);

                textView.setText("Your current location is" + "\n" + "Lattitude = " + lattitude
                        + "\n" + "Longitude = " + longitude);
                updateArtist(longitu, latitu);

            } else if (location1 != null) {
                double latti = location1.getLatitude();
                double longi = location1.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);
                double latitu = Double.parseDouble(lattitude);
                double longitu = Double.parseDouble(longitude);


                textView.setText("Your current location is" + "\n" + "Lattitude = " + lattitude
                        + "\n" + "Longitude = " + longitude);
                updateArtist(longitu, latitu);


            } else if (location2 != null) {
                double latti = location2.getLatitude();
                double longi = location2.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);
                double latitu = Double.parseDouble(lattitude);
                double longitu = Double.parseDouble(longitude);


                textView.setText("Your current location is" + "\n" + "Lattitude = " + lattitude
                        + "\n" + "Longitude = " + longitude);
                updateArtist(longitu, latitu);


            } else {

                Toast.makeText(this, "Unble to Trace your location", Toast.LENGTH_SHORT).show();

            }


        }
    }

    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void addmsg()
    {
        if (cd.isConnected()) {
            String Message = msg.getText().toString().trim();
            if (Message.equals(""))
            {
                Toast.makeText(this, "Write a message",Toast.LENGTH_SHORT).show();
            }
            else
                {
                DatabaseReference dbmsg = FirebaseDatabase.getInstance().getReference("buses").child(id).child("msg");
                dbmsg.setValue(Message);
                Toast.makeText(this, "Message Sent ", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Check Internet Connection !!!",Toast.LENGTH_SHORT).show();
        }
    }
    private void updateroute()
    {
        if (cd.isConnected())
        {
            String update_route = route.getText().toString().trim();

            if (update_route.equals(""))
            {
                Toast.makeText(this, "Give the route to update", Toast.LENGTH_SHORT).show();
            }
            else
                {
                DatabaseReference dbmsg = FirebaseDatabase.getInstance().getReference("buses").child(id).child("route");
                dbmsg.setValue(update_route);
                Toast.makeText(this, "Route Updated", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Check Internet Connection !!!",Toast.LENGTH_SHORT).show();
        }
    }

    private void deletemsg()
    {
        if (cd.isConnected()) {
            String message = msg.getText().toString().trim();
            DatabaseReference dbmsg = FirebaseDatabase.getInstance().getReference("buses").child(id).child("msg");
            dbmsg.setValue("a");

            Toast.makeText(this, "Your message is deleted ", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Check Internet Connection !!!",Toast.LENGTH_SHORT).show();
        }

    }

}