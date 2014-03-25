package com.chdryra.android.reviewer;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;

public class ReviewData implements Iterable<ReviewData.Datum>{
	private LinkedHashMap<String, Datum> mData = new LinkedHashMap<String, Datum>();
	
	public void addDatum(String label, String value) {
		if(label != null && value != null)
			mData.put(label, new Datum(label, value));
	}
	
	public void deleteDatum(String label) {
		mData.remove(label);
	}
	
	public Datum getItem(int position) {
		String[] keys = mData.keySet().toArray(new String[mData.size()]);
		return mData.get(keys[position]);
	}
	
	public String getValue(String name) {
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
				deleteDatum(getItem(position-1).mLabel);
		}
	}
}
