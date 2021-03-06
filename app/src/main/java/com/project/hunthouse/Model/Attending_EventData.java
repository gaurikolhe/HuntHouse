package com.project.hunthouse.Model;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.hunthouse.Controller.Attending_FirebaseRecylerAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gaurikolhe on 11/21/17.
 */

public class Attending_EventData implements Serializable{

    ArrayList<Map<String,?>> eventlist;
    DatabaseReference mRef,eRef;
    Attending_FirebaseRecylerAdapter myFirebaseRecylerAdapter;
    Context mContext;
    private String check;
    public void setAdapter(Attending_FirebaseRecylerAdapter mAdapter) {
        myFirebaseRecylerAdapter = mAdapter;
    }

    public DatabaseReference getFireBaseRef(){
        return mRef;
    }

    public void setContext(Context context){
        mContext = context;
    }

    public List<Map<String, ?>> getworkoutList() {
        return eventlist;
    }

    public int getSize(){
        return eventlist.size();
    }

    public HashMap getItem(String s){
        for(int i=0;i<eventlist.size();i++){
            HashMap event = (HashMap)eventlist.get(i);
            String name = (String)event.get("name");
            if (name.equals(s)){
                return event;
                }
        }
       return null;
    }


    public void onItemRemovedFromCloud(HashMap item){
        int position = -1;
        String id=(String)item.get("id");
        for(int i=0;i<eventlist.size();i++){
            HashMap event = (HashMap)eventlist.get(i);
            String mid = (String)event.get("id");
            if(mid.equals(id)){
                position= i;
                break;
            }
        }
        if(position != -1){
            eventlist.remove(position);
        }
    }

    public void onItemAddedToCloud(final HashMap item){
//        final int insertPosition = 0;
//        final String id= item.get("id").toString();
//        for(int i=0;i<eventlist.size();i++){
//            HashMap event = (HashMap)eventlist.get(i);
//        }
        eventlist.add(item);

    }

    public void onItemUpdatedToCloud(HashMap item){
        String id=(String)item.get("id");
        for(int i=0;i<eventlist.size();i++){
            HashMap event = (HashMap)eventlist.get(i);
            String mid = (String)event.get("id");
            if(mid.equals(id)){
                eventlist.remove(i);
                eventlist.add(i,item);
                break;
            }
        }

    }

    public void initializeDataFromCloud() {
        eventlist.clear();
        mRef.addChildEventListener(new com.google.firebase.database.ChildEventListener() {
            @Override
            public void onChildAdded(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                Log.d("MyTest: OnChildAdded", dataSnapshot.toString());
                HashMap<String,String> event = (HashMap<String,String>)dataSnapshot.getValue();
                onItemAddedToCloud(event);
            }

            @Override
            public void onChildChanged(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                Log.d("MyTest: OnChildChanged", dataSnapshot.toString());
                HashMap<String,String> event = (HashMap<String,String>)dataSnapshot.getValue();
                onItemUpdatedToCloud(event);
            }

            @Override
            public void onChildRemoved(com.google.firebase.database.DataSnapshot dataSnapshot) {
                Log.d("MyTest: OnChildRemoved", dataSnapshot.toString());
                HashMap<String,String> event = (HashMap<String,String>)dataSnapshot.getValue();
                onItemRemovedFromCloud(event);
            }

            @Override
            public void onChildMoved(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public Attending_EventData(String uid){
        eventlist = new ArrayList<Map<String,?>>();
//        eRef = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("events").getRef();
        mRef = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("houses").getRef();
        myFirebaseRecylerAdapter = null;
        mContext = null;
    }

}
