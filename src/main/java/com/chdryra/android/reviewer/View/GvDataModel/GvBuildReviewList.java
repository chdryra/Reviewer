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
    public static final GvDataType TYPE = new GvDataType("create", "create");
    public static final Class<GvBuildReview> DATA_CLASS = GvBuildReview.class;

    private final ReviewBuilder mBuilder;

    private GvBuildReviewList(ReviewBuilder builder) {
        super(null, DATA_CLASS, TYPE);

        mBuilder = builder;

        add(GvTagList.DATA_CLASS, GvTagList.TYPE);
        add(GvChildList.DATA_CLASS, GvChildList.TYPE);
        add(GvImageList.DATA_CLASS, GvImageList.TYPE);
        add(GvCommentList.DATA_CLASS, GvCommentList.TYPE);
        add(GvLocationList.DATA_CLASS, GvLocationList.TYPE);
        add(GvFactList.DATA_CLASS, GvFactList.TYPE);
    }

    public static GvBuildReviewList newInstance(ReviewBuilder adapter) {
        return new GvBuildReviewList(adapter);
    }

    @Override
    public void sort() {
    }

    private <T extends GvData> void add(Class<T> dataClass, GvDataType dataType) {
        add(new GvBuildReview<>(dataClass, dataType, mBuilder));
    }

    public static class GvBuildReview<T extends GvData> extends GvDataList<T>
            implements GridDataObservable.GridDataObserver {
        private final GvDataType                   mDataType;
        private final ConfigGvDataUi.Config        mConfig;
        private final ReviewBuilder.DataBuilder<T> mBuilder;
        private       ViewHolder                   mViewHolder;

        private GvBuildReview(Class<T> dataClass, GvDataType dataType, ReviewBuilder builder) {
            super(null, dataClass, dataType);
            mDataType = dataType;
            mConfig = ConfigGvDataUi.getConfig(dataType);
            mBuilder = (ReviewBuilder.DataBuilder<T>) builder.getDataBuilder(dataType);
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
        public GvDataType getGvDataType() {
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
