package com.example.connfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SemFuture extends AppCompatActivity {

    ListView listView;
    String item1,FutResult;
    String creditSum,gpvSum,passedSem;
    TextView gpa;
    float a;
    float curCreditSum,curGpvSum;
    Button remove,forcast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem_future);

        gpa = findViewById(R.id.curGpa);
        remove = findViewById(R.id.removeSub);
        forcast = findViewById(R.id.forcast);

        final String userName = getIntent().getStringExtra("userName");
        creditSum = getIntent().getStringExtra("creditSum");
        gpvSum = getIntent().getStringExtra("gpvSum");
        curCreditSum = Float.parseFloat(creditSum);
        curGpvSum = Float.parseFloat(gpvSum);
        gpa.setText(String.format("%.2f",curGpvSum/ curCreditSum));
        final ArrayList<String> semList = new ArrayList<>();
        final ArrayList<String> semListFilter = new ArrayList<>();
        final ArrayList<String> filteredSemList = new ArrayList<>();

        final ArrayList<String> selectedSubject = new ArrayList<>();
        final ArrayList<String> selectedSubject_1 = new ArrayList<>();
        final ArrayList<String> resultDetailsList = new ArrayList<>();

        final ArrayList<String> subList = new ArrayList<>();

        final ArrayList<Integer> creditSum = new ArrayList<>();
        final ArrayList<Integer> subGpv= new ArrayList<>();

        creditSum.add((int) curCreditSum);
        subGpv.add((int) curGpvSum);

        listView =findViewById(R.id.list2);

        resultDetailsList.add("A");
        resultDetailsList.add("B");
        resultDetailsList.add("C");
        //final Spinner spinner1 = (Spinner) findViewById(R.id.subject);
        //spinner.setEnabled(false);

        /////////////////CHECK SEMESTER ALREADY DONE OR NOT START//////////////////////////////////

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.filter_sem_list,R.id.sem, filteredSemList);

        listView.setAdapter(adapter);

        final DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("Students").child(userName).child("Result");
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                semList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    passedSem = snapshot.getKey().toString();
                    semListFilter.add(passedSem);
                    /*if(semList.contains("First Year First Semester1")){
                        semListFilter.add("Semester 1");
                        //spinner1.setEnabled(false);
                    }else{
                        //spinner1.setEnabled(true);
                    }*/
                    //subName.add(snapshot.getKey().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
