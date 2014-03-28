package com.chdryra.android.reviewer;

import com.chdryra.android.reviewer.ReviewIDGenerator.ReviewID;

public interface Review{
	
	//Core data
	public ReviewID getID();
	
	public String getTitle();
	public void setTitle(String title);
	
	public float getRating();
	public void setRating(float rating);

	//Optional data
	public void setComment(ReviewComment comment);
	public ReviewComment getComment();
	public void deleteComment();
	public boolean hasComment();

	public ReviewImage getImage();
	public void setImage(ReviewImage image);
	public void deleteImage();
	public boolean hasImage();
	
	public ReviewLocation getLocation();
	public void setLocation(ReviewLocation location);
	public void deleteLocation();	
	public boolean hasLocation();

	public ReviewFacts getFacts();
	public void setFacts(ReviewFacts facts);
	public void deleteFacts();	
	public boolean hasFacts();

	public void acceptVisitor(ReviewVisitor reviewVisitor);
}
