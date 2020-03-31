package com.example.eggtimer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBar;
    Button button;
    TextView textView;
    ImageView imageView;

    CountDownTimer countDownTimer;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);
        imageView = (ImageView) findViewById(R.id.imageView);

        seekBar.setProgress(30);
       // seekBar.setMin(1);
        seekBar.setMax(300);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                myOwnTimerText(progress);
               // setTimerText(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            boolean isTimerRunning = false;
            @Override
            public void onClick(View v) {
                if (!isTimerRunning) {
                    startTimer();
                    button.setText("Stop");
                    isTimerRunning = true;
                } else {
                    stopTimer();
                    button.setText("Start");
                    isTimerRunning = false;
                }

            }
        });

    }

    private void myOwnTimerText(int progress) {

        String time;
        String secondTime = Integer.toString(progress);
        String minuteTime;

        if ( progress % 60 >= 0) {
            if (progress / 60 < 10) {
                minuteTime = "0" + Integer.toString(progress / 60);
            }else{
                minuteTime = Integer.toString(progress/60);
            }
            if (progress % 60 < 10) {
                secondTime = "0" + Integer.toString(progress % 60);
            } else {
                secondTime = Integer.toString(progress % 60);
            }
            time = minuteTime + ":" + secondTime;
            textView.setText(time);
        } else {
            textView.setText("00:"+Integer.toString(progress));
        }
    }

    private void setTimerText(int progress) {
        int minutes = progress / 60;
        int seconds = progress - (minutes * 60);

        String firstString = Integer.toString(minutes);
        String secondString = Integer.toString(seconds);

        if (minutes < 10) {
            firstString = "0" + Integer.toString(minutes);
        }
        if (secondString.equals("0")) {
            secondString = "00";
        } else if (seconds < 10) {
            secondString = "0" + secondString;
        }
        textView.setText(firstString+":"+secondString);
    }

    public void startTimer() {
        Long longTime = new Long(seekBar.getProgress() * 1000);
        countDownTimer = new CountDownTimer(longTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                setTimerText(Integer.parseInt(String.valueOf(millisUntilFinished)) / 1000 );
                seekBar.setProgress(Integer.parseInt(String.valueOf(millisUntilFinished / 1000)));
            }

            @Override
            public void onFinish() {
                Toast.makeText(MainActivity.this, "countDown finished", Toast.LENGTH_SHORT).show();
                button.setText("Start");
                playSound();
            }
        };
        countDownTimer.start();
    }

    public void stopTimer() {
              countDownTimer.cancel();
    }

    private  void playSound() {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.rooster_morning_sound);
        mediaPlayer.start();
    }


}
