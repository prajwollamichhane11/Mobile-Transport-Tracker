package org.anjelikasah.mtt2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "User.db";
    public static final String TABLE_NAME = "User_Table";
    public static final String NAME = "Name";
    public static final String USERNAME = "Username";
    public static final String EMAIL = "Email";
    public static final String PASSWORD = "Password";
    public static final String CPASSWORD = "Confirm_Password";


    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create Table " + TABLE_NAME + " (Name TEXT PRIMARY KEY, Username TEXT, Email TEXT, Password TEXT, Confirm_Password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name, String email, String username, String password, String cpassword)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(USERNAME, username);
        contentValues.put(EMAIL, email);
        contentValues.put(PASSWORD,password);
        contentValues.put(CPASSWORD, cpassword);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;

    }
    public Cursor getAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return res;
    }

    public boolean updateData(String name, String email, String username, String password, String cpassword)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(USERNAME, username);
        contentValues.put(EMAIL, email);
        contentValues.put(PASSWORD,password);
        contentValues.put(CPASSWORD, cpassword);
        db.update(TABLE_NAME, contentValues, "Username = ?", new String[]{ username });
        return true;

    }

    public Integer deleteData(String username)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "Username = ?", new String[] { username });
    }

    //for the main activity's username and pass matching with database
    public String searchPass(String uname)
    {
        SQLiteDatabase db= this.getReadableDatabase();
        String query = "select username, password from "+TABLE_NAME;
        Cursor cursor =db.rawQuery(query, null);
        String ausername, apassword;
        apassword = "Not found";
        if (cursor.moveToFirst())
        {
            do {
                ausername = cursor.getString(0);
                if (ausername.equals(uname))
                {
                    apassword = cursor.getString(1);
                    break;
                }
            }
            while (cursor.moveToNext());
        }
        return apassword;

    }

}
