package com.chdryra.android.reviewer;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ImageDialogFragment extends BasicDialogFragment {
		
	private Bitmap mImage;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_image, null);
		ImageView imageView = (ImageView)v.findViewById(R.id.dialog_image_image_view);
		mImage = getArguments().getParcelable(ReviewOptionsFragment.REVIEW_IMAGE);
		imageView.setImageBitmap(mImage);
		
		return buildDialog(v);
		}
}
