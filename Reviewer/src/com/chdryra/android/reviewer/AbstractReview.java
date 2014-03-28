package com.chdryra.android.reviewer;

import com.chdryra.android.reviewer.ReviewIDGenerator.ReviewID;

public abstract class AbstractReview implements Review {

	@Override
	public abstract ReviewID getID();

	@Override
	public abstract String getTitle();

	@Override
	public abstract void setTitle(String title);

	@Override
	public abstract float getRating();

	@Override
	public abstract void setRating(float rating);

	@Override
	public void setComment(ReviewComment comment) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ReviewComment getComment() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteComment() {
		throw new UnsupportedOperationException();	
	}

	@Override
	public boolean hasComment() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ReviewImage getImage() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setImage(ReviewImage image) {
		throw new UnsupportedOperationException();	
	}

	@Override
	public void deleteImage() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasImage() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ReviewLocation getLocation() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setLocation(ReviewLocation location) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteLocation() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasLocation() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ReviewFacts getFacts() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setFacts(ReviewFacts data) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteFacts() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasFacts() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void acceptVisitor(ReviewVisitor reviewVisitor) {
		throw new UnsupportedOperationException();
	}
}
