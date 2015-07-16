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

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilder;
import com.chdryra.android.reviewer.View.Configs.ConfigGvDataUi;
import com.chdryra.android.reviewer.View.Screens.GridDataObservable;

/**
 * Encapsulates the range of responses and displays available to each data tile depending
 * on the underlying data and user interaction.
 */
public class GvBuildReviewList extends GvDataList<GvBuildReviewList.GvBuildReview> {
    public static final GvDataType<GvBuildReviewList> TYPE =
            GvTypeMaker.newType(GvBuildReviewList.class, GvBuildReview.TYPE);

    private final ReviewBuilder mBuilder;

    private GvBuildReviewList(ReviewBuilder builder) {
        super(GvBuildReview.class, TYPE, null);

        mBuilder = builder;

        add(GvTagList.GvTag.TYPE);
        add(GvChildReviewList.GvChildReview.TYPE);
        add(GvImageList.GvImage.TYPE);
        add(GvCommentList.GvComment.TYPE);
        add(GvLocationList.GvLocation.TYPE);
        add(GvFactList.GvFact.TYPE);
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

        public static GvDataType<GvBuildReview> TYPE =
                GvTypeMaker.newType(GvBuildReview.class, "create", "create");

        private final GvDataType<T> mDataType;
        private final ConfigGvDataUi.Config        mConfig;
        private final ReviewBuilder.DataBuilder<T> mBuilder;

        private GvBuildReview(GvDataType<T> dataType, ReviewBuilder builder) {
            super(dataType.getDataClass(), dataType, null);
            mDataType = dataType;
            mConfig = ConfigGvDataUi.getConfig(dataType);
            mBuilder = builder.getDataBuilder(dataType);
            mBuilder.registerGridDataObserver(this);
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
