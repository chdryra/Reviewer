package com.chdryra.android.reviewer;

public class VisitorNodeCollector implements VisitorReviewNode {

	private RCollectionReviewNode mNodes = new RCollectionReviewNode();
	
	@Override
	public void visit(ReviewNode reviewNode) {
		mNodes.add(reviewNode);
	}

	public RCollectionReviewNode get() {
		return mNodes;
	}
}
