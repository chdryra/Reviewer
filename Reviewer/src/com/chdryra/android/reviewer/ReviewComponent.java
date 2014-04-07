package com.chdryra.android.reviewer;

import android.os.Parcel;
import android.os.Parcelable;

public class ReviewComponent implements ReviewNode {
	
	private Review mReview;
	private ReviewNode mParent;
	private ReviewNodeCollection mChildren;
	
	public ReviewComponent(Review node) {
		mReview = node;
		mChildren = new ReviewNodeCollection();
	}
	
	@Override
	public Review getReview() {
		return mReview;
	}
	
	@Override
	public void setParent(Review parent) {
	    setParent(new ReviewComponent(parent));
	}
	
	@Override
	public void setParent(ReviewNode parentNode) {
		if(mParent != null && mParent.getID().equals(parentNode.getID()))
			return;
		mParent = parentNode;
		mParent.addChild(this);
	}
	
	@Override
	public ReviewNode getParent() {
		return mParent;
	}
	
	@Override
	public void addChild(Review child) {
		addChild(new ReviewComponent(child));
	}

	@Override
	public void addChild(ReviewNode childNode) {
	    if(mChildren.containsID(childNode.getID()))
	    	return;
		mChildren.put(childNode.getID(), childNode);
	    childNode.setParent(this);
	}
	
	@Override
	public ReviewNode getChild(ReviewID id) {
		return mChildren.get(id);
	}
	
	@Override
	public void removeChild(ReviewID id) {
		ReviewNode child = getChild(id);
		child.setParent(null);
		mChildren.remove(child.getID());
	}
	
	@Override
	public void addChildren(ReviewCollection children) {
		for(Review child: children)
			addChild(child);
	}
	
	@Override
	public void addChildren(ReviewNodeCollection children) {
		for(ReviewNode childNode: children)
			addChild(childNode);
	}

	@Override
	public void removeChildren(ReviewNodeCollection children) {
		for(Review child: children)
			removeChild(child.getID());
	}
	
	@Override
	public void removeChildren(ReviewCollection children) {
		for(Review child: children)
			removeChild(child.getID());
	}
	
	@Override
	public void clearChildren() {
		removeChildren(getChildren());
	}
	
	@Override
	public ReviewNodeCollection getChildren() {
		return mChildren;
	}
	
	@Override
	public ReviewCollection getChildrenReviews() {
		ReviewNodeCollection childNodes = getChildren();
		ReviewCollection childReviews = new ReviewCollection();
		for(ReviewNode child : childNodes)
			childReviews.add(child.getReview());
		
		return childReviews;
	}
	
	@Override
	public ReviewID getID() {
		return mReview.getID();
	}

	@Override
	public RDTitle getTitle() {
		return mReview.getTitle();
	}

	@Override
	public void setTitle(String title) {
		mReview.setTitle(title);
	}

	@Override
	public RDRating getRating() {
		return mReview.getRating();
	}

	@Override
	public void setRating(float rating) {
		mReview.setRating(rating);
	}

	@Override
	public void setComment(RDComment comment) {
		mReview.setComment(comment);
	}

	@Override
	public RDComment getComment() {
		return mReview.getComment();
	}

	@Override
	public void deleteComment() {
		mReview.deleteComment();
	}

	@Override
	public boolean hasComment() {
		return mReview.hasComment();
	}

	@Override
	public RDImage getImage() {
		return mReview.getImage();
	}

	@Override
	public void setImage(RDImage image) {
	}

	@Override
	public void deleteImage() {
	}

	@Override
	public boolean hasImage() {
		return mReview.hasImage();
	}

	@Override
	public RDLocation getLocation() {
		return mReview.getLocation();
	}

	@Override
	public void setLocation(RDLocation location) {
	}

	@Override
	public void deleteLocation() {
	}

	@Override
	public boolean hasLocation() {
		return mReview.hasLocation();
	}

	@Override
	public RDFacts getFacts() {
		return mReview.getFacts();
	}

	@Override
	public void setFacts(RDFacts facts) {
	}

	@Override
	public void deleteFacts() {
	}

	@Override
	public boolean hasFacts() {
		return mReview.hasFacts();
	}

	@Override
	public void acceptVisitor(VisitorReviewNode visitorReviewNode) {
		visitorReviewNode.visit(this);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(mReview, flags);
		if(mParent != null)
			dest.writeParcelable(mParent.getReview(), flags);
		else
			dest.writeParcelable(null, flags);
		dest.writeParcelable(mChildren, flags);
	}

	public ReviewComponent(Parcel in) {
		mReview = in.readParcelable(Review.class.getClassLoader());
		Review parent = in.readParcelable(ReviewNode.class.getClassLoader()); 
		if(parent != null)
			setParent(parent);
		else
			mParent = null;
		mChildren = in.readParcelable(ReviewNodeCollection.class.getClassLoader());
	}
	
	public static final Parcelable.Creator<ReviewComponent> CREATOR 
	= new Parcelable.Creator<ReviewComponent>() {
	    public ReviewComponent createFromParcel(Parcel in) {
	        return new ReviewComponent(in);
	    }

	    public ReviewComponent[] newArray(int size) {
	        return new ReviewComponent[size];
	    }
	};

	@Override
	public RDUrl getURL() {
		return mReview.getURL();
	}

	@Override
	public void setURL(RDUrl url) {
		mReview.setURL(url);
	}

	@Override
	public void deleteURL() {
		mReview.deleteURL();
	}

	@Override
	public boolean hasURL() {
		return mReview.hasURL();
	}

	@Override
	public RDDate getDate() {
		return mReview.getDate();
	}

	@Override
	public void setDate(RDDate date) {
		mReview.setDate(date);
	}

	@Override
	public void deleteDate() {
		mReview.deleteDate();
	}

	@Override
	public boolean hasDate() {
		return mReview.hasDate();
	}
}
