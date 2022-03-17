package com.example.st.util;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

public class MediaUtil {

    /**
     * 打开assets下的音乐mp3文件
     */
    public  static void openAssetMusics(Context context) {
        //打开Asset目录
        AssetManager assetManager = context.getAssets();
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            //打开音乐文件shot.mp3
            AssetFileDescriptor assetFileDescriptor = assetManager.openFd("water_ring.mp3");
            mediaPlayer.reset();
            //设置媒体播放器的数据资源
            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("openAssetMusics", "IOException" + e.toString());
        }
    }
}
