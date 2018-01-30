package com.bankapalli.stopwatch;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.os.Handler;

import java.util.Locale;

public class StopWatch extends Activity {

    private int seconds = 0;

    private boolean running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch);

        runTimer();
    }

    public void startCallback(View view) {
        running = true;
    }

    public void stopCallback(View view) {
        running = false;
    }

    public void resetCallback(View view) {
        running = false;
        seconds = 0;
    }

    private void runTimer() {
        final TextView lblTimer = (TextView) findViewById(R.id.lblTimer);
        final Handler handler = new Handler ();

        handler.post (new Runnable () { //Run the code immediately
            @Override
            public void run () {
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;
                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
                lblTimer.setText(time);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this,1000); //Run this code after every 100 milliseconds
            }
        });
    }
}
