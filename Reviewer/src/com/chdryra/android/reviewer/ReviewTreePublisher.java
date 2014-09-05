package com.chdryra.android.reviewer;

import java.util.Date;

public class ReviewTreePublisher {
	private Author mAuthor;
	private Date mPublishDate;
	
	public ReviewTreePublisher(Author author) {
		mAuthor = author;
	}
	
	public Author getAuthor() {
		return mAuthor;
	}

	public Date getPublishDate() {
		return mPublishDate;
	}

	public ReviewNode publish(Review review) {
		mPublishDate = new Date();
		VisitorTreePublisher publisher = new VisitorTreePublisher(this);		
		review.getReviewNode().acceptVisitor(publisher);
		
		return publisher.getPublishedTree();
	}
}
