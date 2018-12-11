package com.example.isabellamaki.mp6;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;


public class AssignmentInput extends AppCompatActivity {
    protected ArrayList<String> subjectTitles = new ArrayList<>();
    protected ArrayList<String> assignmentTitles = new ArrayList<>();
    protected ArrayList<String> dueDates = new ArrayList<>();
    protected ArrayList<String> courseSites = new ArrayList<>();
    protected ArrayList<String> descriptions = new ArrayList<>();

    private static final String TAG = "MP6-AssignmentInput";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_input);
        restoreData(getIntent());

        //Submit Button
        Button submitButton = (Button) findViewById(R.id.submitButton);
        //If Submit Button is pushed...
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get the subjectEditText, assignmentNameEditText, dueDateEditText, courseSiteEditText
                EditText subjectEditText = (EditText) findViewById(R.id.subjectEditText);
                EditText assignmentNameEditText = (EditText) findViewById(R.id.assignmentNameEditText);
                EditText dueDateEditText = (EditText) findViewById(R.id.dueDateEditText);
                EditText courseSiteEditText = (EditText) findViewById(R.id.courseSiteEditText);
                EditText descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);

                //Put user input into String variables
                String subject = subjectEditText.getText().toString();
                String hw = assignmentNameEditText.getText().toString();
                String due = dueDateEditText.getText().toString();
                String site = courseSiteEditText.getText().toString();
                String desc = descriptionEditText.getText().toString();


                //Don't let them submit unless fields are filled, site + desc are optional
                boolean filled = false;
                if (subject.trim().equals("")) {
                    Log.d(TAG, "subject flield is empty");
                    Snackbar.make(v, "Subject field empty", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else if (hw.trim().equals("")) {
                    Log.d(TAG, "hw field is empty");
                    Snackbar.make(v, "Assignment title field empty", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else if (due.trim().equals("")) {
                    Log.d(TAG, "due date is empty");
                    Snackbar.make(v, "Due date field empty", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else if (!due.contains("/")
                        || due.split("/").length != 3
                        || due.split("/")[0].length() != 2
                        || Integer.parseInt(due.split("/")[0]) <= 0
                        || Integer.parseInt(due.split("/")[0]) > 12
                        || due.split("/")[1].length() != 2
                        || Integer.parseInt(due.split("/")[1]) <= 0
                        || Integer.parseInt(due.split("/")[1]) > 31
                        || due.split("/")[2].length() != 2
                        || Integer.parseInt(due.split("/")[2]) < 0
                        || Integer.parseInt(due.split("/")[2]) > 99) {
                    Snackbar.make(v, "Due date incorrectly formatted", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else {
                    if (site.trim().equals("")) {
                        site = "N/A";
                    }
                    if (desc.trim().equals("")) {
                        desc = "N/A";
                    }
                    filled = true;
                }
                if (filled) {
                    Log.d(TAG, "subjectSet: " + subject);
                    Log.d(TAG, "hwSet: " + hw);
                    Log.d(TAG, "dueDateSet: " + due);
                    Log.d(TAG, "siteSet: " + site);
                    Log.d(TAG, "descSet: " + desc);

                    subjectTitles.add(subject);
                    Log.d(TAG, subjectTitles.toString());
                    Log.d(TAG, subjectTitles.get(subjectTitles.size() - 1) + " added to subjectTitles");


                    assignmentTitles.add(hw);
                    Log.d(TAG, assignmentTitles.get(assignmentTitles.size() - 1) + " added to assignmentTitles");


                    dueDates.add(due);
                    Log.d(TAG, dueDates.get(dueDates.size() - 1) + " added to dueDates");


                    courseSites.add(site);
                    Log.d(TAG, courseSites.get(courseSites.size() - 1) + " added to courseSites");


                    descriptions.add(desc);
                    Log.d(TAG, descriptions.get(descriptions.size() - 1) + " added to descriptions");


                    //Pass updated ArrayLists into the container on the MainActivity
                    //Bundle data = getIntent().getExtras();
                    Bundle data = saveData();

                    Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startIntent.putExtras(data);
                    /*startIntent.putExtra("sub", subject);
                    startIntent.putExtra("assign", hw);
                    startIntent.putExtra("due", due);
                    startIntent.putExtra("site", site);
                    startIntent.putExtra("desc", desc);*/

                    //go back to MainActivity
                    setResult(RESULT_OK, startIntent);
                    finish();
                    //startActivity(startIntent);
                }
            }
        });

        //Cancel Button
        final Button cancelButton = (Button) findViewById(R.id.cancelButton);
        //If Cancel Button is pushed...
        cancelButton.setOnClickListener(new View.OnClickListener() {
            //return to the MainActivity, passing no information
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Cancel button was pushed.. returning to home page");
                Bundle data = saveData();
                Intent cancelIntent = new Intent(getApplicationContext(), MainActivity.class);
                cancelIntent.putExtras(data);
                setResult(RESULT_CANCELED, cancelIntent);
                finish();
                //startActivity(cancelIntent);
            }
        });
    }

    private Bundle saveData() {
        //Log.d(TAG, "saveData() called. Saving data...");
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
        if (data != null) {
            subjectTitles = data.getStringArrayList("MySubjectTitles");
            assignmentTitles = data.getStringArrayList("MyAssignmentTitles");
            dueDates = data.getStringArrayList("MyDueDates");
            courseSites = data.getStringArrayList("MyCourseSites");
            ;
            descriptions = data.getStringArrayList("MyDescriptions");
        }
    }
/*    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        //Save UI state changes to the savedInstanceState.
        //This bundle will be passed to onCreate if the process is killed & restarted.
        savedInstanceState.putStringArrayList("MySubjectTitles", subjectTitles);
        savedInstanceState.putStringArrayList("MyAssignmentTitles", assignmentTitles);
        savedInstanceState.putStringArrayList("MyDueDates", dueDates);
        savedInstanceState.putStringArrayList("MyCourseSites", courseSites);
        savedInstanceState.putStringArrayList("MyDescriptions", descriptions);
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

    }*/
}
