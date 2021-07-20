package com.example.connfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FinalForcast extends AppCompatActivity {

    TextView subName,pass,repeat,fail;
    String subjectName1, subCountS;
    int count;
    String[] subjectNameSplit;
    ListView listView;

    PieChart pieChart;
    PieData pieData;
    List<PieEntry> pieEntryList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_forcast);

        subName = findViewById(R.id.subName1);

        pass = findViewById(R.id.pass);
        repeat = findViewById(R.id.repeat);
        fail = findViewById(R.id.fail);

        listView =findViewById(R.id.forcast_list1);

        final ArrayList<String> resultCount = new ArrayList<>();
        //final ArrayList<String> students1 = new ArrayList<>();
        final ArrayList<String> sumResult = new ArrayList<>();
        final ArrayList<String> subPra = new ArrayList<>();
        //final ArrayList<String> result = new ArrayList<>();

        final String subjectName = getIntent().getStringExtra("subjectName");
        final String semName = getIntent().getStringExtra("semName");
        subjectNameSplit = subjectName.split(":");
        //subName.setText(subjectNameSplit[0] +"\n" + subjectNameSplit[1]);

        //students.add("2021s00000");
        //students.add("2021s00001");


       //sumResult.add("55");
       //sumResult.add("55");

        //result.add("44444");
        //result.add("444444");
        //subPra.add("44");
        //subPra.add("4");
        final String result[] = {"==A","==B","==C","==D","==F"};

        final Float cnt_array[] = new Float[5];
        final Float cnt_array_1[] = new Float[3];

        final String cnt_arrayS[] = new String[5];
        final String cnt_arrayS_1[] = new String[3];
        //result[0] = "==A";
        //cnt_array[0] = 1;
        Arrays.fill(cnt_array,new Float(0.0));
        Arrays.fill(cnt_array_1,new Float(0.0));
        //final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.forcast_list,R.id.forcastList, result);
        //listView.setAdapter(adapter);
        subName.setText(subjectNameSplit[0] + "\n" + subjectNameSplit[1]);





        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Students");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot requestSnapshot: dataSnapshot.getChildren()) {
                    DataSnapshot pSnapshot = requestSnapshot.child("Result").child(semName).child(subjectNameSplit[0]);
                    //Forcast forcast = pSnapshot.getValue(Forcast.class);
                    String allResult = pSnapshot.child("SubResult").getValue(String.class);

                    resultCount.add(allResult);

                    ResultCount resultCount1 = new ResultCount();

                    resultCount1.getarray(allResult,cnt_array);
                    resultCount1.getarray_1(allResult,cnt_array_1);
                    /*if (allResult.equals("A")) {
                        cnt_array[0] = cnt_array[0] + 1;
                    } else {
                        cnt_array[1] = cnt_array[1] + 1;
                    }*/

                    System.out.println("==="+allResult+"=======================================================");
                }
                for (int i = 0; i < cnt_array.length; i++) {
                    float cnt = (cnt_array[i])*100/resultCount.size();
                    String cntS = String.format("%.1f",cnt);
                    cnt_arrayS[i] = cntS+"%";
                    System.out.println("==="+String.valueOf(cnt_arrayS[i])+"=======================================================");
                }
                for (int i = 0; i < cnt_array_1.length; i++) {
                    float cnt = (cnt_array_1[i])*100/resultCount.size();
                    String cntS = String.format("%.1f",cnt);
                    cnt_arrayS_1[i] = cntS+"%";
                    //System.out.println("==="+String.valueOf(cnt_arrayS[i])+"=======================================================");
                }
                pass.setText("Pass \n"+cnt_arrayS_1[0]);
                repeat.setText("Repeat \n"+cnt_arrayS_1[1]);
                fail.setText("Fail \n"+cnt_arrayS_1[2]);

                ////////////////////////////////////////////////////////////////////////////////
                pieChart = findViewById(R.id.pieChart);
                pieChart.setUsePercentValues(false);
                pieEntryList.add(new PieEntry(cnt_array_1[0],"Pass"));
                pieEntryList.add(new PieEntry(cnt_array_1[1],"Repeat"));
                pieEntryList.add(new PieEntry(cnt_array_1[2],"Fail"));
                PieDataSet pieDataSet = new PieDataSet(pieEntryList,"Result");
                pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
                pieData = new PieData(pieDataSet);
                pieChart.setData(pieData);
                pieChart.invalidate();
                ////////////////////////////////////////////////////////////////////////////////
                final CustomListAdapter_1 adapter = new CustomListAdapter_1(FinalForcast.this,result, cnt_arrayS);
                //final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.sem_list,R.id.sem, subResult);
                listView.setAdapter(adapter);
                /*for (int i = 0; i < cnt_array.length; i++) {
                    cnt_arrayS[i] = String.valueOf(cnt_array[i]);
                }*/
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException(); // don't ignore errors
            }
        });
        //System.out.println("==============================================================================================");
        /////////////////////////////////////////
    }
}