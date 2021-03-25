package com.example.msvs5;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class result extends Fragment {
    View view;
    ListView listview;
    //DatabaseReference df;
    Spinner spinner2,spinner5;
    //ArrayList<String> mUsername=new ArrayList<>();
    String vote,post,votedater,starttimer,endtimer,currentdater,currenttimer;

    private ArrayList<DataSetFire> arrayList;
    //private FirebaseRecyclerOptions<DataSetFire> options;

    //private FirebaseRecyclerAdapter<DataSetFire,FirebaseViewHolder> adapter;
    private DatabaseReference df;
    private ArrayList<String>mistcaptain,osmanyhall,csedepartment;
    ArrayAdapter<String> arrayAdapterchild;
    Button refreshbtn;

    DatabaseReference df1;

    ArrayList<String> mUsername=new ArrayList<>();
    FirebaseListAdapter adapter;

    public result() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_result, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        mistcaptain=new ArrayList<>();
        mistcaptain.add("captain");
        osmanyhall=new ArrayList<>();
        osmanyhall.add("captain");
        csedepartment=new ArrayList<>();
        csedepartment.add("departmentcaptain");
        csedepartment.add("culturalcaptain");
        csedepartment.add("sportscaptain");
        spinner2=view.findViewById(R.id.spinner2);
        spinner5=view.findViewById(R.id.postspinner5);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(position==1){
                    arrayAdapterchild=new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item,mistcaptain);
                }
                if(position==3){
                    arrayAdapterchild=new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item,osmanyhall);
                }
                if(position==2){
                    arrayAdapterchild=new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item,csedepartment);
                }
                spinner5.setAdapter(arrayAdapterchild);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        refreshbtn=view.findViewById(R.id.resultbtn);
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
        currentdater=sdf.format(calendar.getTime());


        Calendar calendar1=Calendar.getInstance();
        SimpleDateFormat sdf1=new SimpleDateFormat("hh:mm a");
        currenttimer=sdf1.format(calendar1.getTime());
        refreshbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vote=spinner2.getSelectedItem().toString();
                post=spinner5.getSelectedItem().toString();
                DatabaseReference df=FirebaseDatabase.getInstance().getReference().child("Events").child(vote).child(post);
                df.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds:dataSnapshot.getChildren()) {
                            votedater = "" + ds.child("date").getValue(String.class);
                            starttimer = "" + ds.child("start").getValue(String.class);
                            endtimer = "" + ds.child("end").getValue(String.class);

                        }

                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy");
                        SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("hh:mm a");
                        try {
                            Date date1=simpleDateFormat.parse(votedater);
                            Date date2=simpleDateFormat.parse(currentdater);

                            Date startt=simpleDateFormat1.parse(starttimer);
                            Date endt=simpleDateFormat1.parse(endtimer);
                            Date curtime=simpleDateFormat1.parse(currenttimer);

                            long votingdate=date1.getTime();   //14/4/2020
                            long todate=date2.getTime();       //12/4/2020

                            if(todate<votingdate){
                                Intent intent = new Intent(getActivity(), resultview.class);
                                intent.putExtra("vote", vote);
                                intent.putExtra("post", post);
                                startActivity(intent);
                            }

                            else if(todate==votingdate){
                                if(curtime.after(startt)&&curtime.before(endt)){
                                    Toast.makeText(getActivity(), "Voting is going on", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Intent intent = new Intent(getActivity(), resultview.class);
                                    intent.putExtra("vote", vote);
                                    intent.putExtra("post", post);
                                    startActivity(intent);
                                }
                                //Period period=new Period(enddate,startdate,PeriodType.yearMonthDay());
                                //int years=period.getYears();
                                //int month=period.getMonths();
                                //int day=period.getDays();
                                //res.setText(day+" days,"+month+"months,"+years+"years");

                            }
                            else{
                                Intent intent = new Intent(getActivity(), resultview.class);
                                intent.putExtra("vote", vote);
                                intent.putExtra("post", post);
                                startActivity(intent);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });
            }
        });
    }
}
