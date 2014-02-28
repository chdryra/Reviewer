package com.chdryra.android.reviewer;

public class CurrencyUnits extends UnitList{
	
	public static final MeasurementDimension DIMENSION = MeasurementDimension.Currency;
	public static final CurrencyUnits UNITS = new CurrencyUnits();
	
	private static final MeasurementUnit[] MUNITS = new MeasurementUnit[]
	  { new MeasurementUnit("GBP","£", DIMENSION),
		new MeasurementUnit("USD","$", DIMENSION),
		new MeasurementUnit("EUR","€", DIMENSION),
	  };
	
	private CurrencyUnits() {
		super(MUNITS);
	}
}
