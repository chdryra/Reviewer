/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.mygenerallibrary.FileUtils.FileIncrementor;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryDataBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryFileIncrementor;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryImageChooser;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.BuildScreenGridUi;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ImageChooser;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilder;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataTypes;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewAdapterBasic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 15/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewBuilderAdapterImpl<GC extends GvDataList<? extends GvDataParcelable>> extends ReviewViewAdapterBasic<GC>
        implements ReviewBuilderAdapter<GC> {
    private static final ArrayList<GvDataType<? extends GvDataParcelable>> TYPES = GvDataTypes.BUILD_TYPES;

    private final DataBuildersMap mDataBuilders;
    private final BuildScreenGridUi<GC> mGridUi;
    private final FactoryDataBuilderAdapter mDataBuilderAdapterFactory;
    private final FactoryFileIncrementor mIncrementorFactory;
    private final FactoryImageChooser mImageChooserFactory;
    private final ReviewBuilder mBuilder;
    private final DataValidator mDataValidator;
    private FileIncrementor mIncrementor;
    private GvTag mSubjectTag;

    public ReviewBuilderAdapterImpl(ReviewBuilder builder,
                                    BuildScreenGridUi<GC> gridUi,
                                    DataValidator dataValidator,
                                    FactoryDataBuilderAdapter dataBuilderAdapterFactory,
                                    FactoryFileIncrementor incrementorFactory,
                                    FactoryImageChooser imageChooserFactory) {
        mBuilder = builder;
        mDataValidator = dataValidator;
        mDataBuilderAdapterFactory = dataBuilderAdapterFactory;
        mDataBuilders = new DataBuildersMap();
        mGridUi = gridUi;
        mGridUi.setParentAdapter(this);
        mIncrementorFactory = incrementorFactory;
        mImageChooserFactory = imageChooserFactory;
        mSubjectTag = new GvTag("");
        newIncrementor();
    }

    @Override
    public GvDataType<? extends GvData> getGvDataType() {
        return mGridUi.getGridWrapper().getGvDataType();
    }

    @Override
    public void setRatingIsAverage(boolean ratingIsAverage) {
        mBuilder.setRatingIsAverage(ratingIsAverage);
    }

    @Override
    public void setCover(GvImage cover) {
        getCover().setIsCover(false);
        cover.setIsCover(true);
        DataBuilderAdapter<GvImage> builder = getDataBuilderAdapter(GvImage.TYPE);
        builder.add(cover);
        builder.commitData();
    }

    @Override
    public ImageChooser getImageChooser() {
        return mImageChooserFactory.newImageChooser(mIncrementor);
    }

    @Override
    public <T extends GvDataParcelable> DataBuilderAdapter<T> getDataBuilderAdapter(GvDataType<T> dataType) {
        return mDataBuilders.get(dataType);
    }

    @Override
    public Review buildReview() {
        return mBuilder.buildReview();
    }

    @Override
    public ReviewBuilder getBuilder() {
        return mBuilder;
    }

    @Override
    public String getSubject() {
        return mBuilder.getSubject();
    }

    @Override
    public void setSubject(String subject) {
        mBuilder.setSubject(subject);
        newIncrementor();
        mSubjectTag = adjustTagsIfNecessary(mSubjectTag, subject);
    }

    @Override
    public GvDataList<GC> getGridData() {
        return mGridUi.getGridWrapper();
    }

    @Override
    public float getRating() {
        return mBuilder.getRating();
    }

    @Override
    public void setRating(float rating) {
        mBuilder.setRating(rating);
    }

    @Override
    public void getCover(CoverCallback callback) {
        callback.onAdapterCover(getCover());
    }

    @Override
    public GvImage getCover() {
        return mBuilder.getCover();
    }

    private GvTag adjustTagsIfNecessary(GvTag toRemove, String toAdd) {
        GvTag newTag = new GvTag(toAdd);
        DataBuilderAdapter<GvTag> tagBuilder = getDataBuilderAdapter(GvTag.TYPE);
        GvDataList<GvTag> tags = tagBuilder.getGridData();

        if (newTag.equals(toRemove) && tags.contains(newTag)) return newTag;

        boolean added = mDataValidator.validateString(newTag.getTag()) && !tags.contains(newTag)
                && tagBuilder.add(newTag);
        if(!newTag.equals(toRemove)) tagBuilder.delete(toRemove);
        tagBuilder.commitData();

        return added ? newTag : new GvTag("");
    }

    private void newIncrementor() {
        mIncrementor = mIncrementorFactory.newJpgFileIncrementor(mBuilder.getSubject());
    }

    private class DataBuildersMap {
        private final Map<GvDataType<? extends GvData>, DataBuilderAdapter<? extends GvData>>
                mDataBuilders;

        private DataBuildersMap() {
            mDataBuilders = new HashMap<>();
            for (GvDataType<? extends GvDataParcelable> type : TYPES) {
                mDataBuilders.put(type, newDataBuilderAdapter(type));
            }
        }

        private <T extends GvDataParcelable> DataBuilderAdapter<T> newDataBuilderAdapter(GvDataType<T>
                                                                                       dataType) {
            return mDataBuilderAdapterFactory.newDataBuilderAdapter(dataType,
                    ReviewBuilderAdapterImpl.this);
        }

        //TODO make type safe although it is really....
        private <T extends GvDataParcelable> DataBuilderAdapter<T> get(GvDataType<T> type) {
            return (DataBuilderAdapter<T>) mDataBuilders.get(type);
        }
    }
}

