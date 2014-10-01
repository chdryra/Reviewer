/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

public class ReviewTreeEditable extends ReviewEditable {

    private ReviewNode mNode;

    public ReviewTreeEditable(String subject) {
        init(subject);
    }

    private void init(String subject) {
        mNode = FactoryReview.createReviewNode(FactoryReview.createReviewUserEditable(subject));
        mNode.setRatingIsAverageOfChildren(false);
    }

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
    public ReviewTagCollection getTags() {
        return ReviewTagsManager.getTags(this);
    }

    @Override
    public ReviewNode getReviewNode() {
        return mNode;
    }

    @Override
    public ReviewNode publish(ReviewTreePublisher publisher) {
        return publisher.publish(this);
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
    public boolean hasURLs() {
        return mNode.hasURLs();
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
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }

        ReviewTreeEditable objReview = (ReviewTreeEditable) obj;
        return mNode.equals(objReview.mNode);
    }

    @Override
    public int hashCode() {
        return mNode.hashCode();
    }

    @Override
    public boolean hasLocations() {
        return mNode.hasLocations();
    }

    private ReviewEditable getReviewEditable() {
        return (ReviewEditable) mNode.getReview();
    }
}
