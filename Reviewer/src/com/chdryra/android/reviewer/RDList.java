package com.chdryra.android.reviewer;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class RDList<T extends RData> implements RData, Iterable<T> {
	protected Review mHoldingReview;
	protected LinkedList<T> mData = new LinkedList<T>();
	
	public RDList() {
	}

	public RDList(Review holdingReview) {
		mHoldingReview = holdingReview;
	}

	public RDList(RDList<T> data, Review holdingReview) {
		add(data);
		mHoldingReview = holdingReview;
	}

	@Override
	public void setHoldingReview(Review holdingReview) {
		mHoldingReview = holdingReview;
	}
	
	@Override
	public Review getHoldingReview() {
		return mHoldingReview;
	}
	
	@Override
	public boolean hasData() {
		return mData.size() > 0;
	}
	
	public void add(T rData) {
		mData.add(rData);
	}
	
	public void add(RDList<T> data) {
		for(T datum : data)
			mData.add(datum);
	}
	
	public void remove(T datum) {
		mData.remove(datum);
	}
	
	public T getItem(int position) {
		return mData.get(position);
	}
	
	public int size() {
		if(mData != null)
			return mData.size();
		else
			return 0;
	}
	
	@Override
	public Iterator<T> iterator() {
		return new RDIterator();
	}
	
	class RDIterator implements Iterator<T> {
		int position = 0;
		
		@Override
		public boolean hasNext() {
			return position < size() && getItem(position) != null;
		}

		@Override
		public T next() {
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
				RDList.this.remove(getItem(position-1));
		}
	}
}
