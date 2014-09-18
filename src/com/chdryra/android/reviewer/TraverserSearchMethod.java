package com.chdryra.android.reviewer;

public interface TraverserSearchMethod {
	public RCollection<Integer> search(ReviewNode node, VisitorReviewNode visitor, int depth);
}
