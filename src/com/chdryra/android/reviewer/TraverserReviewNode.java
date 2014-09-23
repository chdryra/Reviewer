/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

public class TraverserReviewNode {
	private ReviewNode mHead;
	private TraverserSearchMethod mSearchMethod;	
	private VisitorReviewNode mVisitor;
	private RCollection<Integer> mDepthMap;
	private boolean mRelativeDepth = true;

	public TraverserReviewNode(ReviewNode head) {
		mHead = head;
		mSearchMethod = new TraverserSearchDepthFirstPre();
		setVisitor(null);
	}

	public void setVisitor(VisitorReviewNode visitor) {
		mVisitor = visitor == null? new VisitorNull() : visitor; 
	}
	
	public boolean isRelativeDepth() {
		return mRelativeDepth;
	}

	public void setRelativeDepthMapping() {
		mRelativeDepth = true;
	}

	public void setAbsoluteDepthMapping() {
		mRelativeDepth = false;
	}

	public RCollection<Integer> getDepthMap() {
		if(mDepthMap == null)
			traverse(new VisitorNull());
		
		return mDepthMap;
	}

	public void traverse() {
		traverse(mVisitor);
	}

	private void traverse(VisitorReviewNode visitor) {
		int startDepth = isRelativeDepth()? 0 : mHead.getDepth();
		mDepthMap = mSearchMethod.search(mHead, visitor, startDepth);	
	}
}
