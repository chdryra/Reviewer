package com.chdryra.android.reviewer;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

public class ControllerReviewNodeCollection {
	protected RCollectionReviewNode mReviewNodes;
	protected HashMap<String, ControllerReviewNode> mControllers;
	
	public ControllerReviewNodeCollection(RCollectionReviewNode reviewNodes) {
		mReviewNodes = reviewNodes;
		mControllers = new HashMap<String, ControllerReviewNode>();
	} 
	
	public void remove(String id) {
		get().remove(Controller.convertID(id));
	}
	
	public void removeAll() {
		for(String id : getIDs())
			remove(id);
	}
	
	public void add(String title) {
		Review r = FactoryReview.createUserReview(title);
		mReviewNodes.add(r.getReviewNode());
	}
	
	public ControllerReviewNode getControllerFor(String id) {
		if(mControllers.get(id) == null)
			mControllers.put(id, new ControllerReviewNode(get(id)));
		
		return mControllers.get(id);
	}
	
	protected RCollectionReviewNode get() {
		return mReviewNodes;
	}
	
	protected ReviewNode get(String id) {
		ReviewNode r = get().get(Controller.convertID(id));
		if(r == null)
			r = FactoryReview.createNullReviewNode();
		
		return r; 
	}
	
	public Set<String> getIDs() {
		Set<String> stringIDs = new LinkedHashSet<String>();
		Set<RDId> ids = get().getIDs();
		for(RDId id : ids)
			stringIDs.add(id.toString());
		
		return stringIDs;
	}
	
	public int size() {
		return get().size();
	}
	
	public ControllerReviewNode getItem(int position) {
		return getControllerFor(get().getItem(position).getID().toString());
	}
	
	private ReviewNode getChild(String id) {
		ReviewNode r = get().get(Controller.convertID(id));
		if(r == null)
			r = FactoryReview.createNullReviewNode();
		
		return r; 
	}
	
	//***Accessesors***
	
	//Title
	public void setTitle(String id, String title) {
		getControllerFor(id).setTitle(title);
	}
	
	public String getTitle(String id) {
		return getControllerFor(id).getTitle();
	}

	//RatingISAverage
	public void setReviewRatingAverage(String id, boolean isAverage) {
		getChild(id).getReviewNode().setRatingIsAverageOfChildren(isAverage);
	}
	
	public boolean isReviewRatingAverage(String id) {
		return getChild(id).getReviewNode().isRatingIsAverageOfChildren();
	}

	//Rating
	public void setRating(String id, float rating) {
		getControllerFor(id).setRating(rating);
	}
	
	public float getRating(String id) {
		return getControllerFor(id).getRating();
	}
	
	//Comment
	public boolean hasComments(String id) {
		return getControllerFor(id).hasComments();
	}
	
	public void setComments(String id, GVComments comments) {
		getControllerFor(id).setComments(comments);
	}
	
	public GVComments getComments(String id) {
		return getControllerFor(id).getComments();
	}
	
	public void deleteComments(String id) {
		getControllerFor(id).deleteComments();
	}
	
	//Facts
	public boolean hasFacts(String id) {
		return getControllerFor(id).hasFacts();
	}
	
	public void deleteFacts(String id) {
		getControllerFor(id).deleteFacts();
	}
	
	public GVFacts getFacts(String id) {
		return getControllerFor(id).getFacts();
	}
	
	public void setFacts(String id, GVFacts facts) {
		getControllerFor(id).setFacts(facts);
	}

	//Date
	public boolean hasDate(String id) {
		return getControllerFor(id).hasDate();
	}
	
	public Date getDate(String id) {
		return getControllerFor(id).getDate();
	}
	
	public void setDate(String id, Date date) {
		getControllerFor(id).setDate(date);
	}
	
	public void deleteDate(String id) {
		getControllerFor(id).deleteDate();
	}

	//Image
	public boolean hasImage(String id) {
		return getControllerFor(id).hasImage();
	}
	
	public Bitmap getImageBitmap(String id) {
		return getControllerFor(id).getImageBitmap();
	}
	
	public void setImageBitmap(String id, Bitmap image) {
		getControllerFor(id).setImageBitmap(image);
	}
	
	public boolean hasImageCaption(String id) {
		return getControllerFor(id).hasImageCaption();
	}
	
	public String getImageCaption(String id) {
		return getControllerFor(id).getImageCaption();
	}
	
	public void setImageCaption(String id, String caption) {
		getControllerFor(id).setImageCaption(caption);
	}

	public boolean hasImageLatLng(String id) {
		return getControllerFor(id).hasImageLatLng();
	}
	
	public LatLng getImageLatLng(String id) {
		return getControllerFor(id).getImageLatLng();
	}
	
	public void setImageLatLng(String id, LatLng latLng) {
		getControllerFor(id).setImageLatLng(latLng);
	}
	
	public void deleteImage(String id) {
		getControllerFor(id).deleteImage();
	}

	
	//Location
	public boolean hasLocation(String id) {
		return getControllerFor(id).hasLocation();
	}
	
	public LatLng getLocationLatLng(String id) {
		return getControllerFor(id).getLocationLatLng();
	}
	
	public void setLocation(String id, LatLng latLng, String name) {
		getControllerFor(id).setLocation(latLng, name);
	}

	public boolean hasLocationName(String id) {
		return getControllerFor(id).hasLocationName();
	}
	
	public String getLocationName(String id) {
		return getControllerFor(id).getLocationName();
	}

	public String getShortLocationName(String id) {
		return getControllerFor(id).getShortLocationName();
	}
	
	public void deleteLocation(String id) {
		getControllerFor(id).deleteLocation();
	}
	
	//URL
	public boolean hasURL(String id) {
		return getControllerFor(id).hasURL();
	}
	
	public URL getURL(String id) {
		return getControllerFor(id).getURL();
	}
	
	public void setURL(String id, String urlString) throws MalformedURLException, URISyntaxException{
		getControllerFor(id).setURL(urlString);
	}
	
	public String getURLString(String id) {
		return getControllerFor(id).getURLString();
	}
	
	public String getURLShortenedString(String id) {
		return getControllerFor(id).getURLShortenedString();
	}
	
	public void deleteURL(String id) {
		getControllerFor(id).deleteURL();
	}

	public GVReviewSubjectRatings getGridViewiableData() {
		GVReviewSubjectRatings data = new GVReviewSubjectRatings();
		for(Review r : get())
			data.add(r.getTitle().get(), r.getRating().get());
		
		return data;
	}
}