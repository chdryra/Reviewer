package com.chdryra.android.reviewer;

public interface ReviewEditable extends Review {
	
	//Core data
	public void setTitle(String title);	
	public void setRating(float rating);

	//Optional data
	public void setComments(RDList<RDComment> comment);
	public void deleteComments();

	public void setFacts(RDList<RDFact> facts);
	public void deleteFacts();	

	public void setProCons(RDList<RDProCon> proCons);
	public void deleteProCons();	

	public void setImages(RDList<RDImage> images);
	public void deleteImages();
	
	public void setURLs(RDList<RDUrl> url);
	public void deleteURLs();	
	
	public void setLocations(RDList<RDLocation> locations);
	public void deleteLocations();	
	
	//For speed and comparison
	@Override
	public boolean equals(Object o);
	@Override
	public int hashCode();
}
