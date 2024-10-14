package com.example.dialclassic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class DialListAdapter extends ArrayAdapter<Dial> {
    ArrayList<Dial> dialList;
    Activity context;
    int IDLayout;

    public DialListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Dial> objects) {
        super(context, resource, objects);
        this.context = (Activity) context;
        this.IDLayout = resource;
        this.dialList = objects;
    }

    public View getView(int position, @NonNull View convertView, @NonNull android.view.ViewGroup parent){
        LayoutInflater layoutInflater =context.getLayoutInflater();
        convertView =layoutInflater.inflate(IDLayout,null);
        convertView.setLayoutParams(new android.widget.AbsListView.LayoutParams(android.widget.AbsListView.LayoutParams.MATCH_PARENT, 100));

        Dial dial = dialList.get(position);
        ImageView imageView = convertView.findViewById(R.id.dial_pic);
        imageView.setImageResource(dial.getImage1());
        ImageView imageView2 = convertView.findViewById(R.id.dial_menu);
        imageView2.setImageResource(dial.getImage2());
        TextView textView = convertView.findViewById(R.id.dial_text);
        textView.setText(dial.getInfo());
        return convertView;
    }

}
