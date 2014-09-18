package com.chdryra.android.reviewer;

public class ReviewNull extends ReviewEditable {
	
	public ReviewNull() {
	}
	
	@Override
	public RDId getID() {
		return null;
	}
	
	@Override
	public RDSubject getSubject() {
		return null;
	}

	@Override
	public void setSubject(String title) {
	}

	@Override
	public RDRating getRating() {
		return null;
	}

	@Override
	public void setRating(float rating) {
	}

	@Override
	public ReviewNode getReviewNode() {
		return null;
	}
	
	@Override
	public void setComments(RDList<RDComment> comments) {
	}

	@Override
	public RDList<RDComment> getComments() {
		return null;
	}

	@Override
	public void deleteComments() {
	}

	@Override
	public boolean hasComments() {
		return false;
	}

	@Override
	public RDList<RDImage> getImages() {
		return null;
	}

	@Override
	public void setImages(RDList<RDImage> images) {
	}

	@Override
	public void deleteImages() {
	}

	@Override
	public boolean hasImages() {
		return false;
	}

	@Override
	public RDList<RDLocation> getLocations() {
		return null;
	}

	@Override
	public void setLocations(RDList<RDLocation> location) {
	}

	@Override
	public void deleteLocations() {
	}

	@Override
	public boolean hasLocations() {
		return false;
	}

	@Override
	public RDList<RDFact> getFacts() {
		return null;
	}

	@Override
	public void setFacts(RDList<RDFact> facts) {
	}

	@Override
	public void deleteFacts() {
	}

	@Override
	public boolean hasFacts() {
		return false;
	}

	@Override
	public RDList<RDUrl> getURLs() {
		return null;
	}

	@Override
	public void setURLs(RDList<RDUrl> urls) {
	}

	@Override
	public void deleteURLs() {
	}

	@Override
	public boolean hasURLs() {
		return false;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null || obj.getClass() != getClass())
			return false;
		
		ReviewNull objReview = (ReviewNull)obj;
		return objReview.getID() == null && this.getID() == null;
	}
	
	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public ReviewTagCollection getTags() {
		return null;
	}

	@Override
	public ReviewNode publish(ReviewTreePublisher publisher) {
		return FactoryReview.createNullReviewNode();
	}
}
