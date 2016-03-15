package com.test.musicplayer.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/3/15.
 */
public class MusicInfo implements Parcelable{

    private String title;
    private String artist;

    public MusicInfo(){}

    public MusicInfo(String title,String artist){
        this.title = title;
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(title);
        dest.writeString(artist);
    }

    public static final Parcelable.Creator<MusicInfo> CREATOR = new Creator<MusicInfo>() {
        @Override
        public MusicInfo createFromParcel(Parcel source) {

            MusicInfo musicInfo = new MusicInfo();
            musicInfo.setTitle(source.readString());
            musicInfo.setArtist(source.readString());
            return musicInfo;
        }

        @Override
        public MusicInfo[] newArray(int size) {
            return new MusicInfo[size];
        }
    };
}
