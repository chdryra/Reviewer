package com.chdryra.android.reviewer;

import android.os.Parcel;
import android.os.Parcelable;

public class Datum implements Parcelable {

	private String mLabel;
	private String mValue;
	
	public Datum(String label, String value) {	
		mLabel = label;
		mValue = value;			
	}
	
	public Datum(Parcel in) {
		mLabel = in.readString();
		mValue = in.readString();
	}

	public String getLabel() {
		return mLabel;
	}

	public String getValue() {
		return mValue;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mLabel);
		dest.writeString(mValue);
	}

	public static final Parcelable.Creator<Datum> CREATOR = new Parcelable.Creator<Datum>() {
	    public Datum createFromParcel(Parcel in) {
	        return new Datum(in);
	    }

	    public Datum[] newArray(int size) {
	        return new Datum[size];
	    }
	};

}
