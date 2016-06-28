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
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableCollection;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.NodeDataGetter;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorDataGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 13/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VisitorDataGetterImpl<T extends HasReviewId>
        implements VisitorDataGetter<T> {
    private IdableList<T> mData;
    private NodeDataGetter<? extends T> mGetter;

    public VisitorDataGetterImpl(NodeDataGetter<? extends T> getter) {
        mGetter = getter;
    }

    @Override
    public IdableCollection<T> getData() {
        return mData == null ? new IdableDataList<T>(null) : mData;
    }

    @Override
    public void visit(@NonNull ReviewNode node) {
        if (mData == null) mData = new IdableDataList<>(node.getReviewId());
        mData.addAll(mGetter.getData(node));
    }
}
