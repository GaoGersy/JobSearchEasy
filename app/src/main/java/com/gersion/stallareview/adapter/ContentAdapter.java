package com.gersion.stallareview.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gersion.stallareview.R;

import java.util.List;

/**
 * @作者 Gersy
 * @版本
 * @包名 com.gersion.stallareview.adapter
 * @待完成
 * @创建时间 2016/10/24
 * @功能描述 TODO
 * @更新人 $
 * @更新时间 $
 * @更新版本 $
 */
public class ContentAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mList;

    public ContentAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList==null?0:mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.content_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mContent.setText(mList.get(position));
        return convertView;
    }

    class ViewHolder {

        private TextView mContent;

        private ViewHolder(View view) {
            mContent = (TextView) view.findViewById(R.id.content);
        }
    }
}
