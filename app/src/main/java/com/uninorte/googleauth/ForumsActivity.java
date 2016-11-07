package com.uninorte.googleauth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ForumsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private ListView lv;
    private ArrayList<Foro> Foros;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    String nick_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forums);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        //.setAction("Action", null).show();
                Intent intent = new Intent(ForumsActivity.this, CreateForoActivity.class);
                startActivity(intent);
            }
        });

        Foros = new ArrayList<Foro>();
        nick_user = getIntent().getStringExtra("nick_user");
        lv = (ListView) findViewById(R.id.forums_lv);
        AdapterForum adapter = new AdapterForum(this, Foros);
        lv.setAdapter(adapter);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("foros");
        mFirebaseDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("FOROS_TAG", "onChildAdded:" + dataSnapshot.getKey());
                //String url = "http://your.domain.com/path/to/file.php";

                Foro foro = dataSnapshot.getValue(Foro.class);
                foro.setId(dataSnapshot.getKey().toString());
                Foros.add(foro);
                AdapterForum adapter2 = new AdapterForum(ForumsActivity.this, Foros);
                lv.setAdapter(adapter2);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                Foro foro = Foros.get(position);
                if(foro != null){
                    Intent intent = new Intent(ForumsActivity.this, Main2Activity.class);
                    intent.putExtra("foro_id", foro.getId());
                    intent.putExtra("nick_user", nick_user);
                    intent.putExtra("foro_title", foro.getName());
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
