package ru.vasilenkoaleksandr.time2date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
//import java.util.Date;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;

public class Time2dateActivity extends Activity {
	
	TextView textView, textViewDay, textViewHour, textViewMinute, textViewrezDay, textViewrezHour, textViewrezMinute;
	ImageView butsetdate;
	static final private int CALENDAR_SELECTED = 0;

	Date date = new Date();
	SimpleDateFormat currentDate = new SimpleDateFormat("dd.MM.yyyy");
	String tvText = currentDate.format(date);

	//записываем значение текущей даты
	final long todaymilisec = date.getTime();
	String rezDay, rezHour, rezMinute;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time2date);
		
		textView = (TextView)findViewById(R.id.textView);
		textViewDay  = (TextView)findViewById(R.id.textViewDay);
		textViewHour  = (TextView)findViewById(R.id.textViewHour);
		textViewMinute  = (TextView)findViewById(R.id.textViewMinute); 
		textViewrezDay  = (TextView)findViewById(R.id.textViewrezday);
		textViewrezHour  = (TextView)findViewById(R.id.textViewrezhour);
		textViewrezMinute = (TextView)findViewById(R.id.textViewrezminute);
		
		butsetdate = (ImageView)findViewById(R.id.butsetdate);
		
		textView.setText(tvText);

		
		
		butsetdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				
				Intent i = new Intent(Time2dateActivity.this, CalendarActivity.class);
				startActivityForResult(i, CALENDAR_SELECTED);
			}
		});
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == CALENDAR_SELECTED){
			if (resultCode == RESULT_OK){
				long selectedmilisec = data.getLongExtra(CalendarActivity.RESULT_CAL, 0);
				if (selectedmilisec > 0){
					long resulmilisec = selectedmilisec-todaymilisec;
					
					long day = resulmilisec/(1000*60*60*24);
					long hour = (resulmilisec%(1000*60*60*24))/(1000*60*60);
					long minutes = ((resulmilisec%(1000*60*60*24))%(1000*60*60))/(1000*60);
					
					rezDay=""+day;
					rezHour=""+hour;
					rezMinute=""+minutes;
					
					textViewrezDay.setText(rezDay);
					textViewrezHour.setText(rezHour);
					textViewrezMinute.setText(rezMinute);
					

					date.setTime(selectedmilisec);

					
					tvText = "до "+currentDate.format(date);
					textView.setText(tvText);
					
				}
				
			}
		}
	}
	



}
