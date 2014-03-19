package com.chdryra.android.reviewer;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

public class ClearableAutoCompleteTextView extends AutoCompleteTextView {
	private Drawable mCloseButton = getResources().getDrawable(R.drawable.dialog_ic_close_normal_holo_light);
	private boolean mXTouched = false;
	
	public ClearableAutoCompleteTextView(Context context) {
		super(context);
		init();
	}

	public ClearableAutoCompleteTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public ClearableAutoCompleteTextView(Context contex, AttributeSet attrs, int defStyle) {
		super(contex, attrs, defStyle);
		init();
	}
	
	private void init() {
		// Set bounds of the Clear button so it will look ok
        mCloseButton.setBounds(0, 0, mCloseButton.getIntrinsicWidth(), mCloseButton.getIntrinsicHeight());
  
		setOnTouchListener(new View.OnTouchListener(){
			@Override
	    	public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() != MotionEvent.ACTION_UP)
					return false;
				
				ClearableAutoCompleteTextView et = (ClearableAutoCompleteTextView)v; 
            	et.setCursorVisible(true);
            	et.handleClearButton();
            	
                if (et.getCompoundDrawables()[2] != null && 
                		event.getX() > et.getWidth() - et.getPaddingRight() - mCloseButton.getIntrinsicWidth()) {
                    et.setText(null);
                    mXTouched = true;
                } 
 
                showKeyboard();
                showDropDownIfAvailable();
                
	    		return true;
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

        setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ClearableAutoCompleteTextView et = (ClearableAutoCompleteTextView)v;
				String text = et.getText().toString();
                if(text != null && text.length() > 0) {
                	et.setSelection(text.length());
                }
			}
		});

	    setOnItemClickListener(new AdapterView.OnItemClickListener() {
	    	@Override
	    	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	    		dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
	    	}
	    });

	    addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            	handleClearButton();
            }
 
            @Override
            public void afterTextChanged(Editable arg0) {
            }
 
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });
    	
        handleClearButton();
	}

	public void showDropDownIfAvailable() {
		if(getAdapter() != null && getAdapter().getCount() > 0)
			showDropDown();
	}
	
	@Override
	public void dismissDropDown() {
		if(!mXTouched)
			super.dismissDropDown();
		else
			mXTouched = false;
	}
	
    @Override
    public void onEditorAction(int actionCode) {
        if(actionCode == EditorInfo.IME_ACTION_DONE) 
        	hideChrome();

    	super.onEditorAction(actionCode);
    }
  
    public void hideChrome() {
    	hideClearButton();
    	hideKeyboard();
    	silenceEditor();
    }
    
    private void hideKeyboard() {
    	InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    	imm.hideSoftInputFromWindow(getWindowToken(), 0);
    	setCursorVisible(false);
    }

    private void showKeyboard() {
		InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT);
        setCursorVisible(true);
    }
    
    private void showClearButton() {
    	setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], mCloseButton, getCompoundDrawables()[3]);
    }
    
    private void hideClearButton() {
    	setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], null, getCompoundDrawables()[3]);
    }
    
    private void handleClearButton() {
        if (this.getText().toString().equals(""))
        	hideClearButton();
        else
        	showClearButton();
    }
	
	private void silenceEditor() {
		dismissDropDown();
		
		//For some reason setSelection(0) doesn't work unless I force set the span of the selection
		String text = getText().toString();
		if(text != null && text.length() > 0) {
			setSelection(0, text.length());
			setSelection(0);
		}
		
    	setCursorVisible(false);
	}
}
