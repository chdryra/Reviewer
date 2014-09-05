package com.chdryra.android.reviewer;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import android.graphics.Bitmap;

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
	
	public float getRating() {
		VisitorRatingAverageOverDescendents visitor = new VisitorRatingAverageOverDescendents();
		for(ReviewNode child : mReviewNodes)
			child.acceptVisitor(visitor);
		
		return visitor.getRating();
	}
	
	//***Accessesors***
	
	//Title
	public void setSubject(String id, String subject) {
		getControllerFor(id).setSubject(subject);
	}
	
	public String getSubject(String id) {
		return getControllerFor(id).getSubject();
	}

	//RatingISAverage
	public void setReviewRatingAverage(String id, boolean isAverage) {
		getControllerFor(id).setReviewRatingAverage(isAverage);
	}
	
	public boolean isReviewRatingAverage(String id) {
		return getControllerFor(id).isReviewRatingAverage();
	}

	//Rating
	public void setRating(String id, float rating) {
		getControllerFor(id).setRating(rating);
	}
	
	public float getRating(String id) {
		return getControllerFor(id).getRating();
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
	
	public GVReviewSubjectRatingList getGridViewableData() {
		GVReviewSubjectRatingList data = new GVReviewSubjectRatingList();
		for(Review r : get())
			data.add(r.getSubject().get(), r.getRating().get());
		
		return data;
	}
	
	public GVReviewOverviewList getGridViewablePublished() {
		GVReviewOverviewList data = new GVReviewOverviewList();
		for(Review r : get())
			if(r.isPublished()) {
				ControllerReviewNode c = getControllerFor(r.getID().toString());
				GVImageList images = (GVImageList)c.getData(GVType.IMAGES);
				Bitmap cover = images.size() > 0? images.getRandomCover().getBitmap() : null;
				GVLocationList locations = (GVLocationList)c.getData(GVType.LOCATIONS);
				String location = locations.size() > 0 ? locations.getItem(0).getName() : null;
				data.add(c.getID(), c.getSubject(), c.getRating(), cover, location, c.getAuthor(), c.getPublishDate());
			}
		
		return data;
	}
}