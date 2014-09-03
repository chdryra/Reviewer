package com.chdryra.android.reviewer;

import java.util.Comparator;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.mygenerallibrary.ViewHolder;

public class GVReviewList extends GVReviewDataList<GVReviewList.GVReview> {
	
	public GVReviewList() {
		super(GVType.CRITERIA);
	}
	
	public void add(String subject, float rating) {
		if(!contains(subject))
			add(new GVReview(subject, rating));
	}
	
	public void remove(String subject) {
		remove(new GVReview(subject, 0));
	}
	
	public boolean contains(String subject) {
		GVReview review = new GVReview(subject, 0);
		return contains(review);
	}
	
	public void set(String subject, float rating) {
		GVReview r = getItem(subject);
		if(r != null)
			r.setRating(rating);
	}
	
	private GVReview getItem(String subject) {
		GVReview review = null;
		for(GVReview r : this) {
			if(r.getSubject().equals(subject)) {
				review = r;
				break;
			}
		}
		
		return review;
	}
	
	@Override
	protected Comparator<GVReview> getDefaultComparator() {
		
		return new Comparator<GVReviewList.GVReview>() {
			@Override
			public int compare(GVReview lhs, GVReview rhs) {
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
	
	public class GVReview implements GVData{
		private String mSubject;
		private float mRating;
		
		public GVReview(String subject, float rating) {
			mSubject = subject;
			mRating = rating;
		}
		
		public String getSubject() {
			return mSubject;
		}
		
		public float getRating() {
			return mRating;
		}

		public void setRating(float rating) {
			mRating = rating;
		}
		
		@Override
		public ViewHolder getViewHolder() {
			return new VHReviewNodeCollection();
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			GVReview other = (GVReview) obj;
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

		private GVReviewList getOuterType() {
			return GVReviewList.this;
		}
	}
}
