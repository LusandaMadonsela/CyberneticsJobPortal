package com.example.cyberneticsjobportal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class JobDetailsActivity extends AppCompatActivity {

    //Elements

    private TextView mTitle;
    private TextView mDate;
    private TextView mPosition;
    private TextView mDescription;
    private TextView mSkills;
    private TextView mArea;
    private TextView mSalary;

    //Expands CardView OnClick to display full Job Details and specifications
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        getSupportActionBar().setTitle("Job Details");


        mTitle=findViewById(R.id.job_details_title);
        mDate=findViewById(R.id.job_details_date);
        mPosition=findViewById(R.id.job_details_position);
        mDescription=findViewById(R.id.job_details_description);
        mSkills=findViewById(R.id.job_details_skills);
        mArea=findViewById(R.id.job_details_area);
        mSalary=findViewById(R.id.job_details_salary);

        //Receive data from AllJobActivity using intent

        Intent intent=getIntent();

        String title=intent.getStringExtra("title");
        String date=intent.getStringExtra("date");
        String position=intent.getStringExtra("position");
        String description=intent.getStringExtra("description");
        String skills=intent.getStringExtra("skills");
        String area=intent.getStringExtra("area");
        String salary=intent.getStringExtra("salary");

        mTitle.setText(title);
        mDate.setText(date);
        mPosition.setText(position);
        mDescription.setText(description);
        mSkills.setText(skills);
        mArea.setText(area);
        mSalary.setText(salary);


    }
}