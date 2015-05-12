/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 May, 2015
 */

package com.chdryra.android.reviewer.Controller;

import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.View.GvDataList;
import com.chdryra.android.reviewer.View.GvDataType;
import com.chdryra.android.reviewer.View.GvReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 12/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class NodeDataWrapper implements GridDataWrapper {
    private GvReviewNode mData;

    public NodeDataWrapper(ReviewNode node) {
        mData = new GvReviewNode(node);
    }

    @Override
    public GvDataList getGridData() {
        return mData;
    }

    private static class GvReviewNode extends GvDataList<GvDataList> {
        private static final GvDataType TYPE = new GvDataType("ReviewData", "ReviewData");

        private GvReviewNode(ReviewNode node) {
            super(new GvReviewId(node.getId()), GvDataList.class, TYPE);
            add(MdGvConverter.convertTags(node.getId()));
            add(MdGvConverter.convertChildren(node));
            add(MdGvConverter.convert(node.getImages()));
            add(MdGvConverter.convert(node.getComments()));
            add(MdGvConverter.convert(node.getLocations()));
            add(MdGvConverter.convert(node.getFacts()));
        }

        @Override
        public void sort() {
        }
    }
}
