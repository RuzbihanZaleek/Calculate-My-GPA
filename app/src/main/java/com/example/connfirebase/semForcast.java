package com.example.connfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class semForcast extends AppCompatActivity {

    String semForcast;
    Button searchForcast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem_forcast);
        Spinner spinner1 = (Spinner) findViewById(R.id.semester);
        searchForcast = findViewById(R.id.search);
        final ArrayList<String> semList = new ArrayList<>();
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(semForcast.this, android.R.layout.simple_spinner_item, semList);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Subject");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //semList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String semName = snapshot.getKey().toString();
                    //String credit = snapshot.getValue().toString();
                    semList.add(semName);
                }
                adapter1.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        ////////////////////////////////////////
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                semForcast = adapterView.getItemAtPosition(i).toString();
                //Toast.makeText(adapterView.getContext(), "Selected: " + semForcast, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        searchForcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(semForcast.this, subForcast.class);

                intent.putExtra("semForcast",semForcast);
                startActivity(intent);
            }
        });
        //////////////////////////////////////////
    }
}