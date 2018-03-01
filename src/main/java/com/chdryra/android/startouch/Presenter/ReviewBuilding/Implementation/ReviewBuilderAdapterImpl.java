/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.corelibrary.ReferenceModel.Implementation.DataValue;
import com.chdryra.android.corelibrary.ReferenceModel.Implementation.DereferencableBasic;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Factories.FactoryDataBuilderAdapter;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.BuildScreenGridUi;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewBuilder;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataTypes;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View.ReviewViewAdapterBasic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 15/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewBuilderAdapterImpl<GC extends GvDataList<? extends GvDataParcelable>> extends
        ReviewViewAdapterBasic<GC>
        implements ReviewBuilderAdapter<GC> {
    private static final ArrayList<GvDataType<? extends GvDataParcelable>> TYPES
            = GvDataTypes.BUILD_TYPES;

    private final ReviewBuilder mBuilder;
    private final BuildScreenGridUi<GC> mFullGridUi;
    private final BuildScreenGridUi<GC> mQuickGridUi;
    private final DataValidator mDataValidator;
    private final DataBuildersMap mDataBuilders;
    private final DereferencableBasic<String> mSubject;
    private final DereferencableBasic<Float> mRating;

    private GvTag mSubjectTag;
    private ReviewEditor.EditMode mUiType = ReviewEditor.EditMode.QUICK;

    public ReviewBuilderAdapterImpl(ReviewBuilder builder,
                                    FactoryDataBuilderAdapter factory,
                                    BuildScreenGridUi<GC> fullGridUi,
                                    BuildScreenGridUi<GC> quickGridUi,
                                    DataValidator dataValidator) {
        mBuilder = builder;
        mFullGridUi = fullGridUi;
        mQuickGridUi = quickGridUi;
        mDataValidator = dataValidator;

        mDataBuilders = new DataBuildersMap(factory);
        mFullGridUi.setParentAdapter(this);
        mQuickGridUi.setParentAdapter(this);

        mSubjectTag = new GvTag(builder.getSubject());

        mSubject = new DereferencableBasic<String>() {
            @Override
            protected void doDereferencing(DereferenceCallback<String> callback) {
                callback.onDereferenced(new DataValue<>(getSubject()));
            }
        };
        mRating = new DereferencableBasic<Float>() {
            @Override
            protected void doDereferencing(DereferenceCallback<Float> callback) {
                callback.onDereferenced(new DataValue<>(getRating()));
            }
        };

        mBuilder.registerObserver(this);
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        notifySubscribers();
    }

    @Override
    public DataReference<String> getSubjectReference() {
        return mSubject;
    }

    @Override
    public DataReference<Float> getRatingReference() {
        return mRating;
    }

    @Override
    public GvDataType<GC> getGvDataType() {
        return getGridUi().getGridWrapper().getGvDataType();
    }

    @Override
    public void setRatingIsAverage(boolean ratingIsAverage) {
        mBuilder.setRatingIsAverage(ratingIsAverage);
    }

    @Override
    public <T extends GvDataParcelable> DataBuilderAdapter<T>
    getDataBuilderAdapter(GvDataType<T> dataType) {
        return mDataBuilders.get(dataType);
    }

    @Override
    public Review buildReview() {
        return mBuilder.buildReview();
    }

    @Override
    public ReviewNode buildPreview() {
        return buildPreview(getSubject(), getRating());
    }

    @Override
    public ReviewNode buildPreview(String subject, float rating) {
        return mBuilder.buildPreview(subject, rating);
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
    public void setSubject(String subject, boolean adjustTags) {
        if (!mBuilder.getSubject().equals(subject)) {
            mBuilder.setSubject(subject);
        }
        adjustTagsIfNecessary(adjustTags);
    }

    @Override
    public GvDataList<GC> getGridData() {
        return getGridUi().getGridWrapper();
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

    @Override
    public void setCover(GvImage cover) {
        getCover().setIsCover(false);
        cover.setIsCover(true);
        DataBuilderAdapter<GvImage> builder = getDataBuilderAdapter(GvImage.TYPE);
        builder.add(cover);
        builder.commitData();
    }

    @Override
    protected void onAttach() {
        mDataBuilders.attach();
    }

    @Override
    protected void onDetach() {
        mDataBuilders.detach();
    }

    @Override
    public void setView(ReviewEditor.EditMode uiType) {
        mUiType = uiType;
        notifyDataObservers();
    }

    private BuildScreenGridUi<GC> getGridUi() {
        return mUiType == ReviewEditor.EditMode.QUICK ? mQuickGridUi : mFullGridUi;
    }

    private void notifySubscribers() {
        mSubject.notifySubscribers();
        mRating.notifySubscribers();
    }

    private void adjustTagsIfNecessary(boolean adjustTags) {
        GvTag newTag = new GvTag(mBuilder.getSubject());
        DataBuilderAdapter<GvTag> tagBuilder = getDataBuilderAdapter(GvTag.TYPE);
        GvDataList<GvTag> tags = tagBuilder.getGridData();

        //If nothing to adjust return
        if (newTag.equals(mSubjectTag) && tags.contains(newTag)) return;

        //if don't want to adjust then set appropriate subject tag to reference for future
        // adjustments
        if (!adjustTags) {
            mSubjectTag = tags.contains(newTag) ? newTag : new GvTag("");
            return;
        }

        //Do adjustment
        boolean added = mDataValidator.validateString(newTag.getTag()) && !tags.contains(newTag)
                && tagBuilder.add(newTag);
        if (!newTag.equals(mSubjectTag)) tagBuilder.delete(mSubjectTag);
        tagBuilder.commitData();

        mSubjectTag = added ? newTag : new GvTag("");
        if (added) notifyDataObservers();
    }

    private class DataBuildersMap {
        private final Map<GvDataType<? extends GvData>, DataBuilderAdapterDefault<? extends GvData>>
                mDataBuilders;

        private DataBuildersMap(FactoryDataBuilderAdapter factory) {
            mDataBuilders = new HashMap<>();
            for (GvDataType<? extends GvDataParcelable> type : TYPES) {
                mDataBuilders.put(type, factory.newDataBuilderAdapter(type,
                        ReviewBuilderAdapterImpl.this));
            }
        }

        //TODO make type safe although it is really....
        private <T extends GvDataParcelable> DataBuilderAdapter<T> get(GvDataType<T> type) {
            return (DataBuilderAdapter<T>) mDataBuilders.get(type);
        }

        private void attach() {
            for (DataBuilderAdapterDefault<?> builder : mDataBuilders.values()) {
                builder.attach();
            }
        }

        private void detach() {
            for (DataBuilderAdapterDefault<?> builder : mDataBuilders.values()) {
                builder.detach();
            }
        }
    }
}

