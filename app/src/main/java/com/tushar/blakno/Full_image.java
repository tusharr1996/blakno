package com.tushar.blakno;


import android.Manifest;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

import android.support.v4.app.NotificationCompat.Builder;

import static android.view.View.VISIBLE;
import static com.tushar.blakno.R.id.avi;
import static com.tushar.blakno.R.id.fabaply;
import static com.tushar.blakno.R.id.fabfav;


/**
 * Created by TushaR on 25-09-2016.
 */

public class Full_image extends AppCompatActivity {

    public String filepath = Environment.getExternalStorageDirectory().getAbsolutePath();
    public String url1;
    myDbAdapter helper;

    public int dsize,tsize=0;

    private int STORAGE_PERMISSION_CODE = 23;
    public ProgressDialog progressdialog;
    public File f;
    public int height;
    public int width;
    public Bitmap tempbitMap;
    public Bitmap bitmap;
    public FloatingActionMenu fabmenu;
    public com.github.clans.fab.FloatingActionButton fabapply, fabdownload, fabshare, fabfav;
    private NotificationManager mNotifyManager;
    private Builder mBuilder;
    int id = 1;
    public String name;
    public AVLoadingIndicatorView avi1;
    public ImageView imv;

    public int  tempnameint;

    private FirebaseAnalytics mFirebaseAnalytics;


    public int tk;
    public int downloaded = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.full_image);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        helper = new myDbAdapter(Full_image.this);


        imv = (ImageView)findViewById(R.id.fullscreen_image);
        avi1 = (AVLoadingIndicatorView)findViewById(avi);
        avi1.show();

        ConnectivityManager cm = (ConnectivityManager) Full_image.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();





        if (activeNetwork == null) {

            Toast.makeText(Full_image.this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();

        }




        Intent mintent = getIntent();
        tk = Artsfrag.tk;
      // tk = mintent.getIntExtra("imvposition",0);
        url1 = Artsfrag.imagarray[tk];
        Glide.with(this).load(url1).thumbnail(0.1f).centerCrop().listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
              avi1.hide();
                return false;
            }
        }).into(imv);

//        Toast.makeText(Full_image.this, "no." + tk, Toast.LENGTH_SHORT).show();

  tempnameint = Artsfrag.imagarray.length-tk;
        name = "Art " + tempnameint + ".jpg";

//        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
//                .coordinatorLayout);
        progressdialog = new ProgressDialog(this,R.style.myAlertDialogStyle);

        if(isReadStorageAllowed()){
            //If permission is already having then showing the toast
            //Toast.makeText(this,"You already have the permission",Toast.LENGTH_LONG).show();
            //Existing the method with return

        }
        else {

            //If the app has not the permission then asking for the permission

            requestStoragePermission();
        }


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_menu);
        fabapply = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fabaply);

        fabdownload = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fabdwld);
        fabshare = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fabshr);
        fabfav = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fabfav);
        fabmenu = (FloatingActionMenu) findViewById(R.id.fab_menu);


        fabapply.setOnClickListener(onButtonClick());
        fabdownload.setOnClickListener(onButtonClick());
        fabshare.setOnClickListener(onButtonClick());
        fabfav.setOnClickListener(onButtonClick());

        fabmenu.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                if (opened) {

                } else {

                }
            }
        });


        fabmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fabmenu.isOpened()) {
                    fabmenu.close(true);
                }
            }
        });

//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                int SDK_INT = android.os.Build.VERSION.SDK_INT;
//                if (SDK_INT > 8) {
//                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
//                            .permitAll().build();
//                    StrictMode.setThreadPolicy(policy);
//                    try {
//                        URL url = new URL(url1);
//                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//                        urlConnection.setRequestMethod("GET");
//                        urlConnection.setDoOutput(true);
//                        urlConnection.connect();
//                        String rootpath = Environment.getExternalStorageDirectory().toString();
//                        new File(rootpath + "/Blakno").mkdir();
//                        //File SDCardRoot = Environment.getExternalStorageDirectory().getAbsoluteFile();
//                        String filename = "Art " + tk + ".png";
//                        Log.i("Local filename:", "" + filename);
//                        File file = new File(rootpath + "/Blakno", filename);
//                        if (file.createNewFile()) {
//                            file.createNewFile();
//                        }
//                        FileOutputStream fileOutput = new FileOutputStream(file);
//                        InputStream inputStream = urlConnection.getInputStream();
//                        int totalSize = urlConnection.getContentLength();
//                        int downloadedSize = 0;
//                        byte[] buffer = new byte[1024];
//                        int bufferLength = 0;
//                        while ((bufferLength = inputStream.read(buffer)) > 0) {
//                            fileOutput.write(buffer, 0, bufferLength);
//                            downloadedSize += bufferLength;
//                            Log.i("Progress:", "downloadedSize:" + downloadedSize + "totalSize:" + totalSize);
//                            dsize = downloadedSize;
//                            tsize = totalSize;
//                        }
//                        fileOutput.close();
//                        if (downloadedSize == totalSize) filepath = file.getPath();
//                    } catch (MalformedURLException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        filepath = null;
//                        e.printStackTrace();
//                    }
//                    Log.i("filepath:", " " + filepath);
//
//
//                }
//
//                File f = new File(filepath);


