package com.github.davidji80.helloworld.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.davidji80.helloworld.R;
import com.github.davidji80.helloworld.model.Person;

import java.util.List;

/**
 * 自定义适配器
 */
public class CustomAdapter extends BaseAdapter {
    //数据源
    private List<Person> datas;
    //上下文
    private Context context;

    /**
     * 构造函数
     * @param datas
     * @param context
     */
    public CustomAdapter(List<Person> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    /**
     * 数据数量
     * @return
     */
    @Override
    public int getCount() {
        return datas.size();
    }

    /**
     * 指定的索引对应的数据项
     * @param i
     * @return
     */
    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    /**
     * 指定的索引对应的数据项ID
     * @param i
     * @return
     */
    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * 返回每一项的显示内容
     *  * 1、创建内部类
     *  * 2、判断view是否为空，利用listView的缓存机制， 避免view重复创建
     *  * 3、通过setTag方法将viewHolder与view建立关系绑定，这样可以避免重复重复调用view.findViewById(R.id.image1) etc.
     *
     * @param i
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        //如果view未被实例化过，缓存池中没有对应的缓存
        if (view==null) {
            viewHolder=new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.view_listitem, viewGroup, false);
            //对viewHolder的属性进行赋值
            viewHolder.imageView= view.findViewById(R.id.image1);
            viewHolder.textView1 =  view.findViewById(R.id.text1);
            viewHolder.textView2 =  view.findViewById(R.id.text2);
            //通过setTag将view与viewHolder关联
            view.setTag(viewHolder);
        }else{
            //如果缓存池中有对应的view缓存，则直接通过getTag取出viewHolder
            viewHolder= (ViewHolder) view.getTag();
        }
        // 设置控件的数据
        viewHolder.imageView.setImageResource(datas.get(i).getPhoto());
        viewHolder.textView1.setText(datas.get(i).getName());
        viewHolder.textView2.setText(datas.get(i).getIntro());
        return view;
    }

    /**
     * 创建内部类
     */
    private final class ViewHolder {
        private ImageView imageView;
        private TextView textView1;
        private TextView textView2;
    }
}
