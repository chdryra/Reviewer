package com.chdryra.android.reviewer;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class DateDialogFragment extends BasicDialogFragment {

	private DatePicker mDatePicker;
	private TimePicker mTimePicker;
	private Date mCurrentDate;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_date, null);
		mDatePicker = (DatePicker)v.findViewById(R.id.date_picker);
		mTimePicker = (TimePicker)v.findViewById(R.id.time_picker);
		
		mCurrentDate = (Date)getArguments().getSerializable(ReviewOptionsFragment.REVIEW_DATE);
		
		Calendar calendar = Calendar.getInstance(Locale.getDefault());
		calendar.setTime(mCurrentDate);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int min = calendar.get(Calendar.MINUTE);
		
		mDatePicker.updateDate(year, month, day);			
		mTimePicker.setCurrentHour(hour);
		mTimePicker.setCurrentMinute(min);
		
		return buildDialog(v);
	}

	@Override
	protected void sendResult(int resultCode) {
		if (getTargetFragment() == null || resultCode == Activity.RESULT_CANCELED) {
			return;
		}
		
		Intent i = new Intent();
		if(resultCode == Activity.RESULT_OK) {
			
			int day = mDatePicker.getDayOfMonth();
			int month = mDatePicker.getMonth();
			int year =  mDatePicker.getYear();
			int hour = mTimePicker.getCurrentHour();
			int minute = mTimePicker.getCurrentMinute();
			 
			Calendar calendar = Calendar.getInstance();
			calendar.set(year, month, day, hour, minute);
			
			i.putExtra(ReviewOptionsFragment.REVIEW_DATE, calendar.getTime());
		} else 
			i.putExtra(ReviewOptionsFragment.REVIEW_DATE, mCurrentDate);
		
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
	}
}
