package com.chdryra.android.reviewer;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;

import android.os.Parcel;
import android.os.Parcelable;

public class RDFacts implements Iterable<RDFact>, ReviewData{

	private Review mHoldingReview;
	private LinkedHashMap<String, RDFact> mData = new LinkedHashMap<String, RDFact>();

	public RDFacts() {
	}

	public RDFacts(Review holdingReview) {
		mHoldingReview = holdingReview;
	}

	public RDFacts(Parcel in) {
		Parcelable[] data = in.readParcelableArray(RDFact.class.getClassLoader());
		for(int i = 0; i < data.length; ++i) {
			RDFact rDFact = (RDFact)data[i];
			mData.put(rDFact.getLabel(), rDFact);
		}
	}

	@Override
	public void setHoldingReview(Review review) {
		mHoldingReview = review;
	}

	@Override
	public Review getHoldingReview() {
		return mHoldingReview;
	}	

	@Override
	public boolean hasData() {
		return size() > 0;
	}
	
	public void put(String label, String value) {
		if(label != null && value != null)
			mData.put(label, new RDFact(label, value, mHoldingReview));
	}
	
	public void remove(String label) {
		mData.remove(label);
	}
	
	public RDFact getItem(int position) {
		String[] keys = mData.keySet().toArray(new String[mData.size()]);
		return get(keys[position]);
	}
	
	public RDFact get(String label) {
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
	public Iterator<RDFact> iterator() {
		return new DataIterator();
	}
	
	class DataIterator implements Iterator<RDFact> {
		int position = 0;
		
		@Override
		public boolean hasNext() {
			return position < size() && getItem(position) != null;
		}

		@Override
		public RDFact next() {
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
				RDFacts.this.remove(getItem(position-1).getLabel());
		}
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		RDFact[] data = mData.values().toArray(new RDFact[mData.size()]);
		dest.writeParcelableArray(data, flags);
	}
	
	public static final Parcelable.Creator<RDFacts> CREATOR 
	= new Parcelable.Creator<RDFacts>() {
	    public RDFacts createFromParcel(Parcel in) {
	        return new RDFacts(in);
	    }

	    public RDFacts[] newArray(int size) {
	        return new RDFacts[size];
	    }
	};

}
