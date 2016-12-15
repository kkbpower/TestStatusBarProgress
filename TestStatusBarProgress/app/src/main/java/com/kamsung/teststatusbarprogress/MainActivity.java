package com.kamsung.teststatusbarprogress;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final int id = 1;

        final NotificationManager mNotifyManager  =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        final Notification.Builder mBuilder = new Notification.Builder(this);
        mBuilder.setContentTitle("Picture Download")
                .setContentText("Download in progress")
                .setSmallIcon(R.mipmap.ic_launcher);
// Start a lengthy operation in a background thread
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        int incr;
                        // Do the "lengthy" operation 20 times
                        for (incr = 0; incr <= 100; incr+=5) {
                            // Sets the progress indicator to a max value, the
                            // current completion percentage, and "determinate"
                            // state
                            mBuilder.setProgress(100, incr, false);
                            // Displays the progress bar for the first time.
                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
                                mNotifyManager.notify(id, mBuilder.build());
                            }else{
                                mNotifyManager.notify(id, mBuilder.getNotification());
                            }
                            // Sleeps the thread, simulating an operation
                            // that takes time
                            try {
                                // Sleep for 5 seconds
                                Thread.sleep(5*1000);
                            } catch (InterruptedException e) {
                                Log.d("Run", "sleep failure");
                            }
                        }
                        // When the loop is finished, updates the notification
                        mBuilder.setContentText("Download complete")
                                // Removes the progress bar
                                .setProgress(0,0,false);
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
                            mNotifyManager.notify(id, mBuilder.build());
                        }else{
                            mNotifyManager.notify(id, mBuilder.getNotification());
                        }

                    }
                }
// Starts the thread by calling the run() method in its Runnable
        ).start();
    }
}
