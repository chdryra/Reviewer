/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 14 May, 2015
 */

package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import android.content.Context;

import com.chdryra.android.reviewer.ApplicationSingletons.ReviewMaker;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataCollection;
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
    newChildListAdapter(Context context, ReviewNode node) {
        return new AdapterReviewNode<>(node, new ViewerChildList(context, node));
    }

    public static ReviewViewAdapter<GvData> newTreeDataAdapter(Context context, ReviewNode node) {
        return new AdapterReviewNode<>(node, new ViewerTreeData(context, node));
    }

    public static ReviewViewAdapter<? extends GvData> newExpandToDataAdapter(
            ReviewViewAdapter<? extends GvData> parent, GvDataCollection data) {
        return newGvDataCollectionAdapter(parent, data, new ExpanderToData(parent));
    }

    public static <T extends GvData> ReviewViewAdapter<? extends GvData> newExpandToReviewsAdapter(
            Context context, GvDataCollection<T> data, String subject) {
        ExpanderToReviews<T> expander = new ExpanderToReviews<>(context, data);
        ReviewNode node = ReviewMaker.createMetaReview(context, data, subject);
        ViewerGvDataCollection<T> wrapper = new ViewerGvDataCollection<>(expander, data);
        return new AdapterReviewNode<>(node, wrapper);
    }

    private static <T extends GvData> ReviewViewAdapter<? extends GvData>
    newGvDataCollectionAdapter(
            ReviewViewAdapter<? extends GvData> parent, GvDataCollection<T> data,
            GridCellExpander<T> expander) {
        ViewerGvDataCollection<T> wrapper = new ViewerGvDataCollection<>(expander, data);
        return new AdapterReviewViewAdapter<>(parent, wrapper, expander);
    }
}
