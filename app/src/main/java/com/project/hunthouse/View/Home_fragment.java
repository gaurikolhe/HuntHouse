package com.project.hunthouse.View;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.hunthouse.Controller.MyFirebaseRecylerAdapter;
import com.project.hunthouse.Model.Event;
import com.project.hunthouse.Model.EventData;

import java.util.HashMap;

import com.project.hunthouse.R;

//import com.venyou.venyou.R;


public class Home_fragment extends android.support.v4.app.Fragment {

    private static final String ARG_PARAM1 = "param1";

    private static RecyclerView recyclerView;

    DatabaseReference childRef;
    private static MyFirebaseRecylerAdapter myFirebaseRecylerAdapter;
    EventData eventData;

    private OnFragmentInteractionListener mListener;

    String location,startDate,endDate,startPrice,endPrice,bedRooms;
    boolean apartmentFlag=false;

    public Home_fragment() {
        // Required empty public constructor
    }
                                            //int param1,
    public static Home_fragment newInstance(String param2,String param3,String param4,String param5,String param6) {
        Home_fragment fragment = new Home_fragment();
        Bundle args = new Bundle();
        //args.putInt(ARG_PARAM1, param1);
        args.putString("selectedLocation", param2);
        args.putString("selectedStartPrice",param3);
        args.putString("selectedEndPrice",param4);
        args.putString("selectedStartDate",param5);
        args.putString("selectedEndDate",param6);
        args.putBoolean("apartmentFlag",false);
        fragment.setArguments(args);
        return fragment;
    }
                                            //int param1,
    public static Home_fragment newInstance(String param2,String param3,String param4,String param5,String param6,String param7) {
        Home_fragment fragment = new Home_fragment();
        Bundle args = new Bundle();
        //args.putInt(ARG_PARAM1, param1);
        args.putString("selectedLocation", param2);
        args.putString("selectedStartPrice",param3);
        args.putString("selectedEndPrice",param4);
        args.putString("selectedStartDate",param5);
        args.putString("selectedEndDate",param6);
        args.putBoolean("apartmentFlag",true);
        args.putString("selectedBedRooms",param7);
        fragment.setArguments(args);
        return fragment;
    }

    public interface InterfaceEventData{
        void DisplayEventData(int position, HashMap<String, ?> eventDetails, View view, String name);
        void onClickAddEvent();
    }

    InterfaceEventData interfaceEventData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            location = getArguments().getString("selectedLocation");
            startDate = getArguments().getString("selectedStartDate");
            endDate = getArguments().getString("selectedEndDate");
            startPrice = getArguments().getString("selectedStartPrice");
            endPrice = getArguments().getString("selectedEndPrice");
            if(getArguments().getBoolean("apartmentFlag"))
            {
                apartmentFlag=true;
                bedRooms = getArguments().getString("selectedBedRooms");
                Log.d("Home_Fragment:","BedRooms="+bedRooms);
            }

            Log.d("Home_Fragment:","Location from activity to fragment:"+location);
            Log.d("Home_Fragment:","StartDate="+startDate);
            Log.d("Home_Fragment:","EndDate="+endDate);
            Log.d("Home_Fragment:","StartPrice="+startPrice);
            Log.d("Home_Fragment:","EndPrice="+endPrice);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.home_fragment, container, false);
        Log.d("Inside Home_fragment:","before firebase instance\tLocation="+location);
        childRef =  FirebaseDatabase.getInstance().getReference().child("houses").getRef(); //.child(location).

        interfaceEventData = (InterfaceEventData) view.getContext();
        myFirebaseRecylerAdapter = new MyFirebaseRecylerAdapter(Event.class, R.layout.home_fragment_cardview,
                MyFirebaseRecylerAdapter.EventViewHolder.class, childRef, getContext());

        eventData = new EventData();
        recyclerView = (RecyclerView) view.findViewById(R.id.recviewer1);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(myFirebaseRecylerAdapter);

        if (eventData.getSize() == 0) {
            eventData.setAdapter(myFirebaseRecylerAdapter);
            eventData.setContext(getActivity());
            eventData.initializeDataFromCloud();
        }
        setHasOptionsMenu(true);

        myFirebaseRecylerAdapter.SetOnItemClickListner(new MyFirebaseRecylerAdapter.RecyclerItemClickListener(){

            @Override
            public void onItemClick(View view, int position, String name) {
                if(eventData != null){
                    final HashMap<String, ?> eventDetails = (HashMap<String, ?>) eventData.getItem(name);
                    normalClick(position, eventDetails, view,name);
                }
            }
        });

        return view;

    }

    public void normalClick(int pos, HashMap<String, ?> eventDetails, View view, String name){
        interfaceEventData.DisplayEventData(pos, eventDetails, view, name);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

//    Interface
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
