package com.chdryra.android.reviewer;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

public class MyAutoCompleteTextView extends AutoCompleteTextView {

	public MyAutoCompleteTextView(Context context) {
		super(context);
		init();
	}

	public MyAutoCompleteTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public MyAutoCompleteTextView(Context contex, AttributeSet attrs, int defStyle) {
		super(contex, attrs, defStyle);
		init();
	}
	
	private void init() {
		setOnTouchListener(new View.OnTouchListener(){
	    	   @Override
	    	public boolean onTouch(View v, MotionEvent event) {
	    		if(getAdapter() != null && getAdapter().getCount() > 0)
					showDropDown();
	    		return false;
	    	}
	    	});
	    
	    setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				setCursorVisible(true);
			}				
		});

	    setOnEditorActionListener(new TextView.OnEditorActionListener() {			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
	            if(event == null) {
	            	if(actionId == EditorInfo.IME_ACTION_DONE)
	            		silenceEditor();
	            } else if(event.getAction() == KeyEvent.ACTION_DOWN && 
	            		event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
	            	silenceEditor();
	            	hideKeyboard();
	            	return true;
	            }
	            
	            return false;
			}
		});
	    
	    setOnItemClickListener(new AdapterView.OnItemClickListener() {
	    	  @Override
	    	  public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	    		  dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
	    	  }
	    	});

	}
	
	public void hideKeyboard()
	{
		Activity activity = (Activity)getContext();
		InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
	    inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
	}

	public void silenceEditor() {
		dismissDropDown();
    	setSelection(0);
    	setCursorVisible(false);
	}

}
