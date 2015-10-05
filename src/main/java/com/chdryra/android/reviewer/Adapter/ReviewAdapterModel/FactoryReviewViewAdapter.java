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
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Model.TagsModel.TagsManager;
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
    newChildListAdapter(Context context, ReviewNode node, TagsManager tagsManager) {
        return new AdapterReviewNode<>(node, new ViewerChildList(context, node, tagsManager));
    }

    public static ReviewViewAdapter<GvData> newTreeDataAdapter(Context context, ReviewNode node,
                                                               TagsManager tagsManager) {
        return new AdapterReviewNode<>(node, new ViewerTreeData(context, node, tagsManager));
    }

    public static <T extends GvData> ReviewViewAdapter newExpandToDataAdapter(
            Context context, ReviewViewAdapter parent, GvDataCollection<T> data) {
        return newGvDataCollectionAdapter(parent, data, new ExpanderToData<T>(context, parent));
    }

    public static <T extends GvData> ReviewViewAdapter newExpandToReviewsAdapterForCanonical(
            Context context, GvCanonicalCollection<T> data, String subject) {
        if (data.getGvDataType() == GvCommentList.GvComment.TYPE) {
            //TODO make type safe
            ReviewNode node = getRepository(context).createMetaReview(data, subject);
            return new AdapterCommentsAggregate(context, node,
                    (GvCanonicalCollection<GvCommentList.GvComment>) data);
        }

        ExpanderToReviews<GvCanonical> expander;
        if (data.getGvDataType() == GvCriterionList.GvCriterion.TYPE) {
            expander = new ExpanderCriteria(context);
        } else if(data.getGvDataType() == GvFactList.GvFact.TYPE) {
            expander = new ExpanderFacts(context);
        } else if(data.getGvDataType() == GvImageList.GvImage.TYPE) {
            expander = new ExpanderImages(context);
        } else {
            expander = new ExpanderToReviews<>(context);
        }

        ViewerGvDataCollection<GvCanonical> viewer = new ViewerGvDataCollection<>(expander, data);
        return newExpandToReviewsAdapter(context, data, subject, viewer);
    }

    public static <T extends GvData> ReviewViewAdapter newExpandToReviewsAdapter(
            Context context, GvDataCollection<T> data, String subject) {
        ExpanderToReviews<T> expander = new ExpanderToReviews<>(context);
        ViewerGvDataCollection<T> viewer = new ViewerGvDataCollection<>(expander, data);
        return newExpandToReviewsAdapter(context, data, subject, viewer);
    }

    private static <T extends GvData> ReviewViewAdapter newExpandToReviewsAdapter(
            Context context, GvDataCollection<T> data, String subject,
            ViewerGvDataCollection<T> viewer) {
        ReviewNode node = getRepository(context).createMetaReview(data, subject);
        return new AdapterReviewNode<>(node, viewer);
    }

    private static <T extends GvData> ReviewViewAdapter
    newGvDataCollectionAdapter(ReviewViewAdapter parent, GvDataCollection<T> data,
                               GridDataExpander<T> expander) {
        ViewerGvDataCollection<T> wrapper = new ViewerGvDataCollection<>(expander, data);
        return new AdapterReviewViewAdapter<>(parent, wrapper);
    }

    private static ReviewsRepository getRepository(Context context) {
        return Administrator.get(context).getReviewsRepository();
    }
}
