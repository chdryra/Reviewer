package com.chdryra.android.reviewer;

import java.util.Comparator;
import java.util.Date;

public class ReviewMeta implements Review {
	private RDId mID;
	private RDTitle mTitle;
	private RDRating mRating;
	
	private RCollectionReview mReviews;
	private boolean mRatingIsValid = false;
	
	private ReviewNode mNode;
	
	private Author mAuthor;
	
	public ReviewMeta(String title, Author author) {
		mReviews = new RCollectionReview();
		init(title, author);
	}

	public ReviewMeta(String title, Author author, RCollectionReview reviews) {
		mReviews = reviews;
		init(title, author);
	}
	
	private void init(String title, Author author) {
		mID = RDId.generateID();
		mTitle = new RDTitle(title, this);
		mRating = new RDRating(0, this);
		mNode = FactoryReview.createReviewNode(this);
		mNode.setRatingIsAverageOfChildren(true);
		mAuthor = author;
	}
	
	//Review methods
	@Override
	public RDId getID() {
		return mID;
	}

	@Override
	public Author getAuthor() {
		return mAuthor;
	}
	
	@Override
	public RDRating getRating() {
		if(!mRatingIsValid)
			mRating = getRating(new VisitorRatingAverager());
		
		return mRating;
	}

	public RDRating getRating(VisitorRatingCalculator calculator) {
		return new RDRating(calculateRating(calculator), this);
	}
	
	public RDList<RDRating> getRatings() {
		RDList<RDRating> ratings = new RDList<RDRating>();
		for(Review r : mReviews)
			ratings.add(r.getRating());
		
		return ratings;
	}
	
	@Override
	public RDTitle getTitle() {
		return mTitle;
	}
	
	public RDList<RDTitle> getTitles() {
		RDList<RDTitle> titles = new RDList<RDTitle>();
		for(Review r : mReviews)
			titles.add(r.getTitle());
		
		return titles;
	}

	public RDList<RDDate> getDates() {
		RDList<RDDate> dates = new RDList<RDDate>();
		for(Review r : mReviews)
			dates.add(r.getDate());
		
		return dates;
	}
	
	@Override
	public ReviewTagCollection getTags() {
		return ReviewTagsManager.getManager().getTags(mReviews);
	}
		
	@Override
	public ReviewNode getReviewNode() {
		return mNode;
	}
	
	public void addReview(Review review) {
		mReviews.add(review);
		mRatingIsValid = false;
	}
	
	public void addReviews(RCollectionReview reviews) {
		mReviews.add(reviews);
		mRatingIsValid = false;
	}
	
	public void removeReview(RDId id) {
		mReviews.remove(id);
		mRatingIsValid = false;
	}
	
	public void removeReviews(RCollectionReview reviews) {
		mReviews.remove(reviews);
		mRatingIsValid = false;
	}

	public RCollectionReview getReviews() {
		return mReviews;
	}

	@Override
	public void setRating(float rating) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setTitle(String title) {
		mTitle.set(title);
	}
	
	@Override
	public void setComments(RDList<RDComment> comments) {
		throw new UnsupportedOperationException();
	}

	@Override
	public RDList<RDComment> getComments() {
		RDList<RDComment> comments = new RDList<RDComment>();
		for(Review r : mReviews)
			comments.add(r.getComments());
		
		return comments;
	}

	@Override
	public void deleteComments() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasComments() {
		return getComments().hasData();
	}

	@Override
	public RDList<RDImage> getImages() {
		RDList<RDImage> images = new RDList<RDImage>();
		for(Review r : mReviews)
			images.add(r.getImages());
		
		return images;
	}

	@Override
	public void setImages(RDList<RDImage> image) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteImages() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasImages() {
		return getImages().hasData();
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
		RDList<RDFact> facts = new RDList<RDFact>();
		for(Review r : mReviews)
			facts.add(r.getFacts());
		
		return facts;
	}

	@Override
	public void setFacts(RDList<RDFact> data) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteFacts() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasFacts() {
		return getFacts().hasData();
	}

	@Override
	public RDList<RDUrl> getURLs() {
		RDList<RDUrl> urls = new RDList<RDUrl>();
		for(Review r : mReviews)
			urls.add(r.getURLs());
		
		return urls;
	}

	@Override
	public void setURLs(RDList<RDUrl> urls) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteURLs() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasURLs() {
		return getURLs().hasData();
	}
	

	@Override
	public RDDate getDate() {
		//Returns most recent date;
		RDList<RDDate> dates = getDates();
		dates.sort(new Comparator<RDDate>() {
			
			@Override
			public int compare(RDDate lhs, RDDate rhs) {
				return -lhs.get().compareTo(rhs.get());
			}
		});
		
		return dates.getItem(0);
	}

	@Override
	public void setDate(Date date) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void deleteProCons() {
		throw new UnsupportedOperationException();
	}

	@Override
	public RDList<RDProCon> getProCons() {
		RDList<RDProCon> proCons = new RDList<RDProCon>();
		for(Review r : mReviews)
			proCons.add(r.getProCons());
		
		return proCons;
	}

	@Override
	public boolean hasProCons() {
		return getProCons().hasData();
	}
	
	@Override
	public void setProCons(RDList<RDProCon> proCons) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null || obj.getClass() != getClass())
			return false;
		
		ReviewMeta objReview = (ReviewMeta)obj;
		if(mID.equals(objReview.mID))
			return true;
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return mID.hashCode();
	}

	private float calculateRating(VisitorRatingCalculator calculator) {
		if(calculator != null) {
			RCollectionReviewNode nodes = new RCollectionReviewNode(mReviews);
			for(ReviewNode r : nodes)
				r.acceptVisitor(calculator);
		}

		return calculator.getRating();
	}
}
