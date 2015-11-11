/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 February, 2015
 */

package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding;

import android.content.Context;
import android.widget.Toast;

import com.chdryra.android.mygenerallibrary.FileIncrementor;
import com.chdryra.android.mygenerallibrary.TextUtils;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewAdapterBasic;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.Review;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.Configs.ConfigGvDataUi;
import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataHandler;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.View.Utils.FactoryFileIncrementor;
import com.chdryra.android.reviewer.View.Utils.FactoryImageChooser;
import com.chdryra.android.reviewer.View.Utils.ImageChooser;

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
    public static final ArrayList<GvDataType> TYPES = ConfigGvDataUi.BUILD_TYPES;

    private final Context mContext;
    private final DataBuildersMap mDataBuilders;
    private final AdapterGridUi mGridUi;
    private FactoryFileIncrementor mIncrementorFactory;
    private FileIncrementor mIncrementor;
    private FactoryImageChooser mImageChooserFactory;
    private ReviewBuilder mBuilder;
    private DataValidator mDataValidator;
    private GvTagList.GvTag mSubjectTag;

    //Constructors
    public ReviewBuilderAdapter(Context context,
                                ReviewBuilder builder,
                                AdapterGridUi<ReviewBuilderAdapter> gridUi,
                                DataValidator dataValidator,
                                FactoryFileIncrementor incrementorFactory,
                                FactoryImageChooser imageChooserFactory) {
        mContext = context;
        mBuilder = builder;
        mDataValidator = dataValidator;
        mDataBuilders = new DataBuildersMap();
        mGridUi = gridUi;
        gridUi.setViewAdapter(this);
        mIncrementorFactory = incrementorFactory;
        mImageChooserFactory = imageChooserFactory;
        newIncrementor();
    }

    //public methods
    public void setRatingIsAverage(boolean ratingIsAverage) {
        mBuilder.setRatingIsAverage(ratingIsAverage);
    }

    public ImageChooser getImageChooser(Context context) {
        return mImageChooserFactory.newImageChooser(context, mIncrementor);
    }

    public <T extends GvData> DataBuilderAdapter<T> getDataBuilder(GvDataType<T> dataType) {
        return mDataBuilders.get(dataType);
    }

    private <T extends GvData> DataBuilderAdapter<T> newDataBuilder(GvDataType<T> dataType) {
        return new DataBuilderAdapter<>(dataType);
    }

    public boolean hasTags() {
        return mBuilder.getData(GvTagList.GvTag.TYPE).size() > 0;
    }

    public Review publish(ReviewPublisher publisher) {
        return mBuilder.buildReview(publisher);
    }

    //private methods
    private GvTagList.GvTag adjustTagsIfNecessary(GvTagList.GvTag toRemove, String toAdd) {
        String camel = TextUtils.toCamelCase(toAdd);
        GvTagList.GvTag newTag = new GvTagList.GvTag(camel);
        if (newTag.equals(toRemove)) return toRemove;

        DataBuilderAdapter<GvTagList.GvTag> tagBuilder = getDataBuilder(GvTagList.GvTag.TYPE);
        GvTagList tags = (GvTagList) tagBuilder.getGridData();
        boolean added = mDataValidator.validateString(camel) && !tags.contains(newTag)
                && tagBuilder.add(newTag);
        tagBuilder.delete(toRemove);
        tagBuilder.setData();

        return added ? newTag : null;
    }

    private void newIncrementor() {
        mIncrementor = mIncrementorFactory.newJpgFileIncrementor(mBuilder.getSubject());
    }

    //Overridden
    @Override
    public String getSubject() {
        return mBuilder.getSubject();
    }

    public void setSubject(String subject) {
        mBuilder.setSubject(subject);
        newIncrementor();
        mSubjectTag = adjustTagsIfNecessary(mSubjectTag, subject);
    }

    @Override
    public GvDataList getGridData() {
        return mGridUi.getGridUi();
    }

    public float getRating() {
        return mBuilder.getRating();
    }

    public void setRating(float rating) {
        mBuilder.setRating(rating);
    }

    @Override
    public GvImageList getCovers() {
        return ((GvImageList) mBuilder.getData(GvImageList.GvImage.TYPE)).getCovers();
    }

    public class DataBuilderAdapter<T extends GvData> extends ReviewViewAdapterBasic<T> {
        private DataBuilder<T> mDataBuilder;
        private GvDataType<T> mType;

        private DataBuilderAdapter(GvDataType<T> type) {
            mType = type;
            mDataBuilder = mBuilder.getDataBuilder(mType);
            reset();
        }

        //public methods
        public GvDataType<T> getDataType() {
            return mType;
        }

        public ReviewBuilderAdapter getParentBuilder() {
            return ReviewBuilderAdapter.this;
        }

        public boolean isRatingAverage() {
            return mBuilder.isRatingAverage();
        }

        public float getAverageRating() {
            if (mType.equals(GvCriterionList.GvCriterion.TYPE)) {
                return ((GvCriterionList) getGridData()).getAverageRating();
            } else {
                return mBuilder.getAverageRating();
            }
        }

        public boolean add(T datum) {
            GvDataHandler.ConstraintResult res = mDataBuilder.add(datum);
            if(res == GvDataHandler.ConstraintResult.PASSED) {
                this.notifyGridDataObservers();
                return true;
            } else {
                if(res == GvDataHandler.ConstraintResult.HAS_DATUM) {
                    makeToastHasItem(mContext, datum);
                }
                return false;
            }
        }

        public void delete(T datum) {
            mDataBuilder.delete(datum);
            this.notifyGridDataObservers();
        }

        public void deleteAll() {
            mDataBuilder.deleteAll();
            this.notifyGridDataObservers();
        }

        public void replace(T oldDatum, T newDatum) {
            GvDataHandler.ConstraintResult res = mDataBuilder.replace(oldDatum, newDatum);
            if(res == GvDataHandler.ConstraintResult.PASSED) {
                this.notifyGridDataObservers();
            } else {
                if(res == GvDataHandler.ConstraintResult.HAS_DATUM) {
                    makeToastHasItem(mContext, newDatum);
                }
            }
        }

        public void setData() {
            mDataBuilder.setData();
            getParentBuilder().notifyGridDataObservers();
        }

        public void reset() {
            mDataBuilder.resetData();
            this.notifyGridDataObservers();
        }

        public void setRatingIsAverage(boolean ratingIsAverage) {
            getParentBuilder().setRatingIsAverage(ratingIsAverage);
        }

        //Overridden
        @Override
        public GvDataList<T> getGridData() {
            return mDataBuilder.getData();
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
        public GvImageList getCovers() {
            return mType.equals(GvImageList.GvImage.TYPE) ? (GvImageList) getGridData()
                    : getParentBuilder().getCovers();
        }
    }


    private void makeToastHasItem(Context context, GvData datum) {
        String toast = context.getResources().getString(R.string.toast_has) + " " + datum
                .getGvDataType().getDatumName();
        Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
    }

    //To ensure type safety
    private class DataBuildersMap {
        private final Map<GvDataType<? extends GvData>, DataBuilderAdapter<? extends GvData>>
                mDataBuilders;

        private DataBuildersMap() {
            mDataBuilders = new HashMap<>();
            for (GvDataType dataType : TYPES) {
                add(dataType);
            }
        }

        private <T extends GvData> void add(GvDataType<T> type) {
            mDataBuilders.put(type, newDataBuilder(type));
        }

        //TODO make type safe although it is really....
        private <T extends GvData> DataBuilderAdapter<T> get(GvDataType<T> type) {
            return (DataBuilderAdapter<T>) mDataBuilders.get(type);
        }
    }
}
