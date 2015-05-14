/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 14 May, 2015
 */

package com.chdryra.android.reviewer.Controller;

import android.content.Context;

import com.chdryra.android.reviewer.Model.ReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 14/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewViewAdapter {
    private FactoryReviewViewAdapter() {

    }

    public static ReviewViewAdapter newChildViewAdapter(Context context, ReviewNode node) {
        WrapperChildList wrapper = new WrapperChildList(node);
        GridDataExpander launcher = new ExpanderChildNode(context, wrapper);
        return new AdapterReviewNode(node, wrapper, launcher);
    }

    public static ReviewViewAdapter newDataViewAdapter(Context context, ReviewNode node) {
        return new AdapterReviewData(context, node);
    }
}
