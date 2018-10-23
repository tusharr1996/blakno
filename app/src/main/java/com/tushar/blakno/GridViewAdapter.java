package com.tushar.blakno;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;


import static android.view.View.VISIBLE;
import static com.bumptech.glide.Glide.with;

/**
 * Created by TushaR on 16-09-2016.
 */
public class GridViewAdapter extends ArrayAdapter {

        private Context context;
        private LayoutInflater inflater;

        private String[] imageUrls;





    public class RecordHolder {

        TextView text;



        ImageView image;
    }


        public GridViewAdapter(Context context, String[] imageUrls) {
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
