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
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthorId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDate;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewRef;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSizeRef;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSubject;

import java.util.List;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 05/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerTreeSummary extends ViewerReviewSummary {
    private static final List<GvDataType<?>> ORDER = DataTypeCellOrder.MetaOrder.ORDER;

    public ViewerTreeSummary(ReviewNode node, FactoryReviewViewAdapter adapterFactory) {
        super(node, adapterFactory);
    }

    @Override
    public ReviewStamp getStamp() {
        return ReviewStamp.noStamp();
    }

    @Override
    protected List<GvDataType<?>> getCellOrder() {
        return ORDER;
    }

    @NonNull
    @Override
    protected Map<GvDataType<?>, GvSizeRef> getDataSizesMap(ReviewNode node) {
        Map<GvDataType<?>, GvSizeRef> map = super.getDataSizesMap(node);
        map.put(GvReviewRef.TYPE, new GvSizeRef(GvReviewRef.TYPE, node.getReviews().getSize()));
        map.put(GvAuthorId.TYPE, new GvSizeRef(GvAuthorId.TYPE, node.getAuthorIds().getSize()));
        map.put(GvSubject.TYPE, new GvSizeRef(GvSubject.TYPE, node.getSubjects().getSize()));
        map.put(GvDate.TYPE, new GvSizeRef(GvDate.TYPE, node.getDates().getSize()));

        return map;
    }

    @Nullable
    @Override
    protected ReviewViewAdapter<?> getExpansionAdapter(GvSizeRef datum) {
        FactoryReviewViewAdapter factory = getAdapterFactory();
        if(datum.getSizedType().equals(GvReviewRef.TYPE)) {
            return factory.newFlattenedReviewsListAdapter(getReviewNode());
        } else{
            return factory.newMetaDataAdapter(getReviewNode(), datum.getSizedType());
        }
    }
}
