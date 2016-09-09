package com.yue.simpleresume;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.yue.simpleresume.models.BasicInfo;
import com.yue.simpleresume.utils.ImageUtils;
import com.yue.simpleresume.utils.PermissionUtils;

@SuppressWarnings("ConstantConditions")
public class InfoEditActivity extends AppCompatActivity{

    public static final String KEY_INFO = "information";
    private static final int REQ_CODE_PICK_IMAGE = 100;

    private BasicInfo basicInfo;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                String imagePath = ImageUtils.getRealPathFromUri(this, imageUri);
                showImage(imagePath);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PermissionUtils.REQ_CODE_WRITE_EXTERNAL_STORAGE
                && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            pickImage();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.info_edit_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        basicInfo = getIntent().getParcelableExtra(KEY_INFO);

        ((EditText) findViewById(R.id.info_edit_name)).setText(basicInfo.name);
        ((EditText) findViewById(R.id.info_edit_email)).setText(basicInfo.email);

        if (!TextUtils.isEmpty(basicInfo.imagePath)) {
            showImage(basicInfo.imagePath);
        }

        findViewById(R.id.info_edit_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PermissionUtils.checkPermission(InfoEditActivity.this,
                                                      Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    PermissionUtils.requestReadExternalStoragePermission(InfoEditActivity.this);
                } else {
                    pickImage();
                }
            }
        });
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
        basicInfo.imagePath = findViewById(R.id.info_edit_image).getTag().toString();

        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_INFO, basicInfo);
        setResult(Activity.RESULT_OK, resultIntent);

        finish();
    }

    private void showImage(String imagePath) {

        ImageView imageView = (ImageView) findViewById(R.id.info_edit_image);

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        imageView .setTag(imagePath);

        ImageUtils.loadImage(imagePath, imageView);
    }

    private void pickImage() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Select picture"), REQ_CODE_PICK_IMAGE);
    }



}
