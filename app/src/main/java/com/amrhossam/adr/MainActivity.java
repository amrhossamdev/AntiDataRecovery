package com.amrhossam.adr;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.amrhossam.adr.anti_datarecovery.R;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {
    private NotificationManager notifManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //By Amr Hossam//  fb.com/amrhossamdev


        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

        //to make program make one file in start and check permissions we don't need files to return null
        folders();
        images();

        deleteButton();
        mainProgress();


    }

    private void mainProgress() {
        SeekBar seekBar = findViewById(R.id.seek);
        final TextView txt = findViewById(R.id.txt);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(final SeekBar seekBar, final int progress,
                                          boolean fromUser) {
                txt.setText(String.valueOf(progress) + " Mb");
                final int v1 = Integer.parseInt(String.valueOf(progress));
                CardView button = findViewById(R.id.button);

                button.setOnClickListener(new View.OnClickListener() {
                    ScrollView scrollView = findViewById(R.id.scroll);

                    @Override
                    public void onClick(final View v) {
                        new Thread(new Runnable() {
                            public void run() {
                                if (v1 == 0) {
                                    Snackbar snackbar = Snackbar
                                            .make(scrollView, "Please Enter Valid Size", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                    return;

                                } else {

                                    Snackbar snackbar = Snackbar
                                            .make(scrollView, "Task Started Making Images With " + v1 + " Mb...", Snackbar.LENGTH_LONG);
                                    snackbar.show();

                                }

                                File dir = new File(Environment.getExternalStorageDirectory() + File.separator + "AntiDataRecovery", "Images");
                                try {
                                    FileUtils.deleteDirectory(dir);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                boolean truee = true;
                                try {
                                    while (truee) {

                                        File root = new File(Environment.getExternalStorageDirectory() + File.separator + "AntiDataRecovery", "Images");
                                        if (!root.exists()) {
                                            root.mkdirs();

                                        }
                                        int count = countFiles(new File(Environment.getExternalStorageDirectory() + File.separator + "AntiDataRecovery", "Images"));

                                        Log.v("Values", String.valueOf(v1) + "||" + String.valueOf(count));


                                        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_ss_mm");
                                        Date now = new Date();
                                        String fileName = formatter.format(now) + ".png";
                                        File gpxfile = new File(root, fileName);
                                        InputStream inputStream = getResources().openRawResource(+R.drawable.data);
                                        OutputStream out = new FileOutputStream(gpxfile);

                                        byte buf[] = new byte[1024];
                                        int len;
                                        while ((len = inputStream.read(buf)) > 0)

                                            out.write(buf, 0, len);
                                        out.close();
                                        inputStream.close();
                                        if (count == v1 && v1 != 0) {
                                            sendNotification();

                                            return;
                                        }


                                    }


                                } catch (IOException ignored) {

                                }


                            }


                        }).start();

                    }

                });

                CardView button2 = findViewById(R.id.button2);
                button2.setOnClickListener(new View.OnClickListener() {
                    ScrollView scrollView = findViewById(R.id.scroll);


                    @Override
                    public void onClick(final View v) {
                        new Thread(new Runnable() {
                            public void run() {
                                if (v1 == 0) {
                                    Snackbar snackbar = Snackbar
                                            .make(scrollView, "Please Enter Valid Size", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                    return;
                                } else {

                                    Snackbar snackbar = Snackbar
                                            .make(scrollView, "Task Started Making Folders With " + v1 + " Mb...", Snackbar.LENGTH_LONG);
                                    snackbar.show();

                                }

                                File dir = new File(Environment.getExternalStorageDirectory() + File.separator + "AntiDataRecovery", "Folders");
                                try {
                                    FileUtils.deleteDirectory(dir);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                try {
                                    while (true) {

                                        File root = new File(Environment.getExternalStorageDirectory() + File.separator + "AntiDataRecovery", "Folders");
                                        if (!root.exists()) {
                                            root.mkdirs();

                                        }
                                        int count = countFiles(new File(Environment.getExternalStorageDirectory() + File.separator + "AntiDataRecovery", "Folders"));

                                        Log.v("Values", String.valueOf(v1) + "||" + String.valueOf(count));


                                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_ss_mm");
                                        Date now = new Date();
                                        String fileName = formatter.format(now) + ".zip";
                                        File gpxfile = new File(root, fileName);
                                        InputStream inputStream = getResources().openRawResource(+R.drawable.data);
                                        OutputStream out = new FileOutputStream(gpxfile);

                                        byte buf[] = new byte[1024];
                                        int len;
                                        while ((len = inputStream.read(buf)) > 0)

                                            out.write(buf, 0, len);
                                        out.close();
                                        if (count == v1 && v1 != 0) {
                                            sendNotification();

                                            return;
                                        } else {

                                        }

                                    }

                                } catch (IOException e) {

                                }


                            }


                        }).start();

                    }

                });
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        TextView amr = findViewById(R.id.amr);
        amr.setMovementMethod(LinkMovementMethod.getInstance());


    }

    private void deleteButton() {

        final CardView delete = findViewById(R.id.button1);
        final ScrollView scrollView = findViewById(R.id.scroll);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Snackbar snackbar = Snackbar
                        .make(scrollView, "Done", Snackbar.LENGTH_LONG);
                snackbar.show();

                File dir = new File(Environment.getExternalStorageDirectory() + File.separator + "AntiDataRecovery");
                try {
                    FileUtils.deleteDirectory(dir);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

        });
    }

    private void folders() {
        File root = new File(Environment.getExternalStorageDirectory() + File.separator + "AntiDataRecovery", "Folders");
        if (!root.exists()) {
            root.mkdirs();

        }
        try {

            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_ss_mm");
            Date now = new Date();
            String fileName = formatter.format(now) + ".zip";
            File gpxfile = new File(root, fileName);
            InputStream inputStream = getResources().openRawResource(+R.drawable.data);
            OutputStream out = new FileOutputStream(gpxfile);

            byte buf[] = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0)

                out.write(buf, 0, len);
            out.close();
            inputStream.close();
        } catch (IOException ignored) {

        }

    }

    public void images() {
        File root = new File(Environment.getExternalStorageDirectory() + File.separator + "AntiDataRecovery", "Images");
        if (!root.exists()) {
            root.mkdirs();

        }
        try {

            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_ss_mm");
            Date now = new Date();
            String fileName = formatter.format(now) + ".png";
            File gpxfile = new File(root, fileName);
            InputStream inputStream = getResources().openRawResource(+R.drawable.data);
            OutputStream out = new FileOutputStream(gpxfile);

            byte buf[] = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0)

                out.write(buf, 0, len);
            out.close();
            inputStream.close();
        } catch (IOException e) {

        }

    }


    public static int countFiles(File directory) {
        int count = 0;
        for (File file : directory.listFiles()) {
            if (file.isFile()) {
                count++;
            }
            if (file.isDirectory()) {
                count += countFiles(file);
            }
        }
        return count;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater findMenuItems = getMenuInflater();
        findMenuItems.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main:
                final String appPackageName = getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void sendNotification() {
        final int NOTIFY_ID = 1002;


        String name = "my_package_channel";
        String id = "my_package_channel_1";
        String description = "my_package_first_channel";

        Intent intent;
        NotificationCompat.Builder builder;

        if (notifManager == null) {
            notifManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = null;
            if (notifManager != null) {
                mChannel = notifManager.getNotificationChannel(id);
            }
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, name, importance);
                mChannel.setDescription(description);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(this, id);

            Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);

            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

            PendingIntent contentIntent = PendingIntent.getActivity(this, 1, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


            builder.setContentTitle(this.getString(R.string.app_name))
                    .setSmallIcon(R.drawable.baseline_done_all_white_24)
                    .setContentText(this.getString(R.string.task))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(contentIntent)
                    .setAutoCancel(true)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        } else {

            builder = new NotificationCompat.Builder(this);

            Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);

            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

            PendingIntent contentIntent = PendingIntent.getActivity(this, 1, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


            builder.setContentTitle(this.getString(R.string.app_name))
                    .setSmallIcon(R.drawable.baseline_done_all_white_24)
                    .setContentText(this.getString(R.string.task))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(contentIntent)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setPriority(Notification.PRIORITY_HIGH);
        }

        Notification notification = builder.build();
        notifManager.notify(NOTIFY_ID, notification);


    }


}