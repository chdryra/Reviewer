package com.chdryra.android.reviewer;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class DialogDateFragment extends DialogBasicFragment {

	private DatePicker mDatePicker;
	private TimePicker mTimePicker;
	private CheckBox mCheckBoxIncludeTime;
	private Date mCurrentDate;
	private boolean mIncludeTime;
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_date, null);
		mDatePicker = (DatePicker)v.findViewById(R.id.date_picker);
		mTimePicker = (TimePicker)v.findViewById(R.id.time_picker);
		mCheckBoxIncludeTime = (CheckBox)v.findViewById(R.id.checkbox_include_time);
		
		mCurrentDate = (Date)getArguments().getSerializable(ReviewOptionsFragment.REVIEW_DATE);
		mIncludeTime = getArguments().getBoolean(ReviewOptionsFragment.REVIEW_DATE_INC_TIME);
		
		final Calendar calendar = Calendar.getInstance(Locale.getDefault());
		calendar.setTime(mCurrentDate);
		final int year = calendar.get(Calendar.YEAR);
		final int month = calendar.get(Calendar.MONTH);
		final int day = calendar.get(Calendar.DAY_OF_MONTH);
		final int hour = calendar.get(Calendar.HOUR_OF_DAY);
		final int min = calendar.get(Calendar.MINUTE);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			mDatePicker.updateDate(year, month, day);
			mDatePicker.setMaxDate(Calendar.getInstance(Locale.getDefault()).getTimeInMillis());
		} else {
			mDatePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
	
				@Override
				public void onDateChanged(DatePicker view, int newYear, int newMonth, int newDay) {
		            Calendar newDate = Calendar.getInstance(Locale.getDefault());
		            newDate.set(newYear, newMonth, newDay);
		            if (newDate.after(calendar)) {
		                view.init(year, month, day, this);
		            }
		        }
		    });
		}
		
		mTimePicker.setIs24HourView(DateFormat.is24HourFormat(getActivity()));
		mTimePicker.setCurrentHour(hour);
		mTimePicker.setCurrentMinute(min);
		if(mIncludeTime)
			enableTimePicker();
		else
			disableTimePicker();
		mCheckBoxIncludeTime.setChecked(mIncludeTime);
		mCheckBoxIncludeTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked) {
					enableTimePicker();
					mIncludeTime = true;
				}
				else {
					disableTimePicker();
					mIncludeTime = false;
				}
			}
		});
		
		return buildDialog(v, getResources().getString(R.string.dialog_date_title));
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void enableTimePicker() {
		mTimePicker.setEnabled(true);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			mTimePicker.setAlpha(100);
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void disableTimePicker() {
		mTimePicker.setEnabled(false);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			mTimePicker.setAlpha(25);
	}
	
	@Override
	protected void sendResult(int resultCode) {
		if (getTargetFragment() == null || resultCode == Activity.RESULT_CANCELED) {
			return;
		}
		
		Intent i = new Intent();
		int day = mDatePicker.getDayOfMonth();
		int month = mDatePicker.getMonth();
		int year =  mDatePicker.getYear();
		int hour = mTimePicker.getCurrentHour();
		int minute = mTimePicker.getCurrentMinute();
		 
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day, hour, minute);
		Date newDate = calendar.getTime();
		
		i.putExtra(ReviewOptionsFragment.REVIEW_DATE_INC_TIME, mCheckBoxIncludeTime.isChecked());
		if(resultCode == Activity.RESULT_OK && newDate.before(new Date()))
			i.putExtra(ReviewOptionsFragment.REVIEW_DATE, newDate);
		else 
			i.putExtra(ReviewOptionsFragment.REVIEW_DATE, mCurrentDate);
			
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
	}
}
