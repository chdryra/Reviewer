/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import java.util.Comparator;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.mygenerallibrary.ViewHolder;

public class GVReviewSubjectRatingList extends GVReviewDataList<GVReviewSubjectRatingList.GVReviewSubjectRating> {
	
	public GVReviewSubjectRatingList() {
		super(GVType.CRITERIA);
	}
	
	public void add(String subject, float rating) {
		if(!contains(subject))
			add(new GVReviewSubjectRating(subject, rating));
	}
	
	public void remove(String subject) {
		remove(new GVReviewSubjectRating(subject, 0));
	}
	
	public boolean contains(String subject) {
		GVReviewSubjectRating review = new GVReviewSubjectRating(subject, 0);
		return contains(review);
	}
	
	public void set(String subject, float rating) {
		GVReviewSubjectRating r = getItem(subject);
		if(r != null)
			r.setRating(rating);
	}
	
	private GVReviewSubjectRating getItem(String subject) {
		GVReviewSubjectRating review = null;
		for(GVReviewSubjectRating r : this) {
			if(r.getSubject().equals(subject)) {
				review = r;
				break;
			}
		}
		
		return review;
	}
	
	@Override
	protected Comparator<GVReviewSubjectRating> getDefaultComparator() {
		
		return new Comparator<GVReviewSubjectRatingList.GVReviewSubjectRating>() {
			@Override
			public int compare(GVReviewSubjectRating lhs, GVReviewSubjectRating rhs) {
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
	
	public class GVReviewSubjectRating implements GVData{
		private final String mSubject;
		private float mRating;
		
		public GVReviewSubjectRating(String subject, float rating) {
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
			return new VHReviewNodeSubjectRating();
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			GVReviewSubjectRating other = (GVReviewSubjectRating) obj;
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

		private GVReviewSubjectRatingList getOuterType() {
			return GVReviewSubjectRatingList.this;
		}
	}
}
