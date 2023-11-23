package com.example.cyberneticsjobportal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cyberneticsjobportal.Model.Data;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostJobActivity extends AppCompatActivity {

    //Elements

    private com.google.android.material.floatingactionbutton.FloatingActionButton fabBtn;

    //Recycler View

    private RecyclerView recyclerView;

    //Firebase

    private FirebaseAuth mAuth;
    private DatabaseReference JobPostDatabase;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);

        getSupportActionBar().setTitle("My Jobs");



        fabBtn=findViewById(R.id.fab_add);

        mAuth=FirebaseAuth.getInstance();

        FirebaseUser mUser=mAuth.getCurrentUser();
        String uId=mUser.getUid();

        JobPostDatabase= FirebaseDatabase.getInstance().getReference().child("Job Post").child(uId);

        recyclerView=findViewById(R.id.recycler_job_post_id);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);



        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), InsertJobPostActivity.class));

            }
        });


    }




    //FirebaseRecyclerOptions initialization
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Data> firebaseRecyclerOptions =
                new FirebaseRecyclerOptions.Builder<Data>()
                .setQuery(JobPostDatabase,Data.class)
                .build();

        //FirebaseRecyclerAdapter used to store Database items in local recyclerview located on job_post_item
        FirebaseRecyclerAdapter<Data,MyViewHolder>adapter=new FirebaseRecyclerAdapter<Data, MyViewHolder>(firebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Data model) {

                holder.setJobPosition(model.getPosition());
                holder.setJobDate(model.getDate());
                holder.setJobTitle(model.getTitle());
                holder.setJobDescription(model.getDescription());
                holder.setJobSkills(model.getSkills());
                holder.setJobArea(model.getArea());
                holder.setJobSalary(model.getSalary());

            }

            //onCreateViewHolder used to display items from RecyclerAdapter in local recyclerview located on job_post_item
            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_post_item, parent, false);
                return new MyViewHolder(view);

            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    //Initialization of variables housed in Data to be stored in 'MyViewHolder' viewholder for use in FireBaseRecyclerAdapter
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        View myView;

        //myView initialization
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myView=itemView;
        }

        //JobPosition initialization
        public void setJobPosition(String position){

            TextView mPosition=myView.findViewById(R.id.job_position);
            mPosition.setText(position);

        }

        //JobDate (posted date) initialization
        public void setJobDate(String date){

            TextView mDate=myView.findViewById(R.id.job_date);
            mDate.setText(date);

        }

        //JobTitle (company name) initialization
        public void setJobTitle(String title){

            TextView mTitle=myView.findViewById(R.id.job_title);
            mTitle.setText(title);

        }

        //JobDescription initialization
        public void setJobDescription(String description){

            TextView mDescription=myView.findViewById(R.id.job_description);
            mDescription.setText(description);

        }

        //Skills Required initialization
        public void setJobSkills(String skills){

            TextView mSkills=myView.findViewById(R.id.job_skills);
            mSkills.setText(skills);

        }

        //JobArea (location) initialization
        public void setJobArea(String area){

            TextView mArea=myView.findViewById(R.id.job_area);
            mArea.setText(area);

        }

        //JobSalary initialization
        public void setJobSalary(String salary){

            TextView mSalary=myView.findViewById(R.id.job_salary);
            mSalary.setText(salary);

        }




    }

}