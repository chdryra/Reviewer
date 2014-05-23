package com.chdryra.android.reviewer;

public class ReviewComponent implements ReviewNode {

	private RDId mID;
	
	private Review mReview;
	private ReviewNode mParent;
	private RCollectionReviewNode mChildren;

	private boolean mRatingIsAverage = false;
	
	public ReviewComponent(Review review) {
		mID = RDId.generateID();
		mReview = review;
		mChildren = new RCollectionReviewNode();
	}
	
	//ReviewNode methods
	@Override
	public Review getReview() {
		return mReview;
	}
	
	@Override
	public void setParent(Review parent) {
	    setParent(new ReviewComponent(parent));
	}
	
	@Override
	public void setParent(ReviewNode parentNode) {
		if(mParent != null && parentNode != null && mParent.getID().equals(parentNode.getID()))
			return;
		
		mParent = parentNode;
		if(mParent != null)
			mParent.addChild(this);
	}
	
	@Override
	public ReviewNode getParent() {
		return mParent;
	}
	
	@Override
	public void addChild(Review child) {
		addChild(new ReviewComponent(child));
	}

	@Override
	public void addChild(ReviewNode childNode) {
	    if(mChildren.containsID(childNode.getID()))
	    	return;
		mChildren.put(childNode.getID(), childNode);
	    childNode.setParent(this);
	}
	
	@Override
	public ReviewNode getChild(RDId id) {
		return mChildren.get(id);
	}
	
	@Override
	public void removeChild(RDId id) {
		ReviewNode child = getChild(id);
		child.setParent(null);
		mChildren.remove(child.getID());
	}
	
	@Override
	public void addChildren(RCollectionReview children) {
		for(Review child: children)
			addChild(child);
	}
	
	@Override
	public void addChildren(RCollectionReviewNode children) {
		for(ReviewNode childNode: children)
			addChild(childNode);
	}

	@Override
	public void removeChildren(RCollectionReviewNode children) {
		for(Review child: children)
			removeChild(child.getID());
	}
	
	@Override
	public void removeChildren(RCollectionReview children) {
		for(Review child: children)
			removeChild(child.getID());
	}
	
	@Override
	public void clearChildren() {
		RCollectionReviewNode children = new RCollectionReviewNode();
		children.add(getChildren());
		for(ReviewNode child : children)
			removeChild(child.getID());
	}
	
	@Override
	public RCollectionReviewNode getChildren() {
		return mChildren;
	}
	
	@Override
	public RCollectionReviewNode getDescendents() {
		TraverserReviewNode traverser = new TraverserReviewNode(this);
		VisitorNodeCollector collector = new VisitorNodeCollector();
		traverser.setVisitor(collector);
		traverser.traverse();
		
		return collector.get();
	}
	
	@Override
	public RCollectionReview getChildrenReviews() {
		RCollectionReviewNode childNodes = getChildren();
		RCollectionReview childReviews = new RCollectionReview();
		for(ReviewNode child : childNodes)
			childReviews.add(child.getReview());
		
		return childReviews;
	}
	
	@Override
	public boolean isRatingIsAverageOfChildren() {
		return mRatingIsAverage;
	}
	
	@Override
	public void setRatingIsAverageOfChildren(boolean ratingIsAverage) {
		mRatingIsAverage = ratingIsAverage;
	}
	
	@Override
	public ReviewNode getRoot() {
		ReviewNode root = this;
		while(root != null)
			root = root.getParent();
		
		return root;
	}

	@Override
	public int getDepth() {
		int depth = 0;
		if(mParent != null)
			depth = 1 + mParent.getDepth();
		
		return depth;
	}

	@Override
	public int getHeight() {
		int height = 0;
		for(ReviewNode child : getChildren())
			height = Math.max(height, child.getHeight());
		
		return height;
	}

	@Override
	public boolean isRoot() {
		return mParent == null;
	}

