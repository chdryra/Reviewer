package com.chdryra.android.reviewer;

import java.util.LinkedHashMap;

public class ReviewData {
	private LinkedHashMap<String, Datum> mData = new LinkedHashMap<String, Datum>();
	
	public ReviewData() {
	}
	
	public void addDatum(String label, String value) {
		if(label != null && value != null)
			mData.put(label, new Datum(label, value));
	}
	
	public void deleteDatum(String label) {
		mData.remove(label);
	}
	
	public LinkedHashMap<String, Datum> getDataMap() {
		return mData;
	}
	
	public String getDatumValue(String name) {
		return mData.get(name).getValue();
	}
	
	public int size() {
		if(mData != null)
			return mData.size();
		else
			return 0;
	}
	
	class Datum {
		private String mLabel;
		private String mValue;
		
		public Datum(String label, String value) {	
			mLabel = label;
			mValue = value;			
		}
		
		public String getLabel() {
			return mLabel;
		}

		public String getValue() {
			return mValue;
		}
	}
}
