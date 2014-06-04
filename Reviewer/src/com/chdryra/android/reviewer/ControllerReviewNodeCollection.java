package com.chdryra.android.reviewer;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;

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
	
	public float getRating() {
		ReviewMeta r = (ReviewMeta)FactoryReview.createMetaReview("");
		r.addReviews(mReviewNodes.getReviews());
		return r.getRating().get();
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
	
	//Date
	public Date getDate(String id) {
		return getControllerFor(id).getDate();
	}
	
	public void setDate(String id, Date date) {
		getControllerFor(id).setDate(date);
	}

	//Optional data
	public boolean hasData(String id, GVType dataType) {
		return getControllerFor(id).hasData(dataType);
	}
	
	public <T extends GVReviewDataList<? extends GVData>> void setData(String id, T data) {
		getControllerFor(id).setData(data);
	}
	
	public GVReviewDataList<? extends GVData> getData(String id, GVType dataType) {
		return getControllerFor(id).getData(dataType);
	}
	
	public void deleteData(String id, GVType dataType) {
		getControllerFor(id).deleteData(dataType);
	}
	
	public GVCriterionList getGridViewiableData() {
		GVCriterionList data = new GVCriterionList();
		for(Review r : get())
			data.add(r.getTitle().get(), r.getRating().get());
		
		return data;
	}
}