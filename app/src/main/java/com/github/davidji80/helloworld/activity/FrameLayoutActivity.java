package com.github.davidji80.helloworld.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.github.davidji80.helloworld.R;

import java.util.Timer;
import java.util.TimerTask;

public class FrameLayoutActivity extends AppCompatActivity {
    FrameLayout frame = null;

    Handler handler = new Handler() {
        int i = 0;
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                ChangeImage(i);
                i++;
                if (i == 8) i = 0;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_framelayout);
        frame = (FrameLayout) findViewById(R.id.myFrame);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0x123);
            }
        }, 0, 200);
    }

    void ChangeImage(int i) {
        Drawable a = getResources().getDrawable(R.drawable.m1);
        Drawable b = getResources().getDrawable(R.drawable.m2);
        Drawable c = getResources().getDrawable(R.drawable.m3);
        Drawable d = getResources().getDrawable(R.drawable.m4);
        Drawable e = getResources().getDrawable(R.drawable.m5);
        Drawable f = getResources().getDrawable(R.drawable.m6);
        Drawable g = getResources().getDrawable(R.drawable.m7);
        Drawable h = getResources().getDrawable(R.drawable.m8);
        switch (i) {
            case 0:
                frame.setForeground(a);
                break;
            case 1:
                frame.setForeground(b);
                break;
            case 2:
                frame.setForeground(c);
                break;
            case 3:
                frame.setForeground(d);
                break;
            case 4:
                frame.setForeground(e);
                break;
            case 5:
                frame.setForeground(f);
                break;
            case 6:
                frame.setForeground(g);
                break;
            case 7:
                frame.setForeground(h);
                break;
        }
    }
}
