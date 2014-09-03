package com.chdryra.android.reviewer;

import java.util.Date;

public class ReviewPublisher {
	private Author mAuthor;
	private Date mPublishDate;
	
	public ReviewPublisher(Author author) {
		mAuthor = author;
		mPublishDate = new Date();
	}
	
	public Author getAuthor() {
		return mAuthor;
	}

	public Date getPublishDate() {
		return mPublishDate;
	}

	public ReviewNode publish(Review review) {
		ReviewNode reviewTree = review.getReviewNode();
		
		VisitorTreePublisher publisher = new VisitorTreePublisher(this);
		publisher.visit(reviewTree);
		
		return publisher.getPublishedTree();
	}
}
