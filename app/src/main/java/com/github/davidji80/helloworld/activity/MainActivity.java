package com.github.davidji80.helloworld.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.davidji80.helloworld.R;
import com.github.davidji80.helloworld.intent.CC;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final String Tag = MainActivity.class.getSimpleName();

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

    /**
     * 为获得返回结果而启动Activity
     *
     * @param view
     */
    public void btnLinearClick(View view) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, LinearLayoutActivity.class);
        //startActivityForResult的第二个参数就是请求码
        startActivityForResult(intent, CC.REQUEST_CODE);
    }

    /**
     * 接收返回的方法
     * 该方法首先根据请求码requestCode识别出是哪个Activity返回的，然后根据返回码resultCode做进一步处理
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //检查requestCode是否与发送的请求码匹配
        if (requestCode == CC.REQUEST_CODE) {
            //检查结果代码是否等于RESULT_OK,相等表示成功返回
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getExtras();
                String resultMsg = bundle.getString(CC.RESULT_MSG);
                EditText editText = findViewById(R.id.edit_message);
                editText.setText(resultMsg);
            }
        }
        //联系人APP返回方法获取选择联系人的手机号
        else if (requestCode == CC.REQUEST_SELECT_CONTACT) {
            if (resultCode == Activity.RESULT_OK) {
                // 目标联系人的Uri
                Uri contactUri = data.getData();
                String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};
                Cursor cursor = getContentResolver().query(contactUri, projection,
                        null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    //电话号码
                    String number = cursor.getString(numberIndex);
                    EditText editText = findViewById(R.id.edit_message);
                    editText.setText(number);
                }
            }
        }
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


    public void btnSQLiteClick(View view) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, SQLiteActivity.class);
        startActivity(intent);
    }

    /**
     * 打电话
     *
     * @param view
     */
    public void btnCallClick(View view) {
        Uri number = Uri.parse("tel:5551234");
        Intent intent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(intent);
    }

    /**
     * 打开网页
     *
     * @param view
     */
    public void btnWebPageClick(View view) {
        Uri webPage = Uri.parse("http://www.baidu.com");
        Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
        startActivity(intent);
    }

    /**
     * 从联系人中获取
     *
     * @param view
     */
    public void btnContactsClick(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        //从有电话号码的联系人中选取
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        /*
        //从有电子邮件地址的联系人中选取
        intent.setType(ContactsContract.CommonDataKinds.Email.CONTENT_TYPE);
        //从有邮政地址的联系人中选取。
        intent.setType(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_TYPE);
        */
        startActivityForResult(intent, CC.REQUEST_SELECT_CONTACT);
    }

    // 声明一个Handler对象
    private Handler handler = new Handler();

    //继承Thread类实现线程
    private class CustomThread extends Thread {
        @Override
        public void run() {
            Log.d(Tag, "使用Thread执行一个线程");
            /*
            EditText edit_message = findViewById(R.id.edit_message);
            edit_message.setText("123");
            */
            /*
             以上操作会报错，无法再子线程中访问UI组件，UI组件的属性必须在UI线程中访问
             使用handler.post方式修改UI组件
             */
            handler.post(new Runnable() {
                @Override
                public void run() {
                    EditText edit_message = findViewById(R.id.edit_message);
                    edit_message.setText("123");
                }
            });
        }
    }

    //实现Runnable接口实现线程
    private class CustomRunnable implements Runnable {
        @Override
        public void run() {
            Log.d(Tag, "使用Runnable接口执行一个线程");
            handler.post(new Runnable() {
                @Override
                public void run() {
                    EditText edit_message = findViewById(R.id.edit_message);
                    edit_message.setText("456");
                }
            });
        }
    }

    // 重写Handler的handleMessage() 方法处理Message
    private Handler handlerMsg = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){
                EditText edit_message = findViewById(R.id.edit_message);
                edit_message.setText(msg.obj.toString());
            }
        }
    };

    //handler发送一个Message
    private class RunnableMsgThread implements Runnable {
        @Override
        public void run() {
            Log.d(Tag, "使用Runnable接口执行一个线程");
            // 获取一个Message对象，设置what为1
            Message msg = Message.obtain();
            msg.obj = "789";
            msg.what = 1;
            // 发送这个消息到消息队列中
            handlerMsg.sendMessage(msg);
        }
    }

    public void rgThreadClick(View view) {
        Intent intent = new Intent();
        int id = view.getId();
        switch (id) {
            case R.id.rbT1:
                new CustomThread().start();
                break;
            case R.id.rbT2:
                new Thread(new CustomRunnable()).start();
                break;
            case R.id.rbT3:
                new Thread(new RunnableMsgThread()).start();
                break;
            default:
                break;
        }
    }

}
