package com.tushar.blakno;

/**
 * Created by Tushar on 04-04-2017.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class fav_grid extends ArrayAdapter {
    private Context context;
    private LayoutInflater inflater;

    private String[] imageUrls;

    public class RecordHolder {

        TextView text;

        ImageView image;
    }


    public fav_grid(Context context, String[] imageUrls) {
        super(context, R.layout.image_grid_layout, imageUrls);
        this.context = context;
        this.imageUrls = imageUrls;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RecordHolder holder = null;

        if (null == convertView) {
//                imageView = (ImageView) inflater.inflate(R.layout.image_grid_layout, parent, false);



            convertView = inflater.inflate(R.layout.image_grid_layout, parent, false);

            holder = new RecordHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        }
        else {
            holder = (RecordHolder) convertView.getTag();

        }


        Glide
                .with(context)
                .load(imageUrls[position])
                .override(500,500)
                .centerCrop()
                .into(holder.image);

//   holder.image.setOnClickListener(new View.OnClickListener() {
//       @Override
//       public void onClick(View v) {
//           Toast.makeText(context, "qwerty", Toast.LENGTH_SHORT).show();
//       }
//   });

        return convertView;
    }






}