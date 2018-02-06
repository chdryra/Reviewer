/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.startouch.Application.Implementation.DataTypeCellOrder;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.ReviewStamp;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .ConverterGv;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthorId;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDate;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSize;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSubject;

import java.util.List;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 05/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerTreeSummary extends ViewerReviewSummary {
    private static final List<GvDataType<?>> ORDER = DataTypeCellOrder.Meta.ORDER;

    public ViewerTreeSummary(ReviewNode node, FactoryReviewViewAdapter adapterFactory, ConverterGv converter) {
        super(node, adapterFactory, converter, ORDER);
    }

    @Override
    public ReviewStamp getStamp() {
        return ReviewStamp.noStamp();
    }

    @NonNull
    @Override
    protected Map<GvDataType<?>, GvSize.Reference> getDataSizesMap(ReviewNode node) {
        Map<GvDataType<?>, GvSize.Reference> map = super.getDataSizesMap(node);
        put(map, GvNode.TYPE, node.getReviews().getSize());
        put(map, GvAuthorId.TYPE, node.getAuthorIds().getSize());
        put(map, GvSubject.TYPE, node.getSubjects().getSize());
        put(map, GvDate.TYPE, node.getDates().getSize());

        return map;
    }

    @Nullable
    @Override
    protected ReviewViewAdapter<?> getExpansionAdapter(GvSize.Reference datum) {
        return getAdapterFactory().newTreeDataAdapter(getReviewNode(), datum.getSizedType());
    }

    @Override
    public ReviewViewAdapter<?> expandGridData() {
        return getAdapterFactory().newFlattenedReviewsListAdapter(getReviewNode());
    }
}
