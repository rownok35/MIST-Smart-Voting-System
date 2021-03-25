package com.example.msvs5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class feedback extends AppCompatActivity {
    private DatabaseReference df1;
    ListView feedbacklist;
    //ArrayList<FeedbackAdmin> user=new ArrayList<>();
    FirebaseListAdapter adapter1;
    String barcoderesultid;
    TextView feedtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        feedtext=findViewById(R.id.feedtext);


        try {
            Class<?> cls=Class.forName("com.example.msvs3.MainActivity");
            Field field=cls.getDeclaredField("barcodeID");
            field.setAccessible(true);
            Object object=field.get(new MainActivity());
            barcoderesultid=(String)object;
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        df1= FirebaseDatabase.getInstance().getReference("feedback");
        Query query=df1.orderByChild("barcodeid").equalTo(barcoderesultid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    String a = "hey there, "+ds.child("name").getValue(String.class)+". " + ds.child("feed").getValue(String.class);
                    feedtext.setText(a);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        /*feedbacklist=findViewById(R.id.feedbacklist);
        Query query=df1.orderByChild("barcodeid").equalTo(barcoderesultid);
        FirebaseListOptions<DataSetFire> options=new FirebaseListOptions.Builder<DataSetFire>()
                .setLayout(R.layout.rowforfeedback)
                .setQuery(df1,DataSetFire.class)
                .build();
        adapter1=new FirebaseListAdapter(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Object model, int position) {
                DataSetFire fd=(DataSetFire) model;
                TextView feed=findViewById(R.id.feedtxt);
                feed.setText(fd.getFeed().toString());

            }
        };
        feedbacklist.setAdapter(adapter1);*/

    }

}
