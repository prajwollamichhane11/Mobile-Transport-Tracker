package org.anjelikasah.mtt2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.cert.PolicyNode;
import java.util.ArrayList;
import java.util.List;

public class Bus_signin extends Activity
{
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databseArtist;

    ListView listViewArtists;
    List<Artist> artistList;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_signin);

        databseArtist = FirebaseDatabase.getInstance().getReference("buses");
        artistList = new ArrayList<>();

        location();

        //for sign up button
        Button loginbutton = (Button)findViewById(R.id.loginbutton);
        loginbutton.setOnClickListener(
                new Button.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        Intent loginintent = new Intent(v.getContext(), activity2.class);
                        startActivity(loginintent);
                    }
                }


        );
    }
    public void location()
    {
        //for get location button
        Button location_button = (Button)findViewById(R.id.location);
        location_button.setOnClickListener(
                new Button.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        final EditText busno = (EditText)findViewById(R.id.busno);
                        final String sbusno = busno.getText().toString();// to convert email into string
                        EditText password = (EditText)findViewById(R.id.password);
                        final String spassword = password.getText().toString();// to convert password to string for using in database

                        if (sbusno.equals("") || spassword.equals(""))
                        {
                            Toast.makeText(getApplicationContext(),"Enter bus no. and password",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            databseArtist.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    StringBuffer buffer = new StringBuffer();//new
                                    artistList.clear();
                                    int r = 0;
                                    for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {
                                        Artist artist = artistSnapshot.getValue(Artist.class);
                                        artistList.add(artist);
                                        String viewbusno = artist.getBUS_NO();
                                        String viewpassword = artist.getPASSWORD();
                                        buffer.append("busno:" + viewbusno + "\n");//new
                                        buffer.append("pass:" + viewpassword + "\n \n");

                                        if (viewbusno.equals(sbusno) && viewpassword.equals(spassword)) {
                                            Intent getlocation = new Intent(Bus_signin.this, workpage.class);
//                                        getlocation.putExtra("Username", busno);
                                            startActivity(getlocation);
                                            r = 1;
                                            break;
                                        }
                                    }
                                    if (r == 0) {
                                        Toast.makeText(getApplicationContext(), "Bus no. and password did not match", Toast.LENGTH_SHORT).show();
                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                }
        );
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
