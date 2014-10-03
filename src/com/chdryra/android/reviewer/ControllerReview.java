/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 October, 2014
 */

package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.mygenerallibrary.GVString;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ControllerReview<T extends Review> {
    protected final ArrayList<String> mTagsList = new ArrayList<String>();
    private final T mReview;

    public ControllerReview(T review) {
        mReview = review;
        for (ReviewTagsManager.ReviewTag tag : ReviewTagsManager.getTags(review)) {
            mTagsList.add(tag.toString());
        }
    }

    //***Accessesors***
    protected T getReview() {
        return mReview;
    }

    public String getId() {
        return mReview.getId().toString();
    }

    public String getSubject() {
        return mReview.getSubject().get();
    }

    public float getRating() {
        return mReview.getRating().get();
    }

    public String getAuthor() {
        return mReview.getAuthor().getName();
    }

    public Date getPublishDate() {
        return mReview.getPublishDate();
    }

    public boolean hasData(GVReviewDataList.GVType dataType) {
        if (dataType == GVReviewDataList.GVType.COMMENTS) {
            return hasComments();
        } else if (dataType == GVReviewDataList.GVType.IMAGES) {
            return hasImages();
        } else if (dataType == GVReviewDataList.GVType.FACTS) {
            return hasFacts();
        } else if (dataType == GVReviewDataList.GVType.URLS) {
            return hasUrls();
        } else if (dataType == GVReviewDataList.GVType.LOCATIONS) {
            return hasLocations();
        } else if (dataType == GVReviewDataList.GVType.TAGS) {
            return hasTags();
        } else {
            return false;
        }
    }

    //Comment
    private boolean hasComments() {
        return mReview.hasComments();
    }

    //Image
    private boolean hasImages() {
        return mReview.hasImages();
    }

    //Facts
    private boolean hasFacts() {
        return mReview.hasFacts();
    }

    //URL
    private boolean hasUrls() {
        return mReview.hasURLs();
    }

    //Location
    private boolean hasLocations() {
        return mReview.hasLocations();
    }

    private boolean hasTags() {
        return mTagsList.size() > 0;
    }

    public GVReviewDataList<? extends GVData> getData(GVReviewDataList.GVType dataType) {
        if (dataType == GVReviewDataList.GVType.COMMENTS) {
            return getComments();
        } else if (dataType == GVReviewDataList.GVType.IMAGES) {
            return getImages();
        } else if (dataType == GVReviewDataList.GVType.FACTS) {
            return getFacts();
        } else if (dataType == GVReviewDataList.GVType.URLS) {
            return getUrls();
        } else if (dataType == GVReviewDataList.GVType.LOCATIONS) {
            return getLocations();
        } else if (dataType == GVReviewDataList.GVType.TAGS) {
            return getTags();
        } else {
            return null;
        }
    }

    private GVCommentList getComments() {
        GVCommentList comments = new GVCommentList();
        for (RDComment comment : mReview.getComments()) {
            comments.add(comment.get());
        }
        return comments;
    }

    private GVImageList getImages() {
        GVImageList images = new GVImageList();
        for (RDImage image : mReview.getImages()) {
            images.add(image.getBitmap(), image.getLatLng(), image.getCaption(), image.isCover());
        }

        return images;
    }

    private GVFactList getFacts() {
        GVFactList gvFacts = new GVFactList();
        RDList<RDFact> facts = mReview.getFacts();
        for (RDFact fact : facts) {
            gvFacts.add(fact.getLabel(), fact.getValue());
        }

        return gvFacts;
    }

    private GVUrlList getUrls() {
        GVUrlList urlList = new GVUrlList();
        for (RDUrl url : mReview.getURLs()) {
            urlList.add(url.get());
        }

        return urlList;
    }

    private GVLocationList getLocations() {
        GVLocationList locations = new GVLocationList();
        for (RDLocation location : mReview.getLocations()) {
            locations.add(location.getLatLng(), location.getName());
        }

        return locations;
    }

    private GVTagList getTags() {
        GVTagList gvTags = new GVTagList();
        for (String tag : mTagsList) {
            gvTags.add(tag);
        }

        return gvTags;
    }

    void removeTags() {
        mTagsList.clear();
    }

    void addTags(GVTagList tags) {
        for (GVString tag : tags) {
            mTagsList.add(tag.toString());
        }
    }

    void setTags(GVTagList tags) {
        removeTags();
        addTags(tags);
    }
}
