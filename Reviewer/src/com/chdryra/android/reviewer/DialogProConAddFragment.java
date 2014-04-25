package com.chdryra.android.reviewer;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.chdryra.android.myandroidwidgets.ClearableEditText;

public class DialogProConAddFragment extends SherlockDialogFragment{

	private ControllerReviewNode mController;
	
	private ArrayList<String> mPros;
	private ArrayList<String> mCons;
	
	private ClearableEditText mProEditText;
	private ClearableEditText mConEditText;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mController = Controller.unpack(getArguments());
		mPros = mController.hasProsCons()? mController.getPros() : new ArrayList<String>();
		mCons = mController.hasProsCons()? mController.getCons() : new ArrayList<String>();
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	
		final Dialog dialog = new Dialog(getSherlockActivity());
		dialog.setContentView(R.layout.dialog_procon_add);

		mProEditText = (ClearableEditText)dialog.findViewById(R.id.pro_edit_text);
		mConEditText = (ClearableEditText)dialog.findViewById(R.id.con_edit_text);

		final Button cancelButton = (Button)dialog.findViewById(R.id.button_left);
		final Button addButton = (Button)dialog.findViewById(R.id.button_middle);
		final Button doneButton = (Button)dialog.findViewById(R.id.button_right);
		
		mProEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
	        @Override
	        public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
	        {
	            if(actionId == EditorInfo.IME_ACTION_GO)
	            	addButton.performClick();
	            return false;
	        }
	    });

		mConEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
	        @Override
	        public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
	        {
	            if(actionId == EditorInfo.IME_ACTION_GO)
	            	addButton.performClick();
	            return false;
	        }
	    });
		
		cancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendResult(Activity.RESULT_CANCELED);
				dialog.dismiss();
			}
		});
		
		addButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addProCon();
			}
		});
		
		doneButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendResult(Activity.RESULT_OK);
				dialog.dismiss();
			}
		});
		
		dialog.setTitle(getResources().getString(R.string.dialog_add_procon_title));
		dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

		return dialog;
	}

	private void addProCon() {
		String pro = mProEditText.getText().toString();
		String con = mConEditText.getText().toString();
		if((pro == null || pro.length() == 0) && (con == null || con.length() == 0))
			return;
		
		if(pro.length() > 0)
			mPros.add(pro);
		
		if(con.length() > 0)
			mCons.add(con);
		
		mProEditText.setText(null);
		mConEditText.setText(null);
		
		if(pro.length() > 0 && con.length() > 0)
			getDialog().setTitle("Added Pro: " + pro + ", Con: " + con);
		else if(pro.length() > 0)
			getDialog().setTitle("Added Pro: " + pro);
		else 
			getDialog().setTitle("Added Con: " + con);
	}

	private void sendResult(int resultCode) {
		if (getTargetFragment() == null || resultCode == Activity.RESULT_CANCELED)
			return;
		
		if(resultCode == Activity.RESULT_OK) {
			addProCon();
			mController.setProsCons(mPros, mCons);
		}
		
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, new Intent());	
	}
	
}
