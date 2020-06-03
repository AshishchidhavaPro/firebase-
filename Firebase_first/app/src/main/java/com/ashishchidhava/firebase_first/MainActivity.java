package com.ashishchidhava.firebase_first;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
EditText title,desc,author;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title=findViewById(R.id.title);
        desc=findViewById(R.id.desc);
        author=findViewById(R.id.author);


    }

    public void submit(View view) {
//        create a new hasmap

        HashMap<String,Object> map=new HashMap<>();
        //get value from edittext
        map.put("Title",title.getText().toString());
        map.put("Description",desc.getText().toString());
        map.put("Author",author.getText().toString());

        //connection build to realtime database
        FirebaseDatabase.getInstance().getReference().child("Post").push()              //push() ni lgaynge to ek hi data ko overide krta rhega and ye har barr ek nyi id create kreaga
                .setValue(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.i(TAG, "onComplete: Complete");
                        Toast.makeText(MainActivity.this, "Data Send Successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i(TAG, "onFailure: Datasend faild "+e.toString());
                        Toast.makeText(MainActivity.this, "Data sending failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
