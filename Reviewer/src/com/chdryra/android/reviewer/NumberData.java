package com.chdryra.android.reviewer;

import java.util.HashMap;

public class NumberData {
	private HashMap<String, Datum> mData = new HashMap<String, Datum>();
	
	public NumberData() {
	}
	
	public void addDatum(String name, double value, MeasurementUnit unit) {
		mData.put(name, new Datum(value, unit));	
	}
	
	public HashMap<String, Datum> getDataMap() {
		return mData;
	}
	
	private class Datum {
		private double mValue;
		private MeasurementUnit mUnit;
		
		public Datum(double value, MeasurementUnit unit) {
			mValue = value;
			mUnit = unit;
		}
	}
}
