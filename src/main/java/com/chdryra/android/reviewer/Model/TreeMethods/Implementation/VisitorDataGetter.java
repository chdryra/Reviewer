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
 * On: 05/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class VisitorDataGetter<T extends HasReviewId> implements VisitorReviewNode {
    private IdableList<T> mData;

    @Override
    public abstract void visit(@NonNull ReviewNode node);

    public IdableList<T> getData() {
        return mData == null ? new IdableDataList<T>(null) : mData;
    }

    protected IdableList<T> getDataList(@NonNull ReviewNode node) {
        if (mData == null) mData = new IdableDataList<>(node.getReviewId());
        return mData;
    }

    public static class ItemGetter<T extends HasReviewId> extends VisitorDataGetter<T> {
        private NodeDataGetter<T> mGetter;

        public ItemGetter(NodeDataGetter<T> getter) {
            mGetter = getter;
        }

        @Override
        public void visit(@NonNull ReviewNode node) {
            T data = mGetter.getData(node);
            if(data != null) getDataList(node).add(data);
        }
    }
}
