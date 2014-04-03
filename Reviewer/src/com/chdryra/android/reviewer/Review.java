package com.chdryra.android.reviewer;

import java.net.URL;

import android.os.Parcelable;

public interface Review extends Parcelable{
	
	//Core data
	public ReviewID getID();
	
	public ReviewTitle getTitle();
	public void setTitle(String title);
	
	public ReviewRating getRating();
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
	
	public URL getURL();
	public void setURL(URL url);
	public void deleteURL();	
	public boolean hasURL();
}
