package com.test.musicplayer.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.test.musicplayer.R;
import com.test.musicplayer.adapter.MusicLisBaseAdapter;
import com.test.musicplayer.service.MusicService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by Administrator on 2016/3/15.
 */
public class Main extends AppCompatActivity{

    private ListView listView;
    private Button btn;
    private MusicLisBaseAdapter musicLisBaseAdapter;
    private List<String> listTitle = new ArrayList<>();
    private List<String> listArtist = new ArrayList<>();
    private List<Map<String,Object>> listItems = new ArrayList<>();

    MusicListBroadcastReceive broadcastReceive;
    public static final String ACTION_MUSICLIST = "action.musiclist";

    String[] mCursorCols = new String[]{
            "audio._id AS _id",
            MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.MIME_TYPE, MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.ARTIST_ID, MediaStore.Audio.Media.DURATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.lvMusicList);
        btn = (Button) findViewById(R.id.btnSearch);

        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_MUSICLIST);
        broadcastReceive = new MusicListBroadcastReceive();
        registerReceiver(broadcastReceive,filter);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  listTitle.clear();
                listArtist.clear();
                Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,mCursorCols,"duration > 10000", null, null);
                if(cursor == null){
                    Log.v("MusicPlayer","cursor is null");
                }else if(!cursor.moveToFirst()){
                    Log.v("MusicPlayer","cursor also is null");
                }else{

                    int titleCol = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
                    int artistCol = cusor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                    do{
                        String title = cursor.getString(titleCol);
                        String artist = cursor.getString(artistCol);
                        listTitle.add(title);
                        listArtist.add(artist);
                    }while(cursor.moveToNext());
                }
                for(int i = 0;i < listTitle.size();i++){
                    Map<String,Object> listItem = new HashMap<String, Object>();
                    listItem.put("title",listTitle.get(i));
                    listItem.put("artist",listArtist.get(i));
                    listItems.add(listItem);
                }*/
                Intent it = new Intent(Main.this, MusicService.class);
                startService(it);
               // listView.setAdapter(new MusicLisBaseAdapter(getBaseContext(),listItems));
            }
        });
    }

    private class MusicListBroadcastReceive extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

             btn.setText(intent.getStringExtra("msg"));
             listItems = (List<Map<String, Object>>) intent.getSerializableExtra("musicList");
             listView.setAdapter(new MusicLisBaseAdapter(getBaseContext(),listItems));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceive);
        Log.v("MusicPlayer","onDestory  unregisterReceiver()");
    }
}