//                DisplayMetrics metrics = new DisplayMetrics();
//                getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
//
//
//                int height = metrics.heightPixels;
//                int width = metrics.widthPixels;
//                Bitmap tempbitMap = BitmapFactory.decodeFile(f.getAbsolutePath());
//                Bitmap bitmap = Bitmap.createScaledBitmap(tempbitMap,width,height, true);
//                try {
//                    String path = Environment.getExternalStorageDirectory().toString();
//                    File Ftmp = new File(path + "/Blakno", "cropped.png");
//                    if (Ftmp.createNewFile()) {
//                        Ftmp.createNewFile();
//                    }
//                    FileOutputStream fileOutput1 = new FileOutputStream(Ftmp);
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutput1);
//                    fileOutput1.flush();
//                    fileOutput1.close();
//                    String url = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap,
//                            "Wallpaper.jpg", null);
//                }
//                catch (IOException e) {
//
//                    e.printStackTrace();
//                }
//                WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
//                wallpaperManager.setWallpaperOffsetSteps(1, 1);
//                wallpaperManager.suggestDesiredDimensions(width, height);
//                try {
//                    wallpaperManager.setBitmap(bitmap);
//
//                    Snackbar snackbar = Snackbar.make(coordinatorLayout, "Wallpaper applied", Snackbar.LENGTH_LONG);
//                    if (dsize == tsize) {
//                        progressdialog.dismiss();
//                    }
//                    snackbar.show();
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }




//                Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
//
//                WallpaperManager mywallpapermanager = WallpaperManager.getInstance(getApplicationContext());
//                try
//                {
//                    mywallpapermanager.setBitmap(bmp);
//
//                    Snackbar snackbar = Snackbar
//                            .make(coordinatorLayout, "Wallpaper applied", Snackbar.LENGTH_LONG);
//                    progressdialog.dismiss();
//                    snackbar.show();
//                } catch (IOException e){
//                    e.printStackTrace();
//                }
//
//            }
//        });










