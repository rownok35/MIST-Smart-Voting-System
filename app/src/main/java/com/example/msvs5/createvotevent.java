package com.example.msvs5;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class createvotevent extends Fragment {
    View view;
    Calendar c1,c2,c3;
    DatePickerDialog df1;
    TimePickerDialog tf,tf1;
    String format;
    Button calenderbtn,strttimebtn,endtimebtn,addcanbtn,voterlistbtn,createbtn;
    Spinner event, post;
    TextInputLayout datetxt,strttimetxt,endtimetxt;
    String date,start,end,events,posts,key;
    HashMap<String ,String> hash;
    private DatabaseReference df;
    private DatabaseReference databaseReference;
    private ArrayList<String>mistcaptain,osmanyhall,csedepartment;
    ArrayAdapter<String> arrayAdapterchild;
    int flag=0;

    public createvotevent() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
       view= inflater.inflate(R.layout.fragment_createvotevent, container, false);
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
        calenderbtn=view.findViewById(R.id.calenderbtn);
        strttimebtn=view.findViewById(R.id.starttimebtn);
        endtimebtn=view.findViewById(R.id.endtimebtn);
        createbtn=view.findViewById(R.id.createbtn);
        event=view.findViewById(R.id.event);
        post=view.findViewById(R.id.post);
        datetxt=view.findViewById(R.id.datetxt);
        strttimetxt=view.findViewById(R.id.starttimetxt);
        endtimetxt=view.findViewById(R.id.endtimetxt);
        df= FirebaseDatabase.getInstance().getReference().child("Events");
        addcanbtn=view.findViewById(R.id.addcanbtn);
        addcanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                events=event.getSelectedItem().toString();
                posts=post.getSelectedItem().toString();
                Intent intent=new Intent(getActivity(),addcandidate.class);
                intent.putExtra("event",events);
                intent.putExtra("post",posts);
                startActivity(intent);
            }
        });
        calenderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c1= Calendar.getInstance();
                int day=c1.get(Calendar.DAY_OF_MONTH);
                int month=c1.get(Calendar.MONTH);
                int year=c1.get(Calendar.YEAR);
                df1=new DatePickerDialog(getActivity(), R.style.AppTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        //mTv.setText(mDay+"-"+(mMonth+1)+"-"+mYear);
                        //s1=String.valueOf(mDay)+"-"+String.valueOf(mMonth+1)+"-"+String.valueOf(mYear);
                        //String s=Integer.toString(mYear)+Integer.toString(mMonth+1)+Integer.toString(mDay);
                        if(mMonth+1<10){
                        datetxt.getEditText().setText(mDay+"-0"+(mMonth+1)+"-"+mYear);
                        }
                        else if(mMonth+1>=10){
                            datetxt.getEditText().setText(mDay+"-"+(mMonth+1)+"-"+mYear);
                        }

                    }
                },day,month,year);
                df1.getDatePicker().setMinDate(System.currentTimeMillis());
                df1.show();
            }
        });

        strttimebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c2=Calendar.getInstance();
                int hour=c2.get(Calendar.HOUR_OF_DAY);
                int minute=c2.get(Calendar.MINUTE);
                selectedtimeformat(hour);

                tf=new TimePickerDialog(getActivity(), R.style.AppTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int mHour, int mMinute) {
                        selectedtimeformat(mHour);
                        if(mHour>12){
                        strttimetxt.getEditText().setText((mHour-12)+":"+mMinute+" "+format);}
                        else if(mHour==0){
                            strttimetxt.getEditText().setText((mHour+12)+":"+mMinute+" "+format);
                        }
                        else{
                            strttimetxt.getEditText().setText(mHour+":"+mMinute+" "+format);
                        }

                    }
                },hour,minute,false);
                tf.show();
            }
        });

        endtimebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c3=Calendar.getInstance();
                int hour=c3.get(Calendar.HOUR_OF_DAY);
                int minute=c3.get(Calendar.MINUTE);
                selectedtimeformat(hour);

                tf1=new TimePickerDialog(getActivity(), R.style.AppTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int mHour, int mMinute) {
                        selectedtimeformat(mHour);
                        if(mHour>12){
                            endtimetxt.getEditText().setText((mHour-12)+":"+mMinute+" "+format);}
                        else if(mHour==0){
                            endtimetxt.getEditText().setText((mHour+12)+":"+mMinute+" "+format);
                        }
                        else{
                            endtimetxt.getEditText().setText(mHour+":"+mMinute+" "+format);
                        }
                    }
                },hour,minute,false);
                tf1.show();
            }
        });

        event.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                post.setAdapter(arrayAdapterchild);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        createbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date=datetxt.getEditText().getText().toString().trim();
                start=strttimetxt.getEditText().getText().toString().trim();
                end=endtimetxt.getEditText().getText().toString().trim();
                events=event.getSelectedItem().toString();
                posts=post.getSelectedItem().toString();
                key =df.push().getKey();
                hash=new HashMap<>();
                hash.put("date",date);
                hash.put("start",start);
                hash.put("end",end);
                hash.put("key",key);
                hash.put("post",posts);
                databaseReference=FirebaseDatabase.getInstance().getReference().child("Events").child(events).child(posts);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            Toast.makeText(getActivity(),"vote event exists already!.",Toast.LENGTH_SHORT).show();
                        }
                        else{

                            DatabaseReference df2=FirebaseDatabase.getInstance().getReference().child("Events").child(events).child(posts);
                            df2.child(key).setValue(hash);
                            Toast.makeText(getActivity(),"vote event has created successfully.",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

    }
    public void selectedtimeformat(int hour){
        if(hour==0){
            hour+=12;
            format="AM";}
        else if(hour==12){
            format="PM";
        }
        else if(hour>12){
            hour-=12;
            format="PM";
        }
        else{
            format="AM";
        }

    }
}
