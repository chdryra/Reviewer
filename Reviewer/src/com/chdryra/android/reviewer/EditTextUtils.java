package com.chdryra.android.reviewer;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class EditTextUtils {

	public static void hideKeyboard(Activity activity, EditText editText)
	{
	    InputMethodManager imm= (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
	    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        editText.setCursorVisible(false);
	}
	
	public static void setupEditTextCusorVisibility(EditText editText) {	 
		editText.setCursorVisible(false);
		
		editText.setOnClickListener(new View.OnClickListener() {
		        @Override
		        public void onClick(View v) {
		        	EditText eT = (EditText)v;
		        	eT.setCursorVisible(true);
		        }
		    });
		
		editText.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				EditText eT = (EditText)v;
	        	eT.setCursorVisible(true);
				return false;
			}
		});
	}
	
}
