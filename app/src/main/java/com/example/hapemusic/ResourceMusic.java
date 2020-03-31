package com.example.hapemusic;

public class ResourceMusic extends Music {
    private int musicId;
    public ResourceMusic(String musicName, String singerName, int albumImageId, int musicId){
        super(musicName, singerName, albumImageId,true);
        this.musicId = musicId;
    }
    public ResourceMusic(String musicName, String singerName, int musicId){
        super(musicName, singerName, true);
        this.musicId = musicId;
    }
    public int getMusicId(){
        return musicId;
    }
}
