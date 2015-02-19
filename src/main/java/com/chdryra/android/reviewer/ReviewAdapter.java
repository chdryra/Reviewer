/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 October, 2014
 */

package com.chdryra.android.reviewer;

import java.util.Date;
/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Translation and indirection layer between Model (review data) and View (android). The View
 * layer consist of the activities and fragments in android.
 * <p/>
 * <p>
 * Translates between model data types and view data types:
 * <ul>
 * <li>Model data types: {@link com.chdryra.android.reviewer.MdData} types</li>
 * <li>View data types: {@link GvDataList.GvType} types,
 * java types</li>
 * </ul>
 * </p>
 */
public class ReviewAdapter implements ViewReviewAdapter {
    private final Review            mReview;
    private final GvDataList.GvType mDataType;

    public ReviewAdapter(Review review, GvDataList.GvType dataType) {
        mReview = review;
        mDataType = dataType;
    }

    @Override
    public String getId() {
        return mReview.getId().toString();
    }

    @Override
    public String getSubject() {
        return mReview.getSubject().get();
    }

    @Override
    public float getRating() {
        return mReview.getRating().get();
    }

    @Override
    public float getAverageRating() {
        VisitorRatingAverageOfChildren visitor = new VisitorRatingAverageOfChildren();
        visitor.visit(mReview.getReviewNode());
        return visitor.getRating();
    }

    @Override
    public GvDataList getGridData() {
        if (mDataType == GvDataList.GvType.COMMENTS) {
            return MdGvConverter.convert(mReview.getComments());
        } else if (mDataType == GvDataList.GvType.IMAGES) {
            return getImages();
        } else if (mDataType == GvDataList.GvType.FACTS) {
            return MdGvConverter.convert(mReview.getFacts());
        } else if (mDataType == GvDataList.GvType.URLS) {
            return MdGvConverter.convert(mReview.getUrls());
        } else if (mDataType == GvDataList.GvType.LOCATIONS) {
            return MdGvConverter.convert(mReview.getLocations());
        } else if (mDataType == GvDataList.GvType.TAGS) {
            return getTags();
        } else if (mDataType == GvDataList.GvType.CHILDREN) {
            return getChildren();
        } else {
            return null;
        }
    }

    @Override
    public Author getAuthor() {
        return mReview.getAuthor();
    }

    @Override
    public Date getPublishDate() {
        return mReview.getPublishDate();
    }

    @Override
    public GvImageList getImages() {
        return MdGvConverter.convert(mReview.getImages());
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
        for (TagsManager.ReviewTag tag : TagsManager.getTags(mReview)) {
            gvTags.add(new GvTagList.GvTag(tag.get()));
        }

        return gvTags;
    }
}
