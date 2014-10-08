/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

/**
 * Primary review object the user edits in the review creation process. An editable and expandable
 * review tree.
 *
 * <p>
 *     This essentially wraps a ReviewNodeExpandable where the root node is a ReviewUserEditable.
 *     The getters and setters forward requests and data to the ReviewUserEditable. The tree
 *     editing requests are forwarded to the internal node.
 * </p>
 *
 * <p>
 *     The <code>publish(.)</code> method returns a ReviewTree object that wraps a published
 *     version of the internal tree to stop further editing and expanding.
 * </p>
 *
 * @see com.chdryra.android.reviewer.ReviewUserEditable
 * @see com.chdryra.android.reviewer.ReviewTree
 */
class ReviewTreeEditable extends ReviewEditable implements ReviewNodeExpandable{
    private ReviewNodeExpandable mNode;

    ReviewTreeEditable(String subject) {
        ReviewEditable editableRoot = FactoryReview.createReviewEditable(subject);
        mNode = FactoryReview.createReviewNodeExpandable(editableRoot);
    }

    @Override
    public Review getReview() {
        return mNode.getReview();
    }

    @Override
    public ReviewNode getParent() {
        return mNode.getParent();
    }

    //ReviewNodeExpandable methods
    @Override
    public void setParent(ReviewNodeExpandable parentNode) {
        mNode.setParent(parentNode);
    }

    @Override
    public void addChild(Review child) {
        mNode.addChild(child);
    }

    @Override
    public void addChild(ReviewNodeExpandable childNode) {
        mNode.addChild(childNode);
    }

    @Override
    public void removeChild(ReviewNodeExpandable childNode) {
        mNode.removeChild(childNode);
    }

    @Override
    public void clearChildren() {
        mNode.clearChildren();
    }

    @Override
    public RCollectionReview<ReviewNode> getChildren() {
        return mNode.getChildren();
    }

    @Override
    public boolean isRatingIsAverageOfChildren() {
        return mNode.isRatingIsAverageOfChildren();
    }

    @Override
    public void setRatingIsAverageOfChildren(boolean ratingIsAverage) {
        mNode.setRatingIsAverageOfChildren(ratingIsAverage);
    }

    @Override
    public RCollectionReview<ReviewNode> flattenTree() {
        return mNode.flattenTree();
    }

    @Override
    public void acceptVisitor(VisitorReviewNode visitorReviewNode) {
        mNode.acceptVisitor(visitorReviewNode);
    }

    //ReviewEditable methods
    @Override
    public RDId getId() {
        return mNode.getId();
    }

    @Override
    public RDSubject getSubject() {
        return mNode.getSubject();
    }

    @Override
    public void setSubject(String subject) {
        getReviewEditable().setSubject(subject);
    }

    @Override
    public RDRating getRating() {
        return mNode.getRating();
    }

    @Override
    public void setRating(float rating) {
        getReviewEditable().setRating(rating);
    }

    @Override
    public void deleteComments() {
        getReviewEditable().deleteComments();
    }

    @Override
    public ReviewNode getReviewNode() {
        return mNode;
    }

    @Override
    public Review publish(ReviewTreePublisher publisher) {
        return FactoryReview.createReview(publisher.publish(mNode));
    }

    @Override
    public RDList<RDComment> getComments() {
        return mNode.getComments();
    }

    @Override
    public void setComments(RDList<RDComment> comments) {
        getReviewEditable().setComments(comments);
    }

    @Override
    public void deleteFacts() {
        getReviewEditable().deleteFacts();
    }

    @Override
    public boolean hasComments() {
        return mNode.hasComments();
    }

    @Override
    public RDList<RDFact> getFacts() {
        return mNode.getFacts();
    }

    @Override
    public void setFacts(RDList<RDFact> facts) {
        getReviewEditable().setFacts(facts);
    }

    @Override
    public void deleteImages() {
        getReviewEditable().deleteImages();
    }

    @Override
    public boolean hasFacts() {
        return mNode.hasFacts();
    }

    @Override
    public RDList<RDImage> getImages() {
        return mNode.getImages();
    }

    @Override
    public void setImages(RDList<RDImage> images) {
        getReviewEditable().setImages(images);
    }

    @Override
    public void deleteUrls() {
        getReviewEditable().deleteUrls();
    }

    @Override
    public boolean hasImages() {
        return mNode.hasImages();
    }

    @Override
    public RDList<RDUrl> getURLs() {
        return mNode.getURLs();
    }

    @Override
    public void setURLs(RDList<RDUrl> urls) {
        getReviewEditable().setURLs(urls);
    }

    @Override
    public void deleteLocations() {
        getReviewEditable().deleteLocations();
    }

    @Override
    public boolean hasUrls() {
        return mNode.hasUrls();
    }

    @Override
    public RDList<RDLocation> getLocations() {
        return mNode.getLocations();
    }

    @Override
    public void setLocations(RDList<RDLocation> locations) {
        getReviewEditable().setLocations(locations);
    }

    @Override
    public boolean hasLocations() {
        return mNode.hasLocations();
    }

    private ReviewEditable getReviewEditable() {
        return (ReviewEditable) mNode.getReview();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewTreeEditable)) return false;

        ReviewTreeEditable that = (ReviewTreeEditable) o;

        if (mNode != null ? !mNode.equals(that.mNode) : that.mNode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return mNode != null ? mNode.hashCode() : 0;
    }
}
