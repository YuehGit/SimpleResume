package com.yue.simpleresume;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.EditText;

import com.yue.simpleresume.models.Education;
import com.yue.simpleresume.utils.DateUtils;

import java.util.Arrays;
import java.util.List;

public class EducationEditActivity extends AppCompatActivity {

    public static final String KEY_EDUCATION = "education";

    private Education education;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.education_edit_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        education = getIntent().getParcelableExtra(KEY_EDUCATION);

        if (education != null) {
            ((EditText) findViewById(R.id.education_edit_school)).setText(education.school);
            ((EditText) findViewById(R.id.education_edit_major)).setText(education.major);
            ((EditText) findViewById(R.id.education_edit_start_date)).setText(DateUtils.dateToString(education.startDate));
            ((EditText) findViewById(R.id.education_edit_end_date)).setText(DateUtils.dateToString(education.endDate));
            ((EditText) findViewById(R.id.education_edit_courses)).setText(MainActivity.formatEditItems(education.courses));
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.ic_save:
                saveAndExit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveAndExit() {
        if (education == null) {
            education = new Education();
        }
        // read data from user input and save them into a relative object
        education.school = ((EditText) findViewById(R.id.education_edit_school)).getText().toString();
        education.major = ((EditText) findViewById(R.id.education_edit_major)).getText().toString();
        education.startDate = DateUtils.stringToDate(((EditText) findViewById(R.id.education_edit_start_date)).getText().toString());
        education.endDate = DateUtils.stringToDate(((EditText) findViewById(R.id.education_edit_end_date)).getText().toString());
        education.courses = Arrays.asList(TextUtils.split(((EditText) findViewById(R.id.education_edit_courses)).getText().toString(), "\n"));
        // save the object to an intent as the result like a key-value pair
        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_EDUCATION, education);
        setResult(Activity.RESULT_OK, resultIntent);

        finish();
    }
}
