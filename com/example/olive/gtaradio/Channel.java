package com.example.olive.gtaradio;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by olive on 31/12/2017.
 */

public class Channel{

    private static Channel showingChannel;
    private List<Song> songs = new ArrayList<Song>();

    private int currentSong = 0;

    private Timer timer;
    private TimerTask timerTask;
    private int currentSongTime = 0; //The position of the current time

    /**
     * Initiates the channel object
     * @param activity the mainactivity
     * @param string The name of the folder
     */
    public Channel(MainActivity activity, String string) {


        //Setting up the songs
        try {
            AssetManager manager = activity.getAssets();
            String[] names = manager.list(string);

            //Looping through the folder
            for (String s : names) {
                String string1 = string.concat("/" + s);
                AssetFileDescriptor assetFileDescriptor = manager.openFd(string1);
                Song song = new Song(assetFileDescriptor);
                songs.add(song);
            }

            //Shuffling the songs
            Collections.shuffle(songs);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        final int fps = 50;

        //Setting up the timer
        timer = new Timer(true);
        final Channel channel = this;
        timerTask = new TimerTask() {
            @Override
            public void run() {
                try {


                    currentSongTime += fps;
                    if (currentSongTime > 10000) {// >= songs.get(currentSong).getDuration()) {
                        currentSong++;
                        currentSongTime = 0;
                        if (currentSong >= songs.size()) {
                            currentSong = 0;
                        }

                        if (showingChannel != null && showingChannel.equals(channel)) {
                            songs.get(currentSong).play(currentSongTime);

                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        timer.scheduleAtFixedRate(timerTask, 0, fps);
    }

    /**
     * Shows that this channel was selected
     */
    public void select() {
        if (showingChannel == null || !showingChannel.equals(this)) {
            showingChannel = this;


            Song song = songs.get(currentSong);
            song.play(currentSongTime);
        }

    }


}
