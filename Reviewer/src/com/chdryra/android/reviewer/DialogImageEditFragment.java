package com.chdryra.android.reviewer;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.chdryra.android.myandroidwidgets.ClearableEditText;
import com.chdryra.android.mygenerallibrary.DialogDeleteCancelDoneFragment;
import com.google.android.gms.maps.model.LatLng;

public class DialogImageEditFragment extends DialogDeleteCancelDoneFragment {
	public static final String OLD_CAPTION = "com.chdryra.android.reviewer.old_caption";
	
	protected Bitmap mBitmap;
	protected LatLng mLatLng;
	protected String mCaption;
	protected ClearableEditText mImageCaption;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBitmap = getArguments().getParcelable(FragmentReviewImage.BITMAP);
		mLatLng = getArguments().getParcelable(FragmentReviewImage.LATLNG);
		mCaption = getArguments().getString(FragmentReviewImage.CAPTION);
		setDialogTitle(null);
		showKeyboardOnLaunch(false);
		setDeleteWhatTitle(getResources().getString(R.string.dialog_delete_image_title));
	}

	@Override
	protected View createDialogUI() {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_image_edit, null);
		
		ImageView imageView = (ImageView)v.findViewById(R.id.dialog_image_image_view);
		imageView.setImageBitmap(mBitmap);
		
		mImageCaption = (ClearableEditText)v.findViewById(R.id.dialog_image_caption_edit_text);
		mImageCaption.setText(mCaption);
		mImageCaption.setHint(getResources().getString(R.string.edit_text_image_caption_hint));
		
		//For some reason setSelection(0) doesn't work unless I force set the span of the selection
		if(mCaption != null && mCaption.length() > 0) {
			mImageCaption.setSelection(0, mCaption.length());
			mImageCaption.setSelection(0);
		}
		
		return v;
	}

	@Override
	protected void onDoneButtonClick() {
		Intent i = getNewReturnData();
		i.putExtra(FragmentReviewImage.BITMAP, mBitmap);
		i.putExtra(FragmentReviewImage.LATLNG, mLatLng);
		i.putExtra(FragmentReviewImage.CAPTION, mImageCaption.getText().toString());
		i.putExtra(OLD_CAPTION, mCaption);
	}
	
	@Override
	protected void onDeleteButtonClick() {
		Intent i = getNewReturnData();
		i.putExtra(FragmentReviewImage.BITMAP, mBitmap);
		i.putExtra(FragmentReviewImage.LATLNG, mLatLng);
		i.putExtra(FragmentReviewImage.CAPTION, mCaption);
	}
	
	@Override
	protected boolean hasDataToDelete() {
		return true;
	}
}
