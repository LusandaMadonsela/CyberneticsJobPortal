package com.example.cyberneticsjobportal;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cyberneticsjobportal.Model.Data;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class AllJobActivity extends AppCompatActivity {

    //Recycler

    private RecyclerView recyclerView;

    //Firebase

    private DatabaseReference mAllJobPost;
    private DatabaseReference favouriteref, fvrtref, fvrt_listref;
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();

    //Sort

    LinearLayoutManager mLayoutManager;
    SharedPreferences mSharedPref;

    //Dialog

    private ProgressDialog mDialog;

    //Elements

    ImageButton fvrt_btn;
    Boolean fvrtChecker = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_job);

        getSupportActionBar().setTitle("All Jobs");



        //Date Sort Function
        mSharedPref = getSharedPreferences("SortSettings", MODE_PRIVATE);
        String mSorting =mSharedPref.getString("Sort","newest");



        if (mSorting.equals("newest")){
            mLayoutManager = new LinearLayoutManager(this);
            mLayoutManager.setReverseLayout(true);
            mLayoutManager.setStackFromEnd(true);
        }
        else if (mSorting.equals("oldest")){
            mLayoutManager = new LinearLayoutManager(this);
            mLayoutManager.setReverseLayout(false);
            mLayoutManager.setStackFromEnd(false);
        }

        //Database initialization

        mAllJobPost= FirebaseDatabase.getInstance().getReference().child("Public database");
        mAllJobPost.keepSynced(true);

        recyclerView=findViewById(R.id.recycler_all_job);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu,menu);
        //Hide logout
        menu.findItem(R.id.logout).setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }

    // Sort procedures for alljobpost activity
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int sortid = item.getItemId();

        // Sort by date
        if (sortid == R.id.action_sort){
            //Display alert dialog to choose sorting
            showSortDialog();
            return true;

        }
        // Sort by location
        else if (sortid == R.id.action_location){
            //Display alert dialog to choose sorting
            showLocationDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showSortDialog() {
        //options to display in dialog
        String[] sortOptions = {"Newest","Oldest"};
        //create alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sort by") //set title
                .setIcon(R.drawable.sort) //set icon
                .setItems(sortOptions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (i==0){
                            //sort by newest
                            SharedPreferences.Editor editor = mSharedPref.edit();
                            editor.putString("Sort", "newest"); //where 'Sort' is key & 'newest' is value
                            editor.apply(); //save in shared preferences
                            recreate(); //restart activity to take effect

                        }
                        else if (i==1){{
                            //sort by oldest
                            SharedPreferences.Editor editor = mSharedPref.edit();
                            editor.putString("Sort", "oldest"); //where 'Sort' is key & 'oldest' is value
                            editor.apply(); //save in shared preferences
                            recreate(); //restart activity to take effect

                        }}

                    }
                });
        builder.show();
    }

    // Dialog Box requesting access to devices location services
    private void showLocationDialog(){
        AlertDialog.Builder builder
                = new AlertDialog.Builder(this);

        builder.setMessage("Allow 'Cybernetics Job Portal App' to access Google's location service?");
        builder.setTitle("Enable Location");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            // If permission is granted
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                SharedPreferences.Editor editor = mSharedPref.edit();
                Query firebaseSearchQuery = mAllJobPost.orderByChild("area").startAt("Durban North").endAt("Pinetown"); //where 'area' is key & location is our parameter
                editor.apply(); //save in shared preferences
                recreate(); //restart activity to take effect

            }
        });
        builder.setNegativeButton("No, thanks", new DialogInterface.OnClickListener() {
            // If permission is denied
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    @Override
    protected void onStart() {
        super.onStart();

        //FirebaseRecyclerOptions to initialize FirebaseRecyclerAdapter
        FirebaseRecyclerOptions<Data> firebaseRecyclerOptions=
                new FirebaseRecyclerOptions.Builder<Data>()
                .setQuery(mAllJobPost,Data.class)
                .build();

        //FirebaseRecyclerAdapter to store items from mAllJobPost (public) database
        FirebaseRecyclerAdapter<Data,AllJobPostViewHolder>adapter=new FirebaseRecyclerAdapter<Data, AllJobPostViewHolder>(firebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull AllJobPostViewHolder holder, int position, @NonNull Data model) {

                holder.setJobPosition(model.getPosition());
                holder.setJobDate(model.getDate());
                holder.setJobTitle(model.getTitle());
                holder.setJobDescription(model.getDescription());
                holder.setJobSkills(model.getSkills());
                holder.setJobArea(model.getArea());
                holder.setJobSalary(model.getSalary());

                holder.myview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent=new Intent(getApplicationContext(),JobDetailsActivity.class);

                        intent.putExtra("position",model.getPosition());
                        intent.putExtra("date",model.getDate());
                        intent.putExtra("title",model.getTitle());
                        intent.putExtra("description",model.getDescription());
                        intent.putExtra("skills",model.getSkills());
                        intent.putExtra("area",model.getArea());
                        intent.putExtra("salary",model.getSalary());

                        startActivity(intent);

                    }

                });

            }

            @NonNull
            @Override
            public AllJobPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alljobpost,parent,false);
                return new AllJobPostViewHolder(view);


            }

        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    // AllJobPostViewHolder displays FirebaseRecyclerAdapter items from activity_all_job recyclerview on alljobpost activity CardViews
    public static class AllJobPostViewHolder extends RecyclerView.ViewHolder{

        View myview;


        public AllJobPostViewHolder(@NonNull View itemView) {
            super(itemView);
            myview=itemView;
        }

        public void setJobPosition(String position){

            TextView mPosition=myview.findViewById(R.id.all_job_post_position);
            mPosition.setText(position);

        }

        public void setJobDate(String date){

            TextView mDate=myview.findViewById(R.id.all_job_post_date);
            mDate.setText(date);

        }

        public void setJobTitle(String title){

            TextView mTitle=myview.findViewById(R.id.all_job_post_title);
            mTitle.setText(title);

        }

        public void setJobDescription(String description){

            TextView mDescription=myview.findViewById(R.id.all_job_post_description);
            mDescription.setText(description);

        }

        public void setJobSkills(String skills){

            TextView mSkills=myview.findViewById(R.id.all_job_post_skills);
            mSkills.setText(skills);

        }

        public void setJobArea(String area){

            TextView mArea=myview.findViewById(R.id.all_job_post_area);
            mArea.setText(area);

        }

        public void setJobSalary(String salary){

            TextView mSalary=myview.findViewById(R.id.all_job_post_salary);
            mSalary.setText(salary);

        }

    }
}