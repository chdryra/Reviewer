package com.chdryra.android.reviewer;

public class VisitorNodeCollector implements VisitorReviewNode {

	private CollectionReviewNode mNodes = new CollectionReviewNode();
	
	@Override
	public void visit(ReviewNode reviewNode) {
		mNodes.add(reviewNode);
	}

	public CollectionReviewNode get() {
		return mNodes;
	}
}