//        Toast.makeText(this, "fullscreen activity", Toast.LENGTH_SHORT).show();

    }

    private boolean isReadStorageAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }

    private void requestStoragePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){
            Toast.makeText(this, "To download and set wallpaper we need Storage permission", Toast.LENGTH_LONG).show();
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if(requestCode == STORAGE_PERMISSION_CODE){

            //If permission is granted
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                //Displaying a toast
                //Toast.makeText(this,"Permission granted now you can read the storage",Toast.LENGTH_LONG).show();
            }else{
                //Displaying another toast if permission is not granted
                Toast.makeText(this,"Oops you just denied the permission",Toast.LENGTH_LONG).show();
            }
        }
    }

    private View.OnClickListener onButtonClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == fabapply) {

                    apply();





                } else if (view == fabdownload) {

                    download();



            } else if (view == fabfav) {

                favourite();


            }

                else {
                    share();
                }
                fabmenu.close(true);
            }
        };
    }


    public  void apply(){

        ConnectivityManager cm = (ConnectivityManager) Full_image.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();





        if (activeNetwork != null) {






      final   NotificationCompat.Builder b = new NotificationCompat.Builder(Full_image.this);
        b.setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_file_download_white_24dp)
                .setContentTitle("Applying...")
                .setOngoing(true)
                .setProgress(100,0,true)
                .setContentText(name)
        .setVibrate(new long[]{0l});
        final NotificationManager nm = (NotificationManager) Full_image.this.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(1, b.build());


        Full_image.this.runOnUiThread(new Runnable() {
            public void run() {

                Toast toast = Toast.makeText(getApplicationContext(),"Applying...",Toast.LENGTH_SHORT);
                toast.show();

            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                if (SDK_INT > 8) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    try {
                        URL url = new URL(url1);
                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                        urlConnection.setRequestMethod("GET");
                        urlConnection.setDoOutput(true);
                        urlConnection.connect();
                        String rootpath = Environment.getExternalStorageDirectory().toString();
                        new File(rootpath + "/Blakno").mkdir();
                        //File SDCardRoot = Environment.getExternalStorageDirectory().getAbsoluteFile();
                        String filename = "Art " + tempnameint + ".jpg";
                        Log.i("Local filename:", "" + filename);
                        File file = new File(rootpath + "/Blakno", filename);
                        if (file.createNewFile()) {
                            file.createNewFile();
                        }
                        FileOutputStream fileOutput = new FileOutputStream(file);
                        InputStream inputStream = urlConnection.getInputStream();
                        int totalSize = urlConnection.getContentLength();
                        int downloadedSize = 0;
                        byte[] buffer = new byte[1024];
                        int bufferLength = 0;
                        while ((bufferLength = inputStream.read(buffer)) > 0) {
                            fileOutput.write(buffer, 0, bufferLength);
                            downloadedSize += bufferLength;
                            Log.i("Progress:", "downloadedSize:" + downloadedSize + "totalSize:" + totalSize);
                            dsize = downloadedSize;
                            tsize = totalSize;
                            Log.i("noti", "per:-" + dsize * 100 / tsize);

                        }
                        fileOutput.close();
                        if (downloadedSize == totalSize) filepath = file.getPath();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {

                        ConnectivityManager cm = (ConnectivityManager) Full_image.this.getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

                        if (activeNetwork == null) {
                            nm.cancelAll();
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);

                        }
                        filepath = null;
                        e.printStackTrace();
                    }
                    Log.i("filepath:", " " + filepath);



                    if (filepath != null) {
                        try {
                        MediaScannerConnection.scanFile(Full_image.this, new String[]{filepath}, new String[]{"image/jpeg"}, null);




                    f = new File(filepath);

                    DisplayMetrics metrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getRealMetrics(metrics);


                    height = metrics.heightPixels;
                    width = metrics.widthPixels;
                    tempbitMap = BitmapFactory.decodeFile(f.getAbsolutePath());
                    bitmap = Bitmap.createScaledBitmap(tempbitMap, width, height, true);

                    WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
                    wallpaperManager.setWallpaperOffsetSteps(1, 1);
                    wallpaperManager.suggestDesiredDimensions(width, height);

                        wallpaperManager.setBitmap(bitmap);

                    } catch (IOException e) {
                            nm.cancelAll();
                            apply();
                        e.printStackTrace();

                    }
                    }
                }

                nm.cancelAll();
                final   NotificationCompat.Builder c = new NotificationCompat.Builder(Full_image.this);

                Intent intent = new Intent();
                intent.setAction(android.content.Intent.ACTION_VIEW);
//                File file = new File(filepath);
//                intent.setDataAndType(Uri.fromFile(file), "image/jpeg");

if(filepath!=null) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Uri photoURI = FileProvider.getUriForFile(Full_image.this, getPackageName() + ".provider", new File(filepath));
        intent.setDataAndType(photoURI, "image/jpeg");
    } else {
        File file1 = new File(filepath);
        intent.setDataAndType(Uri.fromFile(file1), "image/jpeg");
    }
}






                PendingIntent pIntent = PendingIntent.getActivity(Full_image.this, 0, intent, 0);

                c.setAutoCancel(true)
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.ic_done_white_24dp)
                        .setContentTitle("Wallpaper Applied")
                        .setContentText(name)
                        .setOngoing(false)
                        .setContentIntent(pIntent)
                        .setVibrate(new long[]{0l});
                nm.notify(1, c.build());


                Bundle params = new Bundle();
                params.putString("apply_btn", "apply clicked");

                mFirebaseAnalytics.logEvent("applybtn", params);
