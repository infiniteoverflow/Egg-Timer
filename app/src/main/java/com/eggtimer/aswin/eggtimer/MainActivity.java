package com.eggtimer.aswin.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView timerTextView;
    String timeString = "";
    SeekBar timerSeekBar;
    boolean isActive = false;
    Button goButton;
    CountDownTimer countDownTimer;
    int currentValue;

    public void onClickButton(View view)
    {
        if(isActive)
        {
            timerSeekBar.setEnabled(true);
            goButton.setText("GO !!");
            countDownTimer.cancel();
            timerSeekBar.setProgress(currentValue);
            isActive = false;
        }
        else {
            goButton.setText("STOP !!");
            timerSeekBar.setEnabled(false);
            isActive = true;
            int progress = timerSeekBar.getProgress();
            startTimer(progress);
        }
    }

    public void updateTimer(int progress)
    {

        int minute = progress/60;
        int second = progress - (minute*60);
        timeString = "";

        if(second<10)
        {
            timeString += Integer.toString(minute)+" : "+"0"+Integer.toString(second);
        }
        else
        {
            timeString += Integer.toString(minute)+" : "+Integer.toString(second);
        }

        timerTextView.setText(timeString);

    }

    public void startTimer(int progress)
    {
        countDownTimer = new CountDownTimer(progress*1000+100,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                currentValue = (int)millisUntilFinished/1000;
                updateTimer(currentValue);
            }

            @Override
            public void onFinish() {

                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.airhorn);
                mediaPlayer.start();
                timerSeekBar.setEnabled(true);
                goButton.setText("GO !!");
                timerSeekBar.setProgress(currentValue);
                isActive = false;
            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = findViewById(R.id.timerSeekBar);
        timerTextView = findViewById(R.id.timerTextView);
        goButton = findViewById(R.id.goButton);

        currentValue = 30;
        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(currentValue);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentValue = progress;
                updateTimer(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
