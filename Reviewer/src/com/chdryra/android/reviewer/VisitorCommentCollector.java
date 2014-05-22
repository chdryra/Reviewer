package com.chdryra.android.reviewer;

public class VisitorCommentCollector implements VisitorReviewNode {
	private RDCommentCollection mData = new RDCommentCollection();
	
	@Override
	public void visit(ReviewNode reviewNode) {
		if(reviewNode.hasComments())
			mData.add(new RDCommentCollection(reviewNode.getComments(), reviewNode.getReview()));
	}
	
	public RDCommentCollection get() {
		return mData;
	}
}
