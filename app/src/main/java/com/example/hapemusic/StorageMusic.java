package com.example.hapemusic;

public class StorageMusic extends Music {
    private String path;
    private long size;
    public StorageMusic(String musicName, String singerName, String path, long size){
        super(musicName, singerName, false);
        this.path = path;
        this.size = size;
    }
    public StorageMusic(String musicName, String singerName, int albumImageId, String path, long size){
        super(musicName, singerName, albumImageId, false);
        this.path = path;
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public long getSize() {
        return size;
    }

}
