/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 May, 2015
 */

package com.chdryra.android.reviewer.Model.Implementation.TreeMethods;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.IdableDataList;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataReviewIdable;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.TreeMethods.VisitorReviewDataGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 13/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VisitorReviewDataGetterImpl<T extends DataReviewIdable>
        implements VisitorReviewDataGetter<T> {
    private IdableList<T> mData;
    private NodeDataGetter<? extends T> mGetter;

    public VisitorReviewDataGetterImpl(NodeDataGetter<? extends T> getter) {
        mGetter = getter;
    }

    //public methods
    @Override
    public IdableList<T> getData() {
        return mData;
    }

    //Overridden
    @Override
    public void visit(ReviewNode node) {
        if(mData == null) mData = new IdableDataList<>(node.getReviewId());
        mData.addAll(mGetter.getData(node));
    }
}
