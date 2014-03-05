package com.chdryra.android.reviewer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class RandomTextUtils {

	public static void hideKeyboard(Activity activity, EditText editText)
	{
	    InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
	    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        editText.setCursorVisible(false);
	}
	
	public static void showKeyboard(Activity activity, EditText editText)
	{
		InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        editText.setCursorVisible(true);
	}
	
	public static void setupEditTextCusorVisibility(EditText editText) {	 
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
	
	public static int getNumberLines(String string, float textSize, int lineWidth) {
		Rect bounds = new Rect();
		Paint paint = new Paint();
		paint.setTextSize(textSize);
		paint.getTextBounds(string, 0, string.length(), bounds);

		int textWidth = (int) Math.ceil( bounds.width());
		return lineWidth > 0? textWidth / lineWidth + 1 : 0;
	}

	
}
