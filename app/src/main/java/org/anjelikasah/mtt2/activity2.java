package org.anjelikasah.mtt2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
//import android.widget.RelativeLayout;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
//import android.graphics.Color;
//import android.widget.EditText;
//import android.content.res.Resources;
//import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class activity2 extends Activity
{
    DatabaseReference databseArtist;
    EditText email, busno, password, cpassword, oname;
    Button button_add, btnViewAll, btnViewUpdate, button_delete;
    private static final int REQUEST_LOCATION = 1;
    Button button;
    TextView textView;
    LocationManager locationManager;
    String lattitude,longitude;
    DatabaseReference mref, mDatabaseReference;
    private ArrayList Latitude,Longitude;


    ListView listViewArtists;
    List<Artist> artistList;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);
        Latitude=new ArrayList<>();
        Longitude=new ArrayList<>();
        databseArtist = FirebaseDatabase.getInstance().getReference("buses");
        mDatabaseReference=FirebaseDatabase.getInstance().getReference("buses");
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    Log.d("DATA", String.valueOf(dataSnapshot));
                    for(DataSnapshot articlesnapshot: dataSnapshot.getChildren())
                    {
                        Artist artist=articlesnapshot.getValue(Artist.class);
                        String Bus=artist.getBUS_NO();
                        String Lat= artist.getLatitude();
                        String Lon=artist.getLongitude();
                        Latitude.add(Lat); //arraylist for latitude
                        Longitude.add(Lon); //arraylist for longitude
                        Toast.makeText(activity2.this, "Busno = "+Bus+ "Longitude= " + Lon + "Latitude= " + Lon,Toast.LENGTH_LONG).show();




                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Log.d("Longitude", String.valueOf(Longitude));


        oname = (EditText) findViewById(R.id.oname);
        email = (EditText) findViewById(R.id.cemail);
        busno = (EditText)findViewById(R.id.busno);
        password = (EditText)findViewById(R.id.cpassword);
        cpassword = (EditText)findViewById(R.id.cconfirmpassword);
        button_add = (Button) findViewById(R.id.button_add);
        btnViewAll = (Button) findViewById(R.id.button_view);
        btnViewUpdate = (Button) findViewById(R.id.button_update);
        button_delete = (Button)findViewById(R.id.button_delete);

//        listViewArtists = (ListView)findViewById(R.id.listViewArtist);
        artistList = new ArrayList<>();

        viewAll();

        button_add.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addBusdata();

                        /// / addBusdata();
                    }
                }
        );

        Button logoutbutton = (Button) findViewById(R.id.logout);

        logoutbutton.setOnClickListener(
                new Button.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        Intent logoutintent = new Intent(v.getContext(), Bus_signin.class);
                        startActivity(logoutintent);
                    }
                }
        );
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        databseArtist.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
//            {
//                artistList.clear();
//                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren())
//                {
//                    Artist artist = artistSnapshot.getValue(Artist.class);
//                    artistList.add(artist);
//                }
//                BusList adapter = new BusList(activity2.this, artistList);
//                listViewArtists.setAdapter(adapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
    public void viewAll()
    {
        btnViewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        databseArtist.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                            {
                                StringBuffer buffer = new StringBuffer();//new
                                artistList.clear();
                                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren())
                                {
                                    Artist artist = artistSnapshot.getValue(Artist.class);
                                    artistList.add(artist);
                                   String viewbusno = artist.getBUS_NO();
                                   String viewpassword = artist.getPASSWORD();
                                    buffer.append("busno:"+viewbusno+"\n");//new
                                    buffer.append("pass:"+viewpassword+"\n \n");

                                }

                                BusList adapter = new BusList(activity2.this, artistList);

                                showMessage("data",buffer.toString());
                                //listViewArtists.setAdapter(adapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
        );
    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(activity2.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (activity2.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity2.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Location location2 = locationManager.getLastKnownLocation(LocationManager. PASSIVE_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);



            } else  if (location1 != null) {
                double latti = location1.getLatitude();
                double longi = location1.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);




            } else  if (location2 != null) {
                double latti = location2.getLatitude();
                double longi = location2.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);


            }else{

                Toast.makeText(this,"Unble to Trace your location",Toast.LENGTH_SHORT).show();

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

    private void addBusdata() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();

        }


        String owner_name = oname.getText().toString().trim();
        String bus_no = busno.getText().toString().trim();
        String add_email = email.getText().toString().trim();
        String add_password = password.getText().toString().trim();
        String add_cpassword = cpassword.getText().toString().trim();

        if ((!TextUtils.isEmpty(owner_name)) && (!TextUtils.isEmpty(bus_no)) && (!TextUtils.isEmpty(add_email)) && (!TextUtils.isEmpty(add_password))&& (!TextUtils.isEmpty(add_cpassword)))
        {
            if (add_password.equals(add_cpassword))
            {
                String id = databseArtist.push().getKey();


                Artist artist = new Artist(bus_no, owner_name, add_email, add_password,lattitude,longitude);
                databseArtist.child(id).setValue(artist);

                Toast.makeText(this, "Data added", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this,"Password did not match", Toast.LENGTH_SHORT).show();
            }
        } else
        {
            Toast.makeText(this,"Fill all the boxes", Toast.LENGTH_SHORT).show();
        }
    }






    public void showMessage(String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


}
