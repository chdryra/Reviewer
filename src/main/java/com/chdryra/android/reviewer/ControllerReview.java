/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 October, 2014
 */

package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.VHDString;

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
 * <li>Model data types: {@link MdData} types</li>
 * <li>View data types: {@link VgDataList.GvType} types,
 * java types</li>
 * </ul>
 * </p>
 *
 * @param <T>: the {@link Review} type being accessed
 */
public class ControllerReview<T extends Review> {
    private final ArrayList<String> mTagsList = new ArrayList<String>();
    private final T                    mReview;
    private       ControllerReviewNode mReviewNode;

    public ControllerReview(T review) {
        mReview = review;
        for (TagsManager.ReviewTag tag : TagsManager.getTags(review)) {
            mTagsList.add(tag.get());
        }
    }

    public ControllerReviewNode getReviewNode() {
        if (mReviewNode == null) {
            mReviewNode = new ControllerReviewNode(mReview.getReviewNode());
        }

        return mReviewNode;
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

    public boolean isPublished() {
        return mReview.isPublished();
    }

    public void addTags(VgTagList tags) {
        for (VHDString tag : tags) {
            mTagsList.add(tag.get());
        }
    }

    public void removeTags() {
        mTagsList.clear();
    }

    public boolean hasData(VgDataList.GvType dataType) {
        if (dataType == VgDataList.GvType.COMMENTS) {
            return mReview.hasComments();
        } else if (dataType == VgDataList.GvType.IMAGES) {
            return mReview.hasImages();
        } else if (dataType == VgDataList.GvType.FACTS) {
            return mReview.hasFacts();
        } else if (dataType == VgDataList.GvType.URLS) {
            return mReview.hasUrls();
        } else if (dataType == VgDataList.GvType.LOCATIONS) {
            return mReview.hasLocations();
        } else {
            return dataType == VgDataList.GvType.TAGS && mTagsList.size() > 0;
        }
    }

    public VgDataList getData(VgDataList.GvType dataType) {
        if (dataType == VgDataList.GvType.COMMENTS) {
            return MdToGvConverter.convert(mReview.getComments());
        } else if (dataType == VgDataList.GvType.IMAGES) {
            return MdToGvConverter.convert(mReview.getImages());
        } else if (dataType == VgDataList.GvType.FACTS) {
            return MdToGvConverter.convert(mReview.getFacts());
        } else if (dataType == VgDataList.GvType.URLS) {
            return MdToGvConverter.convert(mReview.getUrls());
        } else if (dataType == VgDataList.GvType.LOCATIONS) {
            return MdToGvConverter.convert(mReview.getLocations());
        } else if (dataType == VgDataList.GvType.TAGS) {
            return getTags();
        } else {
            return null;
        }
    }

    T getControlledReview() {
        return mReview;
    }

    private VgTagList getTags() {
        VgTagList gvTags = new VgTagList();
        for (String tag : mTagsList) {
            gvTags.add(tag);
        }

        return gvTags;
    }

    void setTags(VgTagList tags) {
        removeTags();
        addTags(tags);
    }
}
