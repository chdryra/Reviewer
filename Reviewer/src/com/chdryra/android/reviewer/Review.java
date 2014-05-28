package com.chdryra.android.reviewer;

import java.util.Date;

public interface Review {
	
	//Core data
	public RDId getID();
	
	public RDTitle getTitle();
	public void setTitle(String title);
	
	public RDRating getRating();
	public void setRating(float rating);

	public RDDate getDate();
	public void setDate(Date date);

	public ReviewTagCollection getTags();
	public ReviewNode getReviewNode();
	
	//Optional data
	public RDList<RDComment> getComments();
	public void setComments(RDList<RDComment> comment);
	public void deleteComments();
	public boolean hasComments();

	public RDList<RDFact> getFacts();
	public void setFacts(RDList<RDFact> facts);
	public void deleteFacts();	
	public boolean hasFacts();

	public RDList<RDProCon> getProCons();
	public void setProCons(RDList<RDProCon> proCons);
	public void deleteProCons();	
	public boolean hasProCons();

	public RDList<RDImage> getImages();
	public void setImages(RDList<RDImage> images);
	public void deleteImages();
	public boolean hasImages();
	
	public RDList<RDUrl> getURLs();
	public void setURLs(RDList<RDUrl> url);
	public void deleteURLs();	
	public boolean hasURLs();
	
	public RDList<RDLocation> getLocations();
	public void setLocations(RDList<RDLocation> locations);
	public void deleteLocations();	
	public boolean hasLocations();
	
	//For speed and comparison
	@Override
	public boolean equals(Object o);
	@Override
	public int hashCode();
}
