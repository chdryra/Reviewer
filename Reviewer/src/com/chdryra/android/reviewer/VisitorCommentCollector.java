package com.chdryra.android.reviewer;

public class VisitorCommentCollector implements ReviewVisitor {
	private ReviewCommentCollection mData = new ReviewCommentCollection();
	
	@Override
	public void visit(Review review) {
		mData.put(review.getID(), review.getComment());
	}
	
	public ReviewCommentCollection getComments() {
		return mData;
	}
}
