/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.TreeMethods.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.NodeDataGetter;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 05/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class VisitorDataGetter<T extends HasReviewId> implements VisitorReviewNode {
    private NodeDataGetter<T> mGetter;
    private IdableList<T> mData;

    public VisitorDataGetter(NodeDataGetter<T> getter) {
        mGetter = getter;
    }

    @Override
    public void visit(@NonNull ReviewNode node) {
        T data = mGetter.getData(node);
        if(data != null) getDataList(node).add(data);
    }

    public IdableList<T> getData() {
        return mData == null ? new IdableDataList<T>(null) : mData;
    }

    protected IdableList<T> getDataList(@NonNull ReviewNode node) {
        if (mData == null) mData = new IdableDataList<>(node.getReviewId());
        return mData;
    }
}
