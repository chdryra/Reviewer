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
import com.chdryra.android.reviewer.Model.TagsModel.TagsManager;
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
    private MdGvConverter mConverter;
    private TagsManager mTagsManager;
    private FactoryReviewViewAdapter mAdapterFactory;

    //Constructors
    public ViewerChildList(ReviewNode node,
                           MdGvConverter converter,
                           TagsManager tagsManager,
                           FactoryReviewViewAdapter adapterFactory) {
        mNode = node;
        mConverter = converter;
        mTagsManager = tagsManager;
        mAdapterFactory = adapterFactory;
    }

    private ReviewViewAdapter newNodeDataAdapter(ReviewNode node) {
        return mAdapterFactory.newNodeDataAdapter(node);
    }

    //Overridden
    @Override
    public GvReviewOverviewList getGridData() {
        return mConverter.convert(mNode.getChildren(), mNode.getId(), mTagsManager);
    }

    @Override
    public boolean isExpandable(GvReviewOverviewList.GvReviewOverview datum) {
        ReviewId id = ReviewId.fromString(datum.getId());
        return mNode.getChildren().containsId(id);
    }

    @Override
    public ReviewViewAdapter expandGridCell(GvReviewOverviewList.GvReviewOverview datum) {
        if (isExpandable(datum)) {
            ReviewId id = ReviewId.fromString(datum.getId());
            return newNodeDataAdapter(mNode.getChildren().get(id));
        } else {
            return null;
        }
    }

    @Override
    public ReviewViewAdapter expandGridData() {
        return newNodeDataAdapter(mNode);
    }
}
