package com.example.myapplication.entity;

public class Music {

    private String name;
    private String path;
    private boolean status;
    private int cover;

    public Music() {
    }

    public Music(String name, String path, boolean status, int cover) {
        this.name = name;
        this.path = path;
        this.status = status;
        this.cover = cover;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getCover() {
        return cover;
    }

    public void setCover(int cover) {
        this.cover = cover;
    }

    @Override
    public String toString() {
        return "Music{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", status=" + status +
                ", cover=" + cover +
                '}';
    }
}
