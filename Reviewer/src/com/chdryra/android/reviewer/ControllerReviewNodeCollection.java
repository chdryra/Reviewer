package com.chdryra.android.reviewer;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map.Entry;
import java.util.Set;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.GridViewableData;
import com.chdryra.android.mygenerallibrary.ViewHolder;
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
	
	public Object getItem(int position) {
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
		getChild(id).getTitle().set(title);
	}
	
	public String getTitle(String id) {
		return getChild(id).getTitle().get();
	}

	//Rating
	public void setRating(String id, float rating) {
		getChild(id).setRating(rating);
	}
	
	public float getRating(String id) {
		return getChild(id).getRating().get();
	}
	
	//RatingISAverage
	public void setReviewRatingAverage(String id, boolean isAverage) {
		getChild(id).getReviewNode().setRatingIsAverageOfChildren(isAverage);
	}
	
	public boolean isReviewRatingAverage(String id) {
		return getChild(id).getReviewNode().isRatingIsAverageOfChildren();
	}
	
	//Comment
	public boolean hasComment(String id) {
		return getChild(id).hasComment();
	}
	
	public void setComment(String id, String comment) {
		Review r = getChild(id);
		r.setComment(new RDCommentSingle(comment, r));
	}
	
	public String getCommentString(String id) {
		if(hasComment(id))
			return getChild(id).getComment().getCommentString();
		else
			return null;
	}

	public String getCommentTitle(String id) {
		if(hasComment(id))
			return getChild(id).getComment().getCommentTitle();
		else
			return null;
	}

	public String getCommentHeadline(String id) {
		CommentFormatter formatter = new CommentFormatter(getChild(id).getComment());
		return formatter.getHeadline();	
	}
	
	public void deleteComment(String id, boolean includingDescendents) {
		if(!includingDescendents)
			getChild(id).deleteComment();
		else {
			TraverserReviewNode traverser = new TraverserReviewNode(getChild(id).getReviewNode());
			traverser.setVisitor(new VisitorCommentDeleter());
			traverser.traverse();			
		}
	}
	
	public int numberOfComments(String id) {
		TraverserReviewNode traverser = new TraverserReviewNode(getChild(id).getReviewNode());
		VisitorCommentCollector collector = new VisitorCommentCollector();
		traverser.setVisitor(collector);
		traverser.traverse();
		return collector.get().size();
	}
	
	//Facts
	public boolean hasFacts(String id) {
		return getChild(id).hasFacts();
	}
	
	public void deleteFacts(String id) {
		getChild(id).deleteFacts();
	}
	
	public LinkedHashMap<String, String> getFacts(String id) {
		LinkedHashMap<String, String> factsMap = new LinkedHashMap<String, String>();
		if(!hasFacts(id))
			return factsMap;
		
		RDFacts facts = getChild(id).getFacts();
		for(RDFact fact : facts)
			factsMap.put(fact.getLabel(), fact.getValue());
		
		return factsMap;
	}
	
	public void setFacts(String id, LinkedHashMap<String, String> factsMap) {
		Review r = getChild(id);
		RDFacts facts = new RDFacts(r);
		for(Entry<String, String> entry: factsMap.entrySet())
			facts.put(entry.getKey(), entry.getValue());
		
		r.setFacts(facts);
	}

	//Date
	public boolean hasDate(String id) {
		return getChild(id).hasDate();
	}
	
	public Date getDate(String id) {
		if(hasDate(id))
			return getChild(id).getDate().get();
		else
			return null;
	}
	
	public void setDate(String id, Date date) {
		Review r =getChild(id);
		r.setDate(new RDDate(date, r));
	}
	
	public void deleteDate(String id) {
		getChild(id).deleteDate();
	}

	//Image
	public boolean hasImage(String id) {
		return getChild(id).hasImage();
	}
	
	public Bitmap getImageBitmap(String id) {
		if(hasImage(id))
			return getChild(id).getImage().getBitmap();
		else
			return null;
	}
	
	public void setImageBitmap(String id, Bitmap image) {
		Review r = getChild(id);
		r.setImage(new RDImage(image, r));
	}
	
	public boolean hasImageCaption(String id) {
		if(hasImage(id))
			return getChild(id).getImage().hasCaption();
		else
			return false;
	}
	
	public String getImageCaption(String id) {
		if(hasImageCaption(id))
			return getChild(id).getImage().getCaption();
		else
			return null;
	}
	
	public void setImageCaption(String id, String caption) {
		if(hasImage(id))
			getChild(id).getImage().setCaption(caption);
	}

	public boolean hasImageLatLng(String id) {
		if(hasImage(id))
			return getChild(id).getImage().hasLatLng();
		else
			return false;
	}
	
	public LatLng getImageLatLng(String id) {
		if(hasImageCaption(id))
			return getChild(id).getImage().getLatLng();
		else
			return null;
	}
	
	public void setImageLatLng(String id, LatLng latLng) {
		if(hasImage(id))
			getChild(id).getImage().setLatLng(latLng);
	}
	
	public void deleteImage(String id) {
		getChild(id).deleteImage();
	}

	
	//Location
	public boolean hasLocation(String id) {
		return getChild(id).hasLocation();
	}
	
	public LatLng getLocationLatLng(String id) {
		if(hasLocation(id))
			return getChild(id).getLocation().getLatLng();
		else
			return null;
	}
	
	public void setLocationLatLng(String id, LatLng latLng) {
		Review r = getChild(id);
		r.setLocation(new RDLocation(latLng, r));
	}

	public boolean hasLocationName(String id) {
		if(hasLocation(id))
			return getChild(id).getLocation().hasName();
		else
			return false;
	}
	
	public String getLocationName(String id) {
		if(hasLocationName(id))
			return getChild(id).getLocation().getName();
		else
			return null;
	}

	public String getShortLocationName(String id) {
		if(hasLocationName(id))
			return getChild(id).getLocation().getShortenedName();
		else
			return null;
	}
	
	public void setLocationName(String id, String name) {
		if(hasLocation(id))
			getChild(id).getLocation().setName(name);
	}

	public void deleteLocation(String id) {
		getChild(id).deleteLocation();
	}
	
	//URL
	public boolean hasURL(String id) {
		return getChild(id).hasURL();
	}
	
	public URL getURL(String id) {
		if(hasURL(id))
			return getChild(id).getURL().get();
		else
			return null;
	}
	
	public void setURL(String id, String urlString) throws MalformedURLException, URISyntaxException{
		Review r = getChild(id);
		RDUrl url;
		url = new RDUrl(urlString, r);
		r.setURL(url);
	}
	
	public String getURLString(String id) {
		if(hasURL(id))
			return getChild(id).getURL().toString();
		else 
			return null;
	}
	
	public String getURLShortenedString(String id) {
		if(hasURL(id))
			return getChild(id).getURL().toShortenedString();
		else 
			return null;
	}
	
	public void deleteURL(String id) {
		getChild(id).deleteURL();
	}

	public GridViewableData getGridViewData() {
		return new GVDReviewNodeCollection();
	}
	
	class GVDReviewNodeCollection implements GridViewableData{
		@Override
		public int size() {
			return ControllerReviewNodeCollection.this.size();
		}

		@Override
		public Object getItem(int position) {
			return ControllerReviewNodeCollection.this.getItem(position);
		}

		@Override
		public ViewHolder getViewHolder(View convertView) {
			TextView subject = (TextView)convertView.findViewById(R.id.review_subject_text_view);
			RatingBar rating = (RatingBar)convertView.findViewById(R.id.total_rating_bar);
			
			return new VHReviewNodeCollection(subject, rating);
		}
		
		class VHReviewNodeCollection implements ViewHolder{
		    private TextView mSubject;
		    private RatingBar mRating;
		    
		    public VHReviewNodeCollection(TextView subject, RatingBar rating) {
		    	mSubject = subject;
		    	mRating = rating;
		    }
		    
			@Override
			public void updateView(Object data, int textColour) {
				ControllerReviewNode controller = (ControllerReviewNode) data;
				mSubject.setText(controller.getTitle());
				mSubject.setTextColor(textColour);
				mRating.setRating(controller.getRating());
			}
		}
	}
}

