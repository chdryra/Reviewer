/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.mygenerallibrary.GVString;
import com.chdryra.android.reviewer.GVCommentList.GVComment;
import com.chdryra.android.reviewer.GVFactList.GVFact;
import com.chdryra.android.reviewer.GVImageList.GVImage;
import com.chdryra.android.reviewer.GVLocationList.GVLocation;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;
import com.chdryra.android.reviewer.GVUrlList.GVUrl;
import com.chdryra.android.reviewer.ReviewTagsManager.ReviewTag;

import java.util.ArrayList;
import java.util.Date;

public class ControllerReviewNode {
    private final ReviewNode     mNode;
    private final ReviewEditable mEditableReview;
    private final ArrayList<String> mTagsList = new ArrayList<String>();
    private ControllerReviewNodeChildren mChildrenController;

    public ControllerReviewNode(ReviewNode node) {
        mNode = node;
        Review review = mNode.getReview();
        mEditableReview = review instanceof ReviewEditable ? (ReviewEditable) review :
                FactoryReview.createNullReview();
        for (ReviewTag tag : ReviewTagsManager.getTags(review)) {
            mTagsList.add(tag.toString());
        }
    }

    public ControllerReviewNode(ReviewEditable editableReview) {
        mEditableReview = editableReview;
        mNode = editableReview.getReviewNode();
        for (ReviewTag tag : ReviewTagsManager.getTags(editableReview)) {
            mTagsList.add(tag.toString());
        }
    }

    public ReviewNode publishAndTag(ReviewTreePublisher publisher) {
        ReviewNode finalReview = publisher.publish(mNode.getReview());
        for (ReviewNode node : finalReview.getDescendants()) {
            ReviewTagsManager.tag(node.getReview(), mTagsList);
        }

        return finalReview;
    }

    //***Accessesors***
    public String getId() {
        return mNode.getId().toString();
    }

    public String getSubject() {
        return mNode.getSubject().get();
    }

    //Title
    public void setSubject(String subject) {
        getEditableReview().setSubject(subject);
    }

    private ReviewEditable getEditableReview() {
        return mEditableReview;
    }

    public float getRating() {
        return mNode.getRating().get();
    }

    //Rating
    public void setRating(float rating) {
        getEditableReview().setRating(rating);
    }

    public String getAuthor() {
        return mNode.getAuthor().getName();
    }

    public Date getPublishDate() {
        return mNode.getPublishDate();
    }

    public boolean isReviewRatingAverage() {
        return mNode.isRatingIsAverageOfChildren();
    }

    //RatingISAverage
    public void setReviewRatingAverage(boolean isAverage) {
        mNode.setRatingIsAverageOfChildren(isAverage);
    }

    public boolean hasData(GVType dataType) {
        if (dataType == GVType.COMMENTS) {
            return hasComments();
        } else if (dataType == GVType.IMAGES) {
            return hasImages();
        } else if (dataType == GVType.FACTS) {
            return hasFacts();
        } else if (dataType == GVType.URLS) {
            return hasUrls();
        } else if (dataType == GVType.LOCATIONS) {
            return hasLocations();
        } else if (dataType == GVType.TAGS) {
            return hasTags();
        } else {
            return dataType == GVType.CRITERIA && getChildrenController().size() > 0;
        }
    }

    //Comment
    private boolean hasComments() {
        return mNode.hasComments();
    }

    //Image
    private boolean hasImages() {
        return mNode.hasImages();
    }

    //Facts
    private boolean hasFacts() {
        return mNode.hasFacts();
    }

    //URL
    private boolean hasUrls() {
        return mNode.hasURLs();
    }

    //Location
    private boolean hasLocations() {
        return mNode.hasLocations();
    }

    private boolean hasTags() {
        return mTagsList.size() > 0;
    }

    ControllerReviewNodeChildren getChildrenController() {
        if (mChildrenController == null) {
            mChildrenController = new ControllerReviewNodeChildren(mNode);
        }

        return mChildrenController;
    }

    public GVReviewDataList<? extends GVData> getData(GVType dataType) {
        if (dataType == GVType.COMMENTS) {
            return getComments();
        } else if (dataType == GVType.IMAGES) {
            return getImages();
        } else if (dataType == GVType.FACTS) {
            return getFacts();
        } else if (dataType == GVType.URLS) {
            return getUrls();
        } else if (dataType == GVType.LOCATIONS) {
            return getLocations();
        } else if (dataType == GVType.TAGS) {
            return getTags();
        } else if (dataType == GVType.CRITERIA) {
            return getChildrenController().getGridViewableData();
        } else {
            return null;
        }
    }

