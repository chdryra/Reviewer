package com.chdryra.android.reviewer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

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

	public static int getNumberLines(String string, TextView textView) {
		Rect bounds = new Rect();
		Paint paint = new Paint();
		paint.setTextSize(textView.getTextSize());
		paint.getTextBounds(string, 0, string.length(), bounds);

		int lineWidth = textView.getLayoutParams().width - textView.getPaddingRight() - textView.getPaddingLeft();
		int textWidth = (int) Math.ceil(bounds.width());
		
		return lineWidth > 0? textWidth / lineWidth + 1 : 0;
	}
	
	public static int getMaxNumberLines(TextView textView) {
		int maxHeight = textView.getLayoutParams().height - textView.getPaddingTop() - textView.getPaddingBottom();
		
		return maxHeight / textView.getLineHeight();
	}
}
