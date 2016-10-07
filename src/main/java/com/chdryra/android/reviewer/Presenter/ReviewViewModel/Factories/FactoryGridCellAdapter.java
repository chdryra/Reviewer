/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories;

import android.app.Activity;

import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderAdapter;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.DataBuilderGridCell;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSize;

/**
 * Created by: Rizwan Choudrey
 * On: 28/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryGridCellAdapter {
    private Activity mActivity;

    public FactoryGridCellAdapter(Activity activity) {
        mActivity = activity;
    }

    public ViewHolderAdapter newAdapter(GvDataList<?> data, int cellWidth, int cellHeight) {
        boolean uniqueViews = data.getGvDataType().equals(GvSize.Reference.TYPE) ||
                data.getGvDataType().equals(DataBuilderGridCell.TYPE);
        return new ViewHolderAdapter(mActivity, data, cellWidth, cellHeight, uniqueViews);
    }
}

