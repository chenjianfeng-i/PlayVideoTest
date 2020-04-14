package com.example.playvideotest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoView = findViewById(R.id.video_view);
        Button play = findViewById(R.id.play);
        Button pause = findViewById(R.id.pause);
        Button stop = findViewById(R.id.replay);
        RadioGroup rg = findViewById(R.id.rg);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);
        rg.setOnCheckedChangeListener(onCheckedChangeListener);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            initVideoPath("2016年6月18日，2016年厦门大学本科生毕业典礼教师发言 - 邹振东教授.mp4");
        }
    }

    private RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (group.getCheckedRadioButtonId()){
                case R.id.rb_1:
                    initVideoPath("2016年6月18日，2016年厦门大学本科生毕业典礼教师发言 - 邹振东教授.mp4");
                    break;
                case R.id.rb_2:
                    initVideoPath("2019年11月11日，14：09，#焰火花园#各种天然可用的容器：绿植与试管相结合的搭配，简单又巧妙打造了极简的植物美学.mp4");
                    break;
                default:
                    break;
            }
        }
    };


    private void initVideoPath(String selectVideo){
        File file = new File(Environment.getExternalStorageDirectory(), selectVideo);
        videoView.setVideoPath(file.getPath());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    initVideoPath("2016年6月18日，2016年厦门大学本科生毕业典礼教师发言 - 邹振东教授.mp4");
                } else {
                    Toast.makeText(this, "拒绝权限将无法使用程序", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.play:
                if (!videoView.isPlaying()){
                    // 开始播放
                    videoView.start();
                }
                break;
            case R.id.pause:
                if (videoView.isPlaying()){
                    // 暂停播放
                    videoView.pause();
                }
                break;
            case R.id.replay:
                if (videoView.isPlaying()){
                    // 停止播放
                    videoView.resume();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoView != null){
            videoView.suspend();
        }
    }
}