downloaded = 1;
            }
        }).start();

        }

        else {
            Toast toast = Toast.makeText(getApplicationContext(), "Please check your internet connection.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }




    public void download(){

        ConnectivityManager cm = (ConnectivityManager) Full_image.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();





        if (activeNetwork != null) {

            final NotificationCompat.Builder b = new NotificationCompat.Builder(Full_image.this);


            b.setAutoCancel(true)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.ic_file_download_white_24dp)
                    .setContentTitle("Downloading...")
                    .setProgress(100, 0, true)
                    .setOngoing(true)
                    .setContentText(name)
                    .setVibrate(new long[]{0l});
            final NotificationManager nm = (NotificationManager) Full_image.this.getSystemService(Context.NOTIFICATION_SERVICE);
            nm.notify(1, b.build());

            Full_image.this.runOnUiThread(new Runnable() {
                public void run() {

                    Toast toast = Toast.makeText(getApplicationContext(), "Downloading...", Toast.LENGTH_SHORT);
                    toast.show();


                }
            });

            new Thread(new Runnable() {
                @Override
                public void run() {
                    int SDK_INT = android.os.Build.VERSION.SDK_INT;
                    if (SDK_INT > 8) {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                .permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        try {
                            URL url = new URL(url1);
                            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                            urlConnection.setRequestMethod("GET");
                            urlConnection.setDoOutput(true);
                            urlConnection.connect();
                            String rootpath = Environment.getExternalStorageDirectory().toString();
                            new File(rootpath + "/Blakno").mkdir();
                            //File SDCardRoot = Environment.getExternalStorageDirectory().getAbsoluteFile();
                            String filename = "Art " + tempnameint + ".jpg";
                            Log.i("Local filename:", "" + filename);
                            File file = new File(rootpath + "/Blakno", filename);
                            if (file.createNewFile()) {
                                file.createNewFile();
                            }
                            FileOutputStream fileOutput = new FileOutputStream(file);
                            InputStream inputStream = urlConnection.getInputStream();
                            int totalSize = urlConnection.getContentLength();
                            int downloadedSize = 0;
                            byte[] buffer = new byte[1024];
                            int bufferLength = 0;
                            while ((bufferLength = inputStream.read(buffer)) > 0) {
                                fileOutput.write(buffer, 0, bufferLength);
                                downloadedSize += bufferLength;
                                Log.i("Progress:", "downloadedSize:" + downloadedSize + "totalSize:" + totalSize);
                                dsize = downloadedSize;
                                tsize = totalSize;
                            }
                            fileOutput.close();
                            if (downloadedSize == totalSize) filepath = file.getPath();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {

                            ConnectivityManager cm = (ConnectivityManager) Full_image.this.getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

                            if (activeNetwork == null) {
                                nm.cancelAll();
                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);

                            }

                            filepath = null;
                            e.printStackTrace();
                        }
                        Log.i("filepath:", " " + filepath);
                        if(filepath!= null){
                            MediaScannerConnection.scanFile(Full_image.this, new String[]{filepath.toString()}, new String[]{"image/jpeg"}, null);
                        }




                    }

                    if (dsize == tsize) {

                        nm.cancelAll();
                        final NotificationCompat.Builder c = new NotificationCompat.Builder(Full_image.this);

                        Intent intent = new Intent();
                        intent.setAction(android.content.Intent.ACTION_VIEW);



                    if(filepath!=null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            Uri photoURI = FileProvider.getUriForFile(Full_image.this, getPackageName() + ".provider", new File(filepath));
                            intent.setDataAndType(photoURI, "image/jpeg");
                        } else {
                            File file1 = new File(filepath);
                            intent.setDataAndType(Uri.fromFile(file1), "image/jpeg");
                        }
                    }

                        PendingIntent pIntent = PendingIntent.getActivity(Full_image.this, 0, intent, 0);

                        c.setAutoCancel(true).setAutoCancel(true)
                                .setWhen(System.currentTimeMillis())
                                .setSmallIcon(R.drawable.ic_done_white_24dp)
                                .setContentTitle("Wallpaper Downloaded")
                                .setContentText(name)
                                .setOngoing(false)
                                .setContentIntent(pIntent)
                                .setVibrate(new long[]{0l});
                        nm.notify(1, c.build());


                    }

                    downloaded = 1;

                }
            }).start();

        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(), "Please check your internet connection.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

//    public SimpleTarget target = new SimpleTarget<Bitmap>() {
//        @Override
//        public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
//            // do something with the bitmap
//            // for demonstration purposes, let's just set it to an ImageView
//            avi1.hide();
//            imv.setImageBitmap( bitmap );
//
//
//        }
//    };


    public void share() {

        ConnectivityManager cm = (ConnectivityManager) Full_image.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();





        if (activeNetwork != null) {


if(downloaded == 0) {
    Full_image.this.runOnUiThread(new Runnable() {
        public void run() {

            Toast toast = Toast.makeText(getApplicationContext(), "Downloading wallpaper please wait.", Toast.LENGTH_SHORT);
            toast.show();


        }
    });
}
            new Thread(new Runnable() {
                @Override
                public void run() {

                    if(downloaded == 0) {
                        int SDK_INT = android.os.Build.VERSION.SDK_INT;
                        if (SDK_INT > 8) {
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                    .permitAll().build();
                            StrictMode.setThreadPolicy(policy);
                            try {
                                URL url = new URL(url1);
                                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                                urlConnection.setRequestMethod("GET");
                                urlConnection.setDoOutput(true);
                                urlConnection.connect();
                                String rootpath = Environment.getExternalStorageDirectory().toString();
                                new File(rootpath + "/Blakno").mkdir();
                                //File SDCardRoot = Environment.getExternalStorageDirectory().getAbsoluteFile();
                                String filename = "Art " + tempnameint + ".jpg";
                                Log.i("Local filename:", "" + filename);
                                File file = new File(rootpath + "/Blakno", filename);
                                if (file.createNewFile()) {
                                    file.createNewFile();
                                }
                                FileOutputStream fileOutput = new FileOutputStream(file);
                                InputStream inputStream = urlConnection.getInputStream();
                                int totalSize = urlConnection.getContentLength();
                                int downloadedSize = 0;
                                byte[] buffer = new byte[1024];
                                int bufferLength = 0;
                                while ((bufferLength = inputStream.read(buffer)) > 0) {
                                    fileOutput.write(buffer, 0, bufferLength);
                                    downloadedSize += bufferLength;
                                    Log.i("Progress:", "downloadedSize:" + downloadedSize + "totalSize:" + totalSize);
                                    dsize = downloadedSize;
                                    tsize = totalSize;
                                }
                                fileOutput.close();
                                if (downloadedSize == totalSize) filepath = file.getPath();
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (IOException e) {

                                ConnectivityManager cm = (ConnectivityManager) Full_image.this.getSystemService(Context.CONNECTIVITY_SERVICE);
                                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

                                if (activeNetwork == null) {

                                    Intent intent = getIntent();
                                    finish();
                                    startActivity(intent);

                                }

                                filepath = null;
                                e.printStackTrace();
                            }
                            Log.i("filepath:", " " + filepath);
                            if(filepath!=null) {

                                MediaScannerConnection.scanFile(Full_image.this, new String[]{filepath.toString()}, new String[]{"image/jpeg"}, null);

                            }downloaded = 1;


                        }
                    }




//                        Uri fileUri = FileProvider.getUriForFile(Full_image.this, getPackageName() + ".provider",  new File(filepath));
//
////                        Log.w("Attempting to attach file " + fileUri.getEncodedPath() + " with mime type: " + URLConnection.guessContentTypeFromName(fileUri.toString()));
//
//                        Intent viewFile = new Intent(Intent.ACTION_SEND);
//
//                        viewFile.setData(fileUri);
//                        viewFile.setType("image/jpg");
//                        viewFile.putExtra(Intent.EXTRA_STREAM, fileUri);
//                        viewFile.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        viewFile.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                        startActivity(Intent.createChooser(viewFile, "Share image using"));




if(filepath!=null) {
    String sAux = "Download more from Blakno Wallpaper app\n";
    sAux = sAux + " http://play.google.com/store/apps/details?id=" + Full_image.this.getPackageName();

    File f = new File(filepath.toString());
    Uri uri = Uri.parse("file://" + f.getAbsolutePath());
    Intent share = new Intent(Intent.ACTION_SEND);
    share.putExtra(Intent.EXTRA_TEXT, sAux);
    share.putExtra(Intent.EXTRA_STREAM, uri);
    share.setType("image/jpeg");
    share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
    startActivity(Intent.createChooser(share, "Share with"));

}


                }
            }).start();

        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(), "Please check your internet connection.", Toast.LENGTH_SHORT);
            toast.show();
        }




    }
    public void favourite(){
        Toast.makeText(Full_image.this, "Added to favourite.", Toast.LENGTH_SHORT).show();

        tempnameint = Artsfrag.imagarray.length-tk;
        String name = "https://tusharrk.000webhostapp.com/art-" + tempnameint + ".jpg";
        helper.insertData(tempnameint,name);

    }


}
