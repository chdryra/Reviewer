package com.chdryra.android.reviewer;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import android.view.View;
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.GridViewCellAdapter.GridViewable;
import com.chdryra.android.mygenerallibrary.ViewHolder;

public class GVFacts implements GridViewable<GVFacts.GVFact> {
	private LinkedList<GVFact> mData = new LinkedList<GVFact>();
	private static final Comparator<GVFact> COMPARATOR = getDefaultComparator();
	private boolean mIsSorted = false;
	
	public GVFacts() {
	}
	
	public GVFacts(GVFacts facts) {
		for(GVFact fact : facts)
			add(fact.getLabel(), fact.getValue());
	}
	
	public void add(String label, String value) {
		if(!(label != null && label.length() > 0 && value != null && value.length() > 0))
			return;
		
		GVFact fact = new GVFact(label, value);
		if(!mData.contains(fact))
			mData.add(fact);
		
		mIsSorted = false;
	}
	
	public void remove(String label, String value) {
		GVFact fact = new GVFact(label, value);
		mData.remove(fact);
		mIsSorted = false;
	}
	
	public void removeAll() {
		mData.clear();
	}
	
	public boolean hasFact(String label, String value) {
		GVFact fact = new GVFact(label, value);
		return mData.contains(fact);
	}
	
	@Override
	public int size() {
		return mData.size();
	}

	@Override
	public GVFact getItem(int position) {
		return mData.get(position);
	}

	@Override
	public ViewHolder getViewHolder(View convertView) {
		TextView label = (TextView)convertView.findViewById(R.id.fact_label_text_view);
		TextView value = (TextView)convertView.findViewById(R.id.fact_value_text_view);
		return new VHFactView(label, value);
	}

	class VHFactView implements ViewHolder {
		private TextView mLabel;
		private TextView mValue;
		
		public VHFactView(TextView label, TextView value) {
			mLabel = label;
			mValue = value;
		}
		
		@Override
		public void updateView(Object data) {
			GVFact fact = (GVFact)data;
			mLabel.setText(fact.getLabel());
			mValue.setText(fact.getValue());
		}
		
	}
	
	@Override
	public boolean isSorted() {
		return mIsSorted;
	}

	@Override
	public void sort() {
		if(!isSorted())
			sort(COMPARATOR);
	}

	@Override
	public void sort(Comparator<GVFact> comparator) {
		if(!isSorted())
			Collections.sort(mData, comparator);
		mIsSorted = true;
	}
	private static Comparator<GVFact> getDefaultComparator() {
		
		return new Comparator<GVFacts.GVFact>() {
			@Override
			public int compare(GVFact lhs, GVFact rhs) {
				int comp = lhs.getLabel().compareTo(rhs.getLabel());
				if(comp == 0)
					comp = lhs.getValue().compareTo(rhs.getValue());
				
				return comp;
			}
		};
	}
	
	class GVFact {
		private String mLabel;
		private String mValue;
		
		private GVFact(String label, String value) {
			mLabel = label;
			mValue = value;
		}
		
		public String getLabel() {
			return mLabel;
		}
		
		public String getValue() {
			return mValue;
		}

		@Override
		public boolean equals(Object obj) {
			if(obj == null || obj.getClass() != getClass())
				return false;
			
			GVFact objFact = (GVFact)obj;
			if(this.mLabel == objFact.mLabel && this.mValue == objFact.mValue)
				return true;
			
			return false;
		}
		
		@Override
		public int hashCode() {
			String combined = mLabel + ":" + mValue;
			
			return combined.hashCode();
		}
	}
	
	@Override
	public Iterator<GVFact> iterator() {
		return new GVFactIterator();
	}
	
	class GVFactIterator implements Iterator<GVFact> {
		int position = 0;
		
		@Override
		public boolean hasNext() {
			return position < size() && getItem(position) != null;
		}

		@Override
		public GVFact next() {
			if(hasNext())
				return (GVFact)getItem(position++);
			else
				throw new NoSuchElementException("No more elements left");
		}

		@Override
		public void remove() {
			if(position <= 0) {
				throw new IllegalStateException("Have to do at least one next() before you can delete");
			} else
				mData.remove(((GVFact)getItem(position-1)).getLabel());
		}
	}
}
