/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 October, 2014
 */

package com.chdryra.android.reviewer;

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
 * <li>View data types: {@link GvDataList.GvType} types,
 * java types</li>
 * </ul>
 * </p>
 *
 * @param <T>: the {@link Review} type being accessed
 */
public class ControllerReview<T extends Review> implements GvAdapterReview {
    private final ArrayList<String> mTagsList = new ArrayList<>();
    private final T mReview;

    public ControllerReview(T review) {
        mReview = review;
        for (TagsManager.ReviewTag tag : TagsManager.getTags(review)) {
            mTagsList.add(tag.get());
        }
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

    public Author getAuthor() {
        return mReview.getAuthor();
    }

    public Date getPublishDate() {
        return mReview.getPublishDate();
    }

    public boolean hasData(GvDataList.GvType dataType) {
        if (dataType == GvDataList.GvType.COMMENTS) {
            return mReview.hasComments();
        } else if (dataType == GvDataList.GvType.IMAGES) {
            return mReview.hasImages();
        } else if (dataType == GvDataList.GvType.FACTS) {
            return mReview.hasFacts();
        } else if (dataType == GvDataList.GvType.URLS) {
            return mReview.hasUrls();
        } else if (dataType == GvDataList.GvType.LOCATIONS) {
            return mReview.hasLocations();
        } else if (dataType == GvDataList.GvType.CHILDREN) {
            return mReview.getReviewNode().getChildren().size() > 0;
        } else {
            return dataType == GvDataList.GvType.TAGS && mTagsList.size() > 0;
        }
    }

    public GvDataList getData(GvDataList.GvType dataType) {
        if (dataType == GvDataList.GvType.COMMENTS) {
            return MdGvConverter.convert(mReview.getComments());
        } else if (dataType == GvDataList.GvType.IMAGES) {
            return MdGvConverter.convert(mReview.getImages());
        } else if (dataType == GvDataList.GvType.FACTS) {
            return MdGvConverter.convert(mReview.getFacts());
        } else if (dataType == GvDataList.GvType.URLS) {
            return MdGvConverter.convert(mReview.getUrls());
        } else if (dataType == GvDataList.GvType.LOCATIONS) {
            return MdGvConverter.convert(mReview.getLocations());
        } else if (dataType == GvDataList.GvType.TAGS) {
            return getTags();
        } else if (dataType == GvDataList.GvType.TAGS) {
            return getChildren();
        } else {
            return null;
        }
    }

    T getControlledReview() {
        return mReview;
    }

    private GvChildrenList getChildren() {
        RCollectionReview<ReviewNode> children = mReview.getReviewNode().getChildren();
        GvChildrenList list = new GvChildrenList();
        for (ReviewNode child : children) {
            GvChildrenList.GvChildReview c = new GvChildrenList.GvChildReview(child
                    .getSubject().get(), child.getRating().get());
            list.add(c);
        }

        return list;
    }

    private GvTagList getTags() {
        GvTagList gvTags = new GvTagList();
        for (String tag : mTagsList) {
            gvTags.add(new GvTagList.GvTag(tag));
        }

        return gvTags;
    }
}
