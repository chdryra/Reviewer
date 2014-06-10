package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.mygenerallibrary.GVList;


public abstract class GVReviewDataList<T extends GVData> extends GVList<T>{
	
	public enum GVType {
		COMMENTS("comment"),
		CRITERIA("criterion", "criteria"),
		IMAGES("image"),	
		FACTS("fact"),
		PROS("pro"),
		CONS("con"),
		PROCONS("+pro -con"),
		URLS("link"),
		LOCATIONS("location"),
		TAGS("tag");
		
		private String mDatumString;
		private String mDataString;
		
		GVType(String datum) {
			mDatumString = datum;
			mDataString = datum + "s";
		}
		
		GVType(String datum, String data) {
			mDatumString = datum;
			mDataString = data;
		}
		
		public String getDatumString() {
			return mDatumString;
		}
		
		public String getDataString() {
			return mDataString;
		}
	}
	
	private GVType mDataType;
	
	protected GVReviewDataList(GVType dataType) {
		mDataType = dataType;
	}
	
	public GVType getDataType() {
		return mDataType;
	}
}
