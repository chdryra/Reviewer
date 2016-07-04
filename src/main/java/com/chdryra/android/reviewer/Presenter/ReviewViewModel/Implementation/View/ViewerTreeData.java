/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.Application.DataTypeCellOrder;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MetaBinder;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.ConverterGv;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataAggregator;
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
public class ViewerTreeData extends ViewerReviewData implements
        MetaBinder.MetaDataSizeBinder{
    private static final List<GvDataType<?>> ORDER = DataTypeCellOrder.MetaOrder.ORDER;
    private static final int NUM_DATA = ORDER.size();
    private GvDataAggregator mAggregator;
    private MetaBinder mBinder;

    public ViewerTreeData(MetaBinder binder,
                   ConverterGv converter,
                   TagsManager tagsManager,
                   FactoryReviewViewAdapter adapterFactory,
                   GvDataAggregator aggregator) {
        super(binder, converter, tagsManager, adapterFactory, NUM_DATA);
        mBinder = binder;
        mAggregator = aggregator;
        mBinder.registerSizeBinder(this);
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
}
