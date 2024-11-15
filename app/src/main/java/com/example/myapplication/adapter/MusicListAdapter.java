package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.entity.Music;


import java.util.List;

public class MusicListAdapter extends ArrayAdapter<Music> {
    private List<Music> musicList;
    private Context context;
    private int resourceId;
    public MusicListAdapter(@NonNull Context context, int resource, @NonNull List<Music> list) {
        super(context, resource,  list);
        this.context = context;
        this.musicList = list;
        this.resourceId=resource;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Music music = musicList.get(position);
        View view = LayoutInflater.from(context).inflate(resourceId,parent,false);
        MusicListAdapter.MusicViewHolder viewHolder = new MusicListAdapter.MusicViewHolder(view);
        viewHolder.tvMusicName.setText(music.getName());
        if(music.isStatus()){
            viewHolder.tvMusicName.setTextColor(Color.parseColor("#03A9F4"));
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemSelectListener.choose(position);
            }

        });
        return view;
    }
    public OnItemSelectListener itemSelectListener;
    public interface OnItemSelectListener{
        public void choose(int position);
    }
    public OnItemSelectListener getItemSelectListener() {
        return itemSelectListener;
    }
    public void setItemSelectListener(OnItemSelectListener itemSelectListener) {
        this.itemSelectListener = itemSelectListener;
    }
    class MusicViewHolder {
        private TextView tvMusicName;
        private View itemView;
        public MusicViewHolder(View itemView){
            tvMusicName = (TextView) itemView.findViewById(R.id.tv_music_name);
            this.itemView = itemView;
        }
    }
}
