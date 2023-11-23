package com.example.cyberneticsjobportal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cyberneticsjobportal.Model.Data;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

public class InsertJobPostActivity extends AppCompatActivity {

    //Elements

    private android.widget.EditText job_title;
    private android.widget.EditText job_position;
    private android.widget.EditText job_description;
    private android.widget.EditText job_skills;
    private android.widget.EditText job_area;
    private android.widget.EditText job_salary;

    private android.widget.Button btn_post_job;

    //Firebase

    private FirebaseAuth mAuth;
    private DatabaseReference mJobPost;

    private DatabaseReference mPublicDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_job_post);

        getSupportActionBar().setTitle("Post A Job");

        // firebase authentication
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser mUser=mAuth.getCurrentUser();
        String uId=mUser.getUid();

        mJobPost= FirebaseDatabase.getInstance().getReference().child("Job Post").child(uId);

        mPublicDatabase=FirebaseDatabase.getInstance().getReference().child("Public database");

        InsertJob();

    }

    // insert job into database using edittext elements on activity_insert_job_post
    private void InsertJob(){

        job_title=findViewById(R.id.job_title);
        job_position=findViewById(R.id.job_position);
        job_description=findViewById(R.id.job_description);
        job_skills=findViewById(R.id.job_skills);
        job_area=findViewById(R.id.job_area);
        job_salary=findViewById(R.id.job_salary);

        btn_post_job=findViewById(R.id.btn_job_post);

        // button to post job to database when all required fields have been filled
        btn_post_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title=job_title.getText().toString().trim();
                String position=job_position.getText().toString().trim();
                String description=job_description.getText().toString().trim();
                String skills=job_skills.getText().toString().trim();
                String area=job_area.getText().toString().trim();
                String salary=job_salary.getText().toString().trim();

                if (TextUtils.isEmpty(title)){
                    job_title.setError("*Required Field");
                    return;
                }
                if (TextUtils.isEmpty(position)){
                    job_position.setError("*Required Field");
                    return;
                }
                if (TextUtils.isEmpty(description)){
                    job_description.setError("*Required Field");
                    return;
                }
                if (TextUtils.isEmpty(skills)){
                    job_skills.setError("*Required Field");
                    return;
                }
                if (TextUtils.isEmpty(area)){
                    job_area.setError("*Required Field");
                    return;
                }
                if (TextUtils.isEmpty(salary)){
                    job_salary.setError("*Required Field");
                    return;
                }

                String id=mJobPost.push().getKey();

                String date= DateFormat.getDateInstance().format(new Date());

                Data data=new Data(title,position,description,skills,area,salary,id,date);

                mJobPost.child(id).setValue(data);

                mPublicDatabase.child(id).setValue(data);

                Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getApplicationContext(),PostJobActivity.class));

            }
        });

    }

}