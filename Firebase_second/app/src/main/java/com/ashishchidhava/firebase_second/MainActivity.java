package com.ashishchidhava.firebase_second;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
EditText titile,desc,author;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titile=findViewById(R.id.title);
        desc=findViewById(R.id.desc);
        author=findViewById(R.id.author);

    }

    public void submit(View view) {
//        create a new Hasmap
        HashMap<String ,Object> map=new HashMap<>();
        map.put("Title",titile.getText().toString());
        map.put("Description",desc.getText().toString());
        map.put("Author",author.getText().toString());
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
    }
}
