package com.chdryra.android.reviewer;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

public class ControllerReviewNode{
	private ReviewNode mNode;
	private ControllerReviewNodeChildren mChildrenController;
	
	public ControllerReviewNode(ReviewNode node) {
		mNode = node;
	}

	public ControllerReviewNodeChildren getChildrenController() {
		if(mChildrenController == null)
			mChildrenController = new ControllerReviewNodeChildren(mNode);
		
		return mChildrenController;
	}
	
	public ControllerReviewNode getControllerForChild(String id) {
		return getChildrenController().getControllerForChild(id);
	}
	
	private Review getReview() {
		return mNode.getReview(); 
	}
	
	//***Accessesors***
	
	public String getID() {
		return mNode.getID().toString();
	}
	
	//Title
	public void setTitle(String title) {
		getReview().getTitle().set(title);
	}
	
	public String getTitle() {
		return getReview().getTitle().get();
	}

	//Rating
	public void setRating(float rating) {
		getReview().setRating(rating);
	}
	
	public float getRating() {
		return getReview().getRating().get();
	}
	
	//RatingISAverage
	public void setReviewRatingAverage(boolean isAverage) {
		getReview().getReviewNode().setRatingIsAverageOfChildren(isAverage);
	}
	
	public boolean isReviewRatingAverage() {
		return getReview().getReviewNode().isRatingIsAverageOfChildren();
	}
	
	//Comment
	public boolean hasComment() {
		return getReview().hasComment();
	}
	
	public void setComment(String comment) {
		Review r = getReview();
		r.setComment(new RDCommentSingle(comment, r));
	}
	
	public String getCommentString() {
		if(hasComment())
			return getReview().getComment().getCommentString();
		else
			return null;
	}

	public String getCommentTitle() {
		if(hasComment())
			return getReview().getComment().getCommentTitle();
		else
			return null;
	}

	public String getCommentHeadline() {
		CommentFormatter formatter = new CommentFormatter(getReview().getComment());
		return formatter.getHeadline();	
	}
	
	public void deleteComment() {
		getReview().deleteComment();
	}
	
	public int numberOfComments() {
		TraverserReviewNode traverser = new TraverserReviewNode(getReview().getReviewNode());
		VisitorCommentCollector collector = new VisitorCommentCollector();
		traverser.setVisitor(collector);
		traverser.traverse();
		return collector.get().size();
	}
	
	//Facts
	public boolean hasFacts() {
		return getReview().hasFacts();
	}
	
	public void deleteFacts() {
		getReview().deleteFacts();
	}
	
	public LinkedHashMap<String, String> getFacts() {
		LinkedHashMap<String, String> factsMap = new LinkedHashMap<String, String>();
		if(!hasFacts())
			return factsMap;
		
		RDFacts facts = getReview().getFacts();
		for(RDFact fact : facts)
			factsMap.put(fact.getLabel(), fact.getValue());
		
		return factsMap;
	}
	
	public void setFacts(LinkedHashMap<String, String> factsMap) {
		Review r = getReview();
		RDFacts facts = new RDFacts(r);
		for(Entry<String, String> entry: factsMap.entrySet())
			facts.put(entry.getKey(), entry.getValue());
		
		r.setFacts(facts);
	}

	//Date
	public boolean hasDate() {
		return getReview().hasDate();
	}
	
	public Date getDate() {
		if(hasDate())
			return getReview().getDate().get();
		else
			return null;
	}
	
	public void setDate(Date date) {
		Review r =getReview();
		r.setDate(new RDDate(date, r));
	}
	
	public void deleteDate() {
		getReview().deleteDate();
	}

	//Image
	public boolean hasImage() {
		return getReview().hasImage();
	}
	
	public Bitmap getImageBitmap() {
		if(hasImage())
			return getReview().getImage().getBitmap();
		else
			return null;
	}
	
	public void setImageBitmap(Bitmap image) {
		Review r = getReview();
		r.setImage(new RDImage(image, r));
	}
	
	public boolean hasImageCaption() {
		if(hasImage())
			return getReview().getImage().hasCaption();
		else
			return false;
	}
	
	public String getImageCaption() {
		if(hasImageCaption())
			return getReview().getImage().getCaption();
		else
			return null;
	}
	
	public void setImageCaption(String caption) {
		if(hasImage())
			getReview().getImage().setCaption(caption);
	}

	public boolean hasImageLatLng() {
		if(hasImage())
			return getReview().getImage().hasLatLng();
		else
			return false;
	}
	
	public LatLng getImageLatLng() {
		if(hasImageCaption())
			return getReview().getImage().getLatLng();
		else
			return null;
	}
	
	public void setImageLatLng(LatLng latLng) {
		if(hasImage())
			getReview().getImage().setLatLng(latLng);
	}
	
	public void deleteImage() {
		getReview().deleteImage();
	}

	
	//Location
	public boolean hasLocation() {
		return getReview().hasLocation();
	}
	
	public LatLng getLocationLatLng() {
		if(hasLocation())
			return getReview().getLocation().getLatLng();
		else
			return null;
	}
	
	public void setLocationLatLng(LatLng latLng) {
		Review r = getReview();
		r.setLocation(new RDLocation(latLng, r));
	}
	
	public boolean hasMapSnapshot() {
		if(hasLocation())
			return getReview().getLocation().hasMapSnapshot();
		else
			return false;
	}
	
	public Bitmap getMapSnapshot() {
		if(hasMapSnapshot())
			return getReview().getLocation().getMapSnapshot();
		else
			return null;
	}

	public void setMapSnapshot(Bitmap snapshot, float zoom) {
		if(hasLocation())
			getReview().getLocation().setMapSnapshot(snapshot, zoom);
	}

	public float getMapSnapshotZoom() {
		if(hasMapSnapshot())
			return getReview().getLocation().getMapSnapshotZoom();
		else
			return 0;
	}

	public boolean hasLocationName() {
		if(hasLocation())
			return getReview().getLocation().hasName();
		else
			return false;
	}
	
	public String getLocationName() {
		if(hasLocationName())
			return getReview().getLocation().getName();
		else
			return null;
	}

	public String getShortLocationName() {
		if(hasLocationName())
			return getReview().getLocation().getShortenedName();
		else
			return null;
	}
	
	public void setLocationName(String name) {
		if(hasLocation())
			getReview().getLocation().setName(name);
	}

	public void deleteLocation() {
		getReview().deleteLocation();
	}
	
	//URL
	public boolean hasURL() {
		return getReview().hasURL();
	}
	
	public URL getURL() {
		if(hasURL())
			return getReview().getURL().get();
		else
			return null;
	}
	
	public void setURL(String urlString) throws MalformedURLException, URISyntaxException{
		Review r = getReview();
		RDUrl url;
		url = new RDUrl(urlString, r);
		r.setURL(url);
	}
	
	public String getURLString() {
		if(hasURL())
			return getReview().getURL().toString();
		else 
			return null;
	}
	
	public String getURLShortenedString() {
		if(hasURL())
			return getReview().getURL().toShortenedString();
		else 
			return null;
	}
	
	public void deleteURL() {
		getReview().deleteURL();
	}
}