    private GVCommentList getComments() {
        GVCommentList comments = new GVCommentList();
        for (RDComment comment : mNode.getComments()) {
            comments.add(comment.get());
        }
        return comments;
    }

    private void setComments(GVCommentList comments) {
        ReviewEditable r = getEditableReview();
        RDList<RDComment> rdComments = new RDList<RDComment>();
        for (GVComment comment : comments) {
            rdComments.add(new RDComment(comment.getComment(), r));
        }

        r.setComments(rdComments);
    }

    private GVImageList getImages() {
        GVImageList images = new GVImageList();
        for (RDImage image : mNode.getImages()) {
            images.add(image.getBitmap(), image.getLatLng(), image.getCaption(), image.isCover());
        }

        return images;
    }

    private void setImages(GVImageList images) {
        ReviewEditable r = getEditableReview();
        RDList<RDImage> rdImages = new RDList<RDImage>();
        for (GVImage image : images) {
            rdImages.add(new RDImage(image.getBitmap(), image.getLatLng(), image.getCaption(),
                    image.isCover(), r));
        }

        r.setImages(rdImages);
    }

    private GVFactList getFacts() {
        GVFactList gvFacts = new GVFactList();
        RDList<RDFact> facts = mNode.getFacts();
        for (RDFact fact : facts) {
            gvFacts.add(fact.getLabel(), fact.getValue());
        }

        return gvFacts;
    }

    private void setFacts(GVFactList gvFacts) {
        ReviewEditable r = getEditableReview();
        RDList<RDFact> facts = new RDList<RDFact>(r);
        for (GVFact fact : gvFacts) {
            facts.add(new RDFact(fact.getLabel(), fact.getValue(), r));
        }

        r.setFacts(facts);
    }

    private GVUrlList getUrls() {
        GVUrlList urlList = new GVUrlList();
        for (RDUrl url : mNode.getURLs()) {
            urlList.add(url.get());
        }

        return urlList;
    }

    private void setUrls(GVUrlList urlList) {
        if (urlList.size() == 0) {
            return;
        }

        ReviewEditable r = getEditableReview();
        RDList<RDUrl> rdUrls = new RDList<RDUrl>();
        for (GVUrl url : urlList) {
            rdUrls.add(new RDUrl(url.getUrl(), r));
        }

        r.setURLs(rdUrls);
    }

    private GVLocationList getLocations() {
        GVLocationList locations = new GVLocationList();
        for (RDLocation location : mNode.getLocations()) {
            locations.add(location.getLatLng(), location.getName());
        }

        return locations;
    }

    private void setLocations(GVLocationList locations) {
        ReviewEditable r = getEditableReview();
        RDList<RDLocation> rdLocations = new RDList<RDLocation>();
        for (GVLocation location : locations) {
            rdLocations.add(new RDLocation(location.getLatLng(), location.getName(), r));
        }

        r.setLocations(rdLocations);
    }

    private GVTagList getTags() {
        GVTagList gvTags = new GVTagList();
        for (String tag : mTagsList) {
            gvTags.add(tag);
        }

        return gvTags;
    }

    private void setTags(GVTagList tags) {
        removeTags();
        addTags(tags);
    }

    public <T extends GVReviewDataList<? extends GVData>> void setData(T data) {
        GVType dataType = data.getDataType();
        if (dataType == GVType.COMMENTS) {
            setComments((GVCommentList) data);
        } else if (dataType == GVType.IMAGES) {
            setImages((GVImageList) data);
        } else if (dataType == GVType.FACTS) {
            setFacts((GVFactList) data);
        } else if (dataType == GVType.URLS) {
            setUrls((GVUrlList) data);
        } else if (dataType == GVType.LOCATIONS) {
            setLocations((GVLocationList) data);
        } else if (dataType == GVType.TAGS) {
            setTags((GVTagList) data);
        } else if (dataType == GVType.CRITERIA) {
            setChildren((GVReviewSubjectRatingList) data);
        }
    }

    void setChildren(GVReviewSubjectRatingList children) {
        getChildrenController().setChildren(children);
    }

    private void removeTags() {
        mTagsList.clear();
    }

    void addTags(GVTagList tags) {
        for (GVString tag : tags) {
            mTagsList.add(tag.toString());
        }
    }

}
