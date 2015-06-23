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

import android.view.View;
import android.view.ViewGroup;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilder;
import com.chdryra.android.reviewer.View.Configs.ConfigGvDataUi;
import com.chdryra.android.reviewer.View.Screens.GridDataObservable;

/**
 * Encapsulates the range of responses and displays available to each data tile depending
 * on the underlying data and user interaction.
 */
public class GvBuildReviewList extends GvDataList<GvBuildReviewList.GvBuildReview> {
    public static final GvDataType<GvBuildReview> TYPE = new GvDataType<>(GvBuildReview.class,
            "create", "create");
    private final ReviewBuilder mBuilder;

    private GvBuildReviewList(ReviewBuilder builder) {
        super(null, TYPE);

        mBuilder = builder;

        add(GvTagList.TYPE);
        add(GvChildList.TYPE);
        add(GvImageList.TYPE);
        add(GvCommentList.TYPE);
        add(GvLocationList.TYPE);
        add(GvFactList.TYPE);
    }

    public static GvBuildReviewList newInstance(ReviewBuilder adapter) {
        return new GvBuildReviewList(adapter);
    }

    @Override
    public void sort() {
    }

    private <T extends GvData> void add(GvDataType<T> dataType) {
        add(new GvBuildReview<>(dataType, mBuilder));
    }

    public static class GvBuildReview<T extends GvData> extends GvDataList<T>
            implements GridDataObservable.GridDataObserver {
        private final GvDataType<T> mDataType;
        private final ConfigGvDataUi.Config        mConfig;
        private final ReviewBuilder.DataBuilder<T> mBuilder;
        private       ViewHolder                   mViewHolder;

        private GvBuildReview(GvDataType<T> dataType, ReviewBuilder builder) {
            super(null, dataType);
            mDataType = dataType;
            mConfig = ConfigGvDataUi.getConfig(dataType);
            mBuilder = builder.getDataBuilder(dataType);
            mBuilder.registerGridDataObserver(this);
            mViewHolder = super.getViewHolder();
        }


        public View updateView(ViewGroup parent) {
            if (mViewHolder.getView() == null) {
                mViewHolder.inflate(mBuilder.getParentBuilder().getContext(), parent);
            }

            mViewHolder.updateView(mBuilder.getGridData());
            return mViewHolder.getView();
        }

        @Override
        public GvDataType<T> getGvDataType() {
            return mDataType;
        }

        @Override
        public String getStringSummary() {
            return mDataType.getDataName();
        }

        public ConfigGvDataUi.Config getConfig() {
            return mConfig;
        }

        public int getDataSize() {
            return mBuilder.getGridData().size();
        }

        @Override
        public void onGridDataChanged() {
            mData = mBuilder.getGridData().toArrayList();
        }
    }
}
