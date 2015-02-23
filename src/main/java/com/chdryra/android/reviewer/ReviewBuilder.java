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
    private static final GvDataList.GvType[] TYPES        = {GvDataList.GvType.COMMENTS, GvDataList
            .GvType.FACTS, GvDataList.GvType.LOCATIONS, GvDataList.GvType.IMAGES, GvDataList
            .GvType.URLS, GvDataList.GvType.TAGS, GvDataList.GvType.CHILDREN};
    private static final File                FILE_DIR_EXT = Environment
            .getExternalStoragePublicDirectory
                    (Environment.DIRECTORY_DCIM);

    private FileIncrementor mIncrementor;
    private Context         mContext;

    private String                              mSubject;
    private float                               mRating;
    private Map<GvDataList.GvType, GvDataList>  mData;
    private Map<GvDataList.GvType, DataBuilder<? extends GvDataList.GvData>> mDataBuilders;
    private ArrayList<ReviewBuilder>                                         mChildren;
    private GvBuildReviewList                                                mBuildUi;

    private boolean mIsAverage = false;

    public ReviewBuilder(Context context) {
        mContext = context;
        mBuildUi = GvBuildReviewList.newInstance(this);
        mChildren = new ArrayList<>();

        mData = new HashMap<>();
        mDataBuilders = new HashMap<>();
        for (GvDataList.GvType dataType : TYPES) {
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

    public DataBuilder<? extends GvDataList.GvData> getDataBuilder(GvDataList.GvType dataType) {
        return mDataBuilders.get(dataType);
    }

    public void resetDataBuilder(GvDataList.GvType dataType) {
        mDataBuilders.get(dataType).resetData();
    }

    public int getDataSize(GvDataList.GvType dataType) {
        return getData(dataType).size();
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

    @Override
    public float getRating() {
        return isRatingAverage() ? getAverageRating() : mRating;
    }

    public GvDataList getData(GvDataList.GvType dataType) {
        if (dataType == GvDataList.GvType.CHILDREN) {
            return getChildren();
        } else {
            GvDataList data = mData.get(dataType);
            return data != null ? MdGvConverter.copy(data) : null;
        }
    }

    private DataBuilder<? extends GvDataList.GvData> newDataBuilder(GvDataList.GvType
            dataType) {
        GvDataList data = getData(dataType);
        DataBuilder<?> builder;
        if (dataType == GvDataList.GvType.CHILDREN) {
            builder = new DataBuilder<>((GvChildrenList) data);
        } else if (dataType == GvDataList.GvType.COMMENTS) {
            builder = new DataBuilder<>((GvCommentList) data);
        } else if (dataType == GvDataList.GvType.IMAGES) {
            builder = new DataBuilder<>((GvImageList) data);
        } else if (dataType == GvDataList.GvType.FACTS) {
            builder = new DataBuilder<>((GvFactList) data);
        } else if (dataType == GvDataList.GvType.LOCATIONS) {
            builder = new DataBuilder<>((GvLocationList) data);
        } else if (dataType == GvDataList.GvType.URLS) {
            builder = new DataBuilder<>((GvUrlList) data);
        } else if (dataType == GvDataList.GvType.TAGS) {
            builder = new DataBuilder<>((GvTagList) data);
        } else {
            return null;
        }

        mDataBuilders.put(dataType, builder);

        return builder;
    }

    private void setData(GvDataList data) {
        GvDataList.GvType dataType = data.getGvType();
        if (dataType == GvDataList.GvType.CHILDREN) {
            setChildren((GvChildrenList) data);
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
            mData = getData(mData.getGvType());
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
            return mData.getGvType() == GvDataList.GvType.CHILDREN ? ReviewBuilder.this
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
            return mData.getGvType() == GvDataList.GvType.IMAGES ? (GvImageList) mData :
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
        return (GvImageList) getData(GvDataList.GvType.IMAGES);
    }
}
