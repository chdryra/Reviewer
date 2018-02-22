/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.Implementation;


import android.app.Activity;
import android.util.DisplayMetrics;

import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;

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
        boolean isWrappedVert = cellHeight.equals(ReviewViewParams.CellDimension.WRAPPED);
        boolean isWrappedHorz = cellWidth.equals(ReviewViewParams.CellDimension.WRAPPED);
        int min = Math.min(mMetrics.widthPixels, mMetrics.heightPixels);
        if(isWrappedHorz) min = mMetrics.heightPixels;
        if(isWrappedVert) min = mMetrics.widthPixels;

        int span = min == mMetrics.widthPixels ? cellWidth.getDivider() : cellHeight.getDivider();
        int maxCellSize = Math.max(min - span * padding, 0);
        return new Dimensions(isWrappedHorz ? -1 : maxCellSize / cellWidth.getDivider(),
                isWrappedVert ? -1 : maxCellSize / cellHeight.getDivider());
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

        public boolean isWrapped() {
            return mCellHeight == -1;
        }
    }
}
