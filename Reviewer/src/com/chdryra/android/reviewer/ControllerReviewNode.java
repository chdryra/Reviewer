package com.chdryra.android.reviewer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.mygenerallibrary.GVString;
import com.chdryra.android.reviewer.GVCommentList.GVComment;
import com.chdryra.android.reviewer.GVFactList.GVFact;
import com.chdryra.android.reviewer.GVImageList.GVImage;
import com.chdryra.android.reviewer.GVLocationList.GVLocation;
import com.chdryra.android.reviewer.GVProConList.GVProCon;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;
import com.chdryra.android.reviewer.GVUrlList.GVUrl;
import com.chdryra.android.reviewer.ReviewTagsManager.ReviewTag;

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
		getChildrenController().removeChild(childId);
	}
	
	public void addChild(String title) {
		getChildrenController().addChild(title);
	}
	
	public void addChildren(GVCriterionList children) {
		getChildrenController().addChildren(children);
	}

	public void setChildren(GVCriterionList children) {
		getChildrenController().setChildren(children);
	}

	public void clear() {
		getChildrenController().removeAll();
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
	
	public Date getDate() {
		return getReview().getDate().get();
	}
	
	public void setDate(Date date) {
		Review r =getReview();
		r.setDate(date);
	}
	
	//RatingISAverage
	public void setReviewRatingAverage(boolean isAverage) {
		getReview().getReviewNode().setRatingIsAverageOfChildren(isAverage);
	}
	
	public boolean isReviewRatingAverage() {
		return getReview().getReviewNode().isRatingIsAverageOfChildren();
	}
	
	//Comment
	private boolean hasComments() {
		return getReview().hasComments();
	}
	
	private void setComments(GVCommentList comments) {
		Review r = getReview();
		RDList<RDComment> rdComments = new RDList<RDComment>();
		for(GVComment comment : comments)
			rdComments.add(new RDComment(comment.getComment(), r));
		
		r.setComments(rdComments);
	}
	
	private GVCommentList getComments() {
		GVCommentList comments = new GVCommentList();
		for(RDComment comment : getReview().getComments())
			comments.add(comment.get());
		return comments;
	}
	
	private void deleteComments() {
		getReview().deleteComments();
	}
	
	//Facts
	private boolean hasFacts() {
		return getReview().hasFacts();
	}
	
	private void deleteFacts() {
		getReview().deleteFacts();
	}
	
	private GVFactList getFacts() {
		GVFactList gvFacts = new GVFactList();
		RDList<RDFact> facts = getReview().getFacts();
		for(RDFact fact : facts)
			gvFacts.add(fact.getLabel(), fact.getValue());
		
		return gvFacts;
	}
		
	private void setFacts(GVFactList gvFacts) {
		Review r = getReview();
		RDList<RDFact> facts = new RDList<RDFact>(r);
		for(GVFact fact: gvFacts)
			facts.add(new RDFact(fact.getLabel(), fact.getValue()));
		
		r.setFacts(facts);
	}
	
	//Image
	private boolean hasImages() {
		return getReview().hasImages();
	}
	
	private void setImages(GVImageList images) {
		Review r = getReview();
		RDList<RDImage> rdImages = new RDList<RDImage>();
		for(GVImage image : images)
			rdImages.add(new RDImage(image.getBitmap(), image.getLatLng(), image.getCaption(), r));
		
		r.setImages(rdImages);
	}
	
	private GVImageList getImages() {
		GVImageList images = new GVImageList();
		for(RDImage image : getReview().getImages())
			images.add(image.getBitmap(), image.getLatLng(), image.getCaption());
		
		return images;
	}
	
	private void deleteImages() {
		getReview().deleteImages();
	}
	
	//Location
	private boolean hasLocations() {
		return getReview().hasLocations();
	}
	
	private GVLocationList getLocations() {
		GVLocationList locations = new GVLocationList();
		for(RDLocation location : getReview().getLocations())
			locations.add(location.getLatLng(), location.getName());
		
		return locations;
	}
	
	private void setLocations(GVLocationList locations) {
		Review r = getReview();
		RDList<RDLocation> rdLocations = new RDList<RDLocation>();
		for(GVLocation location : locations)
			rdLocations.add(new RDLocation(location.getLatLng(), location.getName(), r));
		
		r.setLocations(rdLocations);
	}
	
	private void deleteLocations() {
		getReview().deleteLocations();
	}
	
	//URL
	private boolean hasURLs() {
		return getReview().hasURLs();
	}
	
	private GVUrlList getURLs() {
		GVUrlList urlList = new GVUrlList();
		for(RDUrl url : getReview().getURLs())
			urlList.add(url.get());
		
		return urlList;
	}
	
	private void setURLs(GVUrlList urlList) {
		if(urlList.size() == 0)
			return;
		
		Review r = getReview();
		RDList<RDUrl> rdUrls = new RDList<RDUrl>();
		for(GVUrl url : urlList)
			rdUrls.add(new RDUrl(url.getUrl(), r));
		
		r.setURLs(rdUrls);
	}
	
	private void deleteURLs() {
		getReview().deleteURLs();
	}
	
	//ProsCons
	private boolean hasProCons() {
		return getReview().hasProCons();
	}
	
	private boolean hasPros() {
		return getPros().size() > 0;
	}
	
	private boolean hasCons() {
		return getCons().size() > 0;
	}
	
	private GVProConList getPros() {
		return getProCons(true);
	}
	
	private GVProConList getCons() {
		return getProCons(false);
	}
	
	private GVProConList getProCons(boolean getPros) {
		GVProConList gvProsCons = new GVProConList();
		RDList<RDProCon> proCons = getReview().getProCons();
		
		for(RDProCon proCon : proCons)
			if(proCon.isPro() == getPros)
				gvProsCons.add(proCon.getProCon(), proCon.isPro());
		
		return gvProsCons;
	}
	
	private GVProConList getProCons() {
		GVProConList gvProsCons = new GVProConList();
		RDList<RDProCon> proCons = getReview().getProCons();
		
		for(RDProCon proCon : proCons)
			gvProsCons.add(proCon.getProCon(), proCon.isPro());
		
		return gvProsCons;
	}
	
	public GVProConSummaryList getProConSummary() {
		return new GVProConSummaryList(getPros(), getCons());
	}
	
	private void setProsCons(GVProConList proCons) {
		Review r = getReview();
		RDList<RDProCon> rdProCons = new RDList<RDProCon>(r);
		
		for(GVProCon proCon : proCons)
			rdProCons.add(new RDProCon(proCon.toString(), proCon.isPro(), r));
		
		r.setProCons(rdProCons);
	}
	
	private void deleteProCons() {
		getReview().deleteProCons();
	}
	
	private boolean hasTags() {
		return ReviewTagsManager.hasTags(getReview());
	}
	
	private GVTagList getTags() {
		ReviewTagCollection tags = ReviewTagsManager.getTags(getReview());
		GVTagList gvTags = new GVTagList();
		for(ReviewTag tag : tags)
			gvTags.add(tag.toString());
		
		return gvTags;
	}
	
	public void addTags(GVTagList tags) {
		ArrayList<String> tagsList = new ArrayList<String>();
		for(GVString tag : tags)
			tagsList.add(tag.toString());
		
		Collections.sort(tagsList);
		ReviewTagsManager.tag(getReview(), tagsList);
	}
	
	private void removeTags() {
		ReviewTagsManager.untag(getReview());
	}
	
	private void setTags(GVTagList tags) {
		removeTags();
		addTags(tags);
	}

	public boolean hasData(GVType dataType) {
		if (dataType == GVType.COMMENTS)
			return hasComments();
		else if (dataType == GVType.IMAGES)
			return hasImages();
		else if (dataType == GVType.FACTS)
			return hasFacts();
		else if (dataType == GVType.PROS)
			return hasPros();
		else if (dataType == GVType.CONS)
			return hasCons();
		else if (dataType == GVType.PROCONS)
			return hasProCons();
		else if (dataType == GVType.URLS)
			return hasURLs();
		else if (dataType == GVType.LOCATIONS)
			return hasLocations();
		else if (dataType == GVType.TAGS)
			return hasTags();
		else if (dataType == GVType.CRITERIA)
			return getChildrenController().size() > 0;
		else
			return false;
	}

	public void deleteData(GVType dataType) {
		if (dataType == GVType.COMMENTS)
			deleteComments();
		else if (dataType == GVType.IMAGES)
			deleteImages();
		else if (dataType == GVType.FACTS)
			deleteFacts();
		else if (dataType == GVType.PROCONS)
			deleteProCons();
		else if (dataType == GVType.URLS)
			deleteURLs();
		else if (dataType == GVType.LOCATIONS)
			deleteLocations();
		else if (dataType == GVType.TAGS)
			removeTags();
		else if (dataType == GVType.CRITERIA)
			getChildrenController().removeAll();
	}

	public GVReviewDataList<? extends GVData> getData(GVType dataType) {
		if (dataType == GVType.COMMENTS)
			return getComments();
		else if (dataType == GVType.IMAGES)
			return getImages();
		else if (dataType == GVType.FACTS)
			return getFacts();
		else if (dataType == GVType.PROS)
			return getPros();
		else if (dataType == GVType.CONS)
			return getCons();
		else if (dataType == GVType.PROCONS)
			return getProCons();
		else if (dataType == GVType.URLS)
			return getURLs();
		else if (dataType == GVType.LOCATIONS)
			return getLocations();
		else if (dataType == GVType.TAGS)
			return getTags();
		else if (dataType == GVType.CRITERIA)
			return getChildrenController().getGridViewiableData();
		else
			return null;
	}
	
	public <T extends GVReviewDataList<? extends GVData>> void setData(T data) {
		GVType dataType = data.getDataType();
		if (dataType == GVType.COMMENTS)
			setComments((GVCommentList) data);
		else if (dataType == GVType.IMAGES)
			setImages((GVImageList) data);
		else if (dataType == GVType.FACTS)
			setFacts((GVFactList) data);
		else if (dataType == GVType.URLS)
			setURLs((GVUrlList) data);
		else if (dataType == GVType.PROCONS)
			setProsCons((GVProConList) data);
		else if (dataType == GVType.LOCATIONS)
			setLocations((GVLocationList) data);
		else if (dataType == GVType.TAGS)
			setTags((GVTagList) data);
		else if (dataType == GVType.CRITERIA)
			setChildren((GVCriterionList) data);
	}
}
