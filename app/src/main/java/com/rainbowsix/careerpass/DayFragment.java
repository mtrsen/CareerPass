package com.rainbowsix.careerpass;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lihan on 10/13/2017.
 */

public class DayFragment extends Fragment {
    View rootView1;
    ListView list_interview, list_resume, list_xxx, list_others;
    TagAdapter listAdapter_interview, listAdapter_resume, listAdapter_xxx, listAdapter_others;
    List<TagSingle> data;
    Button addtolist;
    ScrollView scrollView;

    public DayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView1 = inflater.inflate(R.layout.day, container, false);
        initialize();
        listAdapter_interview = new TagAdapter(getContext(), data);
        list_interview.setAdapter(listAdapter_interview);
        listAdapter_resume = new TagAdapter(getContext(), data);
        list_resume.setAdapter(listAdapter_resume);
        listAdapter_xxx = new TagAdapter(getContext(), data);
        list_xxx.setAdapter(listAdapter_xxx);
        listAdapter_others = new TagAdapter(getContext(), data);
        list_others.setAdapter(listAdapter_others);
        return rootView1;
    }

    public void initialize() {
        scrollView = (ScrollView)rootView1.findViewById(R.id.scrollview);
        list_interview = (ListView)rootView1.findViewById(R.id.interviewlist);
        list_resume = (ListView)rootView1.findViewById(R.id.resumelist);
        list_xxx = (ListView)rootView1.findViewById(R.id.xxxlist);
        list_others = (ListView)rootView1.findViewById(R.id.otherslist);
        addtolist = (Button)rootView1.findViewById(R.id.addtolist);
        data = new ArrayList<TagSingle>();
        data.add(new TagSingle("Prepare for oci", 12, false));
        data.add(new TagSingle("Prepare for oci2", 20, false));
        data.add(new TagSingle("Prepare for oci3", 14, false));
        addtolist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "This is my Toast message!", Toast.LENGTH_LONG).show();
            }
        });
    }


}
