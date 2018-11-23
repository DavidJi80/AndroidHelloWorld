package com.github.davidji80.helloworld;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class ClockActivity extends AppCompatActivity {

    protected TextView counter;
    protected Button start;
    protected Button stop;

    //两个按钮互斥变量
    protected boolean timerRunning;
    //时间变量
    protected long startedAt;
    protected long lastStopped;
    /*
    创建一个long类型的静态变量UPDATE-ERVERYo
    为该变量赋值200，这是更新屏幕counter的频率。
    如果设置为1000，那么有可能不能准确地显示每一秒。
    */
    private final long UPDATE_EVERY = 200;

    //在Activity中定义一个用于更新UI界面的handler类
    private Handler handler= new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //判断信息是否是本应用发出的
            if (msg.what == 0x123) {
                setTimeDisplay();
            }
            super.handleMessage(msg);
        }
    };
    //定义一个定时器对象
    private Timer timer = new Timer();
    //在Activity中定义一个继承自TimerTask的UpdateTimer内部类，TimerTask继承自Runnable
    private class UpdateTimer extends TimerTask{
        @Override
        public void run() {
            if (handler!=null) {
                handler.sendEmptyMessage(0x123);
            }
        }
    }
    private UpdateTimer updateTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        counter = (TextView) findViewById(R.id.timer);
        start = (Button) findViewById(R.id.start_button);
        stop = (Button) findViewById(R.id.stop_button);
    }

    public void clickedStart(View view) {
        timerRunning = true;
        enableButtons();
        startedAt = System.currentTimeMillis();
        lastStopped = System.currentTimeMillis();
        updateTimer=new UpdateTimer();
        timer.schedule(updateTimer, 0, 200);
    }

    public void clickedStop(View view) {
        timerRunning = false;
        enableButtons();
        startedAt = System.currentTimeMillis();
        lastStopped = System.currentTimeMillis();
        handler.removeCallbacks(updateTimer);
        //handler = null;
    }

    protected void enableButtons() {
        start.setEnabled(!timerRunning);
        stop.setEnabled(timerRunning);
    }

    protected void setTimeDisplay() {
        String display;
        long timeNow;
        long diff;
        long seconds;
        long minutes;
        long hours;
        if (timerRunning) {
            timeNow = System.currentTimeMillis();
        } else {
            timeNow = lastStopped;
        }
        diff = timeNow - startedAt;
        if (diff < 0) {
            diff = 0;
        }
        seconds = diff / 1000;
        minutes = seconds / 60;
        hours = minutes / 60;
        seconds = seconds % 60;
        minutes = minutes % 60;
        display = String.format("%d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
        counter.setText(display);
    }
}
