/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;


import android.app.Activity;
import android.util.DisplayMetrics;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;

/**
 * Created by: Rizwan Choudrey
 * On: 27/10/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CellDimensionsCalculator {
    private DisplayMetrics mMetrics;

    public CellDimensionsCalculator(Activity activity) {
        mMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
    }

    public Dimensions calcDimensions(ReviewViewParams.CellDimension cellWidth, ReviewViewParams.CellDimension cellHeight, int padding) {
        int min = Math.min(mMetrics.widthPixels, mMetrics.heightPixels);
        int span = min == mMetrics.widthPixels ? cellWidth.getDivider() : cellHeight.getDivider();
        int maxCellSize = Math.max(min - span * padding, 0);
        return new Dimensions(maxCellSize / cellWidth.getDivider(), maxCellSize / cellHeight.getDivider());
    }

    public static class Dimensions {
        private final int mCellWidth;
        private final int mCellHeight;

        private Dimensions(int cellWidth, int cellHeight) {
            mCellWidth = cellWidth;
            mCellHeight = cellHeight;
        }

        public int getCellWidth() {
            return mCellWidth;
        }

        public int getCellHeight() {
            return mCellHeight;
        }
    }
}
