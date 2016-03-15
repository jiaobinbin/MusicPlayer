package com.test.musicplayer.adapter;

import android.app.MediaRouteActionProvider;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.test.musicplayer.R;
import com.test.musicplayer.entity.MusicInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/15.
 */
public class MusicLisBaseAdapter extends BaseAdapter{

    private Context context;
    private LayoutInflater inflater;
    private List<MusicInfo> musicList;
    private List<Map<String,Object>> data;
    public  MusicLisBaseAdapter(Context context,List<Map<String,Object>> data){
        this.context = context;
        this.data=data;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.listview_item,null);
            viewHolder.tvMusicTitle = (TextView) convertView.findViewById(R.id.tvMusicTitle);
            viewHolder.tvMusicArtist = (TextView) convertView.findViewById(R.id.tvMusicArtist);
            convertView.setTag(viewHolder);
        }else{

            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvMusicTitle.setText((String)data.get(position).get("title"));
        viewHolder.tvMusicArtist.setText((String)data.get(position).get("artist"));
        return convertView;
    }

    static class ViewHolder{
        public TextView tvMusicTitle;
        public TextView tvMusicArtist;
    }
}
