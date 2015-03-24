/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 28 January, 2015
 */

package com.chdryra.android.reviewer;

import android.app.Activity;

import com.chdryra.android.mygenerallibrary.ViewHolderAdapter;

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
        return new ViewHolderAdapter(activity, data, cellWidth, cellHeight);
    }
}
