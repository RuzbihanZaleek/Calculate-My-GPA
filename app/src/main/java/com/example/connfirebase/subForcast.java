package com.example.connfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class subForcast extends AppCompatActivity {
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_forcast);
        listView =findViewById(R.id.forcastList);

        final ArrayList<String> subList = new ArrayList<>();
        final String semName = getIntent().getStringExtra("semForcast");
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.sem_list,R.id.sem, subList);
        //subList.add("sss");
        //subList.add("sss");
        //subList.add("sss");
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Subject").child(semName);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                subList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String subName = snapshot.getKey().toString();
                    String credit = snapshot.getValue().toString();
                    subList.add(subName);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        listView.setAdapter(adapter);

        ///////////////////////////////////////////////
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(subForcast.this, "position", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(subForcast.this, FinalForcast.class);

                String subjectName = subList.get(+position);

                //intent.putExtra("userName",userName);
                intent.putExtra("subjectName",subjectName);
                intent.putExtra("semName",semName);
                startActivity(intent);
            }
        });
        /////////////////////////////////////////////////////

    }
}