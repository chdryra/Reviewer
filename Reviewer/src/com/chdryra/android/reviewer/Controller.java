package com.chdryra.android.reviewer;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

public class Controller {
	private static Controller sController;
	
	private CollectionReview mReviews;
	
	private Controller() {
		mReviews = new CollectionReview();
	}
	
	private Controller(CollectionReview reviews) {
		mReviews = reviews;
	}
	
	//Controller Review
	public static Controller getInstance() {
		if(sController == null)
			sController = new Controller();
		
		return sController;
	}

	public Controller getChildReviewsControllerFor(RDId id) {
		return new Controller(getReview(id).getReviewNode().getChildrenReviews());
	}
	
	public RDId addUserReview(String title) {
		Review r = FactoryReview.createUserReview(title);
		mReviews.add(r);
		return r.getID();
	}
	
	public Set<RDId> getIDs() {
		return mReviews.getIDs();
	}
	
	public void remove(RDId id) {
		mReviews.remove(id);
	}
	
	public int size() {
		return mReviews.size();
	}
	
	public Object getItem(int position) {
		return mReviews.getItem(position).getID();
	}
	
	private Review getReview(RDId id) {
		Review r = mReviews.get(id);
		if(r == null)
			r = FactoryReview.createNullReview();
		
		return r; 
	}
	
	
	//***Accessors***
	
	//Title
	public void setTitle(RDId id, String title) {
		getReview(id).getTitle().set(title);
	}
	
	public String getTitle(RDId id) {
		return getReview(id).getTitle().get();
	}

	//Rating
	public void setRating(RDId id, float rating) {
		getReview(id).setRating(rating);
	}
	
	public float getRating(RDId id) {
		return getReview(id).getRating().get();
	}
	
	//RatingISAverage
	public void setReviewRatingAverage(RDId id, boolean isAverage) {
		Review r = getReview(id);
		if(r instanceof ReviewUser)
			((ReviewUser) r).setRatingAverageOfCriteria(isAverage);
	}
	
	public boolean isReviewRatingAverage(RDId id) {
		Review r = getReview(id);
		if(r instanceof ReviewUser)
			return ((ReviewUser) r).isRatingAverageOfCriteria();
		else
			return false;
	}
	
	//Comment
	public boolean hasComment(RDId id) {
		return getReview(id).hasComment();
	}
	
	public void setComment(RDId id, String comment) {
		Review r = getReview(id);
		r.setComment(new RDCommentSingle(comment, r));
	}
	
	public String getCommentString(RDId id) {
		if(hasComment(id))
			return getReview(id).getComment().getCommentString();
		else
			return null;
	}

	public String getCommentTitle(RDId id) {
		if(hasComment(id))
			return getReview(id).getComment().getCommentTitle();
		else
			return null;
	}

	public String getCommentHeadline(RDId id) {
		CommentFormatter formatter = new CommentFormatter(getReview(id).getComment());
		return formatter.getHeadline();	
	}
	
	public void deleteComment(RDId id, boolean includingDescendents) {
		if(!includingDescendents)
			getReview(id).deleteComment();
		else {
			ReviewNodeTraverser traverser = new ReviewNodeTraverser(getReview(id).getReviewNode());
			traverser.setVisitor(new VisitorCommentDeleter());
			traverser.traverse();			
		}
	}
	
	public int numberOfComments(RDId id) {
		ReviewNodeTraverser traverser = new ReviewNodeTraverser(getReview(id).getReviewNode());
		VisitorCommentCollector collector = new VisitorCommentCollector();
		traverser.setVisitor(collector);
		traverser.traverse();
		return collector.getComments().size();
	}
	
	//Facts
	public boolean hasFacts(RDId id) {
		return getReview(id).hasFacts();
	}
	
	public void deleteFacts(RDId id) {
		getReview(id).deleteFacts();
	}
	
	public LinkedHashMap<String, String> getFacts(RDId id) {
		LinkedHashMap<String, String> factsMap = new LinkedHashMap<String, String>();
		if(!hasFacts(id))
			return factsMap;
		
		RDFacts facts = getReview(id).getFacts();
		for(RDFact fact : facts)
			factsMap.put(fact.getLabel(), fact.getValue());
		
		return factsMap;
	}
	
	public void setFacts(RDId id, LinkedHashMap<String, String> factsMap) {
		Review r = getReview(id);
		RDFacts facts = new RDFacts(r);
		for(Entry<String, String> entry: factsMap.entrySet())
			facts.put(entry.getKey(), entry.getValue());
		
		r.setFacts(facts);
	}

