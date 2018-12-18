package com.github.davidji80.helloworld.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.github.davidji80.helloworld.R;
import com.github.davidji80.helloworld.adapter.CustomAdapter;
import com.github.davidji80.helloworld.model.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterActivity extends AppCompatActivity {
    //定义数据
    private String[] datas = {"张三", "李四", "王五", "麻子", "小强"};
    private String[] contents = {"我是张三，你好", "我是李四，你好", "我是王五，你好", "我是麻子，你好", "我是小强，你好"};
    private int[] imageViews = {R.mipmap.ic_launcher, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter);

        Intent intent = getIntent();
        String adapterType = intent.getStringExtra("AdapterType");
        ListView listView;
        listView = findViewById(R.id.lvArrayAdapter);
        if (adapterType.equals("ArrayAdapter")) {
            /**
             * 第一个参数：context上下文对象
             * 第二个参数：每一个item的样式，可以使用系统提供，也可以自定义就是一个TextView
             * 第三个参数：数据源，要显示的数据
             * 系统提供的item的样式
             * simple_list_item1:单独的一行文本框
             * simple_list_item2:有两个文本框组成
             * simple_list_item_checked每项都是由一个已选中的列表项
             * simple_list_item_multiple_choice:都带有一个复选框
             * simple_list_item_single_choice：都带有一个单选框
             */
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, datas);
            listView.setAdapter(adapter);
        } else if (adapterType.equals("SimpleAdapter")) {
            //准备数据源
            List<Map<String, Object>> lists = new ArrayList<>();
            for (int i = 0; i < datas.length; i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("image", imageViews[i]);
                map.put("theme", datas[i]);
                map.put("content", contents[i]);
                lists.add(map);
            }
            /**
             * 第一个参数：上下文对象
             * 第二个参数：数据源是含有Map的一个集合
             * 第三个参数：每一个item的布局文件
             * 第四个参数：new String[]{}数组，数组的里面的每一项要与第二个参数中的存入map集合的的key值一样，一一对应
             * 第五个参数：new int[]{}数组，数组里面的第三个参数中的item里面的控件id。
             */
            SimpleAdapter adapter = new SimpleAdapter(this, lists, R.layout.view_listitem
                    , new String[]{"image", "theme", "content"}
                    , new int[]{R.id.image1, R.id.text1, R.id.text2});
            listView.setAdapter(adapter);
        } else if (adapterType.equals("BaseAdapter")) {
            //准备数据源
            final List<Person> lists = new ArrayList<>();
            for (int i = 0; i < datas.length; i++) {
                lists.add(new Person(imageViews[i], datas[i], contents[i]));
            }
            CustomAdapter adapter = new CustomAdapter(lists, this);
            listView.setAdapter(adapter);
            //设置listview点击事件
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Person person=lists.get(i);
                    Toast.makeText(AdapterActivity.this, person.getIntro(), Toast.LENGTH_LONG).show();
                }
            });
        }


    }
}
