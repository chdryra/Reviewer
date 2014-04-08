package com.chdryra.android.reviewer;

public class VisitorCommentCollector implements VisitorReviewNode {
	private RDCommentCollection mData = new RDCommentCollection();
	
	@Override
	public void visit(ReviewNode reviewNode) {
		if(reviewNode.hasComment())
			mData.put(reviewNode.getID(), reviewNode.getComment());
	}
	
	public RDCommentCollection getComments() {
		return mData;
	}
}
