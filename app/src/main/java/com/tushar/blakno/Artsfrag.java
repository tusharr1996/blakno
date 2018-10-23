package com.tushar.blakno;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;




import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;
import static android.view.View.VISIBLE;
import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;
import static com.tushar.blakno.R.color.red;
import static java.lang.reflect.Array.getInt;

/**
 * Created by TushaR on 16-09-2016.
 */


public class Artsfrag extends Fragment {

    public static Artsfrag newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt("argsInstance", instance);
        Artsfrag artsfragment = new Artsfrag();
        artsfragment.setArguments(args);

        return artsfragment;
    }
    public static int tk;
    public String imvposition;
    public GridView gridView;
    public int maxart = 0;
    public String artsnumber;
    public String str;
    public Button retrybtn;
    public int check = 0;
    SharedPreferences pref;
    public Boolean my_first_time;
public int art_count = 0;
    public int[] fav_array;
    public int fav_count;
    Set<String> favlist = new HashSet<String>();

    myDbAdapter helper;




    SharedPreferences.Editor editor;
    SharedPreferences.Editor editor1;




    public static String[] imagarray;
//            "http://tusharrk.16mb.com/art%203.jpg",
//            "http://tusharrk.16mb.c
//            "http://tusharrk.16mb.com/art-7.jpg",
//            "http://s3.postimg.org/tjci8zsgz/2016_2.jpg",
//            "http://s3.postimg.org/ixsmwzm5f/2016_3.jpg",
//            "http://s3.postimg.org/8p05r5y3n/2016_4.jpg",
//            "http://s3.postimg.org/l66tebt9f/2016_5.jpg",
//            "http://s3.postimg.org/vuakd638j/2016_6.jpg",
//            "http://s3.postimg.org/7rtqiamlf/2016_7.jpg",
//            "http://s3.postimg.org/uhxgp0v6r/2016_8.jpg",
//            "http://s3.postimg.org/z53iqsijn/2016_9.jpg",
//            "http://s3.postimg.org/5ege50xk3/2016_10.jpg",
//            "http://s3.postimg.org/9ov20m2n7/2016_11.jpg",
//            "http://s3.postimg.org/lf8zhzvfn/2016_12.jpg",
//            "http://s3.postimg.org/4srf8x2hv/2016_13.jpg",
//            "http://s3.postimg.org/vf3vxw6oz/2016_14.jpg",
//            "http://s3.postimg.org/smaodv6cj/2016_15.jpg",
//            "http://s3.postimg.org/83fs8ssf7/2016_16.jpg",
//            "http://s3.postimg.org/l8vaewmar/2016_17.jpg",
//            "http://s3.postimg.org/yefdyr5k3/2016_18.jpg",
//            "http://s3.postimg.org/7540k94gz/2016_19.jpg",
//            "http://s3.postimg.org/l02b2pyw3/2016_20.jpg "
//    };





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.arts_frag,container,false);
        retrybtn = (Button)view.findViewById(R.id.retry);
        gridView = (GridView)view.findViewById(R.id.usage_example_gridview1);




        helper = new myDbAdapter(getActivity());

        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();


        if (activeNetwork == null) {
            retrybtn.setVisibility(VISIBLE);
            gridView.setVisibility(View.INVISIBLE);
            Toast.makeText(getActivity(), "Please check your internet connection.", Toast.LENGTH_SHORT).show();

        }
        pref = getActivity().getSharedPreferences("artscount", MODE_PRIVATE); // 0 - for private mode
        editor = pref.edit();

        SharedPreferences pref1 = getActivity().getSharedPreferences("favpref", Context.MODE_PRIVATE);
        editor1 = pref1.edit();









        if (pref1.getBoolean("initart",true)){

//            Toast toast = Toast.makeText(getActivity(),"ftime" ,Toast.LENGTH_SHORT);
//       toast.show();

            pref1.edit().putBoolean("initart", false).commit();
        }
        else {

//            Toast toast = Toast.makeText(getActivity(),"no ftime",Toast.LENGTH_SHORT);
//       toast.show();


        }




        int temp = pref.getInt("arts_count", 0);
