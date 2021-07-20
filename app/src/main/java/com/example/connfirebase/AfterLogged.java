package com.example.connfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AfterLogged extends AppCompatActivity {

    private Button logout,result,gpaBtn;
    private TextView fname, reg_no, index_no, phone_no, email_1,gpa;
    ImageView logo, first, second, third;
    float curGpaVal,creditSum1,gpvSum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_logged);

        //logout = findViewById(R.id.gpa);
        gpaBtn = findViewById(R.id.gpa);
        result = findViewById(R.id.result);

        gpa = findViewById(R.id.curGpa);
        fname = findViewById(R.id.name);
        reg_no = findViewById(R.id.regno);
        index_no = findViewById(R.id.indexno);
        phone_no = findViewById(R.id.phoneno);
        email_1 = findViewById(R.id.email1);

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.p_Bar);
        final ProgressBar progressBar1 = (ProgressBar) findViewById(R.id.p_Bar_2);
        final ProgressBar progressBar2 = (ProgressBar) findViewById(R.id.p_Bar_3);

        final ArrayList<Integer> creditSum = new ArrayList<>();
        final ArrayList<Integer> subGpv= new ArrayList<>();

        logo = (ImageView)findViewById(R.id.logo);
        first = (ImageView)findViewById(R.id.first);
        second = (ImageView)findViewById(R.id.second);
        third = (ImageView)findViewById(R.id.third);
        final String userName = getIntent().getStringExtra("userName");

        logo.setImageResource(R.drawable.uoc);
        first.setImageResource(R.drawable.gold);
        second.setImageResource(R.drawable.silver);
        third.setImageResource(R.drawable.browns);

        progressBar.setVisibility(View.VISIBLE);
        progressBar1.setVisibility(View.VISIBLE);
        progressBar2.setVisibility(View.VISIBLE);
        //progressBar.setProgress(250);
        ///LOGOUT BUTTON////////////////////////////////////////////////////////
        /*logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(AfterLogged.this,"logOut", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AfterLogged.this,MainActivity.class));
                finish();
            }
        });*/
//////////////////////////////////////////////////////////////////

        gpaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AfterLogged.this, "GpaCalculation", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AfterLogged.this, SemFuture.class);
                //intent.putExtra("userName",String.format("%.2f",curGpaVal));
                intent.putExtra("creditSum",String.valueOf(creditSum1));
                intent.putExtra("gpvSum",String.valueOf(gpvSum));
                intent.putExtra("userName",userName);
                startActivity(intent);
            }
        });
        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FirebaseDatabase.getInstance().getReference().child("Test").child("Name").setValue("shihan milinda");
                Toast.makeText(AfterLogged.this, "Result", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AfterLogged.this, DisResult.class);
                intent.putExtra("userName",userName);
                startActivity(intent);
            }
        });
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        final DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("Students").child(userName).child("Result");
        reference1.addChildEventListener(new ChildEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //String key = dataSnapshot.getKey();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String result = snapshot.child("SubResult").getValue().toString();
                    String credit = snapshot.child("SubCredit").getValue().toString();
                    int creditInt = Integer.parseInt(credit);
                    creditSum.add(creditInt);

                    if(result.equals("A")){
                        subGpv.add(creditInt * 4);
                    }else{
                        subGpv.add(creditInt * 3);
                    }
                }

                creditSum1 = creditSum.stream().mapToInt(Integer::intValue).sum();
                gpvSum = subGpv.stream().mapToInt(Integer::intValue).sum();
                curGpaVal = gpvSum/creditSum1;

                int proGpaFirst = (int) ((curGpaVal/3.7)*100);
                int proGpaSecond = (int) ((curGpaVal/3.3)*100);
                int proGpaThird = (int) ((curGpaVal/3.0)*100);
                progressBar.setProgress(proGpaFirst);
                progressBar1.setProgress(proGpaSecond);
                progressBar2.setProgress(proGpaThird);
                gpa.setText("GPA - " +String.format("%.2f",curGpaVal));
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myNme = database.getReference().child("Students").child(userName);

        myNme.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //String Nme = myRef.getKey();
                User user = dataSnapshot.getValue(User.class);
                // String value = dataSnapshot.getValue(String.class);
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                fname.setText(user.getName());
                reg_no.setText(user.getReg());
                index_no.setText(user.getIndex());
                phone_no.setText(user.getPhone());
                email_1.setText(user.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}