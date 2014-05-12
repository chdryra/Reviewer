package com.chdryra.android.reviewer;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.myandroidwidgets.ClearableAutoCompleteTextView;

public class DialogTagAddFragment extends DialogAddFragment{
	
	private ControllerReviewNode mController;
	private ArrayList<String> mTags;	
	private ClearableAutoCompleteTextView mTagEditText;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mController = Controller.unpack(getArguments());
		mTags = mController.hasTags()? mController.getTags() : new ArrayList<String>();
	}
	
	@Override
	protected View getDialogUI() {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_tag, null);
		mTagEditText = (ClearableAutoCompleteTextView)v.findViewById(R.id.tag_edit_text);
		setIMEDoAction(mTagEditText);
		
		return v;
	}

	@Override
	protected void doActionButtonClick() {
		String tag = mTagEditText.getText().toString();
		if(tag == null || tag.length() == 0)
			return;
		
		if(tag.length() > 0)
			mTags.add(tag);
		
		mTagEditText.setText(null);
		
		getDialog().setTitle("Added tag: " + tag);
	}

	@Override
	protected void doDoneButtonClick() {
		doActionButtonClick();
		mController.removeTags();
		mController.addTags(mTags);
		super.doDoneButtonClick();
	}
	
	@Override
	protected String getDialogTitle() {
		return getResources().getString(R.string.dialog_add_tag_title);
	}
}
