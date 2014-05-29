package com.chdryra.android.reviewer;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.GVImageList.GVImage;

class VHImageView implements ViewHolder {
	public static final int LAYOUT = R.layout.grid_cell_image;
	
	private ImageView mImage;
	private TextView mCaption;
	
	public VHImageView(View convertView) {
		init(convertView);
	}
	
	public VHImageView(Context context) {
		init(View.inflate(context, LAYOUT, null));
	}
	
	private void init(View view) {
		mImage = (ImageView)view.findViewById(R.id.image_view);
		mCaption = (TextView)view.findViewById(R.id.text_view);
	}
	
	@Override
	public void updateView(Object data) {
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
