package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.adapter.MusicListAdapter;
import com.example.myapplication.entity.Music;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MusicActivity extends AppCompatActivity {
    private ImageView imgMusic;
    private AppCompatSeekBar seekbar;
    private ImageView btnLast;
    private ImageView btnBack;
    private ImageView btnStartPause;
    private ImageView btnForward;
    private ImageView btnNext;
    //当前播放曲目
    private int currentPosition = 0;
    MediaPlayer player;
    //存放本地所有音乐
    public List<Music> musics = new ArrayList<>();
    private ListView lvMusics;
    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        initView();
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            // 请求存储权限
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 999);
        } else {
            // 已经获得存储权限
            // 在此处执行文件操作
            musicControlSeekBar();
            seekbarControlMusic();
            initData();
            bindData();
        }
    }

    // 处理权限请求结果
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 999 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            musicControlSeekBar();
            seekbarControlMusic();
            initData();
            bindData();
        }
    }

    //初始化界面
    public void initView(){
        imgMusic = (ImageView) findViewById(R.id.img_music);
        seekbar = (AppCompatSeekBar) findViewById(R.id.seekbar);
        btnLast = (ImageView) findViewById(R.id.btn_last);
        btnBack = (ImageView) findViewById(R.id.btn_back);
        btnStartPause = (ImageView) findViewById(R.id.btn_start_pause);
        btnForward = (ImageView) findViewById(R.id.btn_forward);
        btnNext = (ImageView) findViewById(R.id.btn_next);
        lvMusics = (ListView) findViewById(R.id.lv_musics);

        Glide.with(this).load(R.drawable.beyond)
                .circleCrop()
                .into(imgMusic);
    }

    //初始化数据
public void initData(){
    File path =new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC) + "/mymusic");
    File[] files = path.listFiles();
    for(File file:files){
        musics.add(new Music(file.getName(),file.getPath(),false,0));
    }
    musics.get(0).setStatus(true);
}

    private MusicListAdapter adapter;

    //绑定数据
public void bindData(){
    adapter = new MusicListAdapter(this,R.layout.item_music,musics);
    adapter.setItemSelectListener(new MusicListAdapter.OnItemSelectListener() {
        @Override
        public void choose(int position) {
            currentPosition = position;
            changeMusic(currentPosition);
            updateMusicStatus(currentPosition);
        }
    });
    lvMusics.setAdapter(adapter);
}
    //音乐控制面板
    public void musicControlSeekBar(){
        btnStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    musicStartOrPause();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(player == null){
                    Toast.makeText(MusicActivity.this,"请先播放",Toast.LENGTH_SHORT).show();
                }else if(player.getCurrentPosition()-10000<=0){
                    player.seekTo(0);
                }else{
                    player.seekTo(player.getCurrentPosition()-10000);
                }
            }
        });

        btnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(player == null){
                    Toast.makeText(MusicActivity.this,"请先播放",Toast.LENGTH_SHORT).show();
                }else if(player.getCurrentPosition()+10000>=player.getDuration()){
                    player.seekTo(player.getDuration());
                }else{
                    player.seekTo(player.getCurrentPosition()+10000);
                }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentPosition<musics.size()-1){
                    currentPosition +=1;
                }else{
                    currentPosition =0;
                }
                changeMusic(currentPosition);
            }
        });

        btnLast.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(currentPosition>0){
                    currentPosition -=1;
                }else{
                    currentPosition =musics.size()-1;
                }
                changeMusic(currentPosition);
            }
        });

    }

//    切换歌曲
    public void changeMusic(int position){
        if(player != null){
            player.pause();
            player.release();
        }
        player = new MediaPlayer();
        try {
            player.setDataSource(musics.get(currentPosition).getPath());
            player.prepare();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        player.start();
        btnStartPause.setImageResource(R.drawable.stop);
        updateMusicStatus(currentPosition);
    }

    //拖动进度条改变音乐进度
    public void seekbarControlMusic(){
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                player.seekTo(i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
//                time.cancel();
                handler = null;

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                player.seekTo(seekBar.getProgress());
//                startTimer();
                handler = new Handler(Looper.getMainLooper()){
                    @Override
                    public void handleMessage(@NonNull Message msg) {
                        seekbar.setProgress(msg.arg1);
                        updateProgress();
                    }
                };
            }
        });
    }

    //音乐的启停
    public void musicStartOrPause() throws IOException {
//        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
//        File file = new File(path,"情人.mp3");
        String  path = musics.get(0).getPath();
        if(player == null){
            player = new MediaPlayer();
            try {
                player.setDataSource(path);
                player.prepare();
                btnStartPause.setImageResource(R.drawable.stop);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    seekbar.setProgress(0);
                    handler = null;
                    btnStartPause.setImageResource(R.drawable.start);
                }
            });
            seekbar.setMax(player.getDuration());
            player.start();

//            startTimer();
            updateProgress();
        }else if(player.isPlaying()){
            player.pause();
            btnStartPause.setImageResource(R.drawable.start);
        }else{
            player.start();
            updateProgress();
            btnStartPause.setImageResource(R.drawable.stop);
        }
    }



    //更新进度条
    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            seekbar.setProgress(msg.arg1);
            updateProgress();
        }
    };

    public void updateProgress(){
        Message message = Message.obtain();
        message.arg1 = player.getCurrentPosition();
        if(handler != null){
            handler.sendMessageDelayed(message,100);
        }
    }


    //更新音乐列表状态
    private void updateMusicStatus(int currentPosition){
        for(int i=0;i<musics.size();i++){
            if(i==currentPosition){
                musics.get(i).setStatus(true);
            }else{
                musics.get(i).setStatus(false);
            }

        }
        if(adapter != null){
            adapter.notifyDataSetChanged();
        }
    }

}