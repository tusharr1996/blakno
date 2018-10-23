package com.tushar.blakno;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static android.R.attr.name;
import static android.content.Context.MODE_PRIVATE;
import static com.tushar.blakno.Artsfrag.imagarray;

/**
 * Created by Tushar on 04-04-2017.
 */

public class Favouritefrag extends Fragment {


    GridView    gridView;
    public int tk;
    SharedPreferences pref;
    public static String[] favarrayuri;
    SharedPreferences pref1;
    SharedPreferences.Editor editor1;
    SharedPreferences.Editor editor;
    public static Integer[] favarrayname= {
            1,2,3
    };
   // Button btn;
    Set<String> someStringSet;

    myDbAdapter helper;
    TextView error_empty;



    public static Favouritefrag newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt("argsInstance", instance);
        Favouritefrag favfrag = new Favouritefrag();
        favfrag.setArguments(args);
        return favfrag;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.favourite_frag,container,false);

        gridView = (GridView)view.findViewById(R.id.fav_gridview);
        pref = getActivity().getSharedPreferences("artscount", MODE_PRIVATE); // 0 - for private mode
        editor = pref.edit();
error_empty = (TextView)view.findViewById(R.id.errormsg);

//
//        btn = (Button)view.findViewById(R.id.clearall);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                helper.delete();
//            }
//        });

        helper = new myDbAdapter(getActivity());
        boolean checkcount = helper.check();
        if (checkcount == true){


            ArrayList<String> fav_address = helper.getData();
            Collections.reverse(fav_address);


            favarrayuri = fav_address.toArray(new String[fav_address.size()] );




        gridView.setAdapter(new fav_grid(view.getContext(), favarrayuri));

        }
        else {
            gridView.setVisibility(View.INVISIBLE);
            error_empty.setVisibility(View.VISIBLE);
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String temp = favarrayuri[position];
                String[] parts = temp.split("-");
                String temp1 = parts[1];
                String[] parts1 = temp1.split("\\.");
                String temp2 = parts1[0];
                int temppos = Integer.parseInt(temp2.toString());
                int temppos1 = pref.getInt("arts_count", 0);
                tk = temppos1-temppos;
            //    Toast.makeText(getActivity(), " " + tk, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(),Full_image.class);
                intent.putExtra("imvposition", tk);
                startActivity(intent);
                ((Activity) getContext()).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?>  parent, View view, int position, long id) {

                Toast.makeText(getActivity(), "Removed from favourites.", Toast.LENGTH_SHORT).show();
                String temp = favarrayuri[position];
                String[] parts = temp.split("-");
                String temp1 = parts[1];
                String[] parts1 = temp1.split("\\.");
                String temp2 = parts1[0];
                int temppos = Integer.parseInt(temp2.toString());

                helper.deletedata(temppos);

                //to recreate whole fragment without transaction

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(Favouritefrag.this).attach(Favouritefrag.this).commit();
                return true;
            }
        });






        return view;

    }


}
