package ru.vasilenkoaleksandr.time2date;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
//import android.widget.Toast;

public class CalendarActivity extends Activity {
	
	CalendarView calendarView;
	Calendar calendar = Calendar.getInstance();
	public long selectdatemilisec;
	public final static String RESULT_CAL = "ru.vasilenko.aleksandr.time2date.RESULT_CAL";
	
	@Override
	protected void onCreate(Bundle sevedInstanceState){
		super.onCreate(sevedInstanceState);
		setContentView(R.layout.activity_calendar);
		
		
		
		calendarView = (CalendarView)findViewById(R.id.calendarView);
		final int startmonth = calendar.get(Calendar.MONTH);
		final int startdayofmonth = calendar.get(Calendar.DAY_OF_MONTH);
		
		
		calendarView.setOnDateChangeListener(new OnDateChangeListener() {
			
			@Override
			public void onSelectedDayChange(CalendarView view, int year, int month,
					int dayOfMonth) {
				// TODO Auto-generated method stub
				if (startmonth!=month | startdayofmonth!=dayOfMonth){
				selectdatemilisec=calendarView.getDate();
				Intent resultDatemilisec = new Intent();
				resultDatemilisec.putExtra(RESULT_CAL, selectdatemilisec);
				setResult(RESULT_OK, resultDatemilisec);
				finish();
				}
			
			}
		});
		
	}

}
