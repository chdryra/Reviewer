package com.chdryra.android.reviewer;

public class VisitorCommentDeleter implements ReviewVisitor{
	@Override
	public void visit(Review review) {
		review.deleteComment();
	}
}
