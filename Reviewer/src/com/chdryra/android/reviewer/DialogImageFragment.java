package com.chdryra.android.reviewer;
import com.chdryra.android.myandroidwidgets.ClearableEditText;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

public class DialogImageFragment extends DialogBasicFragment {
		
	public static final int CAPTION_CHANGED = 2;	
	
	protected Controller mController = Controller.getInstance();
	protected RDId mReviewID;
	protected ClearableEditText mImageCaption;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_image, null);
		mReviewID = (RDId)getArguments().getParcelable(FragmentReviewOptions.REVIEW_ID);
		
		ImageView imageView = (ImageView)v.findViewById(R.id.dialog_image_image_view);
		imageView.setImageBitmap(getImageBitmap());
		
		String originalCaption = getImageCaption();
		mImageCaption = (ClearableEditText)v.findViewById(R.id.dialog_image_caption_edit_text);
		mImageCaption.setText(originalCaption);
		mImageCaption.setHint(getCaptionHint());
		
		//For some reason setSelection(0) doesn't work unless I force set the span of the selection
		if(originalCaption != null && originalCaption.length() > 0) {
			mImageCaption.setSelection(0, originalCaption.length());
			mImageCaption.setSelection(0);
		}
		
		mImageCaption.setOnEditorActionListener(new TextView.OnEditorActionListener() {			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
	            if(event == null) {
	            	if(actionId == EditorInfo.IME_ACTION_DONE) {
	            		sendResult(CAPTION_CHANGED);
	            	}
	            } 		            
	            return false;
			}
		});
		
		return buildDialog(v);
	}

	@Override
	protected void sendResult(int resultCode) {
		if(getTargetFragment() == null || resultCode == Activity.RESULT_CANCELED || resultCode == RESULT_DELETE) {
			super.sendResult(resultCode);
			return;
		}
		
		if(resultCode == CAPTION_CHANGED)
			changeCaption();
		
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, new Intent());
		
		if(resultCode == CAPTION_CHANGED)
			getDialog().dismiss();
	}
	
	protected Bitmap getImageBitmap() {
		return mController.getImageBitmap(mReviewID);
	}

	protected String getImageCaption() {
		return mController.getImageCaption(mReviewID);
	}
	
	protected String getCaptionHint() {
		return getResources().getString(R.string.edit_text_image_caption_hint);
	}
	
	protected void changeCaption() {
		mController.setImageCaption(mReviewID, mImageCaption.getText().toString());
	}
	
	@Override
	protected String getDeleteConfirmationTitle() {
		return getResources().getString(R.string.image_activity_title);
	}
	
	@Override
	protected void deleteData() {
		mController.deleteImage(mReviewID);
	}
}
