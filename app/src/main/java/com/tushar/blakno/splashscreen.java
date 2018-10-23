package com.tushar.blakno;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import static android.R.attr.max;
import static android.view.View.VISIBLE;

/**
 * Created by Tushar on 22-03-2017.
 */

public class splashscreen extends AppCompatActivity {


    SharedPreferences pref;
    public Boolean my_first_time;
    public int art_count = 0;
    public int maxart = 0;
    SharedPreferences.Editor editor;
    public String artsnumber;
    public String str;
    String serverURL = "https://tusharrk.000webhostapp.com/no%20of%20arts.txt";


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        Toast.makeText(this, "Pqwerwqr", Toast.LENGTH_SHORT).show();


        ConnectivityManager cm = (ConnectivityManager) splashscreen.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();





        if (activeNetwork == null) {

            Intent i = new Intent(splashscreen.this, MainActivity.class);
            startActivity(i);
            splashscreen.this.finish();

//            Toast.makeText(this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();

        }


        pref = this.getSharedPreferences("artscount", 0); // 0 - for private mode
        editor = pref.edit();
        if (pref.getBoolean("my_first_time", true)) {

            new LongOperation().execute(serverURL);
             // Storing integer
            //the app is being launched for first time, do something
            Log.d("Comments", "First time");
//            Toast toast = Toast.makeText(this,"new start",Toast.LENGTH_SHORT);
//           toast.show();
            // first time task


            if (activeNetwork != null) {
                pref.edit().putBoolean("my_first_time", false).commit();

                // record the fact that the app has been started at least once

            }


        }
        else {
            new LongOperation().execute(serverURL);
        }




    }

//    void download_arts_count(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(splashscreen.this, "abcd", Toast.LENGTH_SHORT).show();
//
//
//
//
//
//            try {
//                // Create a URL for the desired page
//                URL url = new URL("https://tusharrk.000webhostapp.com/no%20of%20arts.txt");
//
//                // Read all the text returned by the server
//                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
//                artsnumber = "";
//
//
//
//                while ((str = in.readLine()) != null) {
//                    // str is one line of text; readLine() strips the newline character(s)
//                    artsnumber = artsnumber + str;
//                }
//                in.close();
//            } catch (MalformedURLException e) {
//            } catch (IOException e) {
//            }
//
//
//
//
//
//        try {
//            maxart = Integer.parseInt(artsnumber);
//        } catch(NumberFormatException nfe) {
//
//        }
//            }
//        }).start();
//
//    }



    private class LongOperation  extends AsyncTask<String, Void, Void> {



        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

        }

        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {

            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                try {
                    // Create a URL for the desired page
                    URL url = new URL("https://tusharrk.000webhostapp.com/no%20of%20arts.txt");

                    // Read all the text returned by the server
                    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                    artsnumber = "";
//                Toast.makeText(getActivity(), "abcd", Toast.LENGTH_SHORT).show();


                    while ((str = in.readLine()) != null) {
                        // str is one line of text; readLine() strips the newline character(s)
                        artsnumber = artsnumber + str;
                    }
                    in.close();
                } catch (MalformedURLException e) {
                } catch (IOException e) {
                }



            }


            try {
                maxart = Integer.parseInt(artsnumber);

//                splashscreen.this.runOnUiThread(new Runnable() {
//                    public void run() {
//                        Toast.makeText(splashscreen.this, String.valueOf(maxart), Toast.LENGTH_SHORT).show();
//                    }
//                });


            } catch(NumberFormatException nfe) {

            }
            return null;
        }

        protected void onPostExecute(Void unused) {
            // NOTE: You can call UI Element here.

            // Close progress dialog

if (maxart != 0){
    art_count = maxart;
    editor.putInt("arts_count", maxart);
//
//                                    splashscreen.this.runOnUiThread(new Runnable() {
//                    public void run() {
//                        Toast.makeText(splashscreen.this, "added", Toast.LENGTH_SHORT).show();
//                    }
//                });

    editor.apply();

}


            Intent i = new Intent(splashscreen.this, MainActivity.class);
            startActivity(i);
            splashscreen.this.finish();


        }

    }


}
