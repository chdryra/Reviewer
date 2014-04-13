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
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;

public class DialogDateFragment extends DialogBasicFragment {

	private ControllerReviewNode mController;
	
	private DatePicker mDatePicker;
	private CheckBox mCheckBoxIncludeDate;
	private Date mCurrentDate;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		mController = Controller.unpack(getArguments());
		
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_date, null);
		
		mDatePicker = (DatePicker)v.findViewById(R.id.date_picker);
		mCheckBoxIncludeDate = (CheckBox)v.findViewById(R.id.checkbox_include_date);
		
		mCurrentDate = mController.hasDate()? mController.getDate() : new Date();

		final Calendar calendar = Calendar.getInstance(Locale.getDefault());
		calendar.setTime(mCurrentDate);
		final int year = calendar.get(Calendar.YEAR);
		final int month = calendar.get(Calendar.MONTH);
		final int day = calendar.get(Calendar.DAY_OF_MONTH);

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

		mCheckBoxIncludeDate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mDatePicker.setEnabled(isChecked);
			}
		});

		mCheckBoxIncludeDate.setChecked(true);
		mDatePicker.setEnabled(true);
		
		return buildDialog(v, getResources().getString(R.string.dialog_date_title));
	}
	
	@Override
	protected void sendResult(int resultCode) {
		if (resultCode != Activity.RESULT_OK) {
			super.sendResult(resultCode);
			return;
		}

		if(!mDatePicker.isEnabled())
			sendResult(RESULT_DELETE);
			
		int day = mDatePicker.getDayOfMonth();
		int month = mDatePicker.getMonth();
		int year =  mDatePicker.getYear();
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);
		Date newDate = calendar.getTime();
		
		mController.setDate(newDate);
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, new Intent());
	}
	
	@Override
	protected void deleteData() {
		mController.deleteDate();
	}
	
	@Override
	protected String getDeleteConfirmationTitle() {
		return getResources().getString(R.string.dialog_date_title);
	}
}