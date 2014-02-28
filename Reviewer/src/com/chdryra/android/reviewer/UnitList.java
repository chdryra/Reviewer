package com.chdryra.android.reviewer;

public abstract class UnitList {
	private MeasurementUnit[] mUnits;
	
	protected UnitList(MeasurementUnit[] units) {
		mUnits= units;
	}
	
	public MeasurementUnit[] get() { 
		return mUnits; 
	}
	
	public String[] getNames() {
		String[] names = new String[mUnits.length];
		for(int i = 0; i < mUnits.length; ++i)
			names[i] = mUnits[i].toString();
		
		return names;
	}
}