	//Date
	public boolean hasDate(RDId id) {
		return getReview(id).hasDate();
	}
	
	public Date getDate(RDId id) {
		if(hasDate(id))
			return getReview(id).getDate().get();
		else
			return null;
	}
	
	public void setDate(RDId id, Date date) {
		Review r =getReview(id);
		r.setDate(new RDDate(date, r));
	}
	
	public void deleteDate(RDId id) {
		getReview(id).deleteDate();
	}

	//Image
	public boolean hasImage(RDId id) {
		return getReview(id).hasImage();
	}
	
	public Bitmap getImageBitmap(RDId id) {
		if(hasImage(id))
			return getReview(id).getImage().getBitmap();
		else
			return null;
	}
	
	public void setImageBitmap(RDId id, Bitmap image) {
		Review r = getReview(id);
		r.setImage(new RDImage(image, r));
	}
	
	public boolean hasImageCaption(RDId id) {
		if(hasImage(id))
			return getReview(id).getImage().hasCaption();
		else
			return false;
	}
	
	public String getImageCaption(RDId id) {
		if(hasImageCaption(id))
			return getReview(id).getImage().getCaption();
		else
			return null;
	}
	
	public void setImageCaption(RDId id, String caption) {
		if(hasImage(id))
			getReview(id).getImage().setCaption(caption);
	}

	public boolean hasImageLatLng(RDId id) {
		if(hasImage(id))
			return getReview(id).getImage().hasLatLng();
		else
			return false;
	}
	
	public LatLng getImageLatLng(RDId id) {
		if(hasImageCaption(id))
			return getReview(id).getImage().getLatLng();
		else
			return null;
	}
	
	public void setImageLatLng(RDId id, LatLng latLng) {
		if(hasImage(id))
			getReview(id).getImage().setLatLng(latLng);
	}
	
	public void deleteImage(RDId id) {
		getReview(id).deleteImage();
	}

	
	//Location
	public boolean hasLocation(RDId id) {
		return getReview(id).hasLocation();
	}
	
	public LatLng getLocationLatLng(RDId id) {
		if(hasLocation(id))
			return getReview(id).getLocation().getLatLng();
		else
			return null;
	}
	
	public void setLocationLatLng(RDId id, LatLng latLng) {
		Review r = getReview(id);
		r.setLocation(new RDLocation(latLng, r));
	}
	
	public boolean hasMapSnapshot(RDId id) {
		if(hasLocation(id))
			return getReview(id).getLocation().hasMapSnapshot();
		else
			return false;
	}
	
	public Bitmap getMapSnapshot(RDId id) {
		if(hasMapSnapshot(id))
			return getReview(id).getLocation().getMapSnapshot();
		else
			return null;
	}

	public void setMapSnapshot(RDId id, Bitmap snapshot, float zoom) {
		if(hasLocation(id))
			getReview(id).getLocation().setMapSnapshot(snapshot, zoom);
	}

	public float getMapSnapshotZoom(RDId id) {
		if(hasMapSnapshot(id))
			return getReview(id).getLocation().getMapSnapshotZoom();
		else
			return 0;
	}

	public boolean hasLocationName(RDId id) {
		if(hasLocation(id))
			return getReview(id).getLocation().hasName();
		else
			return false;
	}
	
	public String getLocationName(RDId id) {
		if(hasLocationName(id))
			return getReview(id).getLocation().getName();
		else
			return null;
	}

	public String getShortLocationName(RDId id) {
		if(hasLocationName(id))
			return getReview(id).getLocation().getShortenedName();
		else
			return null;
	}
	
	public void setLocationName(RDId id, String name) {
		if(hasLocation(id))
			getReview(id).getLocation().setName(name);
	}

	public void deleteLocation(RDId id) {
		getReview(id).deleteLocation();
	}
	
	//URL
	public boolean hasURL(RDId id) {
		return getReview(id).hasURL();
	}
	
	public URL getURL(RDId id) {
		if(hasURL(id))
			return getReview(id).getURL().get();
		else
			return null;
	}
	
	public void setURL(RDId id, String urlString) throws MalformedURLException, URISyntaxException{
		Review r = getReview(id);
		RDUrl url;
		url = new RDUrl(urlString, r);
		r.setURL(url);
	}
	
	public String getURLString(RDId id) {
		if(hasURL(id))
			return getReview(id).getURL().toString();
		else 
			return null;
	}
	
	public String getURLShortenedString(RDId id) {
		if(hasURL(id))
			return getReview(id).getURL().toShortenedString();
		else 
			return null;
	}
	
	public void deleteURL(RDId id) {
		getReview(id).deleteURL();
	}
}
