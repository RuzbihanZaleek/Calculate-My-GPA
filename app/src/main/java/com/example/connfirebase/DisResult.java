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

public class DisResult extends AppCompatActivity {

    ListView listView;
    String Nme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dis_result);

        listView =findViewById(R.id.list);

        //final ArrayList<String> subCode = new ArrayList<>();
        //final ArrayList<String> subName= new ArrayList<>();
        //final ArrayList<String> subCredit = new ArrayList<>();
        //final ArrayList<String> subResult = new ArrayList<>();
        final String userName = getIntent().getStringExtra("userName");
        //final CustomListAdapter adapter = new CustomListAdapter(DisResult.this, subCode, subName, subCredit, subResult);
        final ArrayList<String> semList = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.sem_list,R.id.sem, semList);

        listView.setAdapter(adapter);



        /*subCredit.add("ss");
        subCredit.add("ss");
        subCredit.add("ss");
        subCredit.add("ss");
        subCredit.add("ss");

        subResult.add("ss");
        subResult.add("ss");
        subResult.add("ss");
        subResult.add("ss");
        subResult.add("ss");*/
        //final String userName = getIntent().getStringExtra("userName");
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Students").child(userName).child("Result");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                semList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    //Nme = snapshot.getKey().toString();
                    semList.add(snapshot.getKey().toString());
                    //subName.add(snapshot.getKey().toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ///////////////////////////////////////////////
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(DisResult.this, "ssssssssssssssssss", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DisResult.this, DisSemResult.class);

                String semester = semList.get(+position);

                intent.putExtra("userName",userName);
                intent.putExtra("semester",semester);
                startActivity(intent);
            }
        });
        /////////////////////////////////////////////////////
    }
}