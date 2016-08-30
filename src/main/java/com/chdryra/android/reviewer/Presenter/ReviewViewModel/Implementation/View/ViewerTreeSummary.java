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
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .ConverterGv;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthorId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDate;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSize;
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
        FactoryReviewViewAdapter factory = getAdapterFactory();
        if(datum.getSizedType().equals(GvNode.TYPE)) {
            return factory.newFlattenedReviewsListAdapter(getReviewNode());
        } else{
            return factory.newTreeDataAdapter(getReviewNode(), datum.getSizedType());
        }
    }
}
