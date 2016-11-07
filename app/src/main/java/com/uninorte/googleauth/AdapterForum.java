    package com.uninorte.googleauth;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by dbain on 01/11/2016.
 */

public class AdapterForum extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<Foro> Forums;

    public AdapterForum(Activity activity, ArrayList<Foro> Forums){
        this.activity = activity;
        this.Forums = Forums;
    }

    @Override
    public int getCount() {
        return Forums.size();
    }

    public void clear(){
        Forums.clear();
    }

    public void addAll(ArrayList<Foro> Foro){
        for (int i = 0; i < Foro.size(); i++ ){
            Forums.add(Foro.get(i));
        }
    }

    @Override
    public Object getItem(int position) {
        return Forums.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(convertView == null){
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.item_forum, null);
        }

        Foro dir = Forums.get(position);

        TextView title = (TextView) v.findViewById(R.id.tvTitle);
        title.setText(dir.getName_foro());

        return v;
    }
}
