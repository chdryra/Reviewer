/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 14 May, 2015
 */

package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import android.content.Context;

import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 14/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewViewAdapter {
    private FactoryReviewViewAdapter() {

    }

    public static ReviewViewAdapter newChildListAdapter(Context context, ReviewNode node) {
        ViewerChildList wrapper = new ViewerChildList(node);
        GridDataExpander expander = new ExpanderChildNode(context, node);
        return new AdapterReviewNode(node, wrapper, expander);
    }

    public static ReviewViewAdapter newTreeDataAdapter(Context context, ReviewNode node) {
        GridDataViewer wrapper = new ViewerTreeData(node);
        ReviewViewAdapter parent = new AdapterReviewNode(node, wrapper);
        GridDataExpander expander = new ExpanderGridCell(context, parent);
        return new AdapterReviewNode(node, wrapper, expander);
    }
}
