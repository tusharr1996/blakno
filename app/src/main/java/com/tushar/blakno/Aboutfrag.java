package com.tushar.blakno;

import android.app.AlertDialog;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by TushaR on 16-09-2016.
 */


public class Aboutfrag extends Fragment {

    public static Aboutfrag newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt("argsInstance", instance);
        Aboutfrag aboutfragment = new Aboutfrag();
        aboutfragment.setArguments(args);
        return aboutfragment;
    }
    public Button sharelink;
    public Button rateapp;
    public Button devbtn;
    public Button librarybtn;
    public Button privacypolicy;
    public Button contact;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.about_frag,container,false);

        sharelink = (Button)view.findViewById(R.id.sharelink);

        sharelink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                { Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "Blakno");
                    String sAux = "Blakno\n";
                    sAux = sAux + " http://play.google.com/store/apps/details?id=" + getActivity().getPackageName();
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(i, "Share with"));
                }
                catch(Exception e)
                { //e.toString();
                }
            }
        });

        rateapp = (Button)view.findViewById(R.id.rateapp);
        rateapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getActivity().getPackageName())));
                }
            }
        });

        devbtn = (Button)view.findViewById(R.id.devbtn);
        devbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),developerInfo.class);

                startActivity(intent);
            }
        });

        librarybtn = (Button)view.findViewById(R.id.library_button);
        librarybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),library_implements.class);

                startActivity(intent);
            }
        });

        contact = (Button)view.findViewById(R.id.contactus);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_SENDTO);
                i.setType("message/rfc822");
                i.setData(Uri.parse("mailto:"));
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"blakno.tusharrk@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Blakno - Contact");
                i.putExtra(Intent.EXTRA_TEXT   , "Your message here...");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        privacypolicy = (Button)view.findViewById(R.id.privacypolicy);
        privacypolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
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
        });



//        Toast.makeText(getActivity(), "about", Toast.LENGTH_SHORT).show();
        // Inflate the layout for this fragment
        return view;
    }

}
