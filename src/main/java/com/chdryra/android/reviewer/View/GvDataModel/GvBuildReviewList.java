/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 28 January, 2015
 */

package com.chdryra.android.reviewer.View.GvDataModel;

/**
 * Created by: Rizwan Choudrey
 * On: 28/01/2015
 * Email: rizwan.choudrey@gmail.com
 */

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.View.Configs.ConfigGvDataUi;
import com.chdryra.android.reviewer.View.Screens.GridDataObservable;

/**
 * Encapsulates the range of responses and displays available to each data tile depending
 * on the underlying data and user interaction.
 */
public class GvBuildReviewList extends GvDataList<GvBuildReviewList.GvBuildReview> {
    private final ReviewBuilderAdapter mBuilder;

    private GvBuildReviewList(ReviewBuilderAdapter builder) {
        super(GvBuildReview.TYPE, null);

        mBuilder = builder;

        add(GvTagList.GvTag.TYPE);
        add(GvCriterionList.GvCriterion.TYPE);
        add(GvImageList.GvImage.TYPE);
        add(GvCommentList.GvComment.TYPE);
        add(GvLocationList.GvLocation.TYPE);
        add(GvFactList.GvFact.TYPE);
    }

    //Static methods
    public static GvBuildReviewList newInstance(ReviewBuilderAdapter adapter) {
        return new GvBuildReviewList(adapter);
    }

    private <T extends GvData> void add(GvDataType<T> dataType) {
        add(new GvBuildReview<>(dataType, mBuilder));
    }

    //Overridden
    @Override
    public void sort() {
    }

    //Classes
    public static class GvBuildReview<T extends GvData> extends GvDataList<T>
            implements GridDataObservable.GridDataObserver {

        public static GvDataType<GvBuildReview> TYPE =
                new GvDataType<>(GvBuildReview.class, "create", "create");

        private final ConfigGvDataUi.Config mConfig;
        private final ReviewBuilderAdapter.DataBuilderAdapter<T> mBuilder;

        private GvBuildReview(GvDataType<T> dataType, ReviewBuilderAdapter builder) {
            super(dataType, null);
            mConfig = ConfigGvDataUi.getConfig(dataType);
            mBuilder = builder.getDataBuilder(dataType);
            mBuilder.registerGridDataObserver(this);
        }

        //public methods
        public ConfigGvDataUi.Config getConfig() {
            return mConfig;
        }

        public int getDataSize() {
            return mBuilder.getGridData().size();
        }

        //Overridden
        @Override
        public String getStringSummary() {
            return getGvDataType().getDataName();
        }

        @Override
        public ViewHolder getViewHolder() {
            return new VhBuildReviewData();
        }

        @Override
        public void onGridDataChanged() {
            mData = mBuilder.getGridData().toArrayList();
        }
    }
}
