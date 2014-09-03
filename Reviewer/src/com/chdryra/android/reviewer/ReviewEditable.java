package com.chdryra.android.reviewer;

import java.util.Date;

public abstract class ReviewEditable implements Review {
	
	//Core data
	public abstract void setSubject(String subject);	
	public abstract void setRating(float rating);

	//Optional data
	public abstract void setComments(RDList<RDComment> comment);
	public abstract void deleteComments();

	public abstract void setFacts(RDList<RDFact> facts);
	public abstract void deleteFacts();	

	public abstract void setImages(RDList<RDImage> images);
	public abstract void deleteImages();
	
	public abstract void setURLs(RDList<RDUrl> url);
	public abstract void deleteURLs();	
	
	public abstract void setLocations(RDList<RDLocation> locations);
	public abstract void deleteLocations();	
	
	@Override
	public final Author getAuthor() {
		return null;
	}
	
	@Override
	public final Date getPublishDate() {
		return null;
	}
	
	@Override
	public final boolean isPublished() {
		return false;
	}
	
	//For speed and comparison
	@Override
	public abstract boolean equals(Object o);
	@Override
	public abstract int hashCode();
}
