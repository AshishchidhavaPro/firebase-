package com.ashishchidhava.firebase_second;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    EditText titile, desc, author;
    TextView text;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titile = findViewById(R.id.title);
        desc = findViewById(R.id.desc);
        author = findViewById(R.id.author);
        text = findViewById(R.id.text);

    }
    public void submit(View view) {
//        create a new Hasmap
        HashMap<String, Object> map = new HashMap<>();
        map.put("Title", titile.getText().toString());
        map.put("Description", desc.getText().toString());
        map.put("Author", author.getText().toString());
        FirebaseDatabase.getInstance().getReference().child("Post").push()
                .setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(MainActivity.this, "Data Send Successsfully", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onSuccess: Data sended");
            }
        }).
                addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Data Sending Failed....", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "onFailure: sending failed");
                    }
                });
    }

    public void Recieve(View view) {

       // readOneTime();
        readRealTime();
    }

    private void readRealTime() {
        FirebaseDatabase.getInstance().getReference().child("Post").child("-M8teTIIodEfkp2JGDK8")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String post="Title : "+dataSnapshot.child("Title").getValue(String.class)+"\n"+
                                "Description : "+dataSnapshot.child("Description").getValue(String.class)+"\n"+
                                "Author : "+dataSnapshot.child("Author").getValue(String.class);
                        text.setText(post);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(MainActivity.this, "Data Receiving failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //single value event listener
    private void readOneTime() {
        FirebaseDatabase.getInstance().getReference().child("Post").child("-M8teTIIodEfkp2JGDK8")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String post="Title : "+dataSnapshot.child("Title").getValue(String.class)+"\n"+
                               "Description : "+dataSnapshot.child("Description").getValue(String.class)+"\n"+
                                "Author : "+dataSnapshot.child("Author").getValue(String.class);
                        text.setText(post);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.i(TAG, "onCancelled: errorrr"+ databaseError.toString());
                    }
                });
    }
}
