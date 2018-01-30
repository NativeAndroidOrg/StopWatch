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

    // Slice 2 : When the app goes to background, we want to stop the stopwatch and when the app comes to foreground we want the stopwatch to resume
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch);

        // Slice 1 : If the device orientation is changed then the state is lost because activity is destroyed
        // Save the state of activity before the activity is destroyed
        // This saved state will be used in the onCreate method when the activity is recreated
        if (null != savedInstanceState) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            // Slice 2 : When the app goes to background, we want to stop the stopwatch and when the app comes to foreground we want the stopwatch to resume
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }

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

    // Slice 1 : If the device orientation is changed then the state is lost because activity is destroyed
    // Save the state of activity before the activity is destroyed
    // This saved state will be used in the onCreate method when the activity is recreated
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        // Slice 2 : When the app goes to background, we want to stop the stopwatch and when the app comes to foreground we want the stopwatch to resume
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    // Slice 2 : When the app goes to background, we want to stop the stopwatch and when the app comes to foreground we want the stopwatch to resume
    // onStop, is a lifecycle method of activity. It will get called when the activity stops being visible to the user
    @Override
    protected void onStop() {
        super.onStop();
        wasRunning = running;
        running = false;
    }

    // Slice 2 : When the app goes to background, we want to stop the stopwatch and when the app comes to foreground we want the stopwatch to resume
    // onStart, is a lifecycle method of activity. It will get called when the activity is about the become visible to the user
    @Override
    protected void onStart() {
        super.onStart();
        if (wasRunning) {
            running = true;
        }
    }
}
