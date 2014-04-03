package com.chdryra.android.reviewer;

import java.net.URL;

import android.os.Parcel;
import android.os.Parcelable;

public class MetaReview implements Review {
	private static final String REVIEWS = "Reviews";

	private ReviewRating mRating;
	private ReviewNode mNode;
	private VisitorRatingCalculator mRatingCalculator;
	
	public MetaReview(String title, ReviewNodeCollection reviewNodes) {
		mNode = ReviewFactory.createSimpleReviewNode(title);
		mNode.addChildren(reviewNodes);
		mRating = new ReviewRating(0, this);
		mRatingCalculator = new VisitorRatingAverager();
	}

	public MetaReview(Parcel in) {
		mNode = in.readParcelable(ReviewNode.class.getClassLoader());
		mRating = in.readParcelable(ReviewRating.class.getClassLoader());
		mRatingCalculator = new VisitorRatingAverager();
	}

	//Review methods
	@Override
	public ReviewID getID() {
		return mNode.getID();
	}

	@Override
	public ReviewRating getRating() {
		calculateRating();
		return mRating;
	}
	
	@Override
	public ReviewTitle getTitle() {
		StringBuilder sb = new StringBuilder();
		sb.append(getTitle());
		sb.append(": ");
		sb.append(mNode.getChildren().size());
		sb.append(" ");
		sb.append(REVIEWS);
		
		return new ReviewTitle(sb.toString(), this);
	}
	
	@Override
	public void setRating(float rating) {
		throw new UnsupportedOperationException();
	}

	public void setRatingCalculator(VisitorRatingCalculator ratingCalculator) {
		mRatingCalculator = ratingCalculator;
	}
	
	@Override
	public void setTitle(String title) {
		mNode.setTitle(title);
	}
	
	@Override
	public void setComment(ReviewComment comment) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ReviewComment getComment() {
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
	public ReviewImage getImage() {
		return null;
	}

	@Override
	public void setImage(ReviewImage image) {
	}

	@Override
	public void deleteImage() {
	}

	@Override
	public boolean hasImage() {
		return false;
	}

	@Override
	public ReviewLocation getLocation() {
		return null;
	}

	@Override
	public void setLocation(ReviewLocation location) {
	}

	@Override
	public void deleteLocation() {
	}

	@Override
	public boolean hasLocation() {
		return false;
	}

	@Override
	public ReviewFacts getFacts() {
		return null;
	}

	@Override
	public void setFacts(ReviewFacts data) {
	}

	@Override
	public void deleteFacts() {
	}

	@Override
	public boolean hasFacts() {
		return false;
	}

	@Override
	public URL getURL() {
		return null;
	}

	@Override
	public void setURL(URL url) {
	}

	@Override
	public void deleteURL() {
	}

	@Override
	public boolean hasURL() {
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

	private void calculateRating() {
		if(mRatingCalculator != null) {
			for(ReviewNode r : mNode.getChildren())
				r.acceptVisitor(mRatingCalculator);
			
			mRating.set(mRatingCalculator.getRating());
			mRatingCalculator.clear();
		} else
			mRating.set(0);
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
