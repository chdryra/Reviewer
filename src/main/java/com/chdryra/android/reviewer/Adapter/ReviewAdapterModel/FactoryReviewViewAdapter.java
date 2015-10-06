/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 14 May, 2015
 */

package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import android.content.Context;

import com.chdryra.android.reviewer.ApplicationSingletons.Administrator;
import com.chdryra.android.reviewer.Model.ReviewData.IdableList;
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
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;

/**
 * Created by: Rizwan Choudrey
 * On: 14/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewViewAdapter {
    private FactoryReviewViewAdapter() {

    }

    public static AdapterReviewNode<GvReviewOverviewList.GvReviewOverview>
    newChildListAdapter(Context context, ReviewNode node, ReviewsRepository repository) {
        return new AdapterReviewNode<>(node, new ViewerChildList(context, node, repository));
    }

    public static ReviewViewAdapter<GvData> newTreeDataAdapter(Context context, ReviewNode node,
                                                               ReviewsRepository repository) {
        IdableList<ReviewNode> children = node.getChildren();

        GridDataViewer<GvData> viewer;
        if(children.size() > 1) {
            viewer = new ViewerTreeData(context, node, repository);
        } else {
            ReviewNode toExpand = children.size() == 0 ? node : children.getItem(0);
            viewer = new ViewerTreeData(context, toExpand.expand(), repository);
        }

        return new AdapterReviewNode<>(node, viewer);
    }

    public static <T extends GvData> ReviewViewAdapter newExpandToDataAdapter(
            Context context, ReviewNode parent, GvDataCollection<T> data, ReviewsRepository repository) {
        GridDataViewer<T> viewer = new ViewerToData<>(context, parent, data, repository);
        return new AdapterReviewNode<>(parent, viewer);
    }

    //TODO make type safe
    public static <T extends GvData> ReviewViewAdapter newExpandToReviewsAdapterForAggregate(
            Context context, GvCanonicalCollection<T> data, ReviewsRepository repository, String subject) {
        if (data.getGvDataType() == GvCommentList.GvComment.TYPE) {
            ReviewNode node = getRepository(context).createMetaReview(data, subject);
            return new AdapterCommentsAggregate(context, node,
                    (GvCanonicalCollection<GvCommentList.GvComment>) data, repository);
        }

        ViewerToReviews<GvCanonical> viewer;
        if (data.getGvDataType() == GvCriterionList.GvCriterion.TYPE) {
            viewer = new ViewerCriteria(context,
                    (GvCanonicalCollection<GvCriterionList.GvCriterion>) data, repository);
        } else if(data.getGvDataType() == GvFactList.GvFact.TYPE) {
            viewer = new ViewerFacts(context,
                    (GvCanonicalCollection<GvFactList.GvFact>) data, repository);
        } else if(data.getGvDataType() == GvImageList.GvImage.TYPE) {
            viewer = new ViewerImages(context,
                    (GvCanonicalCollection<GvImageList.GvImage>) data, repository);
        } else {
            viewer = new ViewerToReviews<>(context, data, repository);
        }

        return newExpandToReviewsAdapter(context, data, subject, viewer);
    }

    public static <T extends GvData> ReviewViewAdapter newExpandToReviewsAdapter(
            Context context, GvDataCollection<T> data, ReviewsRepository repository, String subject) {
        ViewerToReviews<T> viewer = new ViewerToReviews<>(context, data, repository);
        return newExpandToReviewsAdapter(context, data, subject, viewer);
    }

    private static <T extends GvData> ReviewViewAdapter newExpandToReviewsAdapter(
            Context context, GvDataCollection<T> data, String subject,
            GridDataViewer<T> viewer) {
        ReviewNode node = getRepository(context).createMetaReview(data, subject);
        return new AdapterReviewNode<>(node, viewer);
    }

    private static ReviewsRepository getRepository(Context context) {
        return Administrator.get(context).getReviewsRepository();
    }
}
