package com.example.msvs5;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class votevents extends Fragment {
    View view;
    Button processbtn;
    String votename,postname,currentdate,currenttime;
    Spinner votespin,postspin;
    TextInputLayout date,start,end;
    String votedate,starttime,endtime;
    private ArrayList<String>mistcaptain,osmanyhall,csedepartment;
    ArrayAdapter<String> arrayAdapterchild;

    private DatabaseReference df1;
    public votevents() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_votevents, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        processbtn=view.findViewById(R.id.processbtn);
        votespin=view.findViewById(R.id.eventspin);
        postspin=view.findViewById(R.id.postspin);
        date=view.findViewById(R.id.datetxtb);
        start=view.findViewById(R.id.starttxtb);
        end=view.findViewById(R.id.endtxtb);

        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
        currentdate=sdf.format(calendar.getTime());


        Calendar calendar1=Calendar.getInstance();
        SimpleDateFormat sdf1=new SimpleDateFormat("hh:mm a");
        currenttime=sdf1.format(calendar1.getTime());

        mistcaptain=new ArrayList<>();
        mistcaptain.add("captain");
        osmanyhall=new ArrayList<>();
        osmanyhall.add("captain");
        csedepartment=new ArrayList<>();
        csedepartment.add("departmentcaptain");
        csedepartment.add("culturalcaptain");
        csedepartment.add("sportscaptain");

        votespin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                postspin.setAdapter(arrayAdapterchild);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        processbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                votename = votespin.getSelectedItem().toString();
                postname = postspin.getSelectedItem().toString();
                df1 = FirebaseDatabase.getInstance().getReference().child("Events").child(votename).child(postname);
                df1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds:dataSnapshot.getChildren()) {
                            votedate = "" + ds.child("date").getValue(String.class);
                            starttime = "" + ds.child("start").getValue(String.class);
                            endtime = "" + ds.child("end").getValue(String.class);

                        }

                        date.getEditText().setText(votedate);
                        start.getEditText().setText(starttime);
                        end.getEditText().setText(endtime);

                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy");
                        SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("hh:mm a");
                        try {
                            Date date1=simpleDateFormat.parse(votedate);
                            Date date2=simpleDateFormat.parse(currentdate);

                            Date startt=simpleDateFormat1.parse(starttime);
                            Date endt=simpleDateFormat1.parse(endtime);
                            Date curtime=simpleDateFormat1.parse(currenttime);

                            long votingdate=date1.getTime();   //14/4/2020
                            long todate=date2.getTime();       //12/4/2020

                            if(todate<votingdate){
                                Toast.makeText(getActivity(),"Voting has not started yet!",Toast.LENGTH_SHORT).show();
                            }

                            else if(todate==votingdate){
                                if(curtime.after(startt)&&curtime.before(endt)){
                                    Toast.makeText(getActivity(), "you are on time for voting!", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(getActivity(),osmanyhallcapt.class);
                                intent.putExtra("date",votedate);
                                intent.putExtra("start",starttime);
                                intent.putExtra("end",endtime);
                                intent.putExtra("event",votename);
                                intent.putExtra("post",postname);
                                startActivity(intent);
                                }
                                else{
                                    Toast.makeText(getActivity(), "you are on right day, but not on right time!", Toast.LENGTH_SHORT).show();
                                }
                                //Period period=new Period(enddate,startdate,PeriodType.yearMonthDay());
                                //int years=period.getYears();
                                //int month=period.getMonths();
                                //int day=period.getDays();
                                //res.setText(day+" days,"+month+"months,"+years+"years");

                            }
                            else{
                                Toast.makeText(getActivity(),"Sorry! Voting date is expired",Toast.LENGTH_SHORT).show();
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




