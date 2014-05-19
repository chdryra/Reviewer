package com.chdryra.android.reviewer;

import com.chdryra.android.myandroidwidgets.ClearableEditText;
import com.chdryra.android.mygenerallibrary.DialogDeleteCancelDoneFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DialogFactEditFragment extends DialogDeleteCancelDoneFragment {
	public static final String FACT_OLD_LABEL = "com.chdryra.android.reviewer.datum_old_label";
	public static final String FACT_OLD_VALUE = "com.chdryra.android.reviewer.datum_old_value";
	
	private ClearableEditText mLabel;
	private ClearableEditText mValue;
	private String mOldLabel;
	private String mOldValue;
			
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mOldLabel = getArguments().getString(FragmentReviewFacts.FACT_LABEL);
		mOldValue = getArguments().getString(FragmentReviewFacts.FACT_VALUE);
		setDeleteConfirmation(false);
		setDialogTitle(getResources().getString(R.string.dialog_edit_fact_title));
	}
	
	@Override
	protected View createDialogUI() {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_datum, null);
		mLabel = (ClearableEditText)v.findViewById(R.id.datum_label_edit_text);
		mValue = (ClearableEditText)v.findViewById(R.id.datum_value_edit_text);
		mLabel.setText(mOldLabel);		
		mValue.setText(mOldValue);
		setKeyboardIMEDoDone(mValue);
		
		return v;
	}	

	@Override
	protected void onDoneButtonClick() {
		Intent i = getNewReturnData();
		i.putExtra(FACT_OLD_LABEL, mOldLabel);
		i.putExtra(FACT_OLD_VALUE, mOldValue);
		i.putExtra(FragmentReviewFacts.FACT_LABEL, mLabel.getText().toString());
		i.putExtra(FragmentReviewFacts.FACT_VALUE, mValue.getText().toString());
	}
	
	@Override
	protected void onDeleteButtonClick() {
		Intent i = getNewReturnData();
		i.putExtra(FACT_OLD_LABEL, mOldLabel);
		i.putExtra(FACT_OLD_VALUE, mOldValue);
	}
	
	@Override
	protected boolean hasDataToDelete() {
		return true;
	}
}