//        Toast toast = Toast.makeText(getActivity(),"abc - " + temp,Toast.LENGTH_SHORT);
//        toast.show();

        if (temp == 0) {

            download_arts_count();
            art_count = maxart;
            editor.putInt("arts_count", maxart); // Storing integer
            //the app is being launched for first time, do something
//            Log.d("Comments", "First time");
//            Toast toast = Toast.makeText(getActivity(),"new start",Toast.LENGTH_SHORT);
//            toast.show();
            // first time task


//            if (activeNetwork != null) {
//                pref.edit().putBoolean("my_first_time", false).commit();
//
//                // record the fact that the app has been started at least once
//
//            }

            editor.apply();
        }




if (check == 0) {
//    Toast toast = Toast.makeText(getActivity(),"null" ,Toast.LENGTH_SHORT);
//    toast.show();
    loadarray();
}
        Integer abvc = imagarray.length;
//        Toast toast = Toast.makeText(getActivity(),"not null  " + abvc,Toast.LENGTH_SHORT);
//         toast.show();
        gridView.setAdapter(new GridViewAdapter(view.getContext(), imagarray));
        fillview();


//        final global_variable globalVariable = (global_variable) getActivity().getApplicationContext();
//        final String artsno  = globalVariable.getName();



        retrybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(Artsfrag.this).attach(Artsfrag.this).commit();

                check = 0;
                maxart = 0;
                art_count = 0;
            }
        });


        // Inflate the layout for this fragment
        return view;

    }






    public void loadarray(){
//        Toast.makeText(getActivity(), "exist", Toast.LENGTH_SHORT).show();

        File f = new File(getActivity().getApplicationInfo().dataDir + "/shared_prefs/"
                + "artscount" + ".xml");
        maxart = pref.getInt("arts_count", 0); // getting Integer

        if(f.exists())
        {
//            maxart = pref.getInt("arts_count", 0); // getting Integer
//            Toast.makeText(getActivity(), maxart, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getActivity(), "exist", Toast.LENGTH_SHORT).show();
//
//
//
//                    art_count = maxart;
//                    editor.putInt("arts_count", maxart); // Storing integer


        }
        else {
            download_arts_count();
            art_count = maxart;
            editor.putInt("arts_count", maxart); // Storing integer
//            Toast.makeText(getActivity(), " not exist", Toast.LENGTH_SHORT).show();

        }


        imagarray =   new String[maxart];

//        Toast.makeText(getActivity(), "load" + maxart, Toast.LENGTH_SHORT).show();
//        for(int number = 1;number<=maxart;number++){
//            imagarray[number-1] = "https://tusharrk.000webhostapp.com/art-" + number + ".jpg";
//        }
int loop;
        int number = 1;
        for(loop = maxart;loop>=1;loop--,number++){
            imagarray[number-1] = "https://tusharrk.000webhostapp.com/art-" + loop + ".jpg";
        }


        check = 1;

    }

    public void fillview() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                tk = position;
//                Toast.makeText(getActivity(), "image " + tk, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), Full_image.class);
                //intent.putExtra("imvposition", tk);
                startActivity(intent);
                ((Activity) getContext()).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


//                switch (position){
//                    case 0:
//                        Toast.makeText(getActivity(), "image 1", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 1:
//                        Toast.makeText(getActivity(), "image 2", Toast.LENGTH_SHORT).show();
//                        break;
//
//                }
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?>  parent, View view, int position, long id) {

                Toast.makeText(getActivity(), "Added to favourites.", Toast.LENGTH_SHORT).show();


                int pos = maxart-position;
                String name = "https://tusharrk.000webhostapp.com/art-" + pos + ".jpg";
                helper.insertData(pos,name);

               return true;
            }
        });
    }

    void download_arts_count(){


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

//                                getActivity().runOnUiThread(new Runnable() {
//                    public void run() {
//                        Toast.makeText(getActivity(), "downloaded"  + artsnumber, Toast.LENGTH_SHORT).show();
//                    }
//                });



                in.close();
            } catch (MalformedURLException e) {
            } catch (IOException e) {
            }



        }

        try {
            maxart = Integer.parseInt(artsnumber);
        } catch(NumberFormatException nfe) {

        }

    }
    void doSomething(){}



}
