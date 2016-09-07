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

import com.yue.simpleresume.models.BasicInfo;

@SuppressWarnings("ConstantConditions")
public class InfoEditActivity extends AppCompatActivity{

    public static final String KEY_INFO = "information";

    private BasicInfo basicInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.info_edit_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        basicInfo = getIntent().getParcelableExtra(KEY_INFO);

        if (!TextUtils.isEmpty(basicInfo.name)) {
            ((EditText) findViewById(R.id.info_edit_name)).setText(basicInfo.name);
        }

        if (!TextUtils.isEmpty(basicInfo.email)) {
            ((EditText) findViewById(R.id.info_edit_email)).setText(basicInfo.email);
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

        basicInfo.name = ((EditText) findViewById(R.id.info_edit_name)).getText().toString();
        basicInfo.email = ((EditText) findViewById(R.id.info_edit_email)).getText().toString();

        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_INFO, basicInfo);
        setResult(Activity.RESULT_OK, resultIntent);

        finish();

    }
}
