package com.chdryra.android.reviewer;

import java.util.LinkedHashMap;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.chdryra.android.myandroidwidgets.ClearableEditText;
import com.chdryra.android.mygenerallibrary.DialogAddCancelDoneFragment;

public class DialogFactAddFragment extends DialogAddCancelDoneFragment{

	private ControllerReviewNode mController;
	private LinkedHashMap<String, String> mFacts;
	
	private ClearableEditText mFactLabelEditText;
	private ClearableEditText mFactValueEditText;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setDialogTitle(getResources().getString(R.string.dialog_add_fact_title));
		mController = Controller.unpack(getArguments());
		mFacts = mController.hasFacts()? mController.getFacts() : new LinkedHashMap<String, String>();
	}

	@Override
	protected View createDialogUI() {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_fact_add, null);
		mFactLabelEditText = (ClearableEditText)v.findViewById(R.id.fact_label_edit_text);
		mFactValueEditText = (ClearableEditText)v.findViewById(R.id.fact_value_edit_text);
		setKeyboardIMEDoAction(mFactValueEditText);
		
		return v;
	}

	@Override
	protected void OnAddButtonClick() {
		addFact();
		mFactLabelEditText.requestFocus();
		super.OnAddButtonClick();
	}
	
	@Override
	protected void onDoneButtonClick() {
		addFact();
		mController.setFacts(mFacts);
		super.onDoneButtonClick();
	}
	
	private void addFact() {
		String label = mFactLabelEditText.getText().toString();
		String value = mFactValueEditText.getText().toString();
		if((label == null || label.length() == 0) && (value == null || value.length() == 0))
			return;
		
		if(label == null || label.length() == 0)
			Toast.makeText(getSherlockActivity(), getResources().getString(R.string.toast_enter_label), Toast.LENGTH_SHORT).show();
		else if(value == null || value.length() == 0)
			Toast.makeText(getSherlockActivity(), getResources().getString(R.string.toast_enter_value), Toast.LENGTH_SHORT).show();
		else {
			mFacts.put(label, value);
			mFactLabelEditText.setText(null);
			mFactValueEditText.setText(null);
			getDialog().setTitle("Added " + label + ": " + value);
		}
	}
}
