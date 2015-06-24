/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 28 January, 2015
 */

package com.chdryra.android.reviewer.View.Screens;

import android.app.Activity;

import com.chdryra.android.mygenerallibrary.ViewHolderAdapter;
import com.chdryra.android.reviewer.View.GvDataModel.GvBuildReviewList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;

/**
 * Created by: Rizwan Choudrey
 * On: 28/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryGridCellAdapter {
    private FactoryGridCellAdapter() {
    }

    public static ViewHolderAdapter newAdapter(Activity activity, GvDataList data, int cellWidth,
            int cellHeight) {
        boolean uniqueViews = data.getGvDataType() == GvDataCollection.TYPE ||
                data.getGvDataType() == GvBuildReviewList.TYPE;
        return new ViewHolderAdapter(activity, data, cellWidth, cellHeight, uniqueViews);
    }
}

