package com.tushar.blakno;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.VideoOptions;

/**
 * Created by Tushar on 07-03-2017.
 */

public class ad_activity extends AppCompatActivity {

    AdView mAdView;
    Button btnFullscreenAd;
    Button btnvideo;
    InterstitialAd mInterstitialAd;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ad_activity);

            btnFullscreenAd = (Button)findViewById(R.id.btn_fullscreen_ad);
        btnvideo = (Button)findViewById(R.id.btn_video);

        NativeExpressAdView nativ1 = (NativeExpressAdView)findViewById(R.id.nativ1);

        AdRequest request1 = new AdRequest.Builder()
                .addTestDevice("4CD25325DED63B2ACD9C44A85F2BAA2C")
                .build();
        nativ1.loadAd(request1);

        NativeExpressAdView nativ2 = (NativeExpressAdView)findViewById(R.id.nativ1);

        AdRequest request2 = new AdRequest.Builder()
                .addTestDevice("4CD25325DED63B2ACD9C44A85F2BAA2C")
                .build();
        nativ2.loadAd(request2);

//        mAdView = (AdView) findViewById(R.id.adView1);
//        AdRequest adRequest = new AdRequest.Builder()
//                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                .addTestDevice("4CD25325DED63B2ACD9C44A85F2BAA2C")
//                .build();
//        mAdView.loadAd(adRequest);




        btnvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mInterstitialAd = new InterstitialAd(ad_activity.this);

                // set the ad unit ID
                mInterstitialAd.setAdUnitId(getString(R.string.video_1));

                AdRequest adRequest = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        .addTestDevice("4CD25325DED63B2ACD9C44A85F2BAA2C")
                        .build();

                // Load ads into Interstitial Ads
                mInterstitialAd.loadAd(adRequest);

                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }
                });

            }
        });

        btnFullscreenAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mInterstitialAd = new InterstitialAd(ad_activity.this);

                // set the ad unit ID
                mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));

                AdRequest adRequest = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        .addTestDevice("4CD25325DED63B2ACD9C44A85F2BAA2C")
                        .build();

                // Load ads into Interstitial Ads
                mInterstitialAd.loadAd(adRequest);

                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }
                });

            }
        });

    }

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

}
