/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 February, 2015
 */

package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;

import com.chdryra.android.mygenerallibrary.FileIncrementor;
import com.chdryra.android.mygenerallibrary.FileIncrementorFactory;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.MdGvConverter;
import com.chdryra.android.reviewer.Model.ReviewData.PublishDate;
import com.chdryra.android.reviewer.Model.ReviewStructure.FactoryReview;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewTreeNode;
import com.chdryra.android.reviewer.Model.Tagging.TagsManager;
import com.chdryra.android.reviewer.Model.UserData.Author;
import com.chdryra.android.reviewer.View.Configs.ConfigGvDataUi;
import com.chdryra.android.reviewer.View.GvDataModel.FactoryGvData;
import com.chdryra.android.reviewer.View.GvDataModel.FactoryGvDataHandler;
import com.chdryra.android.reviewer.View.GvDataModel.GvBuildReviewList;
import com.chdryra.android.reviewer.View.GvDataModel.GvChildReviewList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataHandler;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.View.Utils.ImageChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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
 * user is ready using the {@link #publish(PublishDate)} method.
 */
public class ReviewBuilder extends ReviewViewAdapterBasic {
    private static final GvDataType[] TYPES = ConfigGvDataUi.TYPES;
    private static final File         FILE_DIR_EXT = Environment
            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
    private final Context           mContext;
    private final Map<GvDataType<? extends GvData>, GvDataList<?>>  mData;
    private final Map<GvDataType<? extends GvData>, DataBuilder<?>> mDataBuilders;
    private final GvBuildReviewList mBuildUi;

    private FileIncrementor          mIncrementor;
    private String                   mSubject;
    private float                    mRating;
    private ArrayList<ReviewBuilder> mChildren;
    private boolean mIsAverage = false;
    private Author mAuthor;

    public ReviewBuilder(Context context, Author author) {
        mContext = context;
        mAuthor = author;
        mChildren = new ArrayList<>();

        mData = new HashMap<>();
        mDataBuilders = new HashMap<>();
        for (GvDataType dataType : TYPES) {
            mData.put(dataType, FactoryGvData.newDataList(dataType));
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

    //TODO make type safe
    public <T extends GvData> DataBuilder<T> getDataBuilder(GvDataType<T> dataType) {
        return (DataBuilder<T>) mDataBuilders.get(dataType.getElementType());
    }

    public <T extends GvData> void resetDataBuilder(GvDataType<T> dataType) {
        getDataBuilder(dataType).resetData();
    }

    public int getDataSize(GvDataType dataType) {
        return getData(dataType).size();
    }

    public ReviewNode publish(PublishDate publishDate) {
        if (!isValidForPublication()) {
            throw new IllegalStateException("Review is not valid for publication!");
        }

        ReviewTreeNode rootNode = prepareTree(publishDate);
        ReviewNode published = rootNode.createTree();
        tagTree(published);

        return published;
    }

    public <T extends GvData> GvDataList<T> getData(GvDataType<T> dataType) {
        GvDataList data = mData.get(dataType.getElementType());
        return data != null ? MdGvConverter.copy(data) : null;
    }

    private boolean isValidForPublication() {
        return DataValidator.validateString(mSubject) && getData(GvTagList.GvTag.TYPE).size() > 0;
    }

    private void tagTree(ReviewNode node) {
        GvTagList tags = (GvTagList) getData(GvTagList.GvTag.TYPE);
        TagsManager.tag(node.getId(), tags.toStringArray());
        for (ReviewNode child : node.getChildren()) {
            tagTree(child);
        }
    }

    private ReviewTreeNode prepareTree(PublishDate publishDate) {
        Review root = FactoryReview.createReviewUser(mAuthor,
                publishDate, getSubject(), getRating(),
                getData(GvCommentList.GvComment.TYPE),
                getData(GvImageList.GvImage.TYPE),
                getData(GvFactList.GvFact.TYPE),
                getData(GvLocationList.GvLocation.TYPE));

        ReviewTreeNode rootNode = FactoryReview.createReviewTreeNode(root, mIsAverage);

        for (ReviewBuilder child : mChildren) {
            rootNode.addChild(child.prepareTree(publishDate));
        }

        return rootNode;
    }

    //TODO make type safe
    private <T extends GvData> DataBuilder<T> newDataBuilder(GvDataType<T> dataType) {
        return new DataBuilder<>(MdGvConverter.copy(getData(dataType)));
    }

    //TODO make type safe
    private <T extends GvData> void setData(GvDataList<T> data) {
        GvDataType<T> dataType = data.getGvDataType().getElementType();
        if (dataType == GvChildReviewList.GvChildReview.TYPE) {
            setChildren((GvChildReviewList) data);
        } else if (Arrays.asList(TYPES).contains(dataType)) {
            mData.put(dataType, MdGvConverter.copy(data));
        }

        notifyGridDataObservers();
    }

    private void newIncrementor() {
        String dir = mContext.getString(mContext.getApplicationInfo().labelRes);
        String filename = mSubject.length() > 0 ? mSubject : mAuthor.getName();
        mIncrementor = FileIncrementorFactory.newImageFileIncrementor(FILE_DIR_EXT, dir,
                filename);
    }

    private GvChildReviewList getChildren() {
        return (GvChildReviewList) getData(GvChildReviewList.GvChildReview.TYPE);
    }

    private void setChildren(GvChildReviewList children) {
        mChildren = new ArrayList<>();
        for (GvChildReviewList.GvChildReview child : children) {
            ReviewBuilder childBuilder = new ReviewBuilder(mContext, mAuthor);
            childBuilder.setSubject(child.getSubject());
            childBuilder.setRating(child.getRating());
            mChildren.add(childBuilder);
        }
        mData.put(GvChildReviewList.GvChildReview.TYPE, MdGvConverter.copy(children));
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
    public GvImageList getCovers() {
        return (GvImageList) getData(GvImageList.GvImage.TYPE);
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

        @Override
        public GvDataList<T> getGridData() {
            return mData;
        }

        //TODO make type safe
        private void resetData() {
            mData = getData(mData.getGvDataType().getElementType());
            mHandler = FactoryGvDataHandler.newHandler(mData);
            notifyGridDataObservers();
        }

        @Override
        public String getSubject() {
            return getParentBuilder().getSubject();
        }

        public void setSubject(String subject) {
            getParentBuilder().setSubject(subject);
        }

        @Override
        public float getRating() {
            return getParentBuilder().getRating();
        }

        public void setRating(float rating) {
            getParentBuilder().setRating(rating);
        }

        @Override
        public float getAverageRating() {
            return mData.getGvDataType() == GvChildReviewList.TYPE ?
                    ReviewBuilder.this.getAverageRating() : getRating();
        }

        @Override
        public GvImageList getCovers() {
            return mData.getGvDataType().getElementType() == GvImageList.GvImage.TYPE ?
                    (GvImageList) mData : getParentBuilder().getCovers();
        }
    }
}
