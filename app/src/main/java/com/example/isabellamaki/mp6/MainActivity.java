package com.example.isabellamaki.mp6;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.ImageButton;
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
    //protected int tmpLogo = R.drawable.tmp_logo;
    //protected String displayDate = "";

    private static final String TAG = "MP6-AssignmentInput";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent().hasExtra("MySubjectTitles")
        && getIntent().getStringArrayListExtra("MySubjectTitles").size() != 0) {
            restoreData(getIntent());
            ListView assignmentList = findViewById(R.id.assignmentListView);
            MyAdapter arrayAdapter = new MyAdapter(this,
                    subjectTitles,
                    assignmentTitles,
                    dueDates,
                    courseSites,
                    descriptions);
            assignmentList.setAdapter(arrayAdapter);
        }
        setDate(getIntent());
 /*       Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

        TextView textViewDate = findViewById(R.id.text_view_date);
        textViewDate.setText(currentDate);

        Intent incomingDate = getIntent();
        String date = incomingDate.getStringExtra("date");
        textViewDate.setText(date);*/

        ListView assignmentList = findViewById(R.id.assignmentListView);
        //handle item clicks
        assignmentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "List item clicked @ position " + position);
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
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
            //start next Activity
            startActivity(menu_intent);
            return true;
        }
        if (id == R.id.action_month) {
            Intent month_intent = new Intent(this, com.example.isabellamaki.mp6.Month.class);
            Bundle data = saveData();
            month_intent.putExtras(data);
            startActivityForResult(month_intent, 2);
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
        courseSites = savedInstanceState.getStringArrayList("MyCourseSites");
        descriptions = savedInstanceState.getStringArrayList("MyDescriptions");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveData();
    }

    private Bundle saveData() {
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
        courseSites = data.getStringArrayList("MyCourseSites");
        descriptions = data.getStringArrayList("MyDescriptions");

    }

    private void setDate(Intent intent) {
        TextView textViewDate = findViewById(R.id.text_view_date);

        if (intent.hasExtra("date")) {
            String date = intent.getStringExtra("date");
            textViewDate.setText(date);
        } else {
            Calendar calendar = Calendar.getInstance();
            String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

            textViewDate.setText(currentDate);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resultIntent) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //After coming back from AssigntmentInput Activity, update listView
                restoreData(resultIntent);
                setDate(resultIntent);
                ListView assignmentList = findViewById(R.id.assignmentListView);
                MyAdapter arrayAdapter = new MyAdapter(this,
                        subjectTitles,
                        assignmentTitles,
                        dueDates,
                        courseSites,
                        descriptions);
                assignmentList.setAdapter(arrayAdapter);
            } else {
                restoreData(resultIntent);
            }
        }
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                setDate(resultIntent);
                restoreData(resultIntent);
                ListView assignmentList = findViewById(R.id.assignmentListView);
                ArrayList<String> specificSubjectTitles = new ArrayList<>();
                ArrayList<String> specificAssignmentTitles = new ArrayList<>();
                ArrayList<String> specificDueDates = new ArrayList<>();
                ArrayList<String> specificCourseSites = new ArrayList<>();
                ArrayList<String> specificDescriptions = new ArrayList<>();
                ArrayList<Integer> positions = new ArrayList<>();
                for (int i = 0; i < dueDates.size(); i++) {
                    if (dueDates.get(i).equals(resultIntent.getStringExtra("date"))) {
                        Log.d("Month", dueDates.get(i));
                        positions.add(i);
                    }
                }
                for (int i = 0; i < positions.size(); i++) {
                    specificSubjectTitles.add(subjectTitles.get(i));
                    specificAssignmentTitles.add(assignmentTitles.get(i));
                    specificDueDates.add(dueDates.get(i));
                    specificCourseSites.add(courseSites.get(i));
                    specificDescriptions.add(descriptions.get(i));
                }
                Log.d(TAG, specificAssignmentTitles.toString());
                Log.d(TAG, specificAssignmentTitles.toString());
                MyAdapter arrayAdapter2 = new MyAdapter(this,
                        specificSubjectTitles,
                        specificAssignmentTitles,
                        specificDueDates,
                        specificCourseSites,
                        specificDescriptions);
                assignmentList.setAdapter(arrayAdapter2);
            }
        }
        if (requestCode == 10) {
            if (resultCode == RESULT_OK) {
                restoreData(resultIntent);
                setDate(resultIntent);
                ListView assignmentList = findViewById(R.id.assignmentListView);
                MyAdapter arrayAdapter = new MyAdapter(this,
                        subjectTitles,
                        assignmentTitles,
                        dueDates,
                        courseSites,
                        descriptions);
                assignmentList.setAdapter(arrayAdapter);
            }
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
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);

            if (position % 2 == 0) {
                row.setBackgroundColor(Color.LTGRAY);
            }
            //ImageView logo = row.findViewById(R.id.logo);
            TextView mySubject = row.findViewById(R.id.subjectTitle);
            TextView myAssignment = row.findViewById(R.id.assignmentTitle);
            final TextView myDueDate = row.findViewById(R.id.dueDate);
            TextView mySite = row.findViewById(R.id.courseSite);
            TextView myDescription = row.findViewById(R.id.description);
            ImageButton deleteButton = row.findViewById(R.id.deleteButton);
            ImageButton editButton = row.findViewById(R.id.editButton);

            //logo.setImageResource(tmpLogo);
            mySubject.setText("Class: " + subjectTitles.get(position));
            myAssignment.setText(assignmentTitles.get(position));
            myDueDate.setText("[Due: " + dueDates.get(position) + "]");
            mySite.setText("Course Site: " + courseSites.get(position));
            myDescription.setText("Description: " + descriptions.get(position));
            deleteButton.setImageResource(android.R.drawable.ic_delete);
            editButton.setImageResource(android.R.drawable.ic_menu_edit);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    subjectTitles.remove(position);
                    assignmentTitles.remove(position);
                    dueDates.remove(position);
                    courseSites.remove(position);
                    descriptions.remove(position);
                    notifyDataSetChanged();
                }
            });

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle data = new Bundle();
                    data.putStringArrayList("MySubjectTitles", subjectTitles);
                    data.putStringArrayList("MyAssignmentTitles", assignmentTitles);
                    data.putStringArrayList("MyDueDates", dueDates);
                    data.putStringArrayList("MyCourseSites", courseSites);
                    data.putStringArrayList("MyDescriptions", descriptions);
                    data.putInt("i", position);

                    Log.d(TAG, data.toString());
                    Intent editIntent = new Intent(getApplicationContext(), AssignmentEdit.class);
                    editIntent.putExtras(data);
                    //start nextAcitivity
                    startActivity(editIntent);
                }
            });

            return row;
        }
    }
}
