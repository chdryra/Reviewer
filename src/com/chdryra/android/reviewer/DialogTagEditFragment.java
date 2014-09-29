/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.chdryra.android.myandroidwidgets.ClearableAutoCompleteTextView;
import com.chdryra.android.mygenerallibrary.DialogCancelDeleteDoneFragment;

@SuppressWarnings("WeakerAccess")
public class DialogTagEditFragment extends DialogCancelDeleteDoneFragment {
	public static final String TAG_NEW = "com.chdryra.android.reviewer.tag_new";
	public static final String TAG_OLD = "com.chdryra.android.reviewer.tag_old";
	
	private String mOldTag;
	private ClearableAutoCompleteTextView mTagEditText;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		mOldTag = getArguments().getString(FragmentReviewTags.TAG_STRING);
		setDialogTitle(getResources().getString(R.string.dialog_edit_tag_title));
        setDeleteWhatTitle(mOldTag);
	}
	
	@Override
	protected View createDialogUI(ViewGroup parent) {
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
