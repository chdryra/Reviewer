/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 October, 2014
 */

package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.VHDString;
import com.chdryra.android.reviewer.GVReviewDataList.GVReviewData;

import java.util.ArrayList;
import java.util.Date;
/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Translation and indirection layer between Model (review data) and View (android). The "C" in the
 * MVC pattern. The View layer consist of the activities and fragments in android.
 * <p/>
 * <p>
 * Not a "Controller" as such - more of an adapter - but didn't want to confuse with
 * Android's use of the word "Adapter". Translates between model data types and view data types:
 * <ul>
 * <li>Model data types: {@link com.chdryra.android.reviewer.RData} types</li>
 * <li>View data types: {@link com.chdryra.android.reviewer.GVReviewDataList.GVType} types,
 * java types</li>
 * </ul>
 * </p>
 *
 * @param <T>: the {@link Review} type being accessed
 */
class ControllerReview<T extends Review> {
    private final ArrayList<String> mTagsList = new ArrayList<String>();
    private final T                    mReview;
    private       ControllerReviewNode mReviewNode;

    public ControllerReview(T review) {
        mReview = review;
        for (TagsManager.ReviewTag tag : TagsManager.getTags(review)) {
            mTagsList.add(tag.get());
        }
    }

    T getControlledReview() {
        return mReview;
    }

    ControllerReviewNode getReviewNode() {
        if (mReviewNode == null) {
            mReviewNode = new ControllerReviewNode(mReview.getReviewNode());
        }

        return mReviewNode;
    }

    String getId() {
        return mReview.getId().toString();
    }

    String getSubject() {
        return mReview.getSubject().get();
    }

    float getRating() {
        return mReview.getRating().get();
    }

    String getAuthor() {
        return mReview.getAuthor().getName();
    }

    Date getPublishDate() {
        return mReview.getPublishDate();
    }

    void removeTags() {
        mTagsList.clear();
    }

    void addTags(GVTagList tags) {
        for (VHDString tag : tags) {
            mTagsList.add(tag.get());
        }
    }

    boolean hasData(GVReviewDataList.GVType dataType) {
        if (dataType == GVReviewDataList.GVType.COMMENTS) {
            return mReview.hasComments();
        } else if (dataType == GVReviewDataList.GVType.IMAGES) {
            return mReview.hasImages();
        } else if (dataType == GVReviewDataList.GVType.FACTS) {
            return mReview.hasFacts();
        } else if (dataType == GVReviewDataList.GVType.URLS) {
            return mReview.hasUrls();
        } else if (dataType == GVReviewDataList.GVType.LOCATIONS) {
            return mReview.hasLocations();
        } else {
            return dataType == GVReviewDataList.GVType.TAGS && mTagsList.size() > 0;
        }
    }

    GVReviewDataList<? extends GVReviewData> getData(GVReviewDataList.GVType dataType) {
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

    void setTags(GVTagList tags) {
        removeTags();
        addTags(tags);
    }
}
