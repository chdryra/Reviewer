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
import com.chdryra.android.reviewer.View.GvDataModel.GvDataCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataMap;

/**
 * Created by: Rizwan Choudrey
 * On: 14/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewViewAdapter {
    private FactoryReviewViewAdapter() {

    }

    public static ReviewViewAdapter newChildOverviewAdapter(Context context, ReviewNode node) {
        ExpanderChildNode expander = new ExpanderChildNode(context, node);
        ViewerChildList wrapper = new ViewerChildList(node, expander);
        return new AdapterReviewNode(node, wrapper);
    }

    public static ReviewViewAdapter newTreeDataAdapter(Context context, ReviewNode node) {
        GridDataViewer wrapper = new ViewerTreeData(context, node);
        return new AdapterReviewNode(node, wrapper);
    }

    public static ReviewViewAdapter newGvDataCollectionAdapter(Context context, ReviewViewAdapter
            parent, GvDataCollection data) {
        GridDataViewer wrapper = new ViewerGvDataCollection(context, parent, data);
        return new AdapterReviewViewAdapter(context, parent, wrapper);
    }

    public static ReviewViewAdapter newGvDataCollectionAdapter(Context context, ReviewViewAdapter
            parent, GvDataMap data) {
        GridDataViewer wrapper = new ViewerGvDataMap(context, parent, data);
        return new AdapterReviewViewAdapter(parent, wrapper, wrapper);
    }
}
