package com.chdryra.android.reviewer;

public interface Review {
	
	//Core data
	public RDId getID();
	
	public RDTitle getTitle();
	
	public RDRating getRating();

	public ReviewTagCollection getTags();
	public ReviewNode getReviewNode();
	
	//Optional data
	public RDList<RDComment> getComments();
	public boolean hasComments();

	public RDList<RDFact> getFacts();
	public boolean hasFacts();

	public RDList<RDProCon> getProCons();
	public boolean hasProCons();

	public RDList<RDImage> getImages();
	public boolean hasImages();
	
	public RDList<RDUrl> getURLs();
	public boolean hasURLs();
	
	public RDList<RDLocation> getLocations();
	public boolean hasLocations();
	
	//For speed and comparison
	@Override
	public boolean equals(Object o);
	@Override
	public int hashCode();
}
