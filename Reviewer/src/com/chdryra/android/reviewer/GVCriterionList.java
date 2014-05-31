package com.chdryra.android.reviewer;

import java.util.Comparator;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.mygenerallibrary.GVList;
import com.chdryra.android.mygenerallibrary.ViewHolder;
public class GVCriterionList extends GVList<GVCriterionList.GVCriterion> {
	

	public void add(String subject, float rating) {
		if(!contains(subject))
			add(new GVCriterion(subject, rating));
	}
	
	public void remove(String subject) {
		remove(new GVCriterion(subject, 0));
	}
	
	public boolean contains(String subject) {
		GVCriterion criterion = new GVCriterion(subject, 0);
		return mData.contains(criterion);
	}
	
	@Override
	public ViewHolder getViewHolder(int position) {
		return new VHReviewNodeCollection();
	}
	
	@Override
	protected Comparator<GVCriterion> getDefaultComparator() {
		
		return new Comparator<GVCriterionList.GVCriterion>() {
			@Override
			public int compare(GVCriterion lhs, GVCriterion rhs) {
				int comp = lhs.getSubject().compareTo(rhs.getSubject());
				if(comp == 0) {
					if(lhs.getRating() > rhs.getRating())
						comp = 1;
					else if (lhs.getRating() < rhs.getRating())
						comp = -1;
				}
				
				return comp;
			}
		};
	}
	
	public class GVCriterion implements GVData{
		private String mSubject;
		private float mRating;
		
		public GVCriterion(String subject, float rating) {
			mSubject = subject;
			mRating = rating;
		}
		
		public String getSubject() {
			return mSubject;
		}
		
		public float getRating() {
			return mRating;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			GVCriterion other = (GVCriterion) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (mSubject == null) {
				if (other.mSubject != null)
					return false;
			} else if (!mSubject.equals(other.mSubject))
				return false;
			return true;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + Float.floatToIntBits(mRating);
			result = prime * result
					+ ((mSubject == null) ? 0 : mSubject.hashCode());
			return result;
		}

		private GVCriterionList getOuterType() {
			return GVCriterionList.this;
		}
	}
}
