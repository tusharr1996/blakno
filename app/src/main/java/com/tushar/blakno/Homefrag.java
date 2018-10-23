package com.tushar.blakno;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * Created by TushaR on 16-09-2016.
 */


public class Homefrag extends Fragment {

    public static Homefrag newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt("argsInstance", instance);
        Homefrag homefragment = new Homefrag();
        homefragment.setArguments(args);
        return homefragment;
    }

    public Button contributebtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_frag,container,false);

//        Toast.makeText(getActivity(), "home", Toast.LENGTH_SHORT).show();
        // Inflate the layout for this fragment

        contributebtn = (Button) view.findViewById(R.id.contri);
        contributebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ad_activity.class));
            }
        });




        return view;

    }

}