////////////////////////////////GET SUBJECT DETAILS START/////////////////////////////////////////////
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Subject");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                filteredSemList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String result = snapshot.getKey().toString();
                    filteredSemList.add(result);
                    filteredSemList.removeAll(semListFilter);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ////////////////////////////GET SUBJECT DETAILS END/////////////////////////////////////////////
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedItem = (String)listView.getItemAtPosition(position);
                Toast.makeText(SemFuture.this, selectedItem, Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builderVal=new AlertDialog.Builder(SemFuture.this);
                builderVal.setTitle(selectedItem) ;
                View holder=View.inflate(SemFuture.this, R.layout.popupspinner, null);
                builderVal.setView(holder);
                Spinner spinner1 = (Spinner) holder.findViewById(R.id.subject);
                Spinner spinner2 = (Spinner) holder.findViewById(R.id.result);
                builderVal.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(selectedSubject.contains(item1)){
                            Toast.makeText(SemFuture.this, "Already Selected", Toast.LENGTH_SHORT).show();
                            gpa.setText(String.format("%.2f",getGpvSum()/getCreditSum()));
                        }else {
                            SubjectSelection();

                           /* if((getCreditSum() <= 30) && ((selectedItem.equals("Semester 1") && (selectedItem.equals("Semester 2")))){
                                SubjectSelection();
                            }else if((getCreditSum() <= 60) && (selectedItem.equals("Semester 3"))){
                                SubjectSelection();
                            }else{
                                Toast.makeText(SemFuture.this, "Credit Level Exceed", Toast.LENGTH_SHORT).show();
                            }*/

                        }
                    }
                    public void SubjectSelection(){
                        creditSum.add((int) a);
                        selectedSubject.add(item1);
                        selectedSubject_1.add(FutResult+"   "+item1.substring(item1.lastIndexOf("t: ")+2));
                        if (FutResult.equals("A")) {
                            subGpv.add((int) (a * 4));
                        } else {
                            subGpv.add((int) (a * 3));
                        }
                        Toast.makeText(SemFuture.this, "Selected", Toast.LENGTH_SHORT).show();
                        gpa.setText(String.format("%.2f", getGpvSum() / getCreditSum()));
                    }
                });
                builderVal.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //if user select "No", just cancel this dialog and continue with app
                        dialog.cancel();
                    }
                });
                builderVal.show();
                /////////////SELECT SUBJECT START/////////////////////
                final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(SemFuture.this, android.R.layout.simple_spinner_item, subList);
                final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(SemFuture.this, android.R.layout.simple_spinner_item, resultDetailsList);
                // Drop down layout style - list view with radio button
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner1.setAdapter(adapter1);

                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter2);

                spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        item1 = adapterView.getItemAtPosition(i).toString();
                        //Toast.makeText(adapterView.getContext(), "Selected: " + item1, Toast.LENGTH_LONG).show();
                        char crdt = item1.charAt(8);
                        a=Float.parseFloat(String.valueOf(crdt));
                        //curCreditSum = Float.parseFloat(creditSum);
                        //curGpvSum = Float.parseFloat(gpvSum);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                /////////
                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        FutResult = adapterView.getItemAtPosition(i).toString();
                        //Toast.makeText(adapterView.getContext(), "Selected: " + FutResult, Toast.LENGTH_LONG).show();
                        //char crdt = item1.charAt(8);
                        //a=Float.parseFloat(String.valueOf(crdt));
                        //curCreditSum = Float.parseFloat(creditSum);
                        //curGpvSum = Float.parseFloat(gpvSum);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                //////////
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Subject").child(selectedItem);
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        subList.clear();
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            String subName = snapshot.getKey().toString();
                            String credit = snapshot.getValue().toString();
                            subList.add("Credit: "+credit+" Subject: "+subName);
                        }
                        adapter1.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                ///////////////SELECT SUBJECT END//////////////////////////////
            }
            //////////////////////CALCULATION METHODS START//////////////
            public float getCreditSum(){
                float credtSum = 0;
                for(int i = 0; i<creditSum.size(); i++){
                    credtSum+=creditSum.get(i);
                }
                return credtSum;
            }
            public float getGpvSum(){
                float gpvSum = 0;
                for(int i = 0; i<subGpv.size(); i++){
                    gpvSum+=subGpv.get(i);
                }
                return gpvSum;
            }
            ///////////////////////CALCULATION METHOD END///////////////
        });
        //////////////////////CHECK SEMESTER ALREADY DONE OR NOT END////////////////////////////////////
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FirebaseDatabase.getInstance().getReference().child("Test").child("Name").setValue("shihan milinda");
                Toast.makeText(SemFuture.this, "Result", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SemFuture.this);
                alertDialog.setTitle("Tap Courses For Remove");
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(SemFuture.this, android.R.layout.simple_dropdown_item_1line, selectedSubject_1);
                alertDialog.setAdapter(dataAdapter, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int removeCredit = which + 1;
                        selectedSubject.remove(which);
                        selectedSubject_1.remove(which);
                        creditSum.remove(removeCredit);
                        subGpv.remove(removeCredit);
                        Toast.makeText(SemFuture.this,"Removed",Toast.LENGTH_LONG).show();
                        float creditSum1 = creditSum.stream().mapToInt(Integer::intValue).sum();
                        float gpvSum = subGpv.stream().mapToInt(Integer::intValue).sum();
                        gpa.setText(String.format("%.2f", gpvSum / creditSum1));
                    }
                });
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //if user select "No", just cancel this dialog and continue with app
                        dialog.cancel();
                    }
                });
                AlertDialog alert = alertDialog.create();
                alert.show();
            }
        });
        forcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SemFuture.this, "Frcast", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SemFuture.this, semForcast.class);
                startActivity(intent);
            }
        });


    }
}