package ru.vasilenkoaleksandr.time2date;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
//import java.util.Date;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;

public class Time2dateActivity extends Activity {

    private TextView textView, textViewDay, textViewHour, textViewMinute, textViewrezDay, textViewrezHour, textViewrezMinute;
    private ImageView butsetdate;
    private static final int CALENDAR_SELECTED = 0;
    private long resultMilisec;
    private Date date = new Date();
    private SimpleDateFormat currentDate = new SimpleDateFormat("dd.MM.yyyy");

    //записываем значение текущей даты
    private long todaymilisec = date.getTime();
    private String rezDay, rezHour, rezMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time2date);

        textView = (TextView) findViewById(R.id.textView);
        textViewDay = (TextView) findViewById(R.id.textViewDay);
        textViewHour = (TextView) findViewById(R.id.textViewHour);
        textViewMinute = (TextView) findViewById(R.id.textViewMinute);
        textViewrezDay = (TextView) findViewById(R.id.textViewrezday);
        textViewrezHour = (TextView) findViewById(R.id.textViewrezhour);
        textViewrezMinute = (TextView) findViewById(R.id.textViewrezminute);
        butsetdate = (ImageView) findViewById(R.id.butsetdate);

        restorePreferences();

        butsetdate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Time2dateActivity.this, CalendarActivity.class);
                startActivityForResult(i, CALENDAR_SELECTED);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CALENDAR_SELECTED) {
            if (resultCode == RESULT_OK) {
                long selectedmilisec = data.getLongExtra(CalendarActivity.RESULT_CAL, 0);
                if (selectedmilisec > 0) {
                    resultMilisec = selectedmilisec;
                    saveSharedPref();
                    updateCounting(resultMilisec);
                }
            }
        }
    }


    private void updateCounting(long selectedmilisec) {
        todaymilisec = new Date().getTime();
        long resulmilisec = selectedmilisec - todaymilisec;

        long day = resulmilisec / (1000 * 60 * 60 * 24);
        long hour = (resulmilisec % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = ((resulmilisec % (1000 * 60 * 60 * 24)) % (1000 * 60 * 60)) / (1000 * 60);

        rezDay = "" + day;
        rezHour = "" + hour;
        rezMinute = "" + minutes;

        textViewrezDay.setText(rezDay);
        textViewrezHour.setText(rezHour);
        textViewrezMinute.setText(rezMinute);
        date.setTime(selectedmilisec);
        textView.setText("до " + currentDate.format(date));
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        restorePreferences();
//    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        restorePreferences();
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveSharedPref();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        saveSharedPref();
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveSharedPref();
    }

    private void saveSharedPref(){
        if(resultMilisec>0) {
            SharedPreferences preferences = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putLong("RESULT", resultMilisec);
            editor.apply();
        }
    }

    private void restorePreferences(){
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        if(preferences.contains("RESULT")){
            resultMilisec = preferences.getLong("RESULT", 0);
            preferences.edit().remove("RESULT").apply();
        }
        if(resultMilisec!=0)updateCounting(resultMilisec);

    }
}
