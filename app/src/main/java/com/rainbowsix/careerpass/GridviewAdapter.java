package com.rainbowsix.careerpass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.R.id.text1;

/**
 * Created by lihan on 11/13/2017.
 */

public class GridviewAdapter extends ArrayAdapter {
    ArrayList<String> list;

    public GridviewAdapter(Context context, ArrayList<String> objects) {
        super(context, 0, objects);
        list = new ArrayList<>();
        this.list = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.gridviewadapter, parent, false);
        }
        TextView name = (TextView)view.findViewById(R.id.name);
        name.setText(list.get(position));
        return view;
    }


}
