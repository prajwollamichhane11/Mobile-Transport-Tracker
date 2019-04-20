package org.anjelikasah.mtt2;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
//import android.widget.RelativeLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
//import android.graphics.Color;
//import android.widget.EditText;
//import android.content.res.Resources;
//import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends Activity
{
    //for connecting username and password with database
    DatabaseHelper helper = new DatabaseHelper(this);

    //to check if the user have correct verion of Google API
    private static final String TAG = "MainActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private static final int LOGIN_PERMISSION = 1000;
    private Button sign_in_bus;



    EditText eemail,epassword;
    TextView elongclick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sign_in_bus = (Button) findViewById(R.id.sign_in_as_bus);
        sign_in_bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workpage_bus();
            }
        });







        eemail = (EditText)findViewById(R.id.email);
        epassword = (EditText)findViewById(R.id.password);

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

        if (isServicesOK())
        {
            init();
        }

    }



    public void init()
    {
        Button mapbutton = (Button)findViewById(R.id.map);
        mapbutton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View mapview)
                    {
                        EditText username = (EditText)findViewById(R.id.email);
                        String susername = username.getText().toString();// to convert email into string
                        EditText password = (EditText)findViewById(R.id.password);
                        String spassword = password.getText().toString();// to convert password to string for using in database

                        if(spassword.equals("")|| susername.equals(""))
                        {
                            Toast.makeText(getApplicationContext(), "Please enter Username and pasword", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            String pass = helper.searchPass(susername);
                            if (spassword.equals(pass)) {
                                Intent mapintent = new Intent(MainActivity.this, MapsActivity.class);
                                mapintent.putExtra("Username", susername);
                                startActivity(mapintent);
                            } else {
                                Toast.makeText(getApplicationContext(), "Username and pasword didnot match", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
        );
    }

    public boolean isServicesOK()
    {
        Log.d(TAG,"isServicesOK: checking google services verion");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);
        if (available == ConnectionResult.SUCCESS)
        {
            //everthing is fine and user can make map requests
            Log.d(TAG,"isServicesOK: Google play services is working");
            return true;
        }
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(available))
        {
            //an error occured but we can resolve it
            Log.d(TAG,"isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this,available,ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else
        {
            //notiong we can do to resolve the error
            Toast.makeText(this, "You cannot make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void workpage_bus() {
        Intent intent = new Intent(this, workpage.class);
        startActivity(intent);
    }



}
