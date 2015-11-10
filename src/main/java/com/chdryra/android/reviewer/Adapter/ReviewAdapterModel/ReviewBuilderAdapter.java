/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 February, 2015
 */

package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import android.content.Context;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Toast;

import com.chdryra.android.mygenerallibrary.FileIncrementor;
import com.chdryra.android.mygenerallibrary.FileIncrementorFactory;
import com.chdryra.android.mygenerallibrary.TextUtils;
import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.PublishDate;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.Review;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.Configs.ConfigGvDataUi;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataHandler;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.View.GvDataModel.VhBuildReviewData;
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
    public static final ArrayList<GvDataType> TYPES = ConfigGvDataUi.BUILD_TYPES;
    private static final File FILE_DIR_EXT = Environment
            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);

    private final Context mContext;
    private final DataBuildersMap mDataBuilders;
    private final BuilderGridData mBuildUi;
    private FileIncrementor mIncrementor;
    private ReviewBuilder mBuilder;
    private DataValidator mDataValidator;
    private GvTagList.GvTag mSubjectTag;

    //Constructors
    public ReviewBuilderAdapter(Context context, ReviewBuilder builder, DataValidator dataValidator) {
        mContext = context;
        mBuilder = builder;
        mDataValidator = dataValidator;
        mDataBuilders = new DataBuildersMap();
        mBuildUi = new BuilderGridData(this);
        newIncrementor();
    }

    //public methods
    public void setRatingIsAverage(boolean ratingIsAverage) {
        mBuilder.setRatingIsAverage(ratingIsAverage);
    }

    public ImageChooser getImageChooser(Context context) {
        return new ImageChooser(context, (FileIncrementorFactory.ImageFileIncrementor)
                mIncrementor);
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

    public Review publish() {
        return mBuilder.buildReview(PublishDate.now());
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
        String author = mBuilder.getAuthor().getName();
        String dir = mContext.getString(mContext.getApplicationInfo().labelRes);
        String subject = mBuilder.getSubject();
        String filename = mDataValidator.validateString(subject) ? subject : author;
        mIncrementor = FileIncrementorFactory.newImageFileIncrementor(FILE_DIR_EXT, dir, filename);
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
        return mBuildUi;
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
        private ReviewBuilder.DataBuilder<T> mDataBuilder;
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

    /**
     * Encapsulates the range of responses and displays available to each data tile depending
     * on the underlying data and user interaction.
     */
    public static class BuilderGridData extends GvDataList<BuilderGridCell> {
        public static final Parcelable.Creator<BuilderGridData> CREATOR = new Parcelable
                .Creator<BuilderGridData>() {
            //Overridden
            public BuilderGridData createFromParcel(Parcel in) {
                return new BuilderGridData(in);
            }

            public BuilderGridData[] newArray(int size) {
                return new BuilderGridData[size];
            }
        };
        private ReviewBuilderAdapter mBuilder;

        private BuilderGridData(ReviewBuilderAdapter builder) {
            super(BuilderGridCell.TYPE, null);

            mBuilder = builder;

            add(GvTagList.GvTag.TYPE);
            add(GvCriterionList.GvCriterion.TYPE);
            add(GvImageList.GvImage.TYPE);
            add(GvCommentList.GvComment.TYPE);
            add(GvLocationList.GvLocation.TYPE);
            add(GvFactList.GvFact.TYPE);
        }

        private BuilderGridData(Parcel in) {
            super(in);
        }

        private <T extends GvData> void add(GvDataType<T> dataType) {
            add(new BuilderGridCell<>(dataType, mBuilder));
        }

        //Overridden
        @Override
        public void sort() {
        }
    }

    public static class BuilderGridCell<T extends GvData> extends GvDataList<T>
            implements GridDataObserver {
        public static final Parcelable.Creator<BuilderGridCell> CREATOR = new Parcelable
                .Creator<BuilderGridCell>() {
            //Overridden
            public BuilderGridCell createFromParcel(Parcel in) {
                return new BuilderGridCell(in);
            }

            public BuilderGridCell[] newArray(int size) {
                return new BuilderGridCell[size];
            }
        };

        public static GvDataType<BuilderGridCell> TYPE =
                new GvDataType<>(BuilderGridCell.class, "create", "create");

        private ConfigGvDataUi.Config mConfig;
        private DataBuilderAdapter<T> mBuilder;

        private BuilderGridCell(GvDataType<T> dataType, ReviewBuilderAdapter builder) {
            super(dataType, null);
            mConfig = ConfigGvDataUi.getConfig(dataType);
            mBuilder = builder.getDataBuilder(dataType);
            mBuilder.registerGridDataObserver(this);
        }

        private BuilderGridCell(Parcel in) {
            super(in);
        }

        //public methods
        public ConfigGvDataUi.Config getConfig() {
            return mConfig;
        }

        public int getDataSize() {
            return mBuilder.getGridData().size();
        }

        //Overridden
        @Override
        public String getStringSummary() {
            return getGvDataType().getDataName();
        }

        @Override
        public ViewHolder getViewHolder() {
            return new VhBuildReviewData();
        }

        @Override
        public void onGridDataChanged() {
            mData = mBuilder.getGridData().toArrayList();
        }
    }
}
