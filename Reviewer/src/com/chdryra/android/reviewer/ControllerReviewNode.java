package com.chdryra.android.reviewer;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import com.chdryra.android.mygenerallibrary.GVStringList;
import com.chdryra.android.mygenerallibrary.GVStringList.GVString;
import com.chdryra.android.reviewer.GVCommentList.GVComment;
import com.chdryra.android.reviewer.GVFactList.GVFact;
import com.chdryra.android.reviewer.GVImageList.GVImage;
import com.chdryra.android.reviewer.GVUrlList.GVUrl;
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
	public boolean hasComments() {
		return getReview().hasComments();
	}
	
	public void setComments(GVCommentList comments) {
		if(comments.size() == 0)
			return;
		Review r = getReview();
		RDList<RDComment> rdComments = new RDList<RDComment>();
		for(GVComment comment : comments)
			rdComments.add(new RDComment(comment.getComment(), r));
		
		r.setComments(rdComments);
	}
	
	public GVCommentList getComments() {
		GVCommentList comments = new GVCommentList();
		for(RDComment comment : getReview().getComments())
			comments.add(comment.get());
		return comments;
	}
	
	public void deleteComments() {
		getReview().deleteComments();
	}
	
	//Facts
	public boolean hasFacts() {
		return getReview().hasFacts();
	}
	
	public void deleteFacts() {
		getReview().deleteFacts();
	}
	
	public GVFactList getFacts() {
		GVFactList gvFacts = new GVFactList();
		RDList<RDFact> facts = getReview().getFacts();
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
	
	public void setFacts(GVFactList gvFacts) {
		Review r = getReview();
		RDList<RDFact> facts = new RDList<RDFact>(r);
		for(GVFact fact: gvFacts)
			facts.add(new RDFact(fact.getLabel(), fact.getValue()));
		
		r.setFacts(facts);
	}
	
	public Date getDate() {
		return getReview().getDate().get();
	}
	
	public void setDate(Date date) {
		Review r =getReview();
		r.setDate(date);
	}
	
	//Image
	public boolean hasImages() {
		return getReview().hasImages();
	}
	
	public void setImages(GVImageList images) {
		if(images.size() == 0)
			return;
		
		Review r = getReview();
		RDList<RDImage> rdImages = new RDList<RDImage>();
		for(GVImage image : images)
			rdImages.add(new RDImage(image.getBitmap(), image.getLatLng(), image.getCaption(), r));
		
		r.setImages(rdImages);
	}
	
	public GVImageList getImages() {
		GVImageList images = new GVImageList();
		for(RDImage image : getReview().getImages())
			images.add(image.getBitmap(), image.getLatLng(), image.getCaption());
		
		return images;
	}
	
	public void deleteImages() {
		getReview().deleteImages();
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
	public boolean hasURLs() {
		return getReview().hasURLs();
	}
	
	public GVUrlList getURLs() {
		GVUrlList urlList = new GVUrlList();
		for(RDUrl url : getReview().getURLs())
			urlList.add(url.get());
		
		return urlList;
	}
	
	public void setURLs(GVUrlList urlList) {
		if(urlList.size() == 0)
			return;
		
		Review r = getReview();
		RDList<RDUrl> rdUrls = new RDList<RDUrl>();
		for(GVUrl url : urlList)
			rdUrls.add(new RDUrl(url.getUrl(), r));
		
		r.setURLs(rdUrls);
	}
	
	public void deleteURLs() {
		getReview().deleteURLs();
	}
	
	//ProsCons
	public boolean hasProsCons() {
		return getReview().hasProCons();
	}
	
	public boolean hasPros() {
		return getPros().size() > 0;
	}
	
	public boolean hasCons() {
		return getCons().size() > 0;
	}
	
	public GVStringList getPros() {
		return getProsCons(true);
	}
	
	public GVStringList getCons() {
		return getProsCons(false);
	}
	
	private GVStringList getProsCons(boolean getPros) {
		GVStringList gvProsCons = new GVStringList();
		
		RDList<RDProCon> proCons = getReview().getProCons();
		
		for(RDProCon proCon : proCons)
			if(proCon.isPro() == getPros)
				gvProsCons.add(proCon.getProCon());
		
		return gvProsCons;
	}

	public void setProsCons(GVStringList pros, GVStringList cons) {
		Review r = getReview();
		RDList<RDProCon> proCons = new RDList<RDProCon>(r);
		
		for(GVString pro : pros)
			proCons.add(new RDProCon(pro.toString(), true, r));
		
		for(GVString con : cons)
			proCons.add(new RDProCon(con.toString(), false, r));
		
		r.setProCons(proCons);
	}
	
	public void deleteProsCons() {
		getReview().deleteProCons();
	}
	
	public boolean hasTags() {
		return ReviewTagsManager.hasTags(getReview());
	}
	
	public GVStringList getTags() {
		ReviewTagCollection tags = ReviewTagsManager.getTags(getReview());
		GVStringList gvTags = new GVStringList();
		for(ReviewTag tag : tags)
			gvTags.add(tag.toString());
		
		return gvTags;
	}
	
	public void addTags(GVStringList tags) {
		ArrayList<String> tagsList = new ArrayList<String>();
		for(GVString tag : tags)
			tagsList.add(tag.toString());
		
		Collections.sort(tagsList);
		ReviewTagsManager.tag(getReview(), tagsList);
	}
	
	public void removeTags() {
		ReviewTagsManager.untag(getReview());
	}
	
	public void setTags(GVStringList tags) {
		removeTags();
		addTags(tags);
	}
}
