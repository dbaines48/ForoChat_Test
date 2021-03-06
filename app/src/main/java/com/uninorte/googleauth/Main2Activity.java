package com.uninorte.googleauth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

//Aquí cerramos la sesión
public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {
    private String TAG ="Cerro Sesion";
    private GoogleApiClient googleApiClient;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private ArrayList<Post> posts;
    private String nick_user;
    private ListView lv;
    String id_foro;
    String title_foro;
    String Token;
    boolean thread_running = true;

    private String TAG2 = "PUSH_TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        /*try {
            FirebaseInstanceId.getInstance().deleteInstanceId();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        FirebaseMessaging.getInstance().subscribeToTopic("test");
        String a = FirebaseInstanceId.getInstance().getToken();

        /*Log.d(TAG2, "ANTES DE MOSTRAR EL TOKEN");
        Log.d(TAG2, "TOKEN: "+a);
        Log.d(TAG2, "DESPUES DE MOSTRAR EL TOKEN");*/



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView = navigationView.getHeaderView(0);

        //profile_photo = (ImageView) hView.findViewById(R.id.profile_photo);

        Intent intent = getIntent();
        String name = intent.getStringExtra("nick_user");
        String email = intent.getStringExtra("email");
        //String temp_photo_url = intent.getStringExtra("photo_url");


        ((TextView) hView.findViewById(R.id.header_name)).setText(name);
        ((TextView) hView.findViewById(R.id.header_mail)).setText(email);

        posts = new ArrayList<Post>();
        //posts.add(new Post("Daniel Blanco", "Hello World!"));
        lv = (ListView) findViewById(R.id.posts_lv);
        AdapterPost adapter = new AdapterPost(this, posts);
        lv.setAdapter(adapter);
        nick_user = intent.getStringExtra("nick_user");
        mFirebaseInstance = FirebaseDatabase.getInstance();
        id_foro = intent.getStringExtra("foro_id");
        mFirebaseDatabase = (id_foro != null) ? mFirebaseInstance.getReference("foros").child(id_foro).child("posts") : mFirebaseInstance.getReference("foros").child("foro_g").child("posts");
        //mFirebaseDatabase = mFirebaseInstance.getReference("foros").child("foro_g").child("posts");
        title_foro = intent.getStringExtra("foro_title");
        getSupportActionBar().setTitle(title_foro != null ? title_foro : "Foro General");
        mFirebaseDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
                //String url = "http://your.domain.com/path/to/file.php";

                Post post = dataSnapshot.getValue(Post.class);
                posts.add(post);
                AdapterPost adapter2 = new AdapterPost(Main2Activity.this, posts);
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
    }

    public void createPost(View view) {
        String message = ((EditText) findViewById(R.id.editText)).getText().toString();
        Post new_post = new Post(nick_user,message);
        String key = mFirebaseDatabase.push().getKey();
        mFirebaseDatabase.child(key).setValue(new_post);
        String[] messages = new String[1];
        messages[0] = message;
        new PushNotifier().execute(messages);
        ((EditText) findViewById(R.id.editText)).setText("");
        ((EditText) findViewById(R.id.editText)).requestFocus();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            //CREAR FORO
            Intent intent = new Intent(Main2Activity.this, CreateForoActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            //MIS FOROS
            Intent intent_foros = new Intent(Main2Activity.this, ForumsActivity.class);
            intent_foros.putExtra("nick_user", nick_user);
            startActivity(intent_foros);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_salir){
            LoginManager.getInstance().logOut();

                    Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                            new ResultCallback<Status>() {
                                @Override
                                public void onResult(Status status) {
                                    // ...
                                    Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_SHORT).show();
                                    Intent i=new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(i);
                                }
                            });
                }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
