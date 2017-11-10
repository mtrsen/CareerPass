package com.rainbowsix.careerpass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

/**
 * Created by lihan on 10/21/2017.
 */

public class SearchAdapter extends ArrayAdapter {
    List<ListSingle> list;

    private DatabaseReference mDatabase;

    public SearchAdapter(Context context, List<ListSingle> objects) {
        super(context, 0, objects);
        //list = new ArrayList<>();
        this.list = objects;
        //this.mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.search_list, parent, false);
        }
        final ListSingle single = list.get(position);

        TextView tag = (TextView)view.findViewById(R.id.tag);
        TextView time = (TextView)view.findViewById(R.id.time);
        tag.setText(single.getTag());
        time.setText("Created: " + single.getTime() + " | " + single.getCat());
        return view;
    }

}
