package com.example.hapemusic;

public class Music {
    private String musicName;
    private String singerName;
    private int albumImageId;
    private boolean isFromResource;

    public Music(String musicName, String singerName, boolean isFromResource){
        this.musicName = musicName;
        this.singerName = singerName;
        this.albumImageId = R.drawable.image;
        this.isFromResource =isFromResource;
    }
    public Music(String musicName, String singerName, int albumImageId, boolean isFromResource){
        this.musicName = musicName;
        this.singerName = singerName;
        this.albumImageId = albumImageId;
        this.isFromResource =isFromResource;
    }

    public String getMusicName(){
        return musicName;
    }
    public String getSingerName(){
        return singerName;
    }
    public int getAlbumImageId(){
        return albumImageId;
    }
    public boolean getIsFromResource(){return isFromResource;}

}
