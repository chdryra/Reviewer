/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 May, 2015
 */

package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataCollection;

/**
 * Created by: Rizwan Choudrey
 * On: 12/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ExpanderGridCell implements GridCellExpander {
    private ReviewViewAdapter<? extends GvData> mParent;

    public ExpanderGridCell(ReviewViewAdapter<? extends GvData> parent) {
        mParent = parent;
    }

    @Override
    public boolean isExpandable(GvData datum) {
        return datum.hasElements() && mParent.getGridData().contains(datum);
    }

    @Override
    public ReviewViewAdapter<? extends GvData> expandItem(GvData datum) {
        if (isExpandable(datum)) {
            return FactoryReviewViewAdapter.newGvDataCollectionAdapter(mParent,
                    (GvDataCollection<? extends GvData>) datum);
        }

        return null;
    }
}
