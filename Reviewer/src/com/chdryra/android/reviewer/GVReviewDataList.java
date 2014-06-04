package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.mygenerallibrary.GVList;


public abstract class GVReviewDataList<T extends GVData> extends GVList<T>{
	
	public enum GVType {
		COMMENTS,
		CRITERIA,
		IMAGES,	
		FACTS,
		PROS,
		CONS,
		PROCONS,
		URLS,
		LOCATIONS,
		TAGS
	}
	
	public abstract GVType getDataType();
}
