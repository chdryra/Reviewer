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
import android.widget.EditText;
import android.widget.TextView;

public class ClearableEditText extends EditText {
		 
    private Drawable mCloseButton = getResources().getDrawable(R.drawable.ic_clear_search_api_disabled_holo_light);
     
    public ClearableEditText(Context context) {
        super(context);
        init();
    }
 
    public ClearableEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
 
    public ClearableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    @Override
    public void onEditorAction(int actionCode) {
        if(actionCode == EditorInfo.IME_ACTION_DONE) 
        	hideChrome();

    	super.onEditorAction(actionCode);
    }
    
    void init() {
         
        // Set bounds of the Clear button so it will look ok
        mCloseButton.setBounds(0, 0, mCloseButton.getIntrinsicWidth(), mCloseButton.getIntrinsicHeight());
  
        this.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
 
            	ClearableEditText et = (ClearableEditText)v; 
            	et.setCursorVisible(true);
            	et.handleClearButton();
            	
                if (et.getCompoundDrawables()[2] == null)
                    return false;
                 
                if (event.getAction() != MotionEvent.ACTION_UP)
                    return false;
                 
                if (event.getX() > et.getWidth() - et.getPaddingRight() - mCloseButton.getIntrinsicWidth()) {
                    et.setText(null);
                    et.handleClearButton();
                }
                return false;
            }
        });
 
        this.addTextChangedListener(new TextWatcher() {
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
    
    public void hideChrome() {
    	hideClearButton();
    	hideKeyboard();
    }
    
    private void hideKeyboard() {
    	InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    	imm.hideSoftInputFromWindow(getWindowToken(), 0);
    	setCursorVisible(false);
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
}