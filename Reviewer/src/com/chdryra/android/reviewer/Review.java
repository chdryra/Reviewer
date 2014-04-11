package com.chdryra.android.reviewer;

public interface Review {
	
	//Core data
	public RDId getID();
	
	public RDTitle getTitle();
	public void setTitle(String title);
	
	public RDRating getRating();
	public void setRating(float rating);

	public ReviewNode getReviewNode();
	
	//Optional data
	public void setComment(RDComment comment);
	public RDComment getComment();
	public void deleteComment();
	public boolean hasComment();

	public RDImage getImage();
	public void setImage(RDImage image);
	public void deleteImage();
	public boolean hasImage();
	
	public RDLocation getLocation();
	public void setLocation(RDLocation location);
	public void deleteLocation();	
	public boolean hasLocation();

	public RDFacts getFacts();
	public void setFacts(RDFacts facts);
	public void deleteFacts();	
	public boolean hasFacts();
	
	public RDUrl getURL();
	public void setURL(RDUrl url);
	public void deleteURL();	
	public boolean hasURL();
	
	public RDDate getDate();
	public void setDate(RDDate date);
	public void deleteDate();	
	public boolean hasDate();

	//For speed and comparison
	@Override
	public boolean equals(Object o);
	@Override
	public int hashCode();
}
