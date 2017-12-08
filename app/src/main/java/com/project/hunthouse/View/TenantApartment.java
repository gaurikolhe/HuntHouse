package com.project.hunthouse.View;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.project.hunthouse.R;

import java.util.Locale;

public class TenantApartment extends AppCompatActivity implements View.OnClickListener{

    EditText startDate,endDate,startPrice,endPrice,location;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    Button cancel,search;
    Spinner bedRooms;
    Intent intent;
    String selectedLocation,selectedStartDate,selectedEndDate,selectedStartPrice,selectedEndPrice,selectedBedRooms;
    Bundle bundle;
    int flag=-1;

    String uname,uid,url,uemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_apartment);

        uname = getIntent().getExtras().getString("name");
        uemail = getIntent().getExtras().getString("email");
        uid = getIntent().getExtras().getString("id");

        myCalendar = Calendar.getInstance();

        startDate = (EditText) findViewById(R.id.startDateET);
        endDate   = (EditText) findViewById(R.id.endDateET);
        startPrice= (EditText) findViewById(R.id.rentStartET);
        endPrice  = (EditText) findViewById(R.id.rentEndEt);
        cancel    = (Button) findViewById(R.id.cancelBtn);
        search    = (Button) findViewById(R.id.searchBtn);
        location = (EditText) findViewById(R.id.taLocationSpinner);
        bedRooms = (Spinner) findViewById(R.id.taBedroomSpinner);

        startDate.setOnClickListener(this);
        endDate.setOnClickListener(this);
        cancel.setOnClickListener(this);
        search.setOnClickListener(this);

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();

            }

        };
    }

    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        if(flag==0)
            startDate.setText(sdf.format(myCalendar.getTime()));
        else if(flag==1)
            endDate.setText(sdf.format(myCalendar.getTime()));

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch(id) {
            case R.id.startDateET:
                flag = 0;
                new DatePickerDialog(TenantApartment.this, date, myCalendar.get(Calendar.YEAR)
                        , myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.endDateET:
                flag = 1;
                new DatePickerDialog(TenantApartment.this, date, myCalendar.get(Calendar.YEAR)
                        , myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.cancelBtn:
                intent = new Intent(TenantApartment.this, MainActivity.class);
                intent.putExtra("name",uname);
                intent.putExtra("id",uid);
                intent.putExtra("email",uemail);
                startActivity(intent);
                break;
            case R.id.searchBtn:

                selectedLocation  = location.getText().toString();
                selectedStartPrice = startPrice.getText().toString();
                selectedEndPrice = endPrice.getText().toString();
                selectedStartDate = startDate.getText().toString();
                selectedEndDate = endDate.getText().toString();
                selectedBedRooms = bedRooms.getSelectedItem().toString();

                bundle = new Bundle();

                bundle.putString("selectedLocation",selectedLocation);
                bundle.putString("selectedStartPrice",selectedStartPrice);
                bundle.putString("selectedEndPrice",selectedEndPrice);
                bundle.putString("selectedStartDate",selectedStartDate);
                bundle.putString("selectedEndDate",selectedEndDate);
                bundle.putString("selectedBedRooms",selectedBedRooms);
                bundle.putBoolean("apartmentFlag",true);

//                intent = new Intent(TenantApartment.this,TenantSearchResult.class);
//                intent.putExtras(bundle);

                intent = new Intent(TenantApartment.this,Home.class);
                intent.putExtra("name",uname);
                intent.putExtra("id",uid);
                intent.putExtra("email",uemail);
                intent.putExtras(bundle);
                startActivity(intent);

                break;
        }

    }
}
