/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.TreeMethods.Implementation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.NodeDataGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 13/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ConditionalDataGetter<T extends HasReviewId> implements NodeDataGetter<T> {
    private Condition mCondition;
    private NodeDataGetter<T> mMethod;

    public interface Condition {
        boolean passesOnNode(ReviewNode node);
    }

    public ConditionalDataGetter(Condition condition, NodeDataGetter<T> method) {
        mCondition = condition;
        mMethod = method;
    }

    @Override
    @Nullable
    public T getData(@NonNull ReviewNode node) {
        return mCondition.passesOnNode(node) ? mMethod.getData(node) : null;
    }
}
