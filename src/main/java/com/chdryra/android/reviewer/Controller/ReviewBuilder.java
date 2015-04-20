/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 February, 2015
 */

package com.chdryra.android.reviewer.Controller;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;

import com.chdryra.android.mygenerallibrary.FileIncrementor;
import com.chdryra.android.mygenerallibrary.FileIncrementorFactory;
import com.chdryra.android.reviewer.Model.Author;
import com.chdryra.android.reviewer.Model.FactoryReview;
import com.chdryra.android.reviewer.Model.Review;
import com.chdryra.android.reviewer.Model.ReviewIdableList;
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.Model.TagsManager;
import com.chdryra.android.reviewer.View.FactoryGvData;
import com.chdryra.android.reviewer.View.FactoryGvDataHandler;
import com.chdryra.android.reviewer.View.GvBuildReviewList;
import com.chdryra.android.reviewer.View.GvChildList;
import com.chdryra.android.reviewer.View.GvCommentList;
import com.chdryra.android.reviewer.View.GvData;
import com.chdryra.android.reviewer.View.GvDataHandler;
import com.chdryra.android.reviewer.View.GvDataList;
import com.chdryra.android.reviewer.View.GvDataType;
import com.chdryra.android.reviewer.View.GvFactList;
import com.chdryra.android.reviewer.View.GvImageList;
import com.chdryra.android.reviewer.View.GvLocationList;
import com.chdryra.android.reviewer.View.GvTagList;
import com.chdryra.android.reviewer.View.GvUrlList;
import com.chdryra.android.reviewer.View.ImageChooser;

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
 * For building reviews. Collects appropriate data and builds a {@link com.chdryra.android
 * .reviewer.Model.Review} object when
 * user is ready using the {@link #publish(java.util.Date)} method.
 */
public class ReviewBuilder extends ReviewViewAdapterBasic {
    private static final GvDataType[] TYPES        = {GvCommentList.TYPE,
            GvFactList.TYPE, GvLocationList.TYPE, GvImageList.TYPE, GvUrlList.TYPE, GvTagList.TYPE,
            GvChildList.TYPE};
    private static final File         FILE_DIR_EXT = Environment
            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
    private final Context                     mContext;
    private final Map<GvDataType, GvDataList> mData;
    private final Map<GvDataType, DataBuilder<? extends GvData>>
                                              mDataBuilders;
    private final GvBuildReviewList           mBuildUi;
    private       FileIncrementor             mIncrementor;
    private       String                      mSubject;
    private       float                       mRating;
    private       ArrayList<ReviewBuilder>    mChildren;
    private boolean mIsAverage = false;

    public ReviewBuilder(Context context) {
        mContext = context;
        mChildren = new ArrayList<>();

        mData = new HashMap<>();
        mDataBuilders = new HashMap<>();
        for (GvDataType dataType : TYPES) {
            mData.put(dataType, FactoryGvData.newList(dataType));
            mDataBuilders.put(dataType, newDataBuilder(dataType));
        }

        mSubject = "";
        mRating = 0f;

        mBuildUi = GvBuildReviewList.newInstance(this);

        newIncrementor();
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public String getSubject() {
        return mSubject;
    }

    public void setSubject(String subject) {
        mSubject = subject;
        newIncrementor();
    }

    public boolean isRatingAverage() {
        return mIsAverage;
    }

    public void setRatingIsAverage(boolean ratingIsAverage) {
        mIsAverage = ratingIsAverage;
        if (ratingIsAverage) mRating = getChildren().getAverageRating();
    }

    public ImageChooser getImageChooser(Activity activity) {
        Context c = activity.getApplicationContext();
        if (c.equals(mContext.getApplicationContext())) {
            return new ImageChooser(activity, (FileIncrementorFactory.ImageFileIncrementor)
                    mIncrementor);
        } else {
            throw new RuntimeException("Activity should belong to correct application context");
        }
    }

    public DataBuilder<? extends GvData> getDataBuilder(GvDataType dataType) {
        return mDataBuilders.get(dataType);
    }

    public void resetDataBuilder(GvDataType dataType) {
        mDataBuilders.get(dataType).resetData();
    }

    public int getDataSize(GvDataType dataType) {
        return getData(dataType).size();
    }

    public ReviewNode publish(Date publishDate) {
        Review root = FactoryReview.createReviewUser(getAuthor(),
                publishDate, getSubject(), getRating(),
                (GvCommentList) getData(GvCommentList.TYPE),
                (GvImageList) getData(GvImageList.TYPE),
                (GvFactList) getData(GvFactList.TYPE),
                (GvLocationList) getData(GvLocationList.TYPE));

        GvTagList tags = (GvTagList) getData(GvTagList.TYPE);
        for (GvTagList.GvTag tag : tags) {
            TagsManager.tag(root.getId(), tag.get());
        }

        ReviewIdableList<Review> children = new ReviewIdableList<>();
        for (ReviewBuilder child : mChildren) {
            Review childReview = child.publish(publishDate);
            for (GvTagList.GvTag tag : tags) {
                TagsManager.tag(childReview.getId(), tag.get());
            }
            children.add(childReview);
        }

        return FactoryReview.createReviewTree(root, children, mIsAverage);
    }

    public GvDataList getData(GvDataType dataType) {
        GvDataList data = mData.get(dataType);
        return data != null ? MdGvConverter.copy(data) : null;
    }

    private DataBuilder<? extends GvData> newDataBuilder(GvDataType
            dataType) {
        GvDataList data = getData(dataType);
        DataBuilder<?> builder;
        if (dataType == GvChildList.TYPE) {
            builder = new DataBuilder<>((GvChildList) data);
        } else if (dataType == GvCommentList.TYPE) {
            builder = new DataBuilder<>((GvCommentList) data);
        } else if (dataType == GvImageList.TYPE) {
            builder = new DataBuilder<>((GvImageList) data);
        } else if (dataType == GvFactList.TYPE) {
            builder = new DataBuilder<>((GvFactList) data);
        } else if (dataType == GvLocationList.TYPE) {
            builder = new DataBuilder<>((GvLocationList) data);
        } else if (dataType == GvUrlList.TYPE) {
            builder = new DataBuilder<>((GvUrlList) data);
        } else if (dataType == GvTagList.TYPE) {
            builder = new DataBuilder<>((GvTagList) data);
        } else {
            return null;
        }

        return builder;
    }

    private void setData(GvDataList data) {
        GvDataType dataType = data.getGvDataType();
        if (dataType == GvChildList.TYPE) {
            setChildren((GvChildList) data);
        } else if (Arrays.asList(TYPES).contains(dataType)) {
            mData.put(dataType, MdGvConverter.copy(data));
        }

        notifyGridDataObservers();
    }

    private void newIncrementor() {
        String dir = mContext.getString(mContext.getApplicationInfo().labelRes);
        String filename = mSubject.length() > 0 ? mSubject : getAuthor().getName();
        mIncrementor = FileIncrementorFactory.newImageFileIncrementor(FILE_DIR_EXT, dir,
                filename);
    }

    private GvChildList getChildren() {
        return (GvChildList) getData(GvChildList.TYPE);
    }

    private void setChildren(GvChildList children) {
        mChildren = new ArrayList<>();
        for (GvChildList.GvChildReview child : children) {
            ReviewBuilder childBuilder = new ReviewBuilder(mContext);
            childBuilder.setSubject(child.getSubject());
            childBuilder.setRating(child.getRating());
            mChildren.add(childBuilder);
        }
        mData.put(GvChildList.TYPE, MdGvConverter.copy(children));
    }

    public class DataBuilder<T extends GvData> extends ReviewViewAdapterBasic {
        protected GvDataList<T>    mData;
        private   GvDataHandler<T> mHandler;

        private DataBuilder(GvDataList<T> data) {
            mData = data;
            mHandler = FactoryGvDataHandler.newHandler(mData);
        }

        public void reset() {
            getParentBuilder().resetDataBuilder(mData.getGvDataType());
        }

        public ReviewBuilder getParentBuilder() {
            return ReviewBuilder.this;
        }

        public boolean add(T datum) {
            boolean success = mHandler.add(datum, mContext);
            if (success) notifyGridDataObservers();

            return success;
        }

        public void delete(T datum) {
            mHandler.delete(datum);
            notifyGridDataObservers();
        }

        public void deleteAll() {
            mData.removeAll();
            notifyGridDataObservers();
        }

        public void replace(T oldDatum, T newDatum) {
            mHandler.replace(oldDatum, newDatum, mContext);
            notifyGridDataObservers();
        }

        public void setData() {
            getParentBuilder().setData(mData);
        }

        //TODO make type safe
        private void resetData() {
            mData = getData(mData.getGvDataType());
            mHandler = FactoryGvDataHandler.newHandler(mData);
            notifyGridDataObservers();
        }

        @Override
        public String getSubject() {
            return getParentBuilder().getSubject();
        }

        @Override
        public float getRating() {
            return getParentBuilder().getRating();
        }

        @Override
        public float getAverageRating() {
            return mData.getGvDataType() == GvChildList.TYPE ? ReviewBuilder.this
                    .getAverageRating() : getRating();
        }

        @Override
        public GvDataList getGridData() {
            return mData;
        }

        @Override
        public Author getAuthor() {
            return getParentBuilder().getAuthor();
        }

        @Override
        public Date getPublishDate() {
            return getParentBuilder().getPublishDate();
        }

        @Override
        public GvImageList getImages() {
            return mData.getGvDataType() == GvImageList.TYPE ? (GvImageList) mData :
                    getParentBuilder().getImages();
        }

        public void setRating(float rating) {
            getParentBuilder().setRating(rating);
        }

        public void setSubject(String subject) {
            getParentBuilder().setSubject(subject);
        }
    }

    @Override
    public float getRating() {
        return isRatingAverage() ? getAverageRating() : mRating;
    }


    public void setRating(float rating) {
        if (!isRatingAverage()) mRating = rating;
    }


    @Override
    public float getAverageRating() {
        return getChildren().getAverageRating();
    }


    @Override
    public GvDataList getGridData() {
        return mBuildUi;
    }


    @Override
    public Author getAuthor() {
        return Administrator.get(mContext).getAuthor();
    }


    @Override
    public Date getPublishDate() {
        return null;
    }


    public GvImageList getImages() {
        return (GvImageList) getData(GvImageList.TYPE);
    }
}
