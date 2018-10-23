package com.tushar.blakno;

import android.app.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.IdRes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView;


import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ncapdevi.fragnav.FragNavController;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FragNavController fragNavController;
    //indices to fragments
    private final int TAB_FIRST = FragNavController.TAB1;
    private final int TAB_SECOND = FragNavController.TAB2;
    private final int TAB_THIRD = FragNavController.TAB3;
    private final int TAB_FOURTH = FragNavController.TAB4;

    public String str;
    public String artsnumber;
    public int artsno= 0;
    private FirebaseAnalytics mFirebaseAnalytics;


    SharedPreferences settings;
//    SharedPreferences.Editor editor;
    boolean sub;
    boolean isChecked;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        settings = getSharedPreferences("settings", 0);

        if (settings.getBoolean("init",true)){
            FirebaseMessaging.getInstance().subscribeToTopic("notification");
//            Toast.makeText(this, "sub1", Toast.LENGTH_SHORT).show();
            settings.edit().putBoolean("init", false).commit();
            sub = true;
            settings.edit().putBoolean("checkbox", sub);

            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            alertDialog.setTitle("Privacy Policy");
            alertDialog.setMessage(R.string.privacypolicy);
            alertDialog.setCancelable(false);

            alertDialog.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {


                    dialog.dismiss();
                }
            });

            alertDialog.show();
        }

        isChecked = settings.getBoolean("checkbox", true);












//        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//        if (activeNetwork == null) {
//            Toast.makeText(this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
//        }


//        if (android.os.Build.VERSION.SDK_INT > 9) {
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//
//            try {
//                // Create a URL for the desired page
//                URL url = new URL("http://tusharrk.16mb.com/no%20of%20arts.txt");
//
//                // Read all the text returned by the server
//                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
//                artsnumber = "";
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
//        }





//
//        final global_variable globalVariable = (global_variable) getApplicationContext();
//        globalVariable.setName(artsnumber);




        //FragNav
        //list of fragments
        List<Fragment> fragments = new ArrayList<>(4);

        //add fragments to list

        fragments.add(Homefrag.newInstance(0));
        fragments.add(Artsfrag.newInstance(0));
        fragments.add(Favouritefrag.newInstance(0));
        fragments.add(Aboutfrag.newInstance(0));


        //link fragments to container
        fragNavController = new FragNavController(getSupportFragmentManager(),R.id.container,fragments);
        //End of FragNav

        BottomBar bottomBar = (BottomBar)findViewById(R.id.bottomBar);

        bottomBar.setDefaultTabPosition(TAB_SECOND);


        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_home) {

                    fragNavController.switchTab(TAB_FIRST);

                }
                if(tabId == R.id.tab_arts){
                    fragNavController.switchTab(TAB_SECOND);

                }

                if (tabId == R.id.tab_fav){
                    fragNavController.switchTab(TAB_THIRD);

                }
                if (tabId == R.id.tab_about){
                    fragNavController.switchTab(TAB_FOURTH);

                }


            }
        });

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_arts){
                    fragNavController.clearStack();

                }

            }
        });






    }

    private int backButtonCount = 0;
    @Override
    public void onBackPressed()
    {
        if(backButtonCount >= 1)
        {
            MainActivity.this.finish();
//            Intent intent = new Intent(Intent.ACTION_MAIN);
//            intent.addCategory(Intent.CATEGORY_HOME);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Press Back again to Exit.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);






        MenuItem item = menu.findItem(R.id.push_noti);
        item.setChecked(isChecked);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
deleteCache(this);

            Toast.makeText(this, "Cache Cleared", Toast.LENGTH_SHORT).show();

            return true;
        }

        if (id == R.id.push_noti){
            item.setChecked(!item.isChecked());
//            SharedPreferences settings = getSharedPreferences("settings", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("checkbox", item.isChecked());
            editor.commit();
            if (item.isChecked() == true){
                FirebaseMessaging.getInstance().subscribeToTopic("notification");
//                Toast.makeText(this, "sub", Toast.LENGTH_SHORT).show();
                sub = true;
            }
            else{
                FirebaseMessaging.getInstance().unsubscribeFromTopic("notification");
            sub = false;
            }


        }

        return super.onOptionsItemSelected(item);
    }


    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }




}
