package org.anjelikasah.mtt2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
//import android.widget.RelativeLayout;
import android.widget.Button;
//import android.graphics.Color;
//import android.widget.EditText;
//import android.content.res.Resources;
//import android.util.TypedValue;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class activity2 extends Activity
{
    DatabaseHelper myDb;

    EditText email, username, password, cpassword, namee;
    Button button_add, btnViewAll, btnViewUpdate, button_delete;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);
        myDb = new DatabaseHelper(this);

        namee = (EditText) findViewById(R.id.cname);
        email = (EditText) findViewById(R.id.cemail);
        username = (EditText)findViewById(R.id.cusername);
        password = (EditText)findViewById(R.id.cpassword);
        cpassword = (EditText)findViewById(R.id.cconfirmpassword);
        button_add = (Button) findViewById(R.id.button_add);
        btnViewAll = (Button) findViewById(R.id.button_view);
        btnViewUpdate = (Button) findViewById(R.id.button_update);
        button_delete = (Button)findViewById(R.id.button_delete);

        addData();
        viewAll();
        updateData();
        deleteData();

        Button logoutbutton = (Button) findViewById(R.id.logout);

        logoutbutton.setOnClickListener(
                new Button.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        Intent logoutintent = new Intent(v.getContext(), MainActivity.class);
                        startActivity(logoutintent);
                    }
                }
        );
    }



    public void deleteData()
    {
        button_delete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deleteRows = myDb.deleteData(username.getText().toString());
                        if (deleteRows > 0)
                        {
                            Toast.makeText(activity2.this, "Data deleted", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(activity2.this, "Data is not deleted", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
        );
    }

    public void updateData()
    {
        btnViewUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDb.updateData(namee.getText().toString(),email.getText().toString(), username.getText().toString(), password
                                .getText().toString(), cpassword.getText().toString() );
                        if(isUpdate == true)
                        {
                            Toast.makeText(activity2.this, "Data updated", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(activity2.this, "Data is not updated", Toast.LENGTH_SHORT).show();

                        }

                    }
                }
        );
    }

    public void viewAll()
    {
        btnViewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();

                        if (res.getColumnCount() == 0)
                        {
                            //show message
                            showMessage("Error", "No data found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext())
                        {
                            buffer.append("Name :"+res.getString(0)+"\n");
                            buffer.append("Username : "+res.getString(1)+"\n");
                            buffer.append("Email : "+res.getString(2)+"\n");
                            buffer.append("Password : "+res.getString(3)+"\n");
                            buffer.append("Confirm_password : "+res.getString(4)+"\n\n");
                        }
                        //show all data
                        showMessage("Data", buffer.toString());
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

    public void addData()
    {
        button_add.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(namee.getText().toString().equals("")|| email.getText().toString().equals("") || username.getText().toString().equals("") || password.getText().toString().equals("")||cpassword.getText().toString().equals(""))
                        {
                            Toast.makeText(getApplicationContext(), "Please fill all the boxes", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if (password.getText().toString().equals(cpassword.getText().toString())) {
                                boolean isInserted = myDb.insertData(namee.getText().toString(), email.getText().toString(), username.getText().toString(), password
                                        .getText().toString(), cpassword.getText().toString());
                                if (isInserted == true) {
                                    Toast.makeText(activity2.this, "Data inserted", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(activity2.this, "Data is not inserted", Toast.LENGTH_SHORT).show();

                                }
                            } else
                                Toast.makeText(activity2.this, "Password did not match !!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }
//    public void onClick(View v)
//    {
//        Intent logoutintent= new Intent(v.getContext(),MainActivity.class);
//        startActivity(logoutintent);
//    }

}
