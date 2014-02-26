package com.chdryra.android.reviewer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockDialogFragment;

public class ImageDialogFragment extends SherlockDialogFragment {
		
	public static final int RESULT_DELETE_IMAGE = Activity.RESULT_FIRST_USER;	
	private Bitmap mImage;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_image, null);
		ImageView imageView= (ImageView)v.findViewById(R.id.dialog_image_image_view);
		mImage = getArguments().getParcelable(ReviewOptionsFragment.REVIEW_IMAGE);
		
		imageView.setImageBitmap(mImage);
		
		final AlertDialog dialog = buildDialog(v);
		
		return dialog;
		}

	private void sendResult(int resultCode) {
		if (getTargetFragment() == null || resultCode == Activity.RESULT_CANCELED) {
			return;
		}
		
		Intent i = new Intent();
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
	}
	
	private AlertDialog buildDialog(View v) {
		return new AlertDialog.Builder(getActivity()).
				setView(v).
				setPositiveButton(R.string.button_change_photo_text, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						sendResult(Activity.RESULT_OK);
					}
				}).
				setNeutralButton(R.string.button_cancel_text, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						sendResult(Activity.RESULT_CANCELED);
					}
				}).
				setNegativeButton(R.string.button_delete_text, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						sendResult(RESULT_DELETE_IMAGE);
					}
				}).
				create();
	}

}
