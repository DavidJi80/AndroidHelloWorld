package com.github.davidji80.helloworld.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.github.davidji80.helloworld.R;
import java.util.List;

/**
 * 创建一个继承RecyclerView.Adapter<VH>的Adapter类
 * VH是ViewHolder的类名
 */
public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.VH> {
    //数据源
    private List<String> mDatas;

    /**
     * 自定义interface
     * 点击和长按事件
     */
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    //事件对象
    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    /**
     * 构造函数
     * 传递数据
     *
     * @param data
     */
    public CustomRecyclerAdapter(List<String> data) {
        this.mDatas = data;
    }


    /**
     * 为每个Item inflater出一个View
     * 该方法把View直接封装在ViewHolder中，并返回这个ViewHolder
     *
     * @param parent
     * @param viewType
     * @return 该方法返回的是一个ViewHolder
     */
    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_rv_item, parent, false));
    }

    /**
     * 适配渲染数据到View中
     * 方法提供给你了一ViewHolder，而不是BaseAdapter中的View
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull final VH holder, final int position) {
        holder.tv.setText(mDatas.get(position));
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getLayoutPosition();
                mOnItemClickLitener.onItemClick(holder.itemView, pos);
            }
        });
        holder.tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int pos = holder.getLayoutPosition();
                mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                return false;
            }
        });
    }

    public void addData(int position) {
        mDatas.add(position, "Insert One");
        notifyItemInserted(position);
    }

    public void removeData(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 类似于BaseAdapter的getCount方法了，即总共有多少个条目
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /**
     * 创建ViewHolder
     * 在Adapter中创建一个继承RecyclerView.ViewHolder的静态内部类
     * ViewHolder的实现和ListView的ViewHolder实现几乎一样
     */
    public static class VH extends RecyclerView.ViewHolder {

        TextView tv;

        public VH(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.id_num);
        }
    }
}
