package com.chdryra.android.reviewer;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public abstract class DialogActionCancelDoneFragment extends DialogThreeButtonFragment {

	protected abstract View createDialogUI(); 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setMiddleButtonAction(ActionType.CANCEL);
		setRightButtonAction(ActionType.DONE);
		setDismissDialogOnMiddleClick(true);
		setDismissDialogOnRightClick(true);
		
		setLeftButtonAction(ActionType.OTHER);
		setDismissDialogOnLeftClick(false);
	}
	
	@Override
	protected void onLeftButtonClick() {
		onActionButtonClick();
		super.onLeftButtonClick();
	}
	
	@Override
	protected void onMiddleButtonClick() {
		onCancelButtonClick();
		super.onMiddleButtonClick();
	}

	@Override
	protected void onRightButtonClick() {
		onDoneButtonClick();
		super.onRightButtonClick();
	}
	
	protected void onActionButtonClick() {
	}
	
	protected void onCancelButtonClick() {
	};
	
	protected void onDoneButtonClick() {
	};

	public void setActionButtonText(String actionButtonText) {
		setLeftButtonText(actionButtonText);
	}
	
	public void setActionButtonAction(ActionType action) {
		setLeftButtonAction(action);
	}
	
	protected void setDismissDialogOnActionClick(boolean dismiss) {
		setDismissDialogOnLeftClick(dismiss);
	}
	
	protected void setKeyboardIMEDoAction(EditText editText) {
		editText.setImeOptions(EditorInfo.IME_ACTION_GO);
		editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
	        @Override
	        public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
	        {
	            if(actionId == EditorInfo.IME_ACTION_GO)
	            	mLeftButton.performClick();
	            return false;
	        }
	    });
	}
	
	protected void setKeyboardIMEDoDone(EditText editText) {
		editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
		editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
	        @Override
	        public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
	        {
	            if(actionId == EditorInfo.IME_ACTION_DONE)
	            	mRightButton.performClick();
	            return false;
	        }
	    });
	}	
}
