package com.uninorte.googleauth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateForoActivity extends AppCompatActivity {

    private EditText forum_name;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_foro);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        //lo siguiente va en la funcion que creara el foro
        forum_name = (EditText) findViewById(R.id.etForumName);
    }

    public void createForum(View view) {
        String f_name = forum_name.getText().toString();
        mFirebaseDatabase = mFirebaseInstance.getReference("foros");
        Foro new_foro = new Foro(f_name);
        String key = mFirebaseDatabase.push().getKey();
        mFirebaseDatabase.child(key).setValue(new_foro);
        finish();
    }
}
