package com.uninorte.googleauth;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dbain on 25/10/2016.
 */

public class AdapterPost extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<Post> posts;

    public AdapterPost(Activity activity, ArrayList<Post> posts){
        this.activity = activity;
        this.posts = posts;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    public void clear(){
        posts.clear();
    }

    public void addAll(ArrayList<Post> post){
        for (int i = 0; i < post.size(); i++ ){
            posts.add(post.get(i));
        }
    }

    @Override
    public Object getItem(int position) {
        return posts.get(position);
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
            v = inf.inflate(R.layout.forum_post, null);
        }

        Post dir = posts.get(position);

        TextView usuario = (TextView) v.findViewById(R.id.t_nombre);
        usuario.setText(dir.getNick());

        TextView mensaje = (TextView) v.findViewById(R.id.t_mensaje);
        mensaje.setText(dir.getMessage());

        return v;
    }
}
