package com.chdryra.android.reviewer;

public class VisitorCommentCollector implements VisitorReviewNode {
	private RDComments mData = new RDComments();
	
	@Override
	public void visit(ReviewNode reviewNode) {
		if(reviewNode.hasComments())
			mData.add(new RDComments(reviewNode.getComments(), reviewNode.getReview()));
	}
	
	public RDComments get() {
		return mData;
	}
}
