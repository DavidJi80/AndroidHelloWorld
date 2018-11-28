package com.github.davidji80.helloworld.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.github.davidji80.helloworld.R;
import com.github.davidji80.helloworld.intent.CC;

public class LinearLayoutActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linearlayout);
    }

    /**
     * 被调用者点击按钮返回
     * setResult设置了返回的结果码，结果码是用户设定的整数
     * 反映执行结果的情况，一般是Activity.RESULT_OK，Activity.CANCELED
     *
     * @param view
     */
    public void btnClick(View view){
        EditText edit_message = findViewById(R.id.edit_message);
        //返回调用者
        Intent intent=new Intent();
        intent.putExtra(CC.RESULT_MSG,edit_message.getText());
        setResult(Activity.RESULT_OK,intent);
        //关闭自己
        finish();
    }
}
