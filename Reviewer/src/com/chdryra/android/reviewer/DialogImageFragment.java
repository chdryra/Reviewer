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
	
	private Review mReview;
	protected ClearableEditText mImageCaption;
	private String mOriginalCaption;
	protected String mCaptionHint = getResources().getString(R.string.edit_text_image_caption_hint);
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_image, null);
		mReview = getArguments().getParcelable(FragmentReviewOptions.REVIEW_OBJECT);
		
		ImageView imageView = (ImageView)v.findViewById(R.id.dialog_image_image_view);
		mImageCaption = (ClearableEditText)v.findViewById(R.id.dialog_image_caption_edit_text);
		
		Bitmap image = mReview.getImage().getBitmap();
		mOriginalCaption = mReview.getImage().getCaption();
		
		imageView.setImageBitmap(image);
		mImageCaption.setHint(mCaptionHint);
		mImageCaption.setText(mOriginalCaption);
		//For some reason setSelection(0) doesn't work unless I force set the span of the selection
		if(mOriginalCaption != null && mOriginalCaption.length() > 0) {
			mImageCaption.setSelection(0, mOriginalCaption.length());
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
		
		setDeleteConfirmation(getResources().getString(R.string.image_activity_title));
		
		return buildDialog(v);
		}

	@Override
	protected void sendResult(int resultCode) {
		if (getTargetFragment() == null || resultCode == Activity.RESULT_CANCELED) {
			return;
		}
		
		if(resultCode == RESULT_DELETE) {
			super.sendResult(resultCode);
			return;
		}
		
		Intent i = new Intent();
		String currentCaption = mImageCaption.getText().toString();
		
		if(resultCode == CAPTION_CHANGED && currentCaption != mOriginalCaption)
			i.putExtra(FragmentReviewOptions.DIALOG_IMAGE_CAPTION, currentCaption);
		
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
		
		if(resultCode == CAPTION_CHANGED)
			getDialog().dismiss();
	}
}
