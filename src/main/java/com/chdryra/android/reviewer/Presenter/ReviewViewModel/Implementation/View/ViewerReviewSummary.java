/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Application.Implementation.DataTypeCellOrder;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.ConverterGv;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataRefList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSize;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 05/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerReviewSummary extends ViewerNodeBasic<GvSize.Reference> {
    private static final GvDataType<GvSize.Reference> TYPE = GvSize.Reference.TYPE;

    private final FactoryReviewViewAdapter mAdapterFactory;
    private final ConverterGv mConverter;
    private List<GvDataType<?>> mCellOrder;

    ViewerReviewSummary(ReviewNode node,
                        FactoryReviewViewAdapter adapterFactory,
                        ConverterGv converter,
                        List<GvDataType<?>> cellOrder) {
        this(node, adapterFactory, converter);
        mCellOrder = cellOrder;
    }

    public ViewerReviewSummary(ReviewNode node,
                               FactoryReviewViewAdapter adapterFactory,
                               ConverterGv converter) {
        super(node, TYPE);
        mAdapterFactory = adapterFactory;
        mConverter = converter;
        mCellOrder = new ArrayList<>();
        mCellOrder.addAll(DataTypeCellOrder.ReviewOrder.ORDER);
    }

    FactoryReviewViewAdapter getAdapterFactory() {
        return mAdapterFactory;
    }

    protected ConverterGv getConverter() {
        return mConverter;
    }

    @Nullable
    ReviewViewAdapter<?> getExpansionAdapter(GvSize.Reference datum) {
        return mAdapterFactory.newReviewDataAdapter(getReviewNode(), datum.getSizedType());
    }

    @NonNull
    Map<GvDataType<?>, GvSize.Reference> getDataSizesMap(ReviewNode node) {
        Map<GvDataType<?>, GvSize.Reference> map = new HashMap<>();
        put(map, GvTag.TYPE, node.getTags().getSize());
        put(map, GvCriterion.TYPE, node.getCriteria().getSize());
        put(map, GvImage.TYPE, node.getImages().getSize());
        put(map, GvComment.TYPE, node.getComments().getSize());
        put(map, GvLocation.TYPE, node.getLocations().getSize());
        put(map, GvFact.TYPE, node.getFacts().getSize());

        return map;
    }

    void put(Map<GvDataType<?>, GvSize.Reference> map, GvDataType<?> type,
             ReviewItemReference<DataSize> size) {
        map.put(type, new GvSize.Reference(size, mConverter.newConverterSizes(type)));
    }

    @Override
    public boolean isExpandable(GvSize.Reference datum) {
        GvDataList<GvSize.Reference> cache = getCache();
        return cache != null && cache.contains(datum) && datum.getDataValue() != null
                && datum.getDataValue().getSize() > 0;
    }

    @Override
    public ReviewViewAdapter<?> expandGridCell(GvSize.Reference datum) {
        return isExpandable(datum) ? getExpansionAdapter(datum) : null;
    }

    @Override
    protected GvDataRefList<GvSize.Reference> makeGridData() {
        ReviewNode node = getReviewNode();
        GvReviewId id = new GvReviewId(getReviewNode().getReviewId());

        Map<GvDataType<?>, GvSize.Reference> order = getDataSizesMap(node);

        GvDataRefList<GvSize.Reference> data = new GvDataRefList<>(GvSize.Reference.TYPE, id);
        for (GvDataType<?> type : mCellOrder) {
            GvSize.Reference item = order.get(type);
            data.add(item);
        }

        return data;
    }

    @Override
    public void onNodeChanged() {
        notifyDataObservers();
        //Cache is a bunch of references all bound to node anyway so don't need to nullify.
    }

    @Override
    protected void onNullifyCache() {
        GvDataRefList<GvSize.Reference> cache = (GvDataRefList<GvSize.Reference>) getCache();
        if(cache != null) cache.unbind();
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
