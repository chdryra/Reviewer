/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 28 January, 2015
 */

package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Factories;

import android.app.Activity;

import com.chdryra.android.mygenerallibrary.ViewHolderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Implementation.DataBuilderGridCell;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvList;

/**
 * Created by: Rizwan Choudrey
 * On: 28/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryGridCellAdapter {
    private FactoryGridCellAdapter() {
    }

    //Static methods
    public static ViewHolderAdapter newAdapter(Activity activity, GvDataList data, int cellWidth,
                                               int cellHeight) {
        boolean uniqueViews = data.getGvDataType().equals(GvList.TYPE) ||
                data.getGvDataType().equals(DataBuilderGridCell.TYPE);
        return new ViewHolderAdapter(activity, data, cellWidth, cellHeight, uniqueViews);
    }
}

