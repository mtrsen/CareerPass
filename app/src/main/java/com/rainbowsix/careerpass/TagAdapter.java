package com.rainbowsix.careerpass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lihan on 10/14/2017.
 */

public class TagAdapter extends ArrayAdapter {
    List<TagSingle> list;
    HashMap<Integer,Boolean> isSelected;
    boolean add;

    public TagAdapter(Context context, List<TagSingle> objects) {
        super(context, 0, objects);
        list = new ArrayList<>();
        this.list = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.tag_layout, parent, false);
        }
        final TagSingle single = list.get(position);
        TextView tag_content = (TextView)view.findViewById(R.id.tag_content);
        CheckBox checkBox = (CheckBox)view.findViewById(R.id.checkbox);
        tag_content.setText(single.getTag() + " | " + single.getCount());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
                if (ischecked) {
                    single.setAdded(true);
                } else {
                    single.setAdded(false);
                }
            }
        });
        checkBox.setChecked(single.getAdded());
        return view;
    }
}
