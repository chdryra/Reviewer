package com.chdryra.android.reviewer;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.mygenerallibrary.ViewHolderBasic;
import com.chdryra.android.reviewer.GVImageList.GVImage;

public class VHImageView extends ViewHolderBasic {
	private static final int LAYOUT = R.layout.grid_cell_image;
	private static final int IMAGE = R.id.image_view;
	private static final int CAPTION = R.id.text_view;
	
	private ImageView mImage;
	private TextView mCaption;
	
	public VHImageView() {
		super(LAYOUT);
	}
	
	@Override
	protected void initViewsToUpdate() {
		mImage = (ImageView)getView(IMAGE);
		mCaption = (TextView)getView(CAPTION);
	}
	
	@Override
	public void updateView(GVData data) {
		GVImage image = (GVImage)data;
		Bitmap bitmap = image.getBitmap();
		if(bitmap != null) {
			mImage.setImageBitmap(image.getBitmap());
			mCaption.setVisibility(View.GONE);
		} else {
			mImage.setVisibility(View.GONE);
			mCaption.setText(image.getCaption());
		}
	}
}
