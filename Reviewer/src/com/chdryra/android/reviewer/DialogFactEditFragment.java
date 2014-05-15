package com.chdryra.android.reviewer;

import com.chdryra.android.myandroidwidgets.ClearableEditText;
import com.chdryra.android.mygenerallibrary.DialogDeleteCancelDoneFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DialogFactEditFragment extends DialogDeleteCancelDoneFragment {
	public static final String DATUM_OLD_LABEL = "com.chdryra.android.reviewer.datum_old_label";
	public static final String DATUM_OLD_VALUE = "com.chdryra.android.reviewer.datum_old_label";
	
	private ClearableEditText mLabel;
	private ClearableEditText mValue;
	private String mOldLabel;
			
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setDeleteConfirmation(true);
		setDeleteWhatTitle(getResources().getString(R.string.dialog_delete_fact_title));
	}
	
	@Override
	protected void onDoneButtonClick() {
		Intent i = getReturnData();
		i.putExtra(DATUM_OLD_LABEL, mOldLabel);
		i.putExtra(FragmentReviewFacts.DATUM_LABEL, mLabel.getText().toString());
		i.putExtra(FragmentReviewFacts.DATUM_VALUE, mValue.getText().toString());
		super.onDoneButtonClick();
	}
	
	@Override
	protected void onDeleteButtonClick() {
		getReturnData().putExtra(DATUM_OLD_LABEL, mOldLabel);
		super.onDeleteButtonClick();
	}
	
	@Override
	protected View createDialogUI() {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_datum, null);
		mLabel = (ClearableEditText)v.findViewById(R.id.datum_label_edit_text);
		mValue = (ClearableEditText)v.findViewById(R.id.datum_value_edit_text);
		
		mOldLabel = getArguments().getString(FragmentReviewFacts.DATUM_LABEL);
		mLabel.setText(mOldLabel);		
		mValue.setText(getArguments().getString(FragmentReviewFacts.DATUM_VALUE));
		setKeyboardIMEDoDone(mValue);
		
		return v;
	}	
}
