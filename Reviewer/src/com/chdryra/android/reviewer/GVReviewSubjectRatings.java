package com.chdryra.android.reviewer;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.GridViewCellAdapter.GridViewable;
import com.chdryra.android.mygenerallibrary.ViewHolder;

public class GVReviewSubjectRatings implements GridViewable<GVReviewSubjectRatings.GVReviewSubjectRating>, 
	Iterable<GVReviewSubjectRatings.GVReviewSubjectRating>{
	private LinkedList<GVReviewSubjectRating> mData = new LinkedList<GVReviewSubjectRating>();
	private static final Comparator<GVReviewSubjectRating> COMPARATOR = getDefaultComparator();
	private boolean mIsSorted = false;
	
	public void add(String subject, float rating) {
		if(subject == null || subject.length() == 0)
			return;
		
		GVReviewSubjectRating subjectRating = new GVReviewSubjectRating(subject, rating);
		if(!mData.contains(subject))
			mData.add(subjectRating);
		
		mIsSorted = false;
	}
	
	public void remove(String subject) {
		GVReviewSubjectRating subjectRating = new GVReviewSubjectRating(subject, 0);
		mData.remove(subjectRating);
		mIsSorted = false;
	}
	
	public void removeAll() {
		mData.clear();
	}
	
	public boolean hasSubject(String subject) {
		GVReviewSubjectRating subjectRating = new GVReviewSubjectRating(subject, 0);
		return mData.contains(subjectRating);
	}
	
	@Override
	public int size() {
		return mData.size();
	}

	@Override
	public GVReviewSubjectRating getItem(int position) {
		return mData.get(position);
	}

	@Override
	public ViewHolder getViewHolder(View convertView) {
		TextView subject = (TextView)convertView.findViewById(R.id.review_subject_text_view);
		RatingBar rating = (RatingBar)convertView.findViewById(R.id.total_rating_bar);
		
		return new VHReviewNodeCollection(subject, rating);
	}
	
	class VHReviewNodeCollection implements ViewHolder{
	    private TextView mSubject;
	    private RatingBar mRating;
	    
	    public VHReviewNodeCollection(TextView subject, RatingBar rating) {
	    	mSubject = subject;
	    	mRating = rating;
	    }
	    
		@Override
		public void updateView(Object data) {
			GVReviewSubjectRating controller = (GVReviewSubjectRating) data;
			mSubject.setText(controller.getSubject());
			mRating.setRating(controller.getRating());
		}
	}
	
	private static Comparator<GVReviewSubjectRating> getDefaultComparator() {
		
		return new Comparator<GVReviewSubjectRatings.GVReviewSubjectRating>() {
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
	
	class GVReviewSubjectRating {
		private String mSubject;
		private float mRating;
		
		private GVReviewSubjectRating(String subject, float rating) {
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
			if(obj == null || obj.getClass() != getClass())
				return false;
			
			GVReviewSubjectRating objFact = (GVReviewSubjectRating)obj;
			if(this.mSubject == objFact.mSubject)
				return true;
			
			return false;
		}
		
		@Override
		public int hashCode() {
			String combined = mSubject + ":" + mRating;
			
			return combined.hashCode();
		}
	}
	
	@Override
	public Iterator<GVReviewSubjectRating> iterator() {
		return new GVReviewSubjectRatingIterator();
	}
	
	class GVReviewSubjectRatingIterator implements Iterator<GVReviewSubjectRating> {
		int position = 0;
		
		@Override
		public boolean hasNext() {
			return position < size() && getItem(position) != null;
		}

		@Override
		public GVReviewSubjectRating next() {
			if(hasNext())
				return (GVReviewSubjectRating)getItem(position++);
			else
				throw new NoSuchElementException("No more elements left");
		}

		@Override
		public void remove() {
			if(position <= 0) {
				throw new IllegalStateException("Have to do at least one next() before you can delete");
			} else
				mData.remove((GVReviewSubjectRating)getItem(position-1));
		}
	}


		@Override
		public boolean isSorted() {
			return mIsSorted;
		}

		@Override
		public void sort() {
			sort(COMPARATOR);
		}

		@Override
		public void sort(Comparator<GVReviewSubjectRating> comparator) {
			if(!isSorted()) {
				Collections.sort(mData, comparator);
				mIsSorted = true;
			}
		}
	}
