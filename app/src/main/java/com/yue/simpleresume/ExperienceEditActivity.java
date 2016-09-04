package com.yue.simpleresume;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.yue.simpleresume.models.Experience;
import com.yue.simpleresume.utils.DateUtils;

import java.util.Arrays;

public class ExperienceEditActivity extends AppCompatActivity {

    public static final String KEY_EXPERIENCE = "experience";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.experience_edit_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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

        Experience experience = new Experience();
        experience.company = (((EditText) findViewById(R.id.experience_edit_company)).getText()).toString();
        experience.title = (((EditText) findViewById(R.id.experience_edit_title)).getText()).toString();
        experience.startDate = (DateUtils.stringToDate(((EditText) findViewById(R.id.experience_edit_start_date)).getText().toString()));
        experience.endDate = (DateUtils.stringToDate(((EditText) findViewById(R.id.experience_edit_end_date)).getText().toString()));
        experience.details = Arrays.asList(TextUtils.split(((EditText) findViewById(R.id.experience_edit_details)).getText().toString(), "\n"));
        // save data
        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_EXPERIENCE, experience);
        setResult(Activity.RESULT_OK, resultIntent);

        finish();
    }
}