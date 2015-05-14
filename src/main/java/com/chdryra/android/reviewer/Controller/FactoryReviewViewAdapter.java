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

    public static ReviewViewAdapter newChildListAdapter(Context context, ReviewNode node) {
        WrapperChildList wrapper = new WrapperChildList(node);
        GridDataExpander expander = new ExpanderChildNode(context, wrapper);
        return new AdapterReviewNode(node, wrapper, expander);
    }

    public static ReviewViewAdapter newTreeDataAdapter(Context context, ReviewNode node) {
        GridDataWrapper wrapper = new WrapperTreeData(node);
        GridDataExpander expander = new ExpanderGridCell(context, new AdapterReviewNode(node,
                wrapper));
        return new AdapterReviewNode(node, wrapper, expander);
    }
}
