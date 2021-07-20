package com.example.connfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisSemResult extends AppCompatActivity {

    ListView listView;
    TextView gpa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dis_sem_result);

        listView =findViewById(R.id.result_list);
        gpa = findViewById(R.id.gpa);
        final ArrayList<Integer> creditSum = new ArrayList<>();
        final ArrayList<Integer> subGpv= new ArrayList<>();
        final ArrayList<String> subCode = new ArrayList<>();
        final ArrayList<String> subName= new ArrayList<>();
        final ArrayList<String> subCredit = new ArrayList<>();
        final ArrayList<String> subResult = new ArrayList<>();

        final CustomListAdapter adapter = new CustomListAdapter(DisSemResult.this, subCode, subName, subCredit, subResult);
        //final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.sem_list,R.id.sem, subResult);
        listView.setAdapter(adapter);


       /* subCredit.add("ss");
        subCredit.add("ss");
        //subCredit.add("ss");
        //subCredit.add("ss");
        //subCredit.add("ss");

        subResult.add("ss");
        subResult.add("ss");

        subName.add("ss");
        subName.add("ss");

        subCode.add("ss");
        subCode.add("ss");
        //subResult.add("ss");
        //subResult.add("ss");
        //subResult.add("ss");*/

        final String userName = getIntent().getStringExtra("userName");
        final String semester = getIntent().getStringExtra("semester");

        /////////////////////////////////////////////////////result name//////////////////////////////////////////////

       /*final DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("Subject");
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                subAllCode.clear();
                subAllName.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    subAllCode.add(snapshot.getKey().toString());
                    subAllName.add(snapshot.child("SubName").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Students").child(userName).child("Result").child(semester);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                subResult.clear();
                subCode.clear();
                subName.clear();
                subCredit.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    //Nme = snapshot.getKey().toString();
                    String result = snapshot.child("SubResult").getValue().toString();
                    String credit = snapshot.child("SubCredit").getValue().toString();
                    int creditInt =Integer.parseInt(credit);
                    creditSum.add(creditInt);

                    //////////////////////////////////////////////////////////////////////////////////////
                    if(result.equals("A")){
                        subGpv.add(creditInt * 4);
                    }else{
                        subGpv.add(creditInt * 3);
                    }
                    /////////////////////////////////////////////////////////////////////////////////////
                    //int resultInt=Integer.parseInt(s);
                    subCode.add(snapshot.getKey().toString());
                    subName.add(snapshot.child("SubName").getValue().toString());
                    subResult.add(result);
                    subCredit.add(credit);
                    //subName.add(snapshot.getKey().toString());
                }
                //////////////////////////////////////////////////////////////////////////////////////////////

                //float curGpaVal = getGpvSum()/getCreditSum();
                //getSum();
                float sum = creditSum.stream().mapToInt(Integer::intValue).sum();
                gpa.setText(String.valueOf(sum));
                //gpa.setText(String.format("%.2f",curGpaVal));
                //////////////////////////////////////////////////////////////////
                adapter.notifyDataSetChanged();
            }
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
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //adapter.notifyDataSetChanged();
    }
}