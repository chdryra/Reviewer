package com.chdryra.android.reviewer;
import com.chdryra.android.myandroidwidgets.ClearableEditText;
import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.DialogDeleteCancelDoneFragment;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

public class DialogImageFragment extends DialogDeleteCancelDoneFragment {
	protected ControllerReviewNode mController;
	protected ClearableEditText mImageCaption;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mController = Controller.unpack(getArguments());
		setRightButtonAction(ActionType.EDIT);
		setDismissDialogOnRightClick(true);
		setDeleteConfirmation(true);
		setDeleteWhatTitle(getResources().getString(R.string.activity_title_image));
	}

	@Override
	protected View createDialogUI() {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_image, null);
		
		ImageView imageView = (ImageView)v.findViewById(R.id.dialog_image_image_view);
		imageView.setImageBitmap(mController.getImageBitmap());
		
		String originalCaption = mController.getImageCaption();
		mImageCaption = (ClearableEditText)v.findViewById(R.id.dialog_image_caption_edit_text);
		mImageCaption.setText(originalCaption);
		mImageCaption.setHint(getResources().getString(R.string.edit_text_image_caption_hint));
		
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
	            		mController.setImageCaption(mImageCaption.getText().toString());
	            		sendResult(ActivityResultCode.DONE);
	            		dismiss();
	            	}
	            } 		            
	            return false;
			}
		});

		return v;
	}
		
	@Override
	protected void onDeleteButtonClick() {
		mController.deleteImage();
		super.onDeleteButtonClick();
	}
	
	@Override
	protected boolean hasDataToDelete() {
		return mController.hasImage();
	}
}
