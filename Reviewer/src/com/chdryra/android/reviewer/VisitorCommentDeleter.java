package com.chdryra.android.reviewer;

public class VisitorCommentDeleter implements ReviewNodeVisitor{
	@Override
	public void visit(ReviewNode review) {
		review.deleteComment();
	}
}
