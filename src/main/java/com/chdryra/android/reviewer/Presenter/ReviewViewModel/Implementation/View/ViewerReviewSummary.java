/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.Application.DataTypeCellOrder;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryBinders;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReferenceBinder;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .ConverterGv;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataSize;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataSizeList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 05/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerReviewSummary extends ViewerNodeBasic<GvDataSize> implements ReferenceBinder
        .DataSizeBinder {
    private static final GvDataType<GvDataSize> TYPE = GvDataSize.TYPE;
    private static final List<GvDataType<?>> ORDER = DataTypeCellOrder.ReviewOrder.ORDER;
    private static final int NUM_DATA = ORDER.size();

    private ConverterGv mConverter;
    private TagsManager mTagsManager;
    private FactoryReviewViewAdapter mAdapterFactory;
    private ReferenceBinder mBinder;

    private Map<GvDataType<?>, DataSize> mNumDataMap;
    private int mNumDataTypes;

    private boolean mInitialising = true;

    public ViewerReviewSummary(ReviewNode node,
                               ConverterGv converter,
                               TagsManager tagsManager,
                               FactoryBinders bindersFactory,
                               FactoryReviewViewAdapter adapterFactory) {
        this(node, converter, tagsManager, bindersFactory, adapterFactory, NUM_DATA);
    }

    protected ViewerReviewSummary(ReviewNode node,
                                  ConverterGv converter,
                                  TagsManager tagsManager,
                                  FactoryBinders bindersFactory,
                                  FactoryReviewViewAdapter adapterFactory,
                                  int numDataTypes) {
        super(node, TYPE);
        mBinder = bindersFactory.bindTo(node, this, null);
        mConverter = converter;
        mTagsManager = tagsManager;
        mAdapterFactory = adapterFactory;
        mNumDataTypes = numDataTypes;
        mNumDataMap = new HashMap<>();
    }

    protected FactoryReviewViewAdapter getAdapterFactory() {
        return mAdapterFactory;
    }

    protected ConverterGv getConverter() {
        return mConverter;
    }

    protected TagsManager getTagsManager() {
        return mTagsManager;
    }

    protected ReferenceBinder getBinder() {
        return mBinder;
    }

    protected List<GvDataType<?>> getCellOrder() {
        return ORDER;
    }

    protected void update(DataSize size, GvDataType<?> type, CallbackMessage message) {
        if (!message.isError()) mNumDataMap.put(type, size);
        if (mNumDataMap.size() == mNumDataTypes) mInitialising = false;
        if (!mInitialising) nullifyCache();
    }

    @Nullable
    protected ReviewViewAdapter<?> getExpansionAdapter(GvDataSize datum) {
        return mAdapterFactory.newDataAdapter(getReviewNode(), datum.getType());
    }

    @Override
    public boolean isExpandable(GvDataSize datum) {
        GvDataList<GvDataSize> cache = getCache();
        return cache != null && cache.contains(datum) && datum.getSize() > 0;
    }

    @Override
    public ReviewViewAdapter<?> expandGridCell(GvDataSize datum) {
        return isExpandable(datum) ? getExpansionAdapter(datum) : null;
    }

    @Override
    public void onNumComments(DataSize size, CallbackMessage message) {
        update(size, GvComment.TYPE, message);
    }

    @Override
    public void onNumCriteria(DataSize size, CallbackMessage message) {
        update(size, GvCriterion.TYPE, message);
    }

    @Override
    public void onNumFacts(DataSize size, CallbackMessage message) {
        update(size, GvFact.TYPE, message);
    }

    @Override
    public void onNumImages(DataSize size, CallbackMessage message) {
        update(size, GvImage.TYPE, message);
    }

    @Override
    public void onNumLocations(DataSize size, CallbackMessage message) {
        update(size, GvLocation.TYPE, message);
    }

    @Override
    public void onNumTags(DataSize size, CallbackMessage message) {
        update(size, GvTag.TYPE, message);
    }

    @Override
    public void onNumAuthors(DataSize size, CallbackMessage message) {

    }

    @Override
    public void onNumDates(DataSize size, CallbackMessage message) {

    }

    @Override
    public void onNumReviews(DataSize size, CallbackMessage message) {

    }

    @Override
    public void onNumSubjects(DataSize size, CallbackMessage message) {

    }

    @Override
    protected GvDataSizeList makeGridData() {
        GvReviewId id = new GvReviewId(mBinder.getReviewId());

        GvDataSizeList data = new GvDataSizeList(id);
        for (GvDataType<?> type : getCellOrder()) {
            DataSize dataSize = mNumDataMap.get(type);
            GvDataSize datum;
            if (dataSize != null) {
                datum = new GvDataSize(id, type, dataSize.getSize());
            } else {
                datum = new GvDataSize(id, type);
            }
            data.add(datum);
        }

        return data;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        mBinder.bindToNumTags();
        mBinder.bindToNumCriteria();
        mBinder.bindToNumImages();
        mBinder.bindToNumComments();
        mBinder.bindToNumLocations();
        mBinder.bindToNumFacts();
    }

    @Override
    protected void onDetach() {
        mBinder.unbindFromNumTags();
        mBinder.unbindFromNumCriteria();
        mBinder.unbindFromNumImages();
        mBinder.unbindFromNumComments();
        mBinder.unbindFromNumLocations();
        mBinder.unbindFromNumFacts();
        super.onDetach();
    }

    @Override
    public ReviewStamp getStamp() {
        ReviewNode node = getReviewNode();
        return ReviewStamp.newStamp(node.getAuthorId(), node.getPublishDate());
    }

    @Override
    public ReviewViewAdapter<?> expandGridData() {
        return null;
    }
}
