/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 28 January, 2015
 */

package com.chdryra.android.reviewer.View.Screens;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.chdryra.android.mygenerallibrary.ViewHolderAdapter;
import com.chdryra.android.reviewer.View.GvDataModel.GvBuildReviewList;
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
        if (data.getGvDataType() == GvBuildReviewList.TYPE) {
            return new BuildReviewAdapter(activity, data, cellWidth, cellHeight);
        } else {
            return new ViewHolderAdapter(activity, data, cellWidth, cellHeight);
        }
    }

    /**
     * Provides the adapter for the GridView of data tiles when building a review. Can't use the
     * ViewHolder pattern here because each cell can have its own unique look so reuse is not an
     * option. The view update requests are forwarded to the underlying {@link GvBuildReviewList
     * .GvBuildReview} to handle.
     */
    private static class BuildReviewAdapter extends ViewHolderAdapter {
        private final int mCellWidth;
        private final int mCellHeight;

        public BuildReviewAdapter(Activity activity, GvDataList data, int cellWidth,
                int cellHeight) {
            super(activity, data, cellWidth, cellHeight);
            mCellWidth = cellWidth;
            mCellHeight = cellHeight;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = ((GvBuildReviewList.GvBuildReview) getItem(position)).updateView(parent);
            convertView.getLayoutParams().width = mCellWidth;
            convertView.getLayoutParams().height = mCellHeight;

            return convertView;
        }


    }
}

