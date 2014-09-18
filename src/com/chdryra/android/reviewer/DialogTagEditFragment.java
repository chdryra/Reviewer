package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chdryra.android.myandroidwidgets.ClearableAutoCompleteTextView;
import com.chdryra.android.mygenerallibrary.DialogDeleteCancelDoneFragment;

public class DialogTagEditFragment extends DialogDeleteCancelDoneFragment{
	public static final String TAG_NEW = "com.chdryra.android.reviewer.tag_new";
	public static final String TAG_OLD = "com.chdryra.android.reviewer.tag_old";
	
	private String mOldTag;
	private ClearableAutoCompleteTextView mTagEditText;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		mOldTag = getArguments().getString(FragmentReviewTags.TAG_STRING);
		setDeleteConfirmation(false);
		setDialogTitle(getResources().getString(R.string.dialog_edit_tag_title));
	}
	
	@Override
	protected View createDialogUI() {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_tag, null);
		mTagEditText = (ClearableAutoCompleteTextView)v.findViewById(R.id.tag_edit_text);
		mTagEditText.setText(mOldTag);		
		setKeyboardIMEDoDone(mTagEditText);
		return v;
	}

	@Override
	protected void onDoneButtonClick() {
		Intent i = getNewReturnData();
		i.putExtra(TAG_OLD, mOldTag);
		i.putExtra(TAG_NEW, mTagEditText.getText().toString());
	}
	
	@Override
	protected void onDeleteButtonClick() {
		getNewReturnData().putExtra(TAG_OLD, mOldTag);
	}

	@Override
	protected boolean hasDataToDelete() {
		return true;
	}	
}