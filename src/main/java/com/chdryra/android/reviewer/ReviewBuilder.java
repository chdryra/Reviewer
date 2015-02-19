/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 February, 2015
 */

package com.chdryra.android.reviewer;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;

import com.chdryra.android.mygenerallibrary.FileIncrementor;
import com.chdryra.android.mygenerallibrary.FileIncrementorFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 13/02/2015
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * For building reviews. Collects appropriate data and builds a {@link Review} object when
 * user is ready using the {@link #publish(java.util.Date)} method.
 */
public class ReviewBuilder implements ViewReviewAdapter {
    private static final GvDataList.GvType[] TYPES        = {GvDataList.GvType.COMMENTS, GvDataList
            .GvType.FACTS, GvDataList.GvType.LOCATIONS, GvDataList.GvType.IMAGES, GvDataList
            .GvType.URLS, GvDataList.GvType.TAGS};
    private static final File                FILE_DIR_EXT = Environment
            .getExternalStoragePublicDirectory
                    (Environment.DIRECTORY_DCIM);

    private FileIncrementor mIncrementor;
    private Activity        mActivity;

    private ReviewId                           mId;
    private String                             mSubject;
    private float                              mRating;
    private Map<GvDataList.GvType, GvDataList> mData;
    private ArrayList<ReviewBuilder>           mChildren;
    private GvBuildReviewList                  mGridData;

    private boolean mIsAverage = false;


    public ReviewBuilder(Activity activity) {
        mId = ReviewId.generateId();
        mChildren = new ArrayList<>();
        mActivity = activity;
        mGridData = GvBuildReviewList.newInstance(activity, this);

        mData = new HashMap<>();
        mData.put(GvDataList.GvType.COMMENTS, new GvCommentList());
        mData.put(GvDataList.GvType.IMAGES, new GvImageList());
        mData.put(GvDataList.GvType.FACTS, new GvFactList());
        mData.put(GvDataList.GvType.LOCATIONS, new GvLocationList());
        mData.put(GvDataList.GvType.URLS, new GvUrlList());
        mData.put(GvDataList.GvType.TAGS, new GvTagList());

        mSubject = "";
        mRating = 0f;

        newIncrementor();
    }

    @Override
    public String getId() {
        return mId.toString();
    }

    @Override
    public String getSubject() {
        return mSubject;
    }

    public void setSubject(String subject) {
        mSubject = subject;
        newIncrementor();
    }

    @Override
    public float getRating() {
        return isRatingAverage() ? getAverageRating() : mRating;
    }

    @Override
    public float getAverageRating() {
        return getChildren().getAveragerating();
    }

    @Override
    public GvDataList getGridData() {
        return mGridData;
    }

    @Override
    public Author getAuthor() {
        return Administrator.get(mActivity).getAuthor();
    }

    @Override
    public Date getPublishDate() {
        return null;
    }

    public GvImageList getImages() {
        return (GvImageList) getData(GvDataList.GvType.IMAGES);
    }

    public void setRating(float rating) {
        if (!isRatingAverage()) mRating = rating;
    }

    public GvDataList getData(GvDataList.GvType dataType) {
        if (dataType == GvDataList.GvType.CHILDREN) {
            return getChildren();
        } else {
            return MdGvConverter.copy(mData.get(dataType));
        }
    }

    public Review publish(Date publishDate) {
        Review root = FactoryReview.createReviewUser(getAuthor(),
                publishDate, getSubject(), getRating(),
                (GvCommentList) getData(GvDataList.GvType.COMMENTS),
                (GvImageList) getData(GvDataList.GvType.IMAGES),
                (GvFactList) getData(GvDataList.GvType.FACTS),
                (GvLocationList) getData(GvDataList.GvType.LOCATIONS),
                (GvUrlList) getData(GvDataList.GvType.URLS));

        GvTagList tags = (GvTagList) getData(GvDataList.GvType.TAGS);
        TagsManager.tag(root, tags);

        RCollectionReview<Review> children = new RCollectionReview<>();
        for (ReviewBuilder child : mChildren) {
            Review childReview = child.publish(publishDate);
            TagsManager.tag(childReview, tags);
            children.add(childReview);
        }

        return FactoryReview.createReviewTree(root, children, mIsAverage);
    }

    public boolean isRatingAverage() {
        return mIsAverage;
    }

    public void setRatingIsAverage(boolean ratingIsAverage) {
        mIsAverage = ratingIsAverage;
        if (ratingIsAverage) mRating = getChildren().getAveragerating();
    }

    public ImageChooser getImageChooser(Activity activity) {
        Context c = activity.getApplicationContext();
        if (c.equals(mActivity.getApplicationContext())) {
            return new ImageChooser(activity, (FileIncrementorFactory.ImageFileIncrementor)
                    mIncrementor);
        } else {
            throw new RuntimeException("Activity should belong to correct application context");
        }
    }

    public void setData(GvDataList data) {
        GvDataList.GvType dataType = data.getGvType();
        if (dataType == GvDataList.GvType.CHILDREN) {
            setChildren((GvChildrenList) data);
        } else if (Arrays.asList(TYPES).contains(dataType)) {
            mData.put(dataType, MdGvConverter.copy(data));
        }
    }

    public ViewReviewAdapter getDataAdapter(GvDataList.GvType dataType) {
        return new ReviewBuilderData(this, dataType);
    }

    private void newIncrementor() {
        String dir = mActivity.getString(mActivity.getApplicationInfo().labelRes);
        String filename = mSubject.length() > 0 ? mSubject : getAuthor().getName();
        mIncrementor = FileIncrementorFactory.newImageFileIncrementor(FILE_DIR_EXT, dir,
                filename);
    }

    private GvChildrenList getChildren() {
        GvChildrenList children = new GvChildrenList();
        for (ReviewBuilder childBuilder : mChildren) {
            GvChildrenList.GvChildReview review = new GvChildrenList.GvChildReview(childBuilder
                    .getSubject(), childBuilder.getRating());
            children.add(review);
        }

        return children;
    }

    private void setChildren(GvChildrenList children) {
        mChildren = new ArrayList<>();
        for (GvChildrenList.GvChildReview child : children) {
            ReviewBuilder childBuilder = new ReviewBuilder(mActivity);
            childBuilder.setSubject(child.getSubject());
            childBuilder.setRating(child.getRating());
            mChildren.add(childBuilder);
        }
    }
}
