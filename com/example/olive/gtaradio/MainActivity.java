package com.example.olive.gtaradio;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AssetManager manager;

    //Le buttons
    private Button nonStopPopButton;
    private Button losSantosRockButton;
    private Button channelXButton;
    private Button rebelRadioButton;

    private List<Button> buttons = new ArrayList<Button>();

    //Le channels
    private Channel nonStopPop;
    private Channel losSantosRock;
    private Channel channelX;
    private Channel rebelRadio;

    private boolean started = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println(started);
        if (!started) {
            started = true;
            manager = getAssets();

            //Setting up the buttons
            this.nonStopPopButton = findViewById(R.id.nonStopPopButton);
            this.losSantosRockButton = findViewById(R.id.losSantosRock);
            this.channelXButton = findViewById(R.id.channelX);
            this.rebelRadioButton = findViewById(R.id.rebelRadio);


            //Adding all the buttons to the list
            this.buttons.addAll(
                    Arrays.asList(nonStopPopButton, losSantosRockButton, channelXButton, rebelRadioButton)
            );

            //Initialising the channels
            nonStopPop = new Channel(this, "nonstop");
            losSantosRock = new Channel(this, "losSantosRock");
            channelX = new Channel(this, "channelX");
            rebelRadio = new Channel(this, "rebelRadio");
        }



    }


    public void turnOff(View view) {
        Channel.turnOffRadio();
        resetButtons();
    }

    public void buttonPressed(View view) {

        final View viewCopy = view;

        resetButtons();

        //Setting up the correct colours
        try {
            Button button = (Button)view;
            button.setTextColor(Color.WHITE);
            button.setBackgroundColor(Color.BLACK);
        }
        catch (ClassCastException ccex) {
            ccex.printStackTrace();
        }

        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                //Selecting the right channel
                if (viewCopy.equals(nonStopPopButton)) {
                    nonStopPop.select();
                }
                else if (viewCopy.equals(losSantosRockButton)) {
                    losSantosRock.select();
                }
                else if (viewCopy.equals(channelXButton)) {
                    channelX.select();
                }
                else if (viewCopy.equals(rebelRadioButton)) {
                    rebelRadio.select();
                }
                return null;
            }
        };

        task.execute();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        started = false;
        Channel.turnOffRadio();
    }


    private void resetButtons() {
        for (Button button : buttons) {
            button.setBackgroundColor(Color.WHITE);
            button.setTextColor(Color.BLACK);
        }
    }
}
