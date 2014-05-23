package com.chdryra.android.reviewer;

public class VisitorCommentCollector implements VisitorReviewNode {
	private RDCollection<RDComment> mData = new RDCollection<RDComment>();
	
	@Override
	public void visit(ReviewNode reviewNode) {
		if(reviewNode.hasComments())
			mData.add(new RDCollection<RDComment>(reviewNode.getComments(), reviewNode.getReview()));
	}
	
	public RDCollection<RDComment> get() {
		return mData;
	}
}
