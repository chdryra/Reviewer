package com.chdryra.android.reviewer;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.myandroidwidgets.ClearableAutoCompleteTextView;

public class DialogTagEditFragment extends DialogDeleteCancelDoneFragment{
	private ControllerReviewNode mController;
	
	private String mCurrentTag;
	private ArrayList<String> mTags;	
	private ClearableAutoCompleteTextView mTagEditText;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setDialogTitle(getResources().getString(R.string.dialog_edit_tag_title));
		setDeleteConfirmation(false);
		
		mController = Controller.unpack(getArguments());
		mTags = mController.hasTags()? mController.getTags() : new ArrayList<String>();
		mCurrentTag = getArguments().getString(FragmentReviewCreate.TAG_EDIT_STRING);
	}

	@Override
	protected View createDialogUI() {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_tag, null);
		mTagEditText = (ClearableAutoCompleteTextView)v.findViewById(R.id.tag_edit_text);
		mTagEditText.setText(mCurrentTag);
		setKeyboardIMEDoDone(mTagEditText);
		
		return v;
	}
	
	@Override
	protected void onDoneButtonClick() {
		editTag();
		super.onDoneButtonClick();
	}

	@Override
	protected void onDeleteButtonClick() {
		mTagEditText.setText(null);
		editTag();
		super.onDeleteButtonClick();
	}

	@Override
	protected boolean hasDataToDelete() {
		return mCurrentTag != null;
	}
	
	private void editTag() {
		String tag = mTagEditText.getText().toString();
		
		mTags.remove(mCurrentTag);
		if(tag.length() > 0)
			mTags.add(tag);

		mController.removeTags();
		mController.addTags(mTags);	
	}
}
