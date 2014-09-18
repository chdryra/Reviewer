package com.chdryra.android.reviewer;

public class VisitorCommentCollector implements VisitorReviewNode {
	private RDList<RDComment> mData = new RDList<RDComment>();
	
	@Override
	public void visit(ReviewNode reviewNode) {
		if(reviewNode.hasComments())
			mData.add(new RDList<RDComment>(reviewNode.getComments(), reviewNode.getReview()));
	}
	
	public RDList<RDComment> get() {
		return mData;
	}
}
