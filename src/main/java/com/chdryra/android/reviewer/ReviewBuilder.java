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
public class ReviewBuilder extends ReviewViewAdapterBasic {
    private static final GvDataList.GvDataType[] TYPES        = {GvCommentList.TYPE,
            GvFactList.TYPE, GvLocationList.TYPE, GvImageList.TYPE, GvUrlList.TYPE, GvTagList.TYPE,
            GvChildList.TYPE};
    private static final File                    FILE_DIR_EXT = Environment
            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);

    private FileIncrementor mIncrementor;
    private Context         mContext;

    private String                                                               mSubject;
    private float                                                                mRating;
    private Map<GvDataList.GvDataType, GvDataList>                               mData;
    private Map<GvDataList.GvDataType, DataBuilder<? extends GvDataList.GvData>> mDataBuilders;
    private ArrayList<ReviewBuilder>                                             mChildren;
    private GvBuildReviewList                                                    mBuildUi;

    private boolean mIsAverage = false;

    public ReviewBuilder(Context context) {
        mContext = context;
        mBuildUi = GvBuildReviewList.newInstance(this);
        mChildren = new ArrayList<>();

        mData = new HashMap<>();
        mDataBuilders = new HashMap<>();
        for (GvDataList.GvDataType dataType : TYPES) {
            mData.put(dataType, FactoryGvData.newList(dataType));
            mDataBuilders.put(dataType, newDataBuilder(dataType));
        }

        mSubject = "";
        mRating = 0f;

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

    public DataBuilder<? extends GvDataList.GvData> getDataBuilder(GvDataList.GvDataType dataType) {
        return mDataBuilders.get(dataType);
    }

    public void resetDataBuilder(GvDataList.GvDataType dataType) {
        mDataBuilders.get(dataType).resetData();
    }

    public int getDataSize(GvDataList.GvDataType dataType) {
        return getData(dataType).size();
    }

    public Review publish(Date publishDate) {
        Review root = FactoryReview.createReviewUser(getAuthor(),
                publishDate, getSubject(), getRating(),
                (GvCommentList) getData(GvCommentList.TYPE),
                (GvImageList) getData(GvImageList.TYPE),
                (GvFactList) getData(GvFactList.TYPE),
                (GvLocationList) getData(GvLocationList.TYPE),
                (GvUrlList) getData(GvUrlList.TYPE));

        GvTagList tags = (GvTagList) getData(GvTagList.TYPE);
        TagsManager.tag(root, tags);

        RCollectionReview<Review> children = new RCollectionReview<>();
        for (ReviewBuilder child : mChildren) {
            Review childReview = child.publish(publishDate);
            TagsManager.tag(childReview, tags);
            children.add(childReview);
        }

        return FactoryReview.createReviewTree(root, children, mIsAverage);
    }

    public GvDataList getData(GvDataList.GvDataType dataType) {
        if (dataType == GvChildList.TYPE) {
            return getChildren();
        } else {
            GvDataList data = mData.get(dataType);
            return data != null ? MdGvConverter.copy(data) : null;
        }
    }

    private DataBuilder<? extends GvDataList.GvData> newDataBuilder(GvDataList.GvDataType
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

        mDataBuilders.put(dataType, builder);

        return builder;
    }

    private void setData(GvDataList data) {
        GvDataList.GvDataType dataType = data.getGvDataType();
        if (dataType == GvChildList.TYPE) {
            setChildren((GvChildList) data);
        } else if (Arrays.asList(TYPES).contains(dataType)) {
            mData.put(dataType, MdGvConverter.copy(data));
        }

        notifyGridDataObservers();
    }

    @Override
    public float getRating() {
        return isRatingAverage() ? getAverageRating() : mRating;
    }

    private void newIncrementor() {
        String dir = mContext.getString(mContext.getApplicationInfo().labelRes);
        String filename = mSubject.length() > 0 ? mSubject : getAuthor().getName();
        mIncrementor = FileIncrementorFactory.newImageFileIncrementor(FILE_DIR_EXT, dir,
                filename);
    }

    private GvChildList getChildren() {
        GvChildList children = new GvChildList();
        for (ReviewBuilder childBuilder : mChildren) {
            GvChildList.GvChildReview review = new GvChildList.GvChildReview(childBuilder
                    .getSubject(), childBuilder.getRating());
            children.add(review);
        }

        return children;
    }

    private void setChildren(GvChildList children) {
        mChildren = new ArrayList<>();
        for (GvChildList.GvChildReview child : children) {
            ReviewBuilder childBuilder = new ReviewBuilder(mContext);
            childBuilder.setSubject(child.getSubject());
            childBuilder.setRating(child.getRating());
            mChildren.add(childBuilder);
        }
    }

    public class DataBuilder<T extends GvDataList.GvData> extends ReviewViewAdapterBasic {
        private GvDataList<T>    mData;
        private GvDataHandler<T> mHandler;

        private DataBuilder(GvDataList<T> data) {
            mData = data;
            mHandler = FactoryGvDataHandler.newHandler(mData);
        }

        public DataBuilder<T> getCopy() {
            GvDataList<T> copy = MdGvConverter.copy(mData);
            return new DataBuilder<>(copy);
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
