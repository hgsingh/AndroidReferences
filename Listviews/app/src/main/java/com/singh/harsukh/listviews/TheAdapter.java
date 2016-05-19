package com.singh.harsukh.listviews;

import android.content.Context;
import android.content.res.Resources;
import android.test.SingleLaunchActivityTestCase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by harsukh on 1/21/16.
 */
public class TheAdapter  extends BaseAdapter{
    ArrayList<SingleRow> list;
    Context mContext;
    int[] imgs;
    String[] titleArray;
    String[] descArray;

    public TheAdapter(Context c, String[] arr, int[] arr2, String[] arr3)
    {
        this.mContext = c;
        list = new ArrayList<SingleRow>();
        this.imgs = arr2;
        this.titleArray = arr;
        this.descArray = arr3;
        for (int i=0; i<titleArray.length; i++){
            list.add(new SingleRow(titleArray[i], descArray[i], imgs[i]));
        }
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.single_row, parent, false);

        ImageView myImage = (ImageView) row.findViewById(R.id.imageView);
        TextView myTitle = (TextView) row.findViewById(R.id.textView);
        TextView myDesc = (TextView) row.findViewById(R.id.textView2);

        SingleRow temp = list.get(position);
        myImage.setImageResource(temp.image);
        myTitle.setText(temp.title);
        myDesc.setText(temp.description);

        return row;
    }
}
