package com.example.olive.gtaradio;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by olive on 31/12/2017.
 */

public class Song {

    private MediaPlayer player = new MediaPlayer();

    //the song that's being played
    private static Song playingSong;

    /**
     * Constructor creates the mediaplayer
     * @param assetFileDescriptor
     */
    public Song(AssetFileDescriptor assetFileDescriptor, String string) {
        try {
            player.setDataSource(assetFileDescriptor.getFileDescriptor(),
                    assetFileDescriptor.getStartOffset(),
                    assetFileDescriptor.getLength());
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
//            System.out.println(string);
        }
    }

    public void play(int position) {
        if (playingSong != null) {
            playingSong.stop();
        }

        playingSong = this;
        if (position != 0) {
            player.seekTo(position);
        }
        player.start();
    }

    public int getDuration() {
        return player.getDuration();
    }

    void stop() {
        player.pause();

    }

}

