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

public class tenantSingleRoom extends AppCompatActivity implements View.OnClickListener{

    EditText startDate,endDate,startPrice,endPrice;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    Button cancel,search;
    Spinner location;
    Intent intent;
    String selectedLocation,selectedStartDate,selectedEndDate,selectedStartPrice,selectedEndPrice;
    Bundle bundle;
    int flag=-1;

    String uname,uid,url,uemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_single_room);

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
        location = (Spinner) findViewById(R.id.tsrLocationSpinner);

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

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch(id)
        {
            case R.id.startDateET:
                flag=0;
                new DatePickerDialog(tenantSingleRoom.this,date,myCalendar.get(Calendar.YEAR)
                        ,myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.endDateET:
                flag=1;
                new DatePickerDialog(tenantSingleRoom.this,date,myCalendar.get(Calendar.YEAR)
                        ,myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.cancelBtn:
                intent = new Intent(tenantSingleRoom.this,MainActivity.class);
                intent.putExtra("name",uname);
                intent.putExtra("id",uid);
                intent.putExtra("email",uemail);
                startActivity(intent);
                break;
            case R.id.searchBtn:

                selectedLocation = location.getSelectedItem().toString();
                selectedStartPrice = startPrice.getText().toString();
                selectedEndPrice = endPrice.getText().toString();
                selectedStartDate= startDate.getText().toString();
                selectedEndDate=endDate.getText().toString();

                bundle = new Bundle();

                bundle.putString("selectedLocation",selectedLocation);
                bundle.putString("selectedStartPrice",selectedStartPrice);
                bundle.putString("selectedEndPrice",selectedEndPrice);
                bundle.putString("selectedStartDate",selectedStartDate);
                bundle.putString("selectedEndDate",selectedEndDate);
                bundle.putBoolean("apartmentFlag",false);

//                intent = new Intent(tenantSingleRoom.this,TenantSearchResult.class);
//                intent.putExtras(bundle);

                intent = new Intent(tenantSingleRoom.this,Home.class);
                intent.putExtra("name",uname);
                intent.putExtra("id",uid);
                intent.putExtra("email",uemail);
                intent.putExtras(bundle);
                startActivity(intent);

                break;
        }
    }

    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        if(flag==0)
            startDate.setText(sdf.format(myCalendar.getTime()));
        else if(flag==1)
            endDate.setText(sdf.format(myCalendar.getTime()));

    }
}
