package com.example.tristam.studentjobsearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class SearchJobs extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mToggle;
    private FirebaseAuth firebaseAuth;

    //
    private DatabaseReference jobDB;

    //
    private EditText searchValue;
    private Button searchButton;
    public ArrayList<String> list = new ArrayList<>();

    //
    public String region="";
    public String category= "";
    public String type="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_jobs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Jobs Database
        //jobDB = FirebaseDatabase.getInstance().getReference("Jobs");

        //get value from editText(search)
        searchValue = findViewById(R.id.editText);
        searchButton = findViewById(R.id.searchButton);
        //Search list
        list.add("qwer001");
        list.add("qwer002");
        list.add("001asdf");
        list.add("002asdf");

        //Spinners
        Spinner regionSpinner = findViewById(R.id.regionSpinner);
        Spinner categorySpinner = findViewById(R.id.categorySpinner);
        Spinner jobTypeSpinner = findViewById(R.id.jobTypeSpinner);

        ArrayAdapter<CharSequence> regionAdapter = ArrayAdapter.createFromResource(this,R.array.regions, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this,R.array.categories, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> jobTypeAdapter = ArrayAdapter.createFromResource(this,R.array.jobType, android.R.layout.simple_spinner_item);

        regionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regionSpinner.setAdapter(regionAdapter);
        regionSpinner.setOnItemSelectedListener(this);

        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);
        categorySpinner.setOnItemSelectedListener(this);

        jobTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jobTypeSpinner.setAdapter(jobTypeAdapter);
        jobTypeSpinner.setOnItemSelectedListener(this);


        firebaseAuth = FirebaseAuth.getInstance();

        drawerLayout = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startSearch();
                //firebaseSearch();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        switch(item.getItemId()){
            case R.id.logoutMenu:
                Logout();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_jobs, menu);
        return true;
    }

    private void startSearch() {
        String search = searchValue.getText().toString();

        if (TextUtils.isEmpty(search)) {
            Toast.makeText(SearchJobs.this, "Please enter keyword to search", Toast.LENGTH_SHORT).show();
        } else if (list.contains(search)) {         //result found
            Toast.makeText(SearchJobs.this, search + "found.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(SearchJobs.this,  "No searching result for " + search, Toast.LENGTH_SHORT).show();
        }
    }

//    private void firebaseSearch(){
//        Jobs.class;
//        jobDB;
//
//
//    }

    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(SearchJobs.this, Login.class));
    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i > 0) {
            switch (adapterView.getId()) {
                case R.id.regionSpinner:
                    region = adapterView.getItemAtPosition(i).toString();
                    break;
                case R.id.categorySpinner:
                    category = adapterView.getItemAtPosition(i).toString();
                    break;
                case R.id.jobTypeSpinner:
                    type = adapterView.getItemAtPosition(i).toString();
                    break;
            }
        } else {
            switch (adapterView.getId()) {
                case R.id.regionSpinner:
                    region = "";
                    break;
                case R.id.categorySpinner:
                    category = "";
                    break;
                case R.id.jobTypeSpinner:
                    type = "";
                    break;
            }
        }
        //testing output for spinners
        //Toast.makeText(adapterView.getContext(), "Region: " + region + "\nCategory: " + category + "\nJob Type: " + type , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
