package com.project.hunthouse.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.project.hunthouse.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    Switch tenantOwner;
    Button single_room,apartment;
    Intent intent;
    String uname,uid,url,uemail;
    private int navigation_logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,R.string.open_drawer,R.string.close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        uname = getIntent().getExtras().getString("name");
        uemail = getIntent().getExtras().getString("email");
        uid = getIntent().getExtras().getString("id");
        View view = (View)navigationView.getHeaderView(0);
        TextView textView_name = (TextView) view.findViewById(R.id.textView_name);
        textView_name.setText(uname);
        //propic = (ImageView) view.findViewById(R.id.imageView);







        tenantOwner = (Switch) findViewById(R.id.tenant_owner_switch);


        single_room = (Button) findViewById(R.id.single_roomBtn);
        single_room.setOnClickListener(this);

        apartment = (Button) findViewById(R.id.apartmentBtn);
        apartment.setOnClickListener(this);



    }
    /* Code of First Navigation drawer
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.navigation_profile) {
            Toast.makeText(MainActivity.this,"Write Code for User Profile", Toast.LENGTH_LONG).show();
            //Intent intent = new Intent(getApplicationContext(), MyProfile.class);
            //intent.putExtra("name",uname);
            //intent.putExtra("id",uid);
            //intent.putExtra("url",url);
            //intent.putExtra("email",uemail);
            //startActivity(intent);

        } else if (id == R.id.navigation_history) {

            Toast.makeText(MainActivity.this,"Write Code for User History", Toast.LENGTH_LONG).show();

        } else if (id == R.id.navigation_help) {
            Toast.makeText(MainActivity.this,"Write Code for Navigation History", Toast.LENGTH_LONG).show();


        } else if (id == R.id.navigation_logout) {
            Toast.makeText(MainActivity.this,"Logging out of Application", Toast.LENGTH_LONG).show();
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signOut();
            Intent inte = new Intent(this,LoginActivity.class);
            startActivity(inte);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    */

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.map) {
            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            startActivity(intent);
        } else if (id == R.id.add_event) {
            Intent intent = new Intent(MainActivity.this,AddEvent.class);
            intent.putExtra("name",uname);
            intent.putExtra("id",uid);
            intent.putExtra("email",uemail);
            startActivity(intent);
        }else if (id == R.id.chatbot) {
            Intent intent = new Intent(MainActivity.this,ChatActivity.class);
            startActivity(intent);
        }else if (id == R.id.profile) {
            Intent intent = new Intent(getApplicationContext(), MyProfile.class);
            intent.putExtra("name",uname);
            intent.putExtra("id",uid);
            intent.putExtra("url",url);
            intent.putExtra("email",uemail);
            startActivity(intent);
        } else if (id == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this,LoginActivity.class); startActivity(intent);
        }


        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        Boolean switchValue = tenantOwner.isChecked();
        //Toast.makeText(this,"Value of switch :-"+switchValue,Toast.LENGTH_LONG).show();

        if(switchValue)     //This means that, this is Owner
        {
            switch(id)
            {
                case R.id.single_roomBtn:
                    Toast.makeText(this,"This is Owner with single room", Toast.LENGTH_LONG).show();
//                    intent = new Intent(MainActivity.this,OwnerSingleRoom.class);
//                    startActivity(intent);
                    break;
                case R.id.apartmentBtn:
                    Toast.makeText(this,"This is Owner with apartment", Toast.LENGTH_LONG).show();
//                    intent = new Intent(MainActivity.this,OwnerApartment.class);
//                    startActivity(intent);
                    break;
            }
        }
        else                //This means that, this is tenant
        {
            switch(id)
            {
                case R.id.single_roomBtn:
                    //Toast.makeText(this,"This is Tenant with single room",Toast.LENGTH_LONG).show();
                    intent = new Intent(MainActivity.this,tenantSingleRoom.class);
                    intent.putExtra("name",uname);
                    intent.putExtra("id",uid);
                    intent.putExtra("email",uemail);
                    startActivity(intent);
                    break;
                case R.id.apartmentBtn:
                    Toast.makeText(this,"This is Tenant with apartment", Toast.LENGTH_LONG).show();
                    intent = new Intent(MainActivity.this,TenantApartment.class);
                    intent.putExtra("name",uname);
                    intent.putExtra("id",uid);
                    intent.putExtra("email",uemail);
                    startActivity(intent);
                    break;
            }
        }
    }
}