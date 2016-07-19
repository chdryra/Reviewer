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
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.ConverterGv;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataAggregator;
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
public class ViewerTreeData extends ViewerReviewData {
    private static final List<GvDataType<?>> ORDER = DataTypeCellOrder.MetaOrder.ORDER;
    private static final int NUM_DATA = ORDER.size();
    private GvDataAggregator mAggregator;

    public ViewerTreeData(ReviewNode node,
                          FactoryBinders bindersFactory,
                          ConverterGv converter,
                          TagsManager tagsManager,
                          FactoryReviewViewAdapter adapterFactory,
                          GvDataAggregator aggregator) {
        super(node, bindersFactory, converter, tagsManager, adapterFactory, NUM_DATA);
        mAggregator = aggregator;
    }

    @Override
    public void onNumAuthors(DataSize size, CallbackMessage message) {
        update(size, GvAuthor.TYPE, CallbackMessage.ok());
    }

    @Override
    public void onNumDates(DataSize size, CallbackMessage message) {
        update(size, GvDate.TYPE, CallbackMessage.ok());
    }

    @Override
    public void onNumReviews(DataSize size, CallbackMessage message) {
        update(size, GvReference.TYPE, CallbackMessage.ok());
    }

    @Override
    public void onNumSubjects(DataSize size, CallbackMessage message) {
        update(size, GvSubject.TYPE, CallbackMessage.ok());
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
        getBinder().bindToNumReviews();
        getBinder().bindToNumAuthors();
        getBinder().bindToNumSubjects();
        getBinder().bindToNumDates();
    }

    @Override
    protected void onDetach() {
        getBinder().unbindFromNumReviews();
        getBinder().unbindFromNumAuthors();
        getBinder().unbindFromNumSubjects();
        getBinder().unbindFromNumDates();
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
