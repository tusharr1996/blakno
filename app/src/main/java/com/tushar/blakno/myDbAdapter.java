package com.tushar.blakno;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Tushar on 06-06-2017.
 */

public class myDbAdapter {
    myDbHelper myhelper;
    public long id1;
    public myDbAdapter(Context context)
    {
        myhelper = new myDbHelper(context);
    }

    public long insertData(int pos, String address)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();

        String[] columns = {myDbHelper.myPOS};
        Cursor cur = dbb.query(myDbHelper.TABLE_NAME,columns,myDbHelper.myPOS + "=" + pos,null,null,null,null,null);

        if(cur.getCount() == 0)
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(myDbHelper.myPOS, pos);
            contentValues.put(myDbHelper.myADDRESS, address);
           id1 = dbb.insert(myDbHelper.TABLE_NAME, null , contentValues);
        }

        return id1;
    }

    public ArrayList<String> getData()
    {
        ArrayList<String> yourStringValues = new ArrayList<String>();

        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.myADDRESS};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        if (cursor.moveToFirst()) {
            do {
                yourStringValues.add(cursor.getString(cursor
                        .getColumnIndex(myDbHelper.myADDRESS)));
            } while (cursor.moveToNext());
        } else {
            return null;
        }
//        StringBuffer buffer= new StringBuffer();
//        while (cursor.moveToNext())
//        {
//            int cid =cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
//            String pos =cursor.getString(cursor.getColumnIndex(myDbHelper.myPOS));
//            String  address =cursor.getString(cursor.getColumnIndex(myDbHelper.myADDRESS));
//            buffer.append(cid+ "   " + pos + "   " + address +" \n");
//        }
        return yourStringValues;
    }

    public  int delete()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
     //   String[] whereArgs ={upos};
        String[] columns = {myDbHelper.myADDRESS,myDbHelper.myPOS,myDbHelper.UID};

     //   int count =db.delete(myDbHelper.TABLE_NAME ,myDbHelper.myPOS +" = ?",whereArgs);
        return db.delete(myDbHelper.TABLE_NAME,null,null);
    }

    public  int deletedata(int dpos)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        return db.delete(myDbHelper.TABLE_NAME,myDbHelper.myPOS + "=" + dpos,null);
    }

    public  boolean check()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.myPOS};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);



        return cursor.moveToFirst() ;
    }

    public int updateName(String oldName , String newName)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.myPOS,newName);
        String[] whereArgs= {oldName};
        int count =db.update(myDbHelper.TABLE_NAME,contentValues, myDbHelper.myPOS +" = ?",whereArgs );
        return count;
    }

    static class myDbHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "myDatabase";    // Database Name
        private static final String TABLE_NAME = "myTable";   // Table Name
        private static final int DATABASE_Version = 1;   // Database Version
        private static final String UID="_id";     // Column I (Primary Key)
        private static final String myPOS = "position";    //Column II
        private static final String myADDRESS = "address";    // Column III
        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ myPOS +" VARCHAR(255) ,"+ myADDRESS +" VARCHAR(225));";
        private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
        private Context context;

        public myDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }

        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) {

            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {

                db.execSQL(DROP_TABLE);
                onCreate(db);
            }catch (Exception e) {

            }
        }
    }
}