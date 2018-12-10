package com.github.davidji80.helloworld.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.github.davidji80.helloworld.R;
import com.github.davidji80.helloworld.adapter.CustomRecyclerAdapter;
import com.github.davidji80.helloworld.decoration.CustomItemDecoration;
import com.github.davidji80.helloworld.decoration.GridItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    private List<String> mDatas;
    private RecyclerView mRecyclerView;
    private CustomRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        initData();
        mAdapter = new CustomRecyclerAdapter(mDatas);
        mAdapter.setOnItemClickLitener(new CustomRecyclerAdapter.OnItemClickLitener() {

            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(RecyclerViewActivity.this, position + " click",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(RecyclerViewActivity.this, position + " long click",
                        Toast.LENGTH_SHORT).show();
                mAdapter.removeData(position);
            }
        });
        mRecyclerView = findViewById(R.id.id_recyclerview);
        Intent intent = getIntent();
        String type = intent.getStringExtra("RecyclerView");
        if (type.equals("Liner")) {
            linearLayoutManager();
        } else if (type.equals("Grid")) {
            gridLayoutManager();
        } else if (type.equals("Staggered")) {
            staggeredGridLayoutManager();
        }

    }

    private void linearLayoutManager() {
        //Layout Manager：设置布局管理器(必选)
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Adapter：设置adapter(必选)
        mRecyclerView.setAdapter(mAdapter);
        //Item Animator：设置Item增加、移除动画(可选，默认为DefaultItemAnimator)
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //Item Decoration：Item之间的Divider(可选，默认为空)
        mRecyclerView.addItemDecoration(new CustomItemDecoration(this,
                CustomItemDecoration.VERTICAL_LIST));
    }

    private void gridLayoutManager() {
        //Layout Manager：GridLayoutManager
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //Item Decoration：GridItemDecoration
        mRecyclerView.addItemDecoration(new GridItemDecoration(this));
    }

    private void staggeredGridLayoutManager() {
        //Layout Manager：GridLayoutManager
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new GridItemDecoration(this));
    }

    /**
     * 初始化数据
     */
    protected void initData() {
        mDatas = new ArrayList<String>();
        for (int i = 0; i <= 100; i++) {
            mDatas.add("" + i);
        }
    }

}