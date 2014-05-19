package com.chdryra.android.reviewer;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import android.graphics.Bitmap;

import com.chdryra.android.mygenerallibrary.GVStrings;
import com.chdryra.android.reviewer.GVFacts.GVFact;
import com.chdryra.android.reviewer.ReviewTagsManager.ReviewTag;
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
	
	public ControllerReviewNodeCollection getCollectionController() {
		return new ControllerReviewNodeCollection(mNode.getChildren());
	}
	
	public ControllerReviewNode getControllerForChild(String id) {
		return getChildrenController().getControllerFor(id);
	}
	
	public void clearChildren() {
		getChildrenController().removeAll();
	}
	
	public void removeChild(String childId) {
		mNode.removeChild(Controller.convertID(childId));
	}
	
	public void addChild(String title) {
		Review r = FactoryReview.createUserReview(title);
		mNode.addChild(r);
	}
	
	public void clear() {
		mNode.clearChildren();
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
	
	public int getNumberOfComments() {
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
	
	public GVFacts getFacts() {
		GVFacts gvFacts = new GVFacts();
		RDFacts facts = getReview().getFacts();
		for(RDFact fact : facts)
			gvFacts.add(fact.getLabel(), fact.getValue());
		
		return gvFacts;
	}
	
	public int getNumberOfFacts() {
		int numFacts = 0;
		if(getFacts() != null)
			numFacts = getFacts().size();
		
		return numFacts;
	}
	
	public void setFacts(GVFacts gvFacts) {
		Review r = getReview();
		RDFacts facts = new RDFacts(r);
		for(GVFact fact: gvFacts)
			facts.put(fact.getLabel(), fact.getValue());
		
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
	
	public void setLocation(LatLng latLng, String name) {
		Review r = getReview();
		RDLocation location = new RDLocation(latLng, r);
		location.setName(name);
		r.setLocation(location);
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
	
	//ProsCons
	public boolean hasProsCons() {
		return getReview().hasProsCons();
	}
	
	public boolean hasPros() {
		return getPros().size() > 0;
	}
	
	public boolean hasCons() {
		return getCons().size() > 0;
	}
	
	public GVStrings getPros() {
		return getProsCons(true);
	}
	
	public GVStrings getCons() {
		return getProsCons(false);
	}
	
	private GVStrings getProsCons(boolean getPros) {
		GVStrings gvProsCons = new GVStrings();
		
		RDProsCons prosCons = getPros? getReview().getProsCons().getPros() : getReview().getProsCons().getCons();
		for(RDProCon proCon : prosCons)
			gvProsCons.add(proCon.getProCon());
		
		return gvProsCons;
	}

	public void setProsCons(GVStrings pros, GVStrings cons) {
		Review r = getReview();
		RDProsCons prosCons = new RDProsCons(r);
		
		for(String pro : pros)
			prosCons.addPro(pro);
		
		for(String con : cons)
			prosCons.addCon(con);
		
		r.setProsCons(prosCons);
	}
	
	public void deleteProsCons() {
		getReview().deleteProsCons();
	}
	
	public boolean hasTags() {
		return ReviewTagsManager.hasTags(getReview());
	}
	
	public GVStrings getTags() {
		ReviewTagCollection tags = ReviewTagsManager.getTags(getReview());
		GVStrings gvTags = new GVStrings();
		for(ReviewTag tag : tags)
			gvTags.add(tag.toString());
		
		return gvTags;
	}
	
	public void addTags(GVStrings tags) {
		ArrayList<String> tagsList = new ArrayList<String>();
		for(String tag : tags)
			tagsList.add(tag);
		
		Collections.sort(tagsList);
		ReviewTagsManager.tag(getReview(), tagsList);
	}
	
	public void removeTags() {
		ReviewTagsManager.untag(getReview());
	}
	
	public void setTags(GVStrings tags) {
		removeTags();
		addTags(tags);
	}
}
