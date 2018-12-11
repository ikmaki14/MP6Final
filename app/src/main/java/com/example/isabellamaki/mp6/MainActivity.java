package com.example.isabellamaki.mp6;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    protected ArrayList<String> subjectTitles = new ArrayList<>();
    protected ArrayList<String> assignmentTitles = new ArrayList<>();
    protected ArrayList<String> dueDates = new ArrayList<>();
    protected ArrayList<String> courseSites = new ArrayList<>();
    protected ArrayList<String> descriptions = new ArrayList<>();

    private static final String TAG = "MP6-AssignmentInput";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        restoreData(getIntent());

        Calendar calendar = Calendar.getInstance();
        String current_date = DateFormat.getDateInstance().format(calendar.getTime());

        TextView textViewDate = findViewById(R.id.text_view_date);
        textViewDate.setText(current_date);

        Intent incomingDate = getIntent();
        String date = incomingDate.getStringExtra("date");
        textViewDate.setText(date);

        ListView assignmentList = (ListView) findViewById(R.id.assignmentListView);
        //handle item clicks
        assignmentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "List item clicked @ position " + position);
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle data = saveData();
                //Focus on Intent: swapping to different activity
                Intent startIntent = new Intent(getApplicationContext(), AssignmentInput.class);
                startIntent.putExtras(data);
                //start nextAcitivity
                //startActivity(startIntent);
                startActivityForResult(startIntent, 1);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent menu_intent = new Intent(this, MainActivity.class);

            Bundle data = saveData();
            //Focus on Intent: swapping to different activity
            menu_intent.putExtras(data);
            //start nextAcitivity
            //startActivity(startIntent);
            //startActivityForResult(menu_intent, 2);

            startActivity(menu_intent);
            return true;
        }
        if (id == R.id.action_week) {
            Toast.makeText(this, "Week is Clicked", Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.action_month) {
            Intent month_intent = new Intent(this, Month.class);
            startActivity(month_intent);
            Toast.makeText(this, "Month is Clicked", Toast.LENGTH_LONG).show();
            return true;
        }

        if (id == R.id.action_map) {
            Intent map_intent = new Intent(this, FirstFragment.class);
            startActivity(map_intent);
            Toast.makeText(this, "Map is Clicked", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        //Save UI state changes to the savedInstanceState.
        //This bundle will be passed to onCreate if the process is killed & restarted.
        bundle.putStringArrayList("MySubjectTitles", subjectTitles);
        bundle.putStringArrayList("MyAssignmentTitles", assignmentTitles);
        bundle.putStringArrayList("MyDueDates", dueDates);
        bundle.putStringArrayList("MyCourseSites", courseSites);
        bundle.putStringArrayList("MyDescriptions", descriptions);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //Restore UI state from the savedInstanceState.
        //This bundle has also been passed to onCreate.
        subjectTitles = savedInstanceState.getStringArrayList("MySubjectTitles");
        assignmentTitles = savedInstanceState.getStringArrayList("MyAssignmentTitles");
        dueDates = savedInstanceState.getStringArrayList("MyDueDates");
        courseSites = savedInstanceState.getStringArrayList("MyCourseSites");;
        descriptions = savedInstanceState.getStringArrayList("MyDescriptions");

    }

    private Bundle saveData() {
        Log.d(TAG, "saveData() called. Saving data...");
        Bundle data = new Bundle();
        data.putStringArrayList("MySubjectTitles", subjectTitles);
        data.putStringArrayList("MyAssignmentTitles", assignmentTitles);
        data.putStringArrayList("MyDueDates", dueDates);
        data.putStringArrayList("MyCourseSites", courseSites);
        data.putStringArrayList("MyDescriptions", descriptions);
        return data;
    }

    private void restoreData(Intent intent) {
        Bundle data = intent.getExtras();
        subjectTitles = data.getStringArrayList("MySubjectTitles");
        assignmentTitles = data.getStringArrayList("MyAssignmentTitles");
        dueDates = data.getStringArrayList("MyDueDates");
        courseSites = data.getStringArrayList("MyCourseSites");;
        descriptions = data.getStringArrayList("MyDescriptions");
        ListView assignmentList = (ListView) findViewById(R.id.assignmentListView);
        MyAdapter arrayAdapter = new MyAdapter(this,
                subjectTitles,
                assignmentTitles,
                dueDates,
                courseSites,
                descriptions);
        assignmentList.setAdapter(arrayAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resultIntent) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //After coming back from AssigntmentInput Activity, update listView
                restoreData(resultIntent);
                /*ListView assignmentList = (ListView) findViewById(R.id.assignmentListView);
                MyAdapter arrayAdapter = new MyAdapter(this,
                        subjectTitles,
                        assignmentTitles,
                        dueDates,
                        courseSites,
                        descriptions,
                        tmpLogo);
                assignmentList.setAdapter(arrayAdapter);*/
            } else {
                restoreData(resultIntent);
            }
        }
        if (requestCode == 2) {
            restoreData(resultIntent);
        }
    }

    //create custom adapter class; defines what each row in the listView displays
    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        ArrayList<String> mySubjects;
        ArrayList<String> myAssignments;
        ArrayList<String> myDueDates;
        ArrayList<String> myCourseSites;
        ArrayList<String> myDescriptions;
        int images;

        MyAdapter(Context c, ArrayList<String> subjectTitles, ArrayList<String> assignmentTitles,
                  ArrayList<String> dueDates, ArrayList<String> courseSites,
                  ArrayList<String> descriptions) {
            super(c, R.layout.row, R.id.subjectTitle, subjectTitles); //R.id.subjectTextView, subjectTitles);
            this.context = c;
            this.mySubjects = subjectTitles;
            this.myAssignments = assignmentTitles;
            this.myDueDates = dueDates;
            this.myCourseSites = courseSites;
            this.myDescriptions = descriptions;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);

            ImageView logo = row.findViewById(R.id.logo);
            TextView mySubject = row.findViewById(R.id.subjectTitle);
            TextView myAssignment = row.findViewById(R.id.assignmentTitle);
            TextView myDueDate = row.findViewById(R.id.dueDate);
            TextView mySite = row.findViewById(R.id.courseSite);
            TextView myDescription = row.findViewById(R.id.description);

            mySubject.setText("Class: " + subjectTitles.get(position));
            myAssignment.setText(assignmentTitles.get(position));
            myDueDate.setText("[Due: " + dueDates.get(position) + "]");
            mySite.setText("Course Site: " + courseSites.get(position));
            myDescription.setText("Description: " + descriptions.get(position));

            //setContentView(row);
            return row;
        }
    }
}
