/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

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
		REVIEW("review"),
		URLS("link"),
		LOCATIONS("location"),
		TAGS("tag"),
		SOCIAL("social");
		
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
