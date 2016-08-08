/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Application.DataTypeCellOrder;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.ConverterGv;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataSize;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDate;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReference;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSubject;

import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 05/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerTreeSummary extends ViewerReviewSummary {
    private static final List<GvDataType<?>> ORDER = DataTypeCellOrder.MetaOrder.ORDER;
    private static final int NUM_DATA = ORDER.size();

    public ViewerTreeSummary(ReviewNode node,
                             ConverterGv converter,
                             TagsManager tagsManager,
                             FactoryReviewViewAdapter adapterFactory) {
        super(node, converter, tagsManager, adapterFactory, NUM_DATA);
    }

    @Override
    public ReviewStamp getStamp() {
        return ReviewStamp.noStamp();
    }

    @Override
    protected List<GvDataType<?>> getCellOrder() {
        return ORDER;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        ReviewNode node = getReviewNode();
        node.getReviews().getSize().bindToValue(getBinder(GvReference.TYPE));
        node.getAuthorIds().getSize().bindToValue(getBinder(GvAuthor.TYPE));
        node.getSubjects().getSize().bindToValue(getBinder(GvSubject.TYPE));
        node.getDates().getSize().bindToValue(getBinder(GvDate.TYPE));
    }

    @Override
    protected void onDetach() {
        ReviewNode node = getReviewNode();
        node.getReviews().getSize().unbindFromValue(getBinder(GvReference.TYPE));
        node.getAuthorIds().getSize().unbindFromValue(getBinder(GvAuthor.TYPE));
        node.getSubjects().getSize().unbindFromValue(getBinder(GvSubject.TYPE));
        node.getDates().getSize().unbindFromValue(getBinder(GvDate.TYPE));
        super.onDetach();
    }

    @Nullable
    @Override
    protected ReviewViewAdapter<?> getExpansionAdapter(GvDataSize datum) {
        FactoryReviewViewAdapter factory = getAdapterFactory();
        if(datum.getType().equals(GvReference.TYPE)) {
            return factory.newFlattenedReviewsListAdapter(getReviewNode());
        } else{
            return factory.newMetaDataAdapter(getReviewNode(), datum.getType());
        }
    }
}
