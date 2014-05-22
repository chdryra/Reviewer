package com.chdryra.android.reviewer;

public class RDCommentCollection extends RDCollection<RDComment> {
	public RDCommentCollection() {
		super();
	}
	
	public RDCommentCollection(RDCommentCollection comments, Review review) {
		super(comments, review);
	}

//	public void add(String comment) {
//		if(comment != null && comment.length() > 0 )
//			mData.add(new RDComment(comment, mHoldingReview));
//	}	
}
