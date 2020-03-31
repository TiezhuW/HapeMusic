package com.example.hapemusic;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView albumImage;
    private Button playBtn;
    private Button last;
    private Button next;
    private Button musicListButton;
    private Button search;
    private SeekBar volumeBar;
    private SeekBar positionBar;
    private TextView song;
    private TextView singer;
    private TextView elapsedTimeLabel;
    private TextView remainingTimeLabel;
    private MediaPlayer mp;
    int totalTime;
    // 用于判断当前的播放顺序，0->单曲循环,1->顺序播放,2->随机播放
    private int play_style;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.developer:
                Intent intent = new Intent(MainActivity.this,DeveloperActivity.class);
                startActivity(intent);
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListActivity.StartListTimes = 0;
        ListActivity.pos = 0;
        play_style = 0;

        albumImage = findViewById(R.id.album_image);
        playBtn = findViewById(R.id.playBtn);
        last = findViewById(R.id.last);
        next = findViewById(R.id.next);
        musicListButton = findViewById(R.id.music_list_button);
        singer = findViewById(R.id.singer);
        song = findViewById(R.id.song);
        elapsedTimeLabel = findViewById(R.id.elapsedTimeLabel);
        remainingTimeLabel = findViewById(R.id.remainingTimeLabel);

        albumImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                play_style = (play_style + 1) % 2;
                switch (play_style) {
                    case 0:
                        Toast.makeText(MainActivity.this, "ORDER", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(MainActivity.this, "RANDOM", Toast.LENGTH_SHORT).show();
                        break;
//                    case 2:
//                        Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
//                        break;
                    default:
                        break;
                }
            }
        });
        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ListActivity.StartListTimes == 0){
                    Toast.makeText(MainActivity.this,"Please open the music list at first to search music on your device~",Toast.LENGTH_SHORT).show();
                }else{
                    if(play_style == 1){
                        Random random = new Random();
                        ListActivity.pos =  random.nextInt(ListActivity.musicList.size());
                    }else{
                        ListActivity.pos = (ListActivity.pos + ListActivity.musicList.size() - 1) % ListActivity.musicList.size();
                    }
                    if (mp.isPlaying()) {
                        mp.pause();
                        playBtn.setBackgroundResource(R.drawable.play);
                    }
                    if(ListActivity.musicList.get(ListActivity.pos).getIsFromResource()){
                        int music_id = ((ResourceMusic) (ListActivity.musicList.get(ListActivity.pos))).getMusicId();
                        mp = MediaPlayer.create(MainActivity.this, music_id);
                    }else{
                        String path = ((StorageMusic) (ListActivity.musicList.get(ListActivity.pos))).getPath();
                        mp = MediaPlayer.create(MainActivity.this, Uri.parse(path));
                    }
                    albumImage.setImageResource(ListActivity.musicList.get(ListActivity.pos).getAlbumImageId());
                    song.setText(ListActivity.musicList.get(ListActivity.pos).getMusicName());
                    singer.setText(ListActivity.musicList.get(ListActivity.pos).getSingerName());
                    mp.setLooping(true);
                    mp.seekTo(0);
                    mp.setVolume(0.5f, 0.5f);
                    totalTime = mp.getDuration();
                    mp.start();
                    playBtn.setBackgroundResource(R.drawable.stop);
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ListActivity.StartListTimes == 0){
                    Toast.makeText(MainActivity.this,"Please open the music list at first to search music on your device~",Toast.LENGTH_SHORT).show();
                }else{
                    if(play_style == 1){
                        Random random = new Random();
                        ListActivity.pos =  random.nextInt(ListActivity.musicList.size());
                    }else{
                        ListActivity.pos = (ListActivity.pos + 1) % ListActivity.musicList.size();
                    }
                    if (mp.isPlaying()) {
                        mp.pause();
                        playBtn.setBackgroundResource(R.drawable.play);
                    }
                    if(ListActivity.musicList.get(ListActivity.pos).getIsFromResource()){
                        int music_id = ((ResourceMusic) (ListActivity.musicList.get(ListActivity.pos))).getMusicId();
                        mp = MediaPlayer.create(MainActivity.this, music_id);
                    }else{
                        String path = ((StorageMusic) (ListActivity.musicList.get(ListActivity.pos))).getPath();
                        mp = MediaPlayer.create(MainActivity.this, Uri.parse(path));
                    }
                    albumImage.setImageResource(ListActivity.musicList.get(ListActivity.pos).getAlbumImageId());
                    song.setText(ListActivity.musicList.get(ListActivity.pos).getMusicName());
                    singer.setText(ListActivity.musicList.get(ListActivity.pos).getSingerName());
                    mp.setLooping(true);
                    mp.seekTo(0);
                    mp.setVolume(0.5f, 0.5f);
                    totalTime = mp.getDuration();
                    mp.start();
                    playBtn.setBackgroundResource(R.drawable.stop);
                }
            }
        });
        musicListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ListActivity.class);
                startActivity(intent);
            }
        });
        search = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,WebActivity.class);
                startActivity(intent);
            }
        });

        singer.setText("Bag Raiders");
        song.setText("Shooting Stars");
        albumImage.setImageResource(R.drawable.album1);
        mp=MediaPlayer.create(this,R.raw.music1);
        mp.setLooping(true);
        mp.seekTo(0);
        mp.setVolume(0.5f,0.5f);
        totalTime=mp.getDuration();

        positionBar = findViewById(R.id.positionBar);
        positionBar.setMax(totalTime);
        positionBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if(fromUser){
                            //根据seekBar的progress更新音乐进度
                            mp.seekTo(progress);
                            positionBar.setProgress(progress);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }

        );

        volumeBar = findViewById(R.id.volumnBar);
        volumeBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar,int progress,boolean fromUser) {
                        float volumeNum = progress / 100f;
                        mp.setVolume(volumeNum, volumeNum);
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );
        //Thread(Update positionBar & timeLabel)
        new Thread(new Runnable(){
            @Override
            public void run(){
                while(mp!=null){
                    try {
                        Message msg=new Message();
                        //获取音乐播放的进度
                        msg.what=mp.getCurrentPosition();
                        handler.sendMessage(msg);
                        Thread.sleep(1000);

                    }catch (InterruptedException e) {}
                }
            }
        }).start();

    }

    //根据msg更新播放进度/进度条和时间
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int currentPosition=msg.what;
            //update positionBar
            positionBar.setProgress(currentPosition);

            //update labels
            String elapsedTime=createTimeLabel(currentPosition);
            elapsedTimeLabel.setText(elapsedTime);

            String remainingTime=createTimeLabel(totalTime-currentPosition);
            remainingTimeLabel.setText("-"+remainingTime);

        }
    };

    public String createTimeLabel(int time){
        String timeLabel="";
        int min=time/1000/60;
        int sec=time/1000%60;

        timeLabel=min+":";
        if(sec<10)
            //格式化输出
            timeLabel+="0";
        timeLabel+=sec;

        return timeLabel;

    }

    public void playBtnClick(View view){
        if(!mp.isPlaying()){
            mp.start();
            playBtn.setBackgroundResource(R.drawable.stop);
        }else{
            mp.pause();
            playBtn.setBackgroundResource(R.drawable.play);
        }
    }
    public void lowerClick(View v){
        if(volumeBar.getProgress()==0){
            volumeBar.setProgress(10);
            mp.setVolume(0.1f,0.1f);
        }else{
            volumeBar.setProgress(0);
            mp.setVolume(0,0);
        }
    }
    public void higherClick(View v){
        volumeBar.setProgress(100);
        mp.setVolume(1.0f,1.0f);
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        if(ListActivity.fromListFlag == true){
            if(mp.isPlaying()){
                mp.pause();
                playBtn.setBackgroundResource(R.drawable.play);
            }
            if(ListActivity.musicList.get(ListActivity.pos).getIsFromResource()){
                int music_id = ((ResourceMusic) (ListActivity.musicList.get(ListActivity.pos))).getMusicId();
                mp = MediaPlayer.create(MainActivity.this, music_id);
            }else{
                String path = ((StorageMusic) (ListActivity.musicList.get(ListActivity.pos))).getPath();
                mp = MediaPlayer.create(MainActivity.this, Uri.parse(path));
            }
            albumImage.setImageResource(ListActivity.musicList.get(ListActivity.pos).getAlbumImageId());
            song.setText(ListActivity.musicList.get(ListActivity.pos).getMusicName());
            singer.setText(ListActivity.musicList.get(ListActivity.pos).getSingerName());
            mp.setLooping(true);
            mp.seekTo(0);
            mp.setVolume(0.5f,0.5f);
            totalTime=mp.getDuration();
            mp.start();
            playBtn.setBackgroundResource(R.drawable.stop);
        }
        ListActivity.fromListFlag = false;
    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("Notice:");
        dialog.setMessage("Do you want to exit?");
        dialog.setCancelable(true);
        dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mp.stop();
        mp.release();
    }
}
