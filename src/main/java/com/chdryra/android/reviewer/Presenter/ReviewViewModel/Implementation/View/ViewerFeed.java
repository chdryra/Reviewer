/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNodeList;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by: Rizwan Choudrey
 * On: 09/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerFeed extends ViewerChildList {
    public ViewerFeed(ReviewNode node, DataConverter<ReviewNode, GvNode, GvNodeList> converter,
                      FactoryReviewViewAdapter adapterFactory) {
        super(node, converter, adapterFactory);
    }

    @Override
    public ReviewViewAdapter<?> expandGridCell(GvNode datum) {
        ReviewNode child = getReviewNode().getChild(datum.getReviewId());
        return child != null ? getAdapterFactory().newReviewsListAdapter(child.getAuthorId()) : null;
    }

    @Override
    public ReviewViewAdapter<?> expandGridData() {
        Set<AuthorId> authors = new HashSet<>();
        ReviewNode node = getReviewNode();
        for (ReviewNode child : node.getChildren()) {
            authors.add(child.getAuthorId());
        }

        return getAdapterFactory().newTreeSummaryAdapter(node.getAuthorId(), authors);
    }
}
