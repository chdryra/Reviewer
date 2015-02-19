/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 19 February, 2015
 */

package com.chdryra.android.reviewer;

import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 19/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewBuilderData implements ViewReviewAdapter {
    private final ReviewId          mId;
    private final ReviewBuilder     mBuilder;
    private       GvDataList        mData;
    private       GvDataList.GvType mDataType;

    public ReviewBuilderData(ReviewBuilder builder, GvDataList.GvType dataType) {
        mId = ReviewId.generateId();
        mBuilder = builder;
        mData = builder.getData(dataType);
        mDataType = mData.getGvType();
    }

    @Override
    public String getId() {
        return mId.toString();
    }

    @Override
    public String getSubject() {
        return mBuilder.getSubject();
    }

    @Override
    public float getRating() {
        return mBuilder.getRating();
    }

    @Override
    public float getAverageRating() {
        return mDataType == GvDataList.GvType.CHILDREN ? mBuilder.getAverageRating() : getRating();
    }

    @Override
    public GvDataList getGridData() {
        return mData;
    }

    @Override
    public Author getAuthor() {
        return mBuilder.getAuthor();
    }

    @Override
    public Date getPublishDate() {
        return mBuilder.getPublishDate();
    }

    @Override
    public GvImageList getImages() {
        return mDataType == GvDataList.GvType.IMAGES ? (GvImageList) mData : mBuilder.getImages();
    }

    public void setRating(float rating) {
        mBuilder.setRating(rating);
    }

    public void setSubject(String subject) {
        mBuilder.setSubject(subject);
    }

    public void setData(GvDataList data) {
        if (mDataType != data.getGvType()) {
            throw new RuntimeException("Wrong data type. Should be " + mDataType);
        }
        mData = data;
        mBuilder.setData(data);
    }

    public void setRatingIsAverage(boolean isAverage) {
        mBuilder.setRatingIsAverage(isAverage);
    }

    public boolean isRatingAverage() {
        return mBuilder.isRatingAverage();
    }
}
