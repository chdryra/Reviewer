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
import com.chdryra.android.mygenerallibrary.TextUtils;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.UserData.Author;
import com.chdryra.android.reviewer.View.Configs.ConfigGvDataUi;
import com.chdryra.android.reviewer.View.GvDataModel.GvBuildReviewList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.View.Utils.ImageChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 13/02/2015
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * For building reviews. Collects appropriate data and builds a {@link com.chdryra.android
 * .reviewer.Model.Review} object
 */
public class ReviewBuilderAdapter extends ReviewViewAdapterBasic {
    public static final ArrayList<GvDataType<? extends GvData>> TYPES = ConfigGvDataUi.TYPES;
    private static final File FILE_DIR_EXT = Environment
            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);

    private final DataBuildersMap mDataBuilders;
    private final Context mContext;
    private final Author mAuthor;
    private final GvBuildReviewList mBuildUi;
    private FileIncrementor mIncrementor;
    private ReviewBuilder mBuilder;
    private GvTagList.GvTag mSubjectTag;

    public ReviewBuilderAdapter(Context context, Author author) {
        mContext = context;
        mAuthor = author;
        mBuilder = new ReviewBuilder(context, author);
        mDataBuilders = new DataBuildersMap();
        mBuildUi = GvBuildReviewList.newInstance(this);
        newIncrementor();
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public String getSubject() {
        return mBuilder.getSubject();
    }

    public void setSubject(String subject) {
        mBuilder.setSubject(subject);
        newIncrementor();
        mSubjectTag = adjustTagsIfNecessary(mSubjectTag, subject);
    }

    private GvTagList.GvTag adjustTagsIfNecessary(GvTagList.GvTag toRemove, String toAdd) {
        DataBuilderAdapter<GvTagList.GvTag> tagBuilder = getDataBuilder(GvTagList.GvTag.TYPE);
        GvTagList tags = (GvTagList) tagBuilder.getGridData();
        String camel = TextUtils.toCamelCase(toAdd);
        GvTagList.GvTag newTag = new GvTagList.GvTag(camel);
        boolean added = DataValidator.validateString(camel) && !tags.contains(newTag)
                && tagBuilder.add(newTag);
        tagBuilder.delete(toRemove);
        tagBuilder.setData();
        notifyGridDataObservers();

        return added ? newTag : null;
    }

    public boolean isRatingAverage() {
        return mBuilder.isRatingAverage();
    }

    public void setRatingIsAverage(boolean ratingIsAverage) {
        mBuilder.setRatingIsAverage(ratingIsAverage);
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

    public <T extends GvData> DataBuilderAdapter<T> getDataBuilder(GvDataType<T> dataType) {
        return mDataBuilders.get(dataType);
    }

    public <T extends GvData> void resetDataBuilder(GvDataType<T> dataType) {
        getDataBuilder(dataType).reset();
    }

    public int getDataSize(GvDataType dataType) {
        return getData(dataType).size();
    }

    public Review publish() {
        return mBuilder.buildReview();
    }

    public <T extends GvData> GvDataList<T> getData(GvDataType<T> dataType) {
        return mBuilder.getData(dataType);
    }

    private <T extends GvData> DataBuilderAdapter<T> newDataBuilder(GvDataType<T> dataType) {
        return new DataBuilderAdapter<>(dataType);
    }

    private void newIncrementor() {
        String dir = mContext.getString(mContext.getApplicationInfo().labelRes);
        String subject = mBuilder.getSubject();
        String filename = DataValidator.validateString(subject) ? subject : mAuthor.getName();
        mIncrementor = FileIncrementorFactory.newImageFileIncrementor(FILE_DIR_EXT, dir,
                filename);
    }

    private GvCriterionList getChildren() {
        return (GvCriterionList) getData(GvCriterionList.GvCriterion.TYPE);
    }

    @Override
    public GvDataList getGridData() {
        return mBuildUi;
    }

    public class DataBuilderAdapter<T extends GvData> extends ReviewViewAdapterBasic {
        private ReviewBuilder.DataBuilder<T> mDataBuilder;
        private GvDataType<T> mType;

        private DataBuilderAdapter(GvDataType<T> type) {
            mType = type;
            mDataBuilder = mBuilder.getDataBuilder(mType);
            reset();
        }

        public ReviewBuilderAdapter getParentBuilder() {
            return ReviewBuilderAdapter.this;
        }

        public boolean add(T datum) {
            boolean success = mDataBuilder.add(datum);
            notifyGridDataObservers();
            return success;
        }

        public void delete(T datum) {
            mDataBuilder.delete(datum);
            notifyGridDataObservers();
        }

        public void deleteAll() {
            mDataBuilder.deleteAll();
            notifyGridDataObservers();
        }

        public void replace(T oldDatum, T newDatum) {
            mDataBuilder.replace(oldDatum, newDatum);
            notifyGridDataObservers();
        }

        public void setData() {
            mDataBuilder.setData();
        }

        public void reset() {
            mDataBuilder.resetData();
            notifyGridDataObservers();
        }

        @Override
        public GvDataList<T> getGridData() {
            return mDataBuilder.getGvData();
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
            return mDataBuilder.getAverageRating();
        }

        @Override
        public GvImageList getCovers() {
            GvDataList images = getParentBuilder().getCovers();
            if (mType == GvImageList.GvImage.TYPE) {
                images = getGridData();
            }

            return (GvImageList) images;
        }
    }

    public float getRating() {
        return mBuilder.getRating();
    }

    //To ensure type safety
    private class DataBuildersMap {
        private final Map<GvDataType<? extends GvData>, DataBuilderAdapter<? extends GvData>>
                mDataBuilders;

        private DataBuildersMap() {
            mDataBuilders = new HashMap<>();
            for (GvDataType<? extends GvData> dataType : TYPES) {
                add(dataType);
            }
        }

        private <T extends GvData> void add(GvDataType<T> type) {
            mDataBuilders.put(type, newDataBuilder(type));
        }

        //TODO make type safe although it really is....
        private <T extends GvData> DataBuilderAdapter<T> get(GvDataType<T> type) {
            return (DataBuilderAdapter<T>) mDataBuilders.get(type);
        }
    }

    public void setRating(float rating) {
        mBuilder.setRating(rating);
    }


    @Override
    public float getAverageRating() {
        return getChildren().getAverageRating();
    }


    @Override
    public GvImageList getCovers() {
        return (GvImageList) getData(GvImageList.GvImage.TYPE);
    }




}
