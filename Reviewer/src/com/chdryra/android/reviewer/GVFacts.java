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
			add(fact);
	}
	
	public void add(GVFact fact) {
		if(fact == null)
			return;
		
		mData.add(fact);
		mIsSorted = false;
	}
	
	public void remove(GVFact fact) {
		mData.remove(fact);
		mIsSorted = false;
	}
	
	public void removeAll() {
		mData.clear();
	}
	
	public boolean contains(GVFact fact) {
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
		
		public GVFact(String label, String value) {
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
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			GVFact other = (GVFact) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (mLabel == null) {
				if (other.mLabel != null)
					return false;
			} else if (!mLabel.equals(other.mLabel))
				return false;
			if (mValue == null) {
				if (other.mValue != null)
					return false;
			} else if (!mValue.equals(other.mValue))
				return false;
			return true;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result
					+ ((mLabel == null) ? 0 : mLabel.hashCode());
			result = prime * result
					+ ((mValue == null) ? 0 : mValue.hashCode());
			return result;
		}

		private GVFacts getOuterType() {
			return GVFacts.this;
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
				mData.remove(((GVFact)getItem(position-1)));
		}
	}
}
