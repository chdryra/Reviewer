package com.chdryra.android.reviewer;

public class VisitorCommentDeleter implements VisitorReviewNode{
	@Override
	public void visit(ReviewNode review) {
		review.deleteComment();
	}
}
