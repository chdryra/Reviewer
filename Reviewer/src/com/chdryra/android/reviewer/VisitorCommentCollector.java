package com.chdryra.android.reviewer;

public class VisitorCommentCollector implements VisitorReviewNode {
	private ReviewCommentCollection mData = new ReviewCommentCollection();
	
	@Override
	public void visit(ReviewNode reviewNode) {
		mData.put(reviewNode.getID(), reviewNode.getComment());
	}
	
	public ReviewCommentCollection getComments() {
		return mData;
	}
}
