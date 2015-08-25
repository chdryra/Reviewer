/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 May, 2015
 */

package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.MdGvConverter;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;

/**
 * Created by: Rizwan Choudrey
 * On: 12/05/2015
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Grid data is {@link GvReviewOverviewList}.
 */
public class ViewerChildList implements GridDataViewer<GvReviewOverviewList.GvReviewOverview> {
    private ReviewNode mNode;

    public ViewerChildList(ReviewNode node) {
        mNode = node;
    }

    @Override
    public GvReviewOverviewList getGridData() {
        return MdGvConverter.convert(mNode.getChildren(), mNode.getId());
    }

    @Override
    public boolean isExpandable(GvReviewOverviewList.GvReviewOverview datum) {
        ReviewId id = ReviewId.fromString(datum.getId());
        return mNode.getChildren().containsId(id);
    }

    @Override
    public ReviewViewAdapter<? extends GvData> expandItem(GvReviewOverviewList.GvReviewOverview
                                                                      datum) {
        if (isExpandable(datum)) {
            ReviewNode node = mNode.getChildren().get(ReviewId.fromString(datum.getId()));
            return FactoryReviewViewAdapter.newTreeDataAdapter(node);
        } else {
            return null;
        }
    }
}
