package com.chdryra.android.reviewer;

import java.util.Collection;
import com.chdryra.android.reviewer.ReviewIDGenerator.ReviewID;

public class ReviewComponent implements ReviewNode {
	
	private TreeNode<Review> mNode;
	
	public ReviewComponent(Review node) {
		mNode = new TreeNode<Review>(node);
	}
	
	private ReviewComponent(TreeNode<Review> node) {
		mNode = node;
	} 
	
	@Override
	public void setParent(Review parent) {
		mNode.setParent(parent);
	}
	
	@Override
	public ReviewNode getParent() {
		return new ReviewComponent(mNode.getParent());
	}
	
	@Override
	public void addChild(Review child) {
		mNode.addChild(child);
	}
	
	@Override
	public void addChildren(ReviewCollection children) {
		for(Review child: children)
			addChild(child);
	}
	
	@Override
	public ReviewCollection getChildren() {
		Collection<TreeNode<Review>> children = mNode.getChildren();
		ReviewCollection rc = new ReviewCollection();
		for(TreeNode<Review> child: children)
			rc.add(new ReviewComponent(child));
		return rc;
	}
	
	@Override
	public ReviewID getID() {
		return mNode.getHead().getID();
	}

	@Override
	public String getTitle() {
		return mNode.getHead().getTitle();
	}

	@Override
	public void setTitle(String title) {
	}

	@Override
	public float getRating() {
		return mNode.getHead().getRating();
	}

	@Override
	public void setRating(float rating) {
	}

	@Override
	public void setComment(ReviewComment comment) {
	}

	@Override
	public ReviewComment getComment() {
		return mNode.getHead().getComment();
	}

	@Override
	public void deleteComment() {
	}

	@Override
	public boolean hasComment() {
		return mNode.getHead().hasComment();
	}

	@Override
	public ReviewImage getImage() {
		return mNode.getHead().getImage();
	}

	@Override
	public void setImage(ReviewImage image) {
	}

	@Override
	public void deleteImage() {
	}

	@Override
	public boolean hasImage() {
		return mNode.getHead().hasImage();
	}

	@Override
	public ReviewLocation getLocation() {
		return mNode.getHead().getLocation();
	}

	@Override
	public void setLocation(ReviewLocation location) {
	}

	@Override
	public void deleteLocation() {
	}

	@Override
	public boolean hasLocation() {
		return mNode.getHead().hasLocation();
	}

	@Override
	public ReviewFacts getFacts() {
		return mNode.getHead().getFacts();
	}

	@Override
	public void setFacts(ReviewFacts facts) {
	}

	@Override
	public void deleteFacts() {
	}

	@Override
	public boolean hasFacts() {
		return mNode.getHead().hasFacts();
	}

	@Override
	public void acceptVisitor(ReviewVisitor reviewVisitor) {
		mNode.getHead().acceptVisitor(reviewVisitor);
	}
}
