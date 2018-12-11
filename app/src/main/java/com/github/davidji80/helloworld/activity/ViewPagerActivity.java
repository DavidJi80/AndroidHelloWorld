package com.github.davidji80.helloworld.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.davidji80.helloworld.R;
import com.github.davidji80.helloworld.adapter.CustomPagerAdapter;
import com.github.davidji80.helloworld.animator.CustomPageTransformer;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        setVp();
    }

    private void setVp() {
        //初始化数据
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add("第" + i + "个View");
        }
        ViewPager vp = findViewById(R.id.vp);
        //设置适配器
        vp.setAdapter(new CustomPagerAdapter(this, list));
        //设置ViewPager切换时的动画效果
        vp.setPageTransformer(false, new CustomPageTransformer());
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /**
             * 页面滑动状态停止前一直调用
             *
             * @param position 当前点击滑动页面的位置
             * @param positionOffset 当前页面偏移的百分比
             * @param positionOffsetPixels 当前页面偏移的像素位置
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("vp", "滑动中=====position:" + position + "   positionOffset:" + positionOffset + "   positionOffsetPixels:" + positionOffsetPixels);
            }

            /**
             * 滑动后显示的页面和滑动前不同，调用
             *
             * @param position 选中显示页面的位置
             */
            @Override
            public void onPageSelected(int position) {
                Log.e("vp", "显示页改变=====postion:" + position);
            }

            /**
             * 页面状态改变时调用
             * @param state 当前页面的状态
             *              SCROLL_STATE_IDLE：空闲状态
             *              SCROLL_STATE_DRAGGING：滑动状态
             *              SCROLL_STATE_SETTLING：滑动后滑翔的状态
             */
            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_IDLE:
                        Log.e("vp", "状态改变=====SCROLL_STATE_IDLE====静止状态");
                        break;
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        Log.e("vp", "状态改变=====SCROLL_STATE_DRAGGING==滑动状态");
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        Log.e("vp", "状态改变=====SCROLL_STATE_SETTLING==滑翔状态");
                        break;
                }
            }
        });
    }

}
