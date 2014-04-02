package com.chdryra.android.reviewer;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class ReviewFacts implements Iterable<Datum>, Parcelable{
	private static final String DATA = "FACTS DATA";
	private LinkedHashMap<String, Datum> mData = new LinkedHashMap<String, Datum>();
		
	@SuppressWarnings("unchecked")
	public ReviewFacts(Parcel in) {
		Bundle args = in.readBundle();
		mData = (LinkedHashMap<String, Datum>) args.getSerializable(DATA);
	}

	public ReviewFacts() {
	}

	public void put(String label, String value) {
		if(label != null && value != null)
			mData.put(label, new Datum(label, value));
	}
	
	public void remove(String label) {
		mData.remove(label);
	}
	
	public Datum getItem(int position) {
		String[] keys = mData.keySet().toArray(new String[mData.size()]);
		return get(keys[position]);
	}
	
	public Datum get(String label) {
		return mData.get(label);
	}
	
	public String getValue(String label) {
		return mData.get(label).getValue();
	}
	
	public int size() {
		if(mData != null)
			return mData.size();
		else
			return 0;
	}
	
	@Override
	public Iterator<Datum> iterator() {
		return new DataIterator();
	}
	
	class DataIterator implements Iterator<Datum> {
		int position = 0;
		
		@Override
		public boolean hasNext() {
			return position < size() && getItem(position) != null;
		}

		@Override
		public Datum next() {
			if(hasNext())
				return getItem(position++);
			else
				throw new NoSuchElementException("No more elements left");
		}

		@Override
		public void remove() {
			if(position <= 0) {
				throw new IllegalStateException("Have to do at least one next() before you can delete");
			} else
				ReviewFacts.this.remove(getItem(position-1).getLabel());
		}
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		Bundle args = new Bundle();
		args.putSerializable(DATA, mData);
	}
	
	public static final Parcelable.Creator<ReviewFacts> CREATOR 
	= new Parcelable.Creator<ReviewFacts>() {
	    public ReviewFacts createFromParcel(Parcel in) {
	        return new ReviewFacts(in);
	    }

	    public ReviewFacts[] newArray(int size) {
	        return new ReviewFacts[size];
	    }
	};

}
