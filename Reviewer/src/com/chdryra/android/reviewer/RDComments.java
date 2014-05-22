package com.chdryra.android.reviewer;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class RDComments implements RData, Iterable<RDComment> {
	private Review mHoldingReview;
	private LinkedList<RDComment> mComments = new LinkedList<RDComment>();
	
	public RDComments() {
	}

	public RDComments(RDComments comments, Review review) {
		for(RDComment comment : comments)
			mComments.add(comment);
		mHoldingReview = review;
	}

	@Override
	public void setHoldingReview(Review review) {
		mHoldingReview = review;
	}
	
	@Override
	public Review getHoldingReview() {
		return mHoldingReview;
	}
	
	@Override
	public boolean hasData() {
		return mComments.size() > 0;
	}
	
	public void add(String comment) {
		if(comment != null && comment.length() > 0 )
			mComments.add(new RDComment(comment, mHoldingReview));
	}
	
	public void add(RDComment comment) {
		mComments.add(comment);
	}
	
	public void add(RDComments comments) {
		for(RDComment comment : comments)
			mComments.add(comment);
	}
	
	public void remove(RDComment comment) {
		mComments.remove(comment);
	}
	
	public RDComment getItem(int position) {
		return mComments.get(position);
	}
	
	public int size() {
		if(mComments != null)
			return mComments.size();
		else
			return 0;
	}
	
	@Override
	public Iterator<RDComment> iterator() {
		return new CommentIterator();
	}
	
	class CommentIterator implements Iterator<RDComment> {
		int position = 0;
		
		@Override
		public boolean hasNext() {
			return position < size() && getItem(position) != null;
		}

		@Override
		public RDComment next() {
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
				RDComments.this.remove(getItem(position-1));
		}
	}
}
