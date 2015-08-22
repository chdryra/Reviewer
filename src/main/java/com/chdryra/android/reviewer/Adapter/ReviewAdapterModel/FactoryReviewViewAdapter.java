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
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataMap;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;

/**
 * Created by: Rizwan Choudrey
 * On: 14/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewViewAdapter {
    private FactoryReviewViewAdapter() {

    }

    public static ReviewViewAdapter<GvReviewOverviewList.GvReviewOverview>
    newChildOverviewAdapter(Context context,
                                                                              ReviewNode node) {
        ExpanderChildNode expander = new ExpanderChildNode(context, node);
        ViewerChildList wrapper = new ViewerChildList(node, expander);
        return new AdapterReviewNode<>(node, wrapper);
    }

    public static ReviewViewAdapter<GvData> newTreeDataAdapter(Context context,
                                                                         ReviewNode node) {
        ViewerTreeData wrapper = new ViewerTreeData(context, node);
        return new AdapterReviewNode<>(node, wrapper);
    }

    public static <T extends GvData> ReviewViewAdapter<? extends GvData> newGvDataCollectionAdapter(
            Context context, ReviewViewAdapter<? extends GvData> parent, GvDataCollection<T> data) {
        ExpanderGridCell expander = new ExpanderGridCell(context, parent);
        ViewerGvDataCollection<T> wrapper = new ViewerGvDataCollection<>(expander, data);
        return new AdapterReviewViewAdapter<>(context, parent, wrapper);
    }

    public static <T extends GvData> ReviewViewAdapter<? extends GvData> newGvDataCollectionAdapter(
            Context context, ReviewViewAdapter<? extends GvData> parent, GvDataMap<T, ? extends
            GvData> data) {
        ViewerGvDataMap<T> wrapper = new ViewerGvDataMap<>(context, parent, data);
        return new AdapterReviewViewAdapter<>(parent, wrapper, wrapper);
    }
}