	@Override
	public boolean isLeaf() {
		return getChildren().size() == 0;
	}

	@Override
	public boolean isInternal() {
		return !(isRoot() || isLeaf());
	}

	@Override
	public void acceptVisitor(VisitorReviewNode visitorReviewNode) {
		visitorReviewNode.visit(this);
	}
	
	//Review methods
	@Override
	public ReviewNode getReviewNode() {
		return this;
	}

	@Override
	public RDId getID() {
		return mID;
	}

	@Override
	public RDTitle getTitle() {
		return mReview.getTitle();
	}

	@Override
	public void setTitle(String title) {
		mReview.setTitle(title);
	}

	@Override
	public RDRating getRating() {
		return isRatingIsAverageOfChildren()? getAverageRatingOfChildren() : mReview.getRating();
	}

	private RDRating getAverageRatingOfChildren() {
		ReviewMeta metaReview = (ReviewMeta)FactoryReview.createMetaReview("Children");
		metaReview.addReviews(getChildrenReviews());
		return metaReview.getRating();
	}

	@Override
	public void setRating(float rating) {
		mReview.setRating(rating);
	}

	@Override
	public void setComments(RDCollection<RDComment> comments) {
		mReview.setComments(comments);
	}

	@Override
	public RDCollection<RDComment> getComments() {
		return mReview.getComments();
	}

	@Override
	public void deleteComments() {
		mReview.deleteComments();
	}

	@Override
	public boolean hasComments() {
		return mReview.hasComments();
	}

	@Override
	public RDImage getImage() {
		return mReview.getImage();
	}

	@Override
	public void setImage(RDImage image) {
		mReview.setImage(image);
	}

	@Override
	public void deleteImage() {
		mReview.deleteImage();
	}

	@Override
	public boolean hasImage() {
		return mReview.hasImage();
	}

	@Override
	public RDLocation getLocation() {
		return mReview.getLocation();
	}

	@Override
	public void setLocation(RDLocation location) {
		mReview.setLocation(location);
	}

	@Override
	public void deleteLocation() {
		mReview.deleteLocation();
	}

	@Override
	public boolean hasLocation() {
		return mReview.hasLocation();
	}

	@Override
	public RDCollection<RDFact> getFacts() {
		return mReview.getFacts();
	}

	@Override
	public void setFacts(RDCollection<RDFact> facts) {
		mReview.setFacts(facts);
	}

	@Override
	public void deleteFacts() {
		mReview.deleteFacts();
	}

	@Override
	public boolean hasFacts() {
		return mReview.hasFacts();
	}

	@Override
	public RDUrl getURL() {
		return mReview.getURL();
	}

	@Override
	public void setURL(RDUrl url) {
		mReview.setURL(url);
	}

	@Override
	public void deleteURL() {
		mReview.deleteURL();
	}

	@Override
	public boolean hasURL() {
		return mReview.hasURL();
	}

	@Override
	public RDDate getDate() {
		return mReview.getDate();
	}

	@Override
	public void setDate(RDDate date) {
		mReview.setDate(date);
	}

	@Override
	public void deleteDate() {
		mReview.deleteDate();
	}

	@Override
	public boolean hasDate() {
		return mReview.hasDate();
	}

	@Override
	public void deleteProCons() {
		mReview.deleteProCons();
	}
	
	@Override
	public RDCollection<RDProCon> getProCons() {
		return mReview.getProCons();
	}
	
	@Override
	public boolean hasProCons() {
		return mReview.hasProCons();
	}
	
	@Override
	public void setProCons(RDCollection<RDProCon> proCons) {
		mReview.setProCons(proCons);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null || obj.getClass() != getClass())
			return false;
		
		ReviewComponent objNode = (ReviewComponent)obj;
		if(mID.equals(objNode.mID))
			return true;
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return mID.hashCode();
	}

	@Override
	public ReviewTagCollection getTags() {
		return mReview.getTags();
	}
}
