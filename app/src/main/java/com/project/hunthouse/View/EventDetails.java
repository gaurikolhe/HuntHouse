package com.project.hunthouse.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import com.project.hunthouse.R;



public class EventDetails extends AppCompatActivity {

    private TextView name, venue ,city, state, street, description, fee, host, date, time;
    private ImageView image,coverpic;
    private RatingBar ratingBar;
    private float rating;
    private DatabaseReference mRef;
    private HashMap<String, ?> eventDetails;
    private Button button, makePayment, photoBoxButton, commentButton;
    private HashMap<String, ?> register_check;
    String host_rating, uname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        uname = getIntent().getExtras().get("name").toString();
        name = (TextView) findViewById(R.id.event_name);
        city = (TextView) findViewById(R.id.event_city);
        street = (TextView) findViewById(R.id.event_street);
        fee = (TextView) findViewById(R.id.event_fee);
        host = (TextView) findViewById(R.id.event_host);
        description = (TextView) findViewById(R.id.event_description);
        state = (TextView) findViewById(R.id.event_state);
        venue = (TextView) findViewById(R.id.event_venue);
        date = (TextView) findViewById(R.id.event_date);
//        time = (TextView) findViewById(R.id.event_time);
//        image = (ImageView) findViewById(R.id.event_image);
        coverpic = (ImageView) findViewById(R.id.event_coverpic);
        button = (Button) findViewById(R.id.register_event);
        photoBoxButton = (Button) findViewById(R.id.photoBox);
        photoBoxButton.setVisibility(View.INVISIBLE);
        makePayment = (Button) findViewById(R.id.paypal);
        ratingBar = (RatingBar) findViewById(R.id.host_ratings);
        commentButton = (Button) findViewById(R.id.commentButton);
        commentButton.setVisibility(View.INVISIBLE);

        eventDetails = (HashMap<String, ?>) getIntent().getSerializableExtra("eventData");
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String currentDate = df.format(c.getTime());
        String eventdate = (String) eventDetails.get("date");

        if (currentDate.compareTo(eventdate) > 0) {
            button.setVisibility(View.INVISIBLE);
            makePayment.setVisibility(View.INVISIBLE);
            photoBoxButton.setVisibility(View.INVISIBLE);
            commentButton.setVisibility(View.INVISIBLE);
        }

        final String event_name = (String) eventDetails.get("name");
        String uid = (String) getIntent().getExtras().get("id");
        mRef = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("houses").getRef();
        if(mRef != null){
            mRef.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    HashMap<String,String> event = (HashMap<String,String>)dataSnapshot.getValue();
                    if(event!=null){
//                        String str = event.get(event_name);
                        if(event.get(event_name) != null){
                            button.setText("Unregister");
                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                            String currentDate = df.format(c.getTime());
                            String eventdate = (String) eventDetails.get("date");

                            if (currentDate.compareTo(eventdate) > 0) {
                                button.setVisibility(View.INVISIBLE);
                                makePayment.setVisibility(View.INVISIBLE);
                                photoBoxButton.setVisibility(View.VISIBLE);
                                commentButton.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        name.setText((String) eventDetails.get("name"));
        date.setText("Date : " + (String) eventDetails.get("date"));
//        time.setText("Time : " + (String) eventDetails.get("time"));
        city.setText("City : " + (String) eventDetails.get("city"));
        state.setText("State : " + (String) eventDetails.get("state"));
        venue.setText("House : " + (String) eventDetails.get("venue"));
        street.setText("Street : " + (String) eventDetails.get("street"));
        fee.setText("Rent : " + (String) eventDetails.get("fee") + "$");
        description.setText((String) eventDetails.get("description"));
        makePayment.setText("Pay : " + (String) eventDetails.get("fee") + "$");
        host.setText("Owner : " + (String) eventDetails.get("host_name"));
        host_rating = (String) eventDetails.get("host_rating");
        rating = Float.parseFloat(host_rating);
        ratingBar.setRating(rating);

//        Picasso.with(getApplicationContext()).load((String) eventDetails.get("image")).into(image);
        Picasso.with(getApplicationContext()).load((String) eventDetails.get("image")).into(coverpic);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String check = (String)button.getText();

                HashMap<String,Object> map = new HashMap<>();
                if(check.equals("Unregister")){
                    button.setText("Register");
                    mRef.child(event_name).removeValue();
                }
                else if(check.equals("Register")){
                    map.put(event_name,eventDetails);
                    button.setText("Unregister");
                    mRef.updateChildren(map);
                }
            }
        });
        makePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventDetails.this, PaypalActivity.class);
                startActivity(intent);
            }
        });

        photoBoxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventDetails.this, Gallery.class);
                intent.putExtra("name",(String) eventDetails.get("name"));
                startActivity(intent);
            }
        });

        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventDetails.this, CommentsActivity.class);
                intent.putExtra("name",(String) eventDetails.get("name"));
                intent.putExtra("uname", uname);
                startActivity(intent);
            }
        });

    }
}
