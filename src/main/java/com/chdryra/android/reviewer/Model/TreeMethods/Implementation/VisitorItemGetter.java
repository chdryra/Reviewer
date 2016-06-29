/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.TreeMethods.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.NodeDataGetter;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 13/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VisitorItemGetter<T extends HasReviewId> implements VisitorReviewNode{
    private IdableList<T> mData;
    private NodeDataGetter<? extends T> mGetter;

    public VisitorItemGetter(NodeDataGetter<? extends T> getter) {
        mGetter = getter;
    }

    public IdableList<T> getData() {
        return mData == null ? new IdableDataList<T>(null) : mData;
    }

    @Override
    public void visit(@NonNull ReviewNode node) {
        if (mData == null) mData = new IdableDataList<>(node.getReviewId());
        T data = mGetter.getData(node);
        if(data != null) mData.add(data);
    }
}
