package com.yue.simpleresume;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.yue.simpleresume.models.Project;
import com.yue.simpleresume.utils.DateUtils;

import java.util.Arrays;
import java.util.Date;
@SuppressWarnings("ConstantConditions")
public class ProjectEditActivity extends AppCompatActivity {

    public static final String KEY_PROJECT = "project";

    public static final String KEY_PROJECT_ID = "project_id";

    private Project project;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_edit_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        project = getIntent().getParcelableExtra(KEY_PROJECT);

        if (project == null) {
            findViewById(R.id.project_edit_delete).setVisibility(View.GONE);
        } else {
            ((EditText) findViewById(R.id.project_edit_name)).setText(project.name);
            ((EditText) findViewById(R.id.project_edit_start_date)).setText(DateUtils.dateToString(project.startDate));
            ((EditText) findViewById(R.id.project_edit_end_date)).setText(DateUtils.dateToString(project.endDate));
            ((EditText) findViewById(R.id.project_edit_details)).setText(MainActivity.formatEditItems(project.details));

            findViewById(R.id.project_edit_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(KEY_PROJECT_ID, project.id);
                    setResult(Activity.RESULT_OK, resultIntent);

                    finish();
                }
            });
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
        if (project == null) {
            project = new Project();
        }
        project.name = (((EditText) findViewById(R.id.project_edit_name)).getText()).toString();
        project.startDate = DateUtils.stringToDate((((EditText) findViewById(R.id.project_edit_start_date)).getText()).toString());
        project.endDate = DateUtils.stringToDate((((EditText) findViewById(R.id.project_edit_end_date)).getText()).toString());
        project.details = Arrays.asList(TextUtils.split((((EditText) findViewById(R.id.project_edit_details)).getText()).toString(), "\n"));
        // save data
        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_PROJECT, project);
        setResult(Activity.RESULT_OK, resultIntent);

        finish();
    }
}