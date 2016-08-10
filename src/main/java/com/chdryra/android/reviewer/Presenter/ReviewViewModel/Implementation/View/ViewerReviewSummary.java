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

import com.chdryra.android.reviewer.Application.DataTypeCellOrder;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataRefList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSizeRef;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 05/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerReviewSummary extends ViewerNodeBasic<GvSizeRef> {
    private static final GvDataType<GvSizeRef> TYPE = GvSizeRef.TYPE;
    private static final List<GvDataType<?>> ORDER = DataTypeCellOrder.ReviewOrder.ORDER;

    private FactoryReviewViewAdapter mAdapterFactory;

    public ViewerReviewSummary(ReviewNode node, FactoryReviewViewAdapter adapterFactory) {
        super(node, TYPE);
        mAdapterFactory = adapterFactory;
    }

    protected FactoryReviewViewAdapter getAdapterFactory() {
        return mAdapterFactory;
    }

    protected List<GvDataType<?>> getCellOrder() {
        return ORDER;
    }

    @Nullable
    protected ReviewViewAdapter<?> getExpansionAdapter(GvSizeRef datum) {
        return mAdapterFactory.newDataAdapter(getReviewNode(), datum.getSizedType());
    }

    @NonNull
    protected Map<GvDataType<?>, GvSizeRef> getDataSizesMap(ReviewNode node) {
        Map<GvDataType<?>, GvSizeRef> map = new HashMap<>();
        map.put(GvTag.TYPE, new GvSizeRef(GvTag.TYPE, node.getTags().getSize()));
        map.put(GvCriterion.TYPE, new GvSizeRef(GvCriterion.TYPE, node.getCriteria().getSize()));
        map.put(GvImage.TYPE, new GvSizeRef(GvImage.TYPE, node.getImages().getSize()));
        map.put(GvComment.TYPE, new GvSizeRef(GvComment.TYPE, node.getComments().getSize()));
        map.put(GvLocation.TYPE, new GvSizeRef(GvLocation.TYPE, node.getLocations().getSize()));
        map.put(GvFact.TYPE, new GvSizeRef(GvFact.TYPE, node.getFacts().getSize()));

        return map;
    }

    @Override
    public boolean isExpandable(GvSizeRef datum) {
        GvDataList<GvSizeRef> cache = getCache();
        return cache != null && cache.contains(datum) && datum.getDataValue() != null
                && datum.getDataValue().getSize() > 0;
    }

    @Override
    public ReviewViewAdapter<?> expandGridCell(GvSizeRef datum) {
        return isExpandable(datum) ? getExpansionAdapter(datum) : null;
    }

    @Override
    protected GvDataRefList<GvSizeRef> makeGridData() {
        ReviewNode node = getReviewNode();
        GvReviewId id = new GvReviewId(getReviewNode().getReviewId());

        Map<GvDataType<?>, GvSizeRef> order = getDataSizesMap(node);

        GvDataRefList<GvSizeRef> data = new GvDataRefList<>(GvSizeRef.TYPE, id);
        for (GvDataType<?> type : getCellOrder()) {
            data.add(order.get(type));
        }

        return data;
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
