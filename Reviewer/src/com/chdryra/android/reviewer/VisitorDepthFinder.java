package com.chdryra.android.reviewer;

public class VisitorDepthFinder implements VisitorReviewNode {
	int mDepth = 0;
	
	@Override
	public void visit(ReviewNode reviewNode) {
		ReviewNode parent = reviewNode.getParent();
		if(parent != null) {
			mDepth++;
			visit(parent);
		}
	}
	
	public int getDepth() {
		return mDepth;
	}

}
