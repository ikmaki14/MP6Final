package com.example.isabellamaki.mp6;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;


public class AssignmentEdit extends AppCompatActivity {
    protected ArrayList<String> subjectTitles = new ArrayList<>();
    protected ArrayList<String> assignmentTitles = new ArrayList<>();
    protected ArrayList<String> dueDates = new ArrayList<>();
    protected ArrayList<String> courseSites = new ArrayList<>();
    protected ArrayList<String> descriptions = new ArrayList<>();
    protected int position = 0;

    private static final String TAG = "MP6-AssignmentInput";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_edit);
        restoreData(getIntent());

        EditText editSubjectEditText = findViewById(R.id.editSubjectEditText);
        EditText editAssignmentNameEditText = findViewById(R.id.editAssignmentNameEditText);
        EditText editDueDateEditText = findViewById(R.id.editDueDateEditText);
        EditText editCourseSiteEditText = findViewById(R.id.editCourseSiteEditText);
        EditText editDescriptionEditText = findViewById(R.id.editDescriptionEditText);

        editSubjectEditText.setText(subjectTitles.get(position), TextView.BufferType.EDITABLE);
        editAssignmentNameEditText.setText(assignmentTitles.get(position), TextView.BufferType.EDITABLE);
        editDueDateEditText.setText(dueDates.get(position), TextView.BufferType.EDITABLE);
        editCourseSiteEditText.setText(courseSites.get(position), TextView.BufferType.EDITABLE);
        editDescriptionEditText.setText(descriptions.get(position), TextView.BufferType.EDITABLE);

        //Submit Button
        Button submitButton = findViewById(R.id.editSubmitButton);
        //If Submit Button is pushed...
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get the subjectEditText, editAssignmentNameEditText, editDueDateEditText, courseSiteEditText
                EditText editSubjectEditText = findViewById(R.id.editSubjectEditText);
                EditText editAssignmentNameEditText = findViewById(R.id.editAssignmentNameEditText);
                EditText editDueDateEditText = findViewById(R.id.editDueDateEditText);
                EditText editCourseSiteEditText = findViewById(R.id.editCourseSiteEditText);
                EditText editDescriptionEditText = findViewById(R.id.editDescriptionEditText);

                //Put user input into String variables
                String subject = editSubjectEditText.getText().toString();
                String hw = editAssignmentNameEditText.getText().toString();
                String due = editDueDateEditText.getText().toString();
                String site = editCourseSiteEditText.getText().toString();
                String desc = editDescriptionEditText.getText().toString();


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
                        || due.split("/")[2].length() != 4
                        || Integer.parseInt(due.split("/")[2]) <= 2017
                        || Integer.parseInt(due.split("/")[2]) > 9999) {
                    Snackbar.make(v, "Due date incorrectly formatted", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else {
                    if (due.split("/")[1].contains("0")) {
                        StringBuilder date = new StringBuilder(due);
                        if(due.charAt(3) == '0') {
                            date.deleteCharAt(3);
                        }
                        due = date.toString();
                    }
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

                    subjectTitles.remove(position);
                    subjectTitles.add(position, subject);
                    Log.d(TAG, subjectTitles.toString());
                    Log.d(TAG, subjectTitles.get(subjectTitles.size() - 1) + " added to subjectTitles");

                    assignmentTitles.remove(position);
                    assignmentTitles.add(position, hw);
                    Log.d(TAG, assignmentTitles.get(assignmentTitles.size() - 1) + " added to assignmentTitles");

                    dueDates.remove(position);
                    dueDates.add(position, due);
                    Log.d(TAG, dueDates.get(dueDates.size() - 1) + " added to dueDates");

                    courseSites.remove(position);
                    courseSites.add(position, site);
                    Log.d(TAG, courseSites.get(courseSites.size() - 1) + " added to courseSites");

                    descriptions.remove(position);
                    descriptions.add(position, desc);
                    Log.d(TAG, descriptions.get(descriptions.size() - 1) + " added to descriptions");


                    //Pass updated ArrayLists into the container on the MainActivity
                    //Bundle data = getIntent().getExtras();
                    Bundle data = saveData();

                    Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startIntent.putExtras(data);

                    //go back to MainActivity
                    //setResult(RESULT_OK, startIntent);
                    //finish();
                    startActivity(startIntent);
                }
            }
        });

        //Cancel Button
        final Button cancelButton = findViewById(R.id.editCancelButton);
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
            descriptions = data.getStringArrayList("MyDescriptions");
            position = data.getInt("i");
        }
    }

}
