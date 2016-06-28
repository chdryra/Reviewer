/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
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
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 05/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerReviewData extends ViewerNodeBasic<GvDataSize> implements ReferenceBinder
        .DataSizeBinder {
    private static final GvDataType<GvDataSize> TYPE = GvDataSize.TYPE;
    private static final int NUM = 6;

    private ConverterGv mConverter;
    private TagsManager mTagsManager;
    private FactoryReviewViewAdapter mAdapterFactory;
    private ReferenceBinder mBinder;

    private Map<GvDataType<?>, DataSize> mNumDataMap;
    private boolean mInitialising = true;

    public ViewerReviewData(ReferenceBinder binder,
                            ConverterGv converter,
                            TagsManager tagsManager,
                            FactoryReviewViewAdapter adapterFactory) {
        super(binder.getReference().asNode(), TYPE);
        mBinder = binder;
        mConverter = converter;
        mTagsManager = tagsManager;
        mAdapterFactory = adapterFactory;
        mNumDataMap = new HashMap<>();
        mBinder.registerSizeBinder(this);
    }

    @Override
    public boolean isExpandable(GvDataSize datum) {
        GvDataList<GvDataSize> cache = getCache();
        return datum.hasElements() && cache != null && cache.contains(datum);
    }

    @Override
    public ReviewViewAdapter<?> expandGridCell(GvDataSize datum) {
        if (isExpandable(datum)) {
            return mAdapterFactory.newDataAdapter(getReviewNode(), datum.getGvDataType());
        } else {
            return null;
        }
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

    @Override
    public void onNumComments(DataSize size, CallbackMessage message) {
        update(size, message, GvComment.TYPE);
    }

    @Override
    public void onNumCriteria(DataSize size, CallbackMessage message) {
        update(size, message, GvCriterion.TYPE);
    }

    @Override
    public void onNumFacts(DataSize size, CallbackMessage message) {
        update(size, message, GvFact.TYPE);
    }

    @Override
    public void onNumImages(DataSize size, CallbackMessage message) {
        update(size, message, GvImage.TYPE);
    }

    @Override
    public void onNumLocations(DataSize size, CallbackMessage message) {
        update(size, message, GvLocation.TYPE);
    }

    @Override
    public void onNumTags(DataSize size, CallbackMessage message) {
        update(size, message, GvTag.TYPE);
    }

    @Override
    protected GvDataSizeList makeGridData() {
        GvReviewId id = new GvReviewId(mBinder.getReviewId());

        GvDataSizeList data = new GvDataSizeList(id);
        for(Map.Entry<GvDataType<?>, DataSize> entry : mNumDataMap.entrySet()) {
            data.add(new GvDataSize(id, entry.getKey(), entry.getValue().getSize()));
        }

        return data;
    }

    @Override
    public ReviewStamp getStamp() {
        ReviewNode node = getReviewNode();
        return ReviewStamp.newStamp(node.getAuthor(), node.getPublishDate());
    }

    @Override
    public ReviewViewAdapter<?> expandGridData() {
        return null;
    }

    private void update(DataSize size, CallbackMessage message, GvDataType<?> type) {
        if (!message.isError()) mNumDataMap.put(type, size);
        if (mNumDataMap.size() == NUM) mInitialising = false;
        if (!mInitialising) nullifyCache();
    }
}
