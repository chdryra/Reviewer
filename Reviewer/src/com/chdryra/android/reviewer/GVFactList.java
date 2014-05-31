package com.chdryra.android.reviewer;

import java.util.Comparator;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.mygenerallibrary.GVList;
import com.chdryra.android.mygenerallibrary.ViewHolder;

public class GVFactList extends GVList<GVFactList.GVFact> {
	
	public GVFactList() {
	}

	public void add(String label, String value) {
		add(new GVFact(label, value));
	}
	
	public boolean contains(String label, String value) {
		return contains(new GVFact(label, value));
	}
	
	public void remove(String label, String value) {
		remove(new GVFact(label, value));
	}
	
	@Override
	public ViewHolder getViewHolder(int position) {
		return new VHFactView();
	}
		
	@Override
	protected Comparator<GVFact> getDefaultComparator() {
		
		return new Comparator<GVFactList.GVFact>() {
			@Override
			public int compare(GVFact lhs, GVFact rhs) {
				int comp = lhs.getLabel().compareTo(rhs.getLabel());
				if(comp == 0)
					comp = lhs.getValue().compareTo(rhs.getValue());
				
				return comp;
			}
		};
	}
	
	class GVFact implements GVData{
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

		private GVFactList getOuterType() {
			return GVFactList.this;
		}
	}
}
