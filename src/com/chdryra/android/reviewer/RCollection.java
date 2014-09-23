/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;
import java.util.Set;

public class RCollection<T> implements Iterable<T> {
	protected LinkedHashMap<RDId, T> mData = new LinkedHashMap<RDId, T>();

	public RCollection() {
	}
	
	public void put(RDId id, T t) {
		if(!containsID(id))
			mData.put(id, t);
	}

	public void add(RCollection<T> items) {
		mData.putAll(items.mData);
	}
	
	public void remove(RDId id) {
		if(containsID(id)) {
			mData.remove(id);
		} 
	}

	public T get(RDId id) {
		return mData.get(id);
	}
	
	public Set<RDId> getIDs() {
		return mData.keySet();
	}
	
	public boolean containsID(RDId id) {
		return mData.containsKey(id);
	}

	public int size() {
		return mData.size();
	}

	public T getItem(int position) {
		return get(getID(position));
	}

	public RDId getID(int position) {
		RDId[] keys = mData.keySet().toArray(new RDId[mData.size()]);
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
