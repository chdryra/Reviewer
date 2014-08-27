package com.chdryra.android.reviewer;

import java.util.ArrayList;

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
	private ReviewEditable mEditableReview;
	ArrayList<String> mTagsList = new ArrayList<String>();
	
	public ControllerReviewNode(ReviewNode node) {
		mNode = node;
		mEditableReview = FactoryReview.createNullReview();
		for(ReviewTag tag : ReviewTagsManager.getTags(getReview()))
				mTagsList.add(tag.toString());
	}

	public ControllerReviewNode(ReviewEditable editableReview) {
		mEditableReview = editableReview;
		mNode = editableReview.getReviewNode();
		for(ReviewTag tag : ReviewTagsManager.getTags(getReview()))
				mTagsList.add(tag.toString());
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

	private ReviewEditable getEditableReview() {
		return mEditableReview; 
	}
	
	//***Accessesors***
	
	public String getID() {
		return mNode.getID().toString();
	}
	
	public String getTitle() {
		return getReview().getTitle().toString();
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
	private boolean hasComments() {
		return getReview().hasComments();
	}
		
	private GVCommentList getComments() {
		GVCommentList comments = new GVCommentList();
		for(RDComment comment : getReview().getComments())
			comments.add(comment.get());
		return comments;
	}
		
	//Facts
	private boolean hasFacts() {
		return getReview().hasFacts();
	}
		
	private GVFactList getFacts() {
		GVFactList gvFacts = new GVFactList();
		RDList<RDFact> facts = getReview().getFacts();
		for(RDFact fact : facts)
			gvFacts.add(fact.getLabel(), fact.getValue());
		
		return gvFacts;
	}
		
	//Image
	private boolean hasImages() {
		return getReview().hasImages();
	}
	
	private GVImageList getImages() {
		GVImageList images = new GVImageList();
		for(RDImage image : getReview().getImages())
			images.add(image.getBitmap(), image.getLatLng(), image.getCaption(), image.isCover());
		
		return images;
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
		
	private boolean hasTags() {
		return mTagsList.size() > 0;
	}
	
	private GVTagList getTags() {
		GVTagList gvTags = new GVTagList();
		for(String tag : mTagsList)
			gvTags.add(tag);
		
		return gvTags;
	}
	
	public void addTags(GVTagList tags) {
		for(GVString tag : tags)
			mTagsList.add(tag.toString());
	}
	
	private void removeTags() {
		mTagsList.clear();
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
	
	//Title
	public void setTitle(String title) {
		getEditableReview().setTitle(title);
	}
	
	//Rating
	public void setRating(float rating) {
		getEditableReview().setRating(rating);
	}
	
	private void setComments(GVCommentList comments) {
		ReviewEditable r = getEditableReview();
		RDList<RDComment> rdComments = new RDList<RDComment>();
		for(GVComment comment : comments)
			rdComments.add(new RDComment(comment.getComment(), r));
		
		r.setComments(rdComments);
	}

	private void deleteComments() {
		getEditableReview().deleteComments();
	}

	private void deleteFacts() {
		getEditableReview().deleteFacts();
	}

	private void setFacts(GVFactList gvFacts) {
		ReviewEditable r = getEditableReview();
		RDList<RDFact> facts = new RDList<RDFact>(r);
		for(GVFact fact: gvFacts)
			facts.add(new RDFact(fact.getLabel(), fact.getValue()));
		
		r.setFacts(facts);
	}

	private void setImages(GVImageList images) {
		ReviewEditable r = getEditableReview();
		RDList<RDImage> rdImages = new RDList<RDImage>();
		for(GVImage image : images)
			rdImages.add(new RDImage(image.getBitmap(), image.getLatLng(), image.getCaption(), image.isCover(), r));
		
		r.setImages(rdImages);
	}
	
	private void deleteImages() {
		getEditableReview().deleteImages();
	}

	private void setLocations(GVLocationList locations) {
		ReviewEditable r = getEditableReview();
		RDList<RDLocation> rdLocations = new RDList<RDLocation>();
		for(GVLocation location : locations)
			rdLocations.add(new RDLocation(location.getLatLng(), location.getName(), r));
		
		r.setLocations(rdLocations);
	}

	
	private void deleteLocations() {
		getEditableReview().deleteLocations();
	}

	private void setURLs(GVUrlList urlList) {
		if(urlList.size() == 0)
			return;
		
		ReviewEditable r = getEditableReview();
		RDList<RDUrl> rdUrls = new RDList<RDUrl>();
		for(GVUrl url : urlList)
			rdUrls.add(new RDUrl(url.getUrl(), r));
		
		r.setURLs(rdUrls);
	}
	
	private void deleteURLs() {
		getEditableReview().deleteURLs();
	}

	private void setProsCons(GVProConList proCons) {
		ReviewEditable r = getEditableReview();
		RDList<RDProCon> rdProCons = new RDList<RDProCon>(r);
		
		for(GVProCon proCon : proCons)
			rdProCons.add(new RDProCon(proCon.toString(), proCon.isPro(), r));
		
		r.setProCons(rdProCons);
	}
	
	private void deleteProCons() {
		getEditableReview().deleteProCons();
	}
}
