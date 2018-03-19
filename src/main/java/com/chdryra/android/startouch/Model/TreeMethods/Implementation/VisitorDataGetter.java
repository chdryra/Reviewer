/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Model.TreeMethods.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Model.TreeMethods.Interfaces.NodeDataGetter;
import com.chdryra.android.startouch.Model.TreeMethods.Interfaces.VisitorReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 05/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class VisitorDataGetter<T extends HasReviewId> implements VisitorReviewNode {
    private final NodeDataGetter<T> mGetter;
    private IdableList<T> mData;

    public VisitorDataGetter(NodeDataGetter<T> getter) {
        mGetter = getter;
    }

    public IdableList<T> getData() {
        return mData == null ? new IdableDataList<T>(null) : mData;
    }

    @Override
    public void visit(@NonNull ReviewNode node) {
        T data = mGetter.getData(node);
        if (data != null) getDataList(node).add(data);
    }

    private IdableList<T> getDataList(@NonNull ReviewNode node) {
        if (mData == null) mData = new IdableDataList<>(node.getReviewId());
        return mData;
    }
}
