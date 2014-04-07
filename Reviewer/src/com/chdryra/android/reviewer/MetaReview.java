package com.chdryra.android.reviewer;

import android.os.Parcel;
import android.os.Parcelable;

public class MetaReview implements Review {
	private static final String REVIEWS = "Reviews";

	private RDRating mRating;
	private ReviewNode mNode;

	public MetaReview(String title) {
		mNode = ReviewFactory.createSimpleReviewNode(title);
		mRating = new RDRating(0, this);
	}

	public MetaReview(String title, ReviewCollection reviews) {
		mNode = ReviewFactory.createSimpleReviewNode(title);
		mNode.addChildren(reviews);
		mRating = new RDRating(0, this);
	}

	public MetaReview(Parcel in) {
		mNode = in.readParcelable(ReviewNode.class.getClassLoader());
		mRating = in.readParcelable(RDRating.class.getClassLoader());
	}

	//Review methods
	@Override
	public ReviewID getID() {
		return mNode.getID();
	}

	@Override
	public RDRating getRating() {
		mRating.set(calculateRating(new VisitorRatingAverager()));
		return mRating;
	}

	public RDRating getRating(VisitorRatingCalculator calculator) {
		return new RDRating(calculateRating(calculator), this);
	}
	
	@Override
	public RDTitle getTitle() {
		StringBuilder sb = new StringBuilder();
		sb.append(mNode.getTitle());
		sb.append(": ");
		sb.append(mNode.getChildren().size());
		sb.append(" ");
		sb.append(REVIEWS);
		
		return new RDTitle(sb.toString(), this);
	}

	public void addReview(Review review) {
		mNode.addChild(review);
	}
	
	public void addReviews(ReviewCollection reviews) {
		mNode.addChildren(reviews);
	}
	
	public void removeReview(ReviewID id) {
		mNode.removeChild(id);
	}
	
	public void removeReviews(ReviewCollection reviews) {
		mNode.removeChildren(reviews);
	}
	
	public ReviewCollection getReviews() {
		return mNode.getChildrenReviews();
	}
	
	@Override
	public void setRating(float rating) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setTitle(String title) {
		mNode.setTitle(title);
	}
	
	@Override
	public void setComment(RDComment comment) {
		throw new UnsupportedOperationException();
	}

	@Override
	public RDComment getComment() {
		return null;
	}

	@Override
	public void deleteComment() {
	}

	@Override
	public boolean hasComment() {
		return false;
	}

	@Override
	public RDImage getImage() {
		return null;
	}

	@Override
	public void setImage(RDImage image) {
	}

	@Override
	public void deleteImage() {
	}

	@Override
	public boolean hasImage() {
		return false;
	}

	@Override
	public RDLocation getLocation() {
		return null;
	}

	@Override
	public void setLocation(RDLocation location) {
	}

	@Override
	public void deleteLocation() {
	}

	@Override
	public boolean hasLocation() {
		return false;
	}

	@Override
	public RDFacts getFacts() {
		return null;
	}

	@Override
	public void setFacts(RDFacts data) {
	}

	@Override
	public void deleteFacts() {
	}

	@Override
	public boolean hasFacts() {
		return false;
	}

	@Override
	public RDUrl getURL() {
		return null;
	}

	@Override
	public void setURL(RDUrl url) {
	}

	@Override
	public void deleteURL() {
	}

	@Override
	public boolean hasURL() {
		return false;
	}
	

	@Override
	public RDDate getDate() {
		return null;
	}

	@Override
	public void setDate(RDDate date) {
	}

	@Override
	public void deleteDate() {
	}

	@Override
	public boolean hasDate() {
		return false;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(mNode, flags);
		dest.writeParcelable(mRating, flags);
	}

	private float calculateRating(VisitorRatingCalculator calculator) {
		if(calculator != null) {
			for(ReviewNode r : mNode.getChildren())
				r.acceptVisitor(calculator);
		}

		return calculator.getRating();
	}

	public static final Parcelable.Creator<MetaReview> CREATOR 
	= new Parcelable.Creator<MetaReview>() {
	    public MetaReview createFromParcel(Parcel in) {
	        return new MetaReview(in);
	    }

	    public MetaReview[] newArray(int size) {
	        return new MetaReview[size];
	    }
	};
}
