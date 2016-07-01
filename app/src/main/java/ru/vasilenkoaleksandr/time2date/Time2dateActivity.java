package ru.vasilenkoaleksandr.time2date;

import java.util.Calendar;
//import java.util.Date;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;

public class Time2dateActivity extends Activity {
	
	TextView textView, textViewDay, textViewHour, textViewMinute, textViewrezDay, textViewrezHour, textViewrezMinute;
	ImageButton butsetdate;
	static final private int CALENDAR_SELECTED = 0;
	
	Calendar today = Calendar.getInstance();
	final long todaymilisec = today.getTimeInMillis();
	
	int tdday = today.get(Calendar.DAY_OF_MONTH);
	int tdmounth = today.get(Calendar.MONTH)+1;
	int tdYear = today.get(Calendar.YEAR);
	
	String tvText = " " + tdday + "." +tdmounth+"."+tdYear;
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
		
		butsetdate = (ImageButton)findViewById(R.id.butsetdate);
		
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
					
					Calendar rezDate = Calendar.getInstance();
					rezDate.setTimeInMillis(selectedmilisec);
					tdday = rezDate.get(Calendar.DAY_OF_MONTH);
					tdmounth = rezDate.get(Calendar.MONTH)+1;
					tdYear = rezDate.get(Calendar.YEAR);
					
					tvText = " " + tdday + "." +tdmounth+"."+tdYear;
					textView.setText(tvText);
					
				}
				
			}
		}
	}
	



}
