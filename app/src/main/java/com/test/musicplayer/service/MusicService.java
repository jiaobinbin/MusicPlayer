package com.test.musicplayer.service;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;

import com.test.musicplayer.activity.Main;
import com.test.musicplayer.entity.MusicInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/15.
 */
public class MusicService extends Service{

    private List<String> listTitle = new ArrayList<>();
    private List<String> listArtist = new ArrayList<>();
    private List<Map<String,Object>> listItems = new ArrayList<>();
    private List<MusicInfo> musicList = new ArrayList<MusicInfo>();

    String[] mCursorCols = new String[] {
            "audio._id AS _id", // index must match IDCOLIDX below
            MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.MIME_TYPE, MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.ARTIST_ID, MediaStore.Audio.Media.DURATION
    };
    private Cursor mCursor;
    private int mPlayPosition = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("MusicPlayer", "Service onCreate");
        Uri MUSIC_URL = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        mCursor = getContentResolver().query(MUSIC_URL, mCursorCols, "duration > 10000", null, null);
        /*Intent it = new Intent();
        it.setAction(Main.ACTION_MUSICLIST);*/

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.v("MusicPlayer", "Service onStartCommand");
        Intent it = new Intent();
        it.setAction(Main.ACTION_MUSICLIST);
        it.putExtra("msg","11111");
        listTitle.clear();
        listArtist.clear();
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,mCursorCols,"duration > 10000", null, null);
        if(cursor == null){
            Log.v("MusicPlayer", "cursor is null");
        }else if(!cursor.moveToFirst()){
            Log.v("MusicPlayer","cursor also is null");
        }else{

            int titleCol = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int artistCol = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            do{
                String title = cursor.getString(titleCol);
                String artist = cursor.getString(artistCol);

                MusicInfo musicInfo = new MusicInfo();
                musicInfo.setTitle(title);
                musicInfo.setArtist(artist);
                musicList.add(musicInfo);

                listTitle.add(title);
                listArtist.add(artist);
            }while(cursor.moveToNext());
        }
        for(int i = 0;i < listTitle.size();i++){
            Map<String,Object> listItem = new HashMap<String, Object>();
            listItem.put("title",listTitle.get(i));
            listItem.put("artist",listArtist.get(i));
            listItems.add(listItem);
        }
       // it.putParcelableArrayListExtra("MusicList",listItems );
        it.putExtra("musicList",(Serializable)listItems);
        sendBroadcast(it);
        return START_STICKY;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v("MusicPlayer","Service onDestory");
    }
}
