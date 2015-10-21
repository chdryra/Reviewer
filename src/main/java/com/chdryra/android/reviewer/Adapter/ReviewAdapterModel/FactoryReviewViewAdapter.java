/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 14 May, 2015
 */

package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import android.content.Context;

import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsRepository;
import com.chdryra.android.reviewer.View.GvDataModel.GvCanonical;
import com.chdryra.android.reviewer.View.GvDataModel.GvCanonicalCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.Screens.ChildListScreen;

/**
 * Created by: Rizwan Choudrey
 * On: 14/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewViewAdapter {
    private FactoryReviewViewAdapter() {

    }

    //Static methods
    public static ReviewViewAdapter newReviewsListAdapter(Context context, ReviewNode node,
                                                          ReviewsRepository repository) {
        return ChildListScreen.newScreen(context, node, repository).getAdapter();
    }

    public static ReviewViewAdapter newNodeDataAdapter(Context context, ReviewNode node,
                                                       ReviewsRepository repository) {
        return newAdapterReviewNode(node, new ViewerNodeData(context, node, repository));
    }

    public static <T extends GvData> ReviewViewAdapter newDataToDataAdapter(
            Context context, ReviewNode parent, GvDataCollection<T> data,
            ReviewsRepository repository) {
        return newAdapterReviewNode(parent, new ViewerDataToData<>(context, parent, data,
                repository));
    }

    //TODO make type safe
    public static <T extends GvData> ReviewViewAdapter newAggregateToReviewsAdapter(
            Context context, GvCanonicalCollection<T> data, ReviewsRepository repository,
            String subject) {
        if (data.getGvDataType().equals(GvCommentList.GvComment.TYPE)) {
            ReviewNode node = repository.createMetaReview(data, subject);
            return new AdapterCommentsAggregate(context, node,
                    (GvCanonicalCollection<GvCommentList.GvComment>) data, repository);
        }

        GridDataViewer<GvCanonical> viewer;
        if (data.getGvDataType().equals(GvCriterionList.GvCriterion.TYPE)) {
            viewer = new ViewerAggregateCriteria(context,
                    (GvCanonicalCollection<GvCriterionList.GvCriterion>) data, repository);
        } else if (data.getGvDataType().equals(GvFactList.GvFact.TYPE) ||
                data.getGvDataType().equals(GvImageList.GvImage.TYPE)) {
            viewer = new ViewerAggregateToData<>(context, data, repository);
        } else {
            viewer = new ViewerDataToReviews<>(context, data, repository);
        }

        return newMetaReviewAdapter(data, repository, subject, viewer);
    }

    public static <T extends GvData> ReviewViewAdapter newDataToReviewsAdapter(
            Context context, GvDataCollection<T> data, ReviewsRepository repository, String
            subject) {
        ViewerDataToReviews<T> viewer = new ViewerDataToReviews<>(context, data, repository);
        return newMetaReviewAdapter(data, repository, subject, viewer);
    }

    private static <T extends GvData> ReviewViewAdapter newAdapterReviewNode(ReviewNode node,
                                                                             GridDataViewer<T>
                                                                                     viewer) {
        return new AdapterReviewNode<>(node, viewer);
    }

    private static <T extends GvData> ReviewViewAdapter newMetaReviewAdapter(
            GvDataCollection<T> data, ReviewsRepository repository, String subject,
            GridDataViewer<T> viewer) {
        ReviewNode node = repository.createMetaReview(data, subject);
        return newAdapterReviewNode(node, viewer);
    }
}
