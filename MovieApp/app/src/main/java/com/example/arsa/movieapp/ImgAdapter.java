package com.example.arsa.movieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Arsa on 07.02.2016.
 */
public class ImgAdapter extends BaseAdapter {
    private final String LOG_TAG = ImgAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<String> pics;
    private String[] pic_Array;
    private LayoutInflater mInflater;


    public ImgAdapter(Context context, ArrayList<String> pics){
        mContext=context;
        this.pics=pics;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        pic_Array = new String[pics.size()];

        for(int i = 0;i<pics.size();i++){
            pic_Array[i] = pics.get(i);
        }

    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup){

        View cView = view;
        ImageView imgView;


        if(cView == null){
            cView = mInflater.inflate(R.layout.movie_poster_item,null);
            imgView= (ImageView) cView.findViewById(R.id.poster);
            imgView.setLayoutParams(new LinearLayout
                    .LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            cView.setTag(imgView);
        } else {
            imgView = (ImageView) cView.getTag();
        }

        ImageView imageView = imgView;

        if(pic_Array[i] != null){
            Picasso.with(mContext).load(pic_Array[i]).into(imageView);
        } else {
            imageView.setImageResource(R.drawable.sample_0);
        }

        return cView;



        /*Log.v(LOG_TAG, "count1: " + pic_Array.length + " : i = " + i);
        ImageView imgView;
        if(view == null) {
            imgView= new ImageView(mContext);
        } else {
            imgView = (ImageView) view;
        }
        Log.v(LOG_TAG, "count2: " + pic_Array.length);

        Picasso.with(mContext).load(pic_Array[i]).into(imgView);
        Log.v(LOG_TAG, "link: " +i +" : "+ pic_Array[i]);
        return imgView;*/


        /*
            if  (view == null){
            imgView = new ImageView(context);
        } else {
            imgView = (ImageView) view;
        }

        Picasso.with(context)
                .load(pic_Array[i])
                .into(imgView);

        return imgView;*/
    }

    @Override
    public int getCount(){
        return pics.size();
    }

    @Override
    public Object getItem(int i){
        return pics.get(i);
    }

    @Override
    public long getItemId(int i){
        return i;
    }

}
