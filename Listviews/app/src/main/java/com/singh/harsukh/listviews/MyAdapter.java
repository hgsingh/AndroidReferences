package com.singh.harsukh.listviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by harsukh on 1/21/16.
 */
public class MyAdapter extends ArrayAdapter<String> {

    Context context;
    int[] imgs;
    String[] titleArray;
    String[] descArray;

    public MyAdapter(Context c, String[] arr, int[] arr2, String[] arr3)
    {
        super(c, R.layout.single_row, R.id.textView, arr); //needs to call the inherited class
        this.context = c;
        this.imgs = arr2;
        this.titleArray = arr;
        this.descArray = arr3;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //over ride this function to properly display
        //inflate the layout by using the context class (inflate single row)
         //use convertView to recycle the view every time getView is called
        //get the inflater service from the context
        //the argument to inflate are the view to inflate, the parent that will take the view (contains the view)
        //and whether the
        View row = convertView;
        MyViewHolder holder = null;
        if(row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row  = inflater.inflate(R.layout.single_row, parent, false);
            holder = new MyViewHolder(row);
            row.setTag(holder);
        }
        else{
            holder = (MyViewHolder) row.getTag();
        }
        holder.myImage.setImageResource(imgs[position]);
        holder.myTitle.setText(titleArray[position]);
        holder.myDesc.setText(descArray[position]);
        return row;
    }
    //holds the views to reduce the function calls
    class MyViewHolder {
        ImageView myImage;
        TextView myTitle;
        TextView myDesc;

        MyViewHolder(View v){
            myImage = (ImageView) v.findViewById(R.id.imageView);
            myTitle = (TextView) v.findViewById(R.id.textView);
            myDesc = (TextView) v.findViewById(R.id.textView2);
        }
    }
}
