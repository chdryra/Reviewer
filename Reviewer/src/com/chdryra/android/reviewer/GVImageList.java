package com.chdryra.android.reviewer;

import android.graphics.Bitmap;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.google.android.gms.maps.model.LatLng;

public class GVImageList extends GVReviewDataList<GVImageList.GVImage> {
	
	public GVImageList() {
	}

	@Override
	public GVType getDataType() {
		return GVType.IMAGES;
	}
	
	public GVImageList(GVImageList images) {
		for(GVImage image : images)
			add(image);
	}

	public void add(Bitmap bitmap, LatLng latLng, String caption) {
		add(new GVImage(bitmap, latLng, caption));
	}

	public void remove(Bitmap bitmap, LatLng latLng, String caption) {
		remove(new GVImage(bitmap, latLng, caption));
	}

	public void updateCaption(Bitmap bitmap, LatLng latLng, String oldCaption, String newCaption) {
		GVImage image = getItem(indexOf(new GVImage(bitmap, latLng, oldCaption)));
		image.setCaption(newCaption);
	}

	class GVImage implements GVData{
		private Bitmap mBitmap;
		private String mCaption;
		private LatLng mLatLng;
		
		public GVImage(Bitmap bitmap, LatLng latLng, String caption) {
			mBitmap = bitmap;
			mCaption = caption;
			mLatLng = latLng;
		}
		
		public Bitmap getBitmap() {
			return mBitmap;
		}
		
		public String getCaption() {
			return mCaption;
		}

		public LatLng getLatLng() {
			return mLatLng;
		}
		
		public void setCaption(String caption) {
			mCaption = caption;
		}
		
		@Override
		public ViewHolder getViewHolder() {
			return new VHImageView();
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			GVImage other = (GVImage) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (mBitmap == null) {
				if (other.mBitmap != null)
					return false;
			} else if (!mBitmap.equals(other.mBitmap))
				return false;
			if (mCaption == null) {
				if (other.mCaption != null)
					return false;
			} else if (!mCaption.equals(other.mCaption))
				return false;
			if (mLatLng == null) {
				if (other.mLatLng != null)
					return false;
			} else if (!mLatLng.equals(other.mLatLng))
				return false;
			return true;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result
					+ ((mBitmap == null) ? 0 : mBitmap.hashCode());
			result = prime * result
					+ ((mCaption == null) ? 0 : mCaption.hashCode());
			result = prime * result
					+ ((mLatLng == null) ? 0 : mLatLng.hashCode());
			return result;
		}

		private GVImageList getOuterType() {
			return GVImageList.this;
		}
	}
}
