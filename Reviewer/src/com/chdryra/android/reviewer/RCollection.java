package com.chdryra.android.reviewer;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;

public class RCollection<T> implements Iterable<T> {
	protected LinkedHashMap<ReviewID, T> mData = new LinkedHashMap<ReviewID, T>();

	public RCollection() {
	}
	
	public void put(ReviewID id, T t) {
		if(!containsID(id))
			mData.put(id, t);
	}
	
	public void remove(ReviewID id) {
		if(containsID(id)) {
			mData.remove(id);
		} 
	}

	public T get(ReviewID id) {
		return mData.get(id);
	}
	
	public boolean containsID(ReviewID id) {
		return mData.containsKey(id);
	}

	public int size() {
		return mData.size();
	}

	public T getItem(int position) {
		return get(getID(position));
	}

	public ReviewID getID(int position) {
		ReviewID[] keys = mData.keySet().toArray(new ReviewID[mData.size()]);
		return keys[position];
	}

	@Override
	public Iterator<T> iterator() {
		return new CollectionIterator();
	}
	
	class CollectionIterator implements Iterator<T> {
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
				RCollection.this.remove(getID(position));
		}
	}
	
}
