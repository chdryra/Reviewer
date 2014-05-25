package com.chdryra.android.reviewer;

public interface Review {
	
	//Core data
	public RDId getID();
	
	public RDTitle getTitle();
	public void setTitle(String title);
	
	public RDRating getRating();
	public void setRating(float rating);

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
	
	public RDLocation getLocation();
	public void setLocation(RDLocation location);
	public void deleteLocation();	
	public boolean hasLocation();
	
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
