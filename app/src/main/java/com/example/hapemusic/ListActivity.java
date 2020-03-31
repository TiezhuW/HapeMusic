package com.example.hapemusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    public static int pos;//传参
    public static int StartListTimes;//记录访问List次数
    public static boolean fromListFlag;//标志是否从List跳转
    public static List<Music> musicList = new ArrayList<Music>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.developer:
                Intent intent = new Intent(ListActivity.this,DeveloperActivity.class);
                startActivity(intent);
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        if(StartListTimes == 0){
            initResMusics();
            initStorageMusics(this);
        }
        MusicAdapter adapter = new MusicAdapter(this,R.layout.music_item,musicList);
        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                if(pos != position){
                    pos = position;
                    fromListFlag = true;
                    Intent intent = new Intent(ListActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void initResMusics(){//添加res歌曲
        ResourceMusic music1 = new ResourceMusic("Shooting Stars","Bag Raiders",R.drawable.album1, R.raw.music1);
        musicList.add(music1);
        ResourceMusic music2 = new ResourceMusic("Love the Way You Lie","Rihanna",R.drawable.album2, R.raw.music2);
        musicList.add(music2);
        ResourceMusic music3 = new ResourceMusic("Ezio's Family","Jesper Kyd", R.drawable.album3, R.raw.music3);
        musicList.add(music3);
        ResourceMusic music4 = new ResourceMusic("Casablanca","Bertie Higgins", R.drawable.album4, R.raw.music4);
        musicList.add(music4);
    }

    public void initStorageMusics(Context context) {//添加存储中歌曲
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                MediaStore.Audio.AudioColumns.IS_MUSIC);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String musicName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
                String singerName = musicName;
                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
                if (size > 1000 * 800) {//过滤掉短音频
                    // 分离出歌曲名和歌手
                    if (musicName.contains("-")) {
                        String[] str = musicName.split("-");
                        singerName = str[0];
                        musicName = str[1];
                        //除去文件格式后缀
                        if (musicName.endsWith(".mp3")) {
                            musicName = musicName.substring(0,musicName.length()-".mp3".length());
                        }
                        if (musicName.endsWith(".flac")) {
                            musicName = musicName.substring(0,musicName.length()-".flac".length());
                        }
                    }
                    StorageMusic music = new StorageMusic(musicName,singerName,path,size);
                    musicList.add(music);
                }
            }
            // 释放资源
            cursor.close();
}
    }

    protected void onDestroy(){
        super.onDestroy();
        StartListTimes ++;
    }
}
