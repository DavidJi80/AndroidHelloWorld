package com.github.davidji80.helloworld;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        find_and_modify_view();
        verifyStoragePermissions(this);
    }

    public void sendMessage(View view) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra("EXTRA_MESSAGE", message);
        startActivity(intent);
    }

    public void btnFrameClick(View view) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, FrameLayoutActivity.class);
        startActivity(intent);
    }

    public void btnLinearClick(View view) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, LinearLayoutActivity.class);
        startActivity(intent);
    }

    public void btnTableClick(View view) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, TabIeLayoutActivity.class);
        startActivity(intent);
    }

    public void btnGridClick(View view) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, GridLayoutActivity.class);
        startActivity(intent);
    }

    public void rbClick(View view) {
        Intent intent = new Intent();
        int id = view.getId();
        switch (id) {
            case R.id.rb1:
                Toast.makeText(getApplicationContext(), "点击了看图片 ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rb2:
                Toast.makeText(getApplicationContext(), "点击了日期时间", Toast.LENGTH_SHORT).show();
                intent.setClass(MainActivity.this, DatePickerActivity.class);
                startActivity(intent);
                break;
            case R.id.rb3:
                Toast.makeText(getApplicationContext(), "点击了下拉列表", Toast.LENGTH_SHORT).show();
                intent.setClass(MainActivity.this, ClockActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void find_and_modify_view() {
        String[] mProvinces = {"北京市", "上海市", "天津市", "重庆市", "江苏省"};
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayList arrayList = new ArrayList<String>();
        for (int i = 0; i < mProvinces.length; i++) {
            arrayList.add(mProvinces[i]);
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }


    public void btnDataKeyClick(View view) {

        Intent intent = new Intent();
        intent.setClass(MainActivity.this, SaveReadDataActivity.class);
        startActivity(intent);
    }


}
