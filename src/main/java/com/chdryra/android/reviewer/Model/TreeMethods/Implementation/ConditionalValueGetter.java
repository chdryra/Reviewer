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

import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.NodeValueGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 13/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ConditionalValueGetter<T> implements NodeValueGetter<T> {
    private final Condition mCondition;
    private final NodeValueGetter<T> mMethod;

    public interface Condition {
        boolean isTrue(ReviewNode node);
    }

    public ConditionalValueGetter(Condition condition, NodeValueGetter<T> method) {
        mCondition = condition;
        mMethod = method;
    }

    @Override
    @Nullable
    public T getData(@NonNull ReviewNode node) {
        return mCondition.isTrue(node) ? mMethod.getData(node) : null;
    }
}
