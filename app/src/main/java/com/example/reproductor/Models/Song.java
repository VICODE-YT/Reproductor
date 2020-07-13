package com.example.reproductor.Models;

public class Song {

    private String songName = "Unknown name song";
    private String author = "Unkown author";

    private String path;

    private boolean stared = false;

    public Song(String songName, String author, String path) {
        this.songName = songName;
        this.author = author;
        this.path = path;
    }

    public Song(String songName, String author, String path, boolean stared) {
        this.songName = songName;
        this.author = author;
        this.path = path;
        this.stared = stared;
    }

    public String getSongName() {
        return songName;
    }

    public String getAuthor() {
        return author;
    }

    public String getPath() {
        return path;
    }

    public boolean isStared() {
        return stared;
    }

    public void setStared(boolean stared) {
        this.stared = stared;
    }
}
