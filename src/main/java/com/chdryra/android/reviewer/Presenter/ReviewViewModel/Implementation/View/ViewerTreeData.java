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
import com.chdryra.android.reviewer.DataDefinitions.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReferenceBinder;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .ConverterGv;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCanonicalCollection;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataAggregator;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataSize;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvReference;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReview;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewId;

import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 05/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerTreeData extends ViewerReviewData implements GvDataAggregator.NumReviewsCallback{
    private static final int NUM_DATA = 10;
    private GvDataAggregator mAggregator;

    public ViewerTreeData(ReferenceBinder binder,
                   ConverterGv converter,
                   TagsManager tagsManager,
                   FactoryReviewViewAdapter adapterFactory,
                   GvDataAggregator aggregator) {
        super(binder, converter, tagsManager, adapterFactory, NUM_DATA);
        mAggregator = aggregator;
        getNumberReviews();
    }

    @Override
    public void onNumReviews(DataSize size) {
        update(size, CallbackMessage.ok(), GvReference.TYPE);
    }

    private void getNumberReviews() {
        mAggregator.getNumReviews(getReviewNode(), this);
    }

    @Override
    protected GvList makeGridData() {
        ReviewNode node = getReviewNode();
        ReviewId id = node.getReviewId();
        IdableList<ReviewNode> nodes = node.getChildren();

        ConverterGv converter = getConverter();
        GvList data = new GvList(new GvReviewId(id));
        data.add(converter.toGvReviewList(nodes));
        data.add(mAggregator.aggregateAuthors(node));
        data.add(mAggregator.aggregateSubjects(node));
        data.add(mAggregator.aggregateDates(node));
        data.add(mAggregator.aggregateTags(node, getTagsManager()));
        data.add(mAggregator.aggregateCriteria(node));
        data.add(mAggregator.aggregateImages(node));
        data.add(mAggregator.aggregateComments(node));
        data.add(mAggregator.aggregateLocations(node));
        data.add(mAggregator.aggregateFacts(node));

        return data;
    }

    @Override
    public ReviewStamp getStamp() {
        return ReviewStamp.noStamp();
    }

    @Nullable
    @Override
    protected ReviewViewAdapter<?> getExpansionAdapter(GvDataSize datum) {
        return super.getExpansionAdapter(datum);
    }
}
