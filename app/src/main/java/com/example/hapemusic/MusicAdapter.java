package com.example.hapemusic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MusicAdapter extends ArrayAdapter<Music> {
    private int resourceId;
    public MusicAdapter(Context context, int musicItemResourceId, List<Music> objects){
        super(context,musicItemResourceId,objects);
        resourceId = musicItemResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Music music = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.albumImage = view.findViewById(R.id.item_album_image);
            viewHolder.musicName = view.findViewById(R.id.music_name);
            viewHolder.singerName = view.findViewById(R.id.singer_name);
            view.setTag(viewHolder);
        }
        else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.albumImage.setImageResource(music.getAlbumImageId());
        viewHolder.musicName.setText(music.getMusicName());
        viewHolder.singerName.setText(music.getSingerName());
        return view;
    }
    class ViewHolder{
        ImageView albumImage;
        TextView musicName;
        TextView singerName;
    }
}
