package com.chdryra.android.reviewer;

public class MeasurementUnit {
	private String mName;
	private String mSymbol;
	private MeasurementDimension mDimension;
	
	MeasurementUnit(String name, String symbol, MeasurementDimension dimension) {
	    mName = name;
		mSymbol = symbol;
		mDimension = dimension;
	}
	
	public String getSymbol() { return mSymbol; }
	public String toString() { return mName; }
	public MeasurementDimension getDimension() { return mDimension; }
}
