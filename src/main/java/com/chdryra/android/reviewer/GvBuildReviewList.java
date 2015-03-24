/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 28 January, 2015
 */

package com.chdryra.android.reviewer;

/**
 * Created by: Rizwan Choudrey
 * On: 28/01/2015
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Encapsulates the range of responses and displays available to each data tile depending
 * on the underlying data and user interaction.
 */
public class GvBuildReviewList extends GvDataList<GvBuildReviewList.GvBuildReview> {
    public static final GvDataType TYPE = new GvDataType("create2", "create2");
    private final ReviewBuilder mBuilder;

    private GvBuildReviewList(ReviewBuilder builder) {
        super(GvBuildReview.class, TYPE);

        mBuilder = builder;

        add(GvTagList.GvTag.class, GvTagList.TYPE);
        add(GvChildList.GvChildReview.class, GvChildList.TYPE);
        add(GvImageList.GvImage.class, GvImageList.TYPE);
        add(GvCommentList.GvComment.class, GvCommentList.TYPE);
        add(GvLocationList.GvLocation.class, GvLocationList.TYPE);
        add(GvFactList.GvFact.class, GvFactList.TYPE);
    }

    public static GvBuildReviewList newInstance(ReviewBuilder adapter) {
        return new GvBuildReviewList(adapter);
    }

    private <T extends GvData> void add(Class<T> dataClass, GvDataType dataType) {
        add(new GvBuildReview<>(dataClass, dataType, mBuilder));
    }

    public static class GvBuildReview<T extends GvData> extends GvDataList<T>
            implements ReviewViewAdapter.GridDataObserver {
        private final GvDataType            mDataType;
        private final ConfigGvDataUi.Config mConfig;
        private final ReviewBuilder.DataBuilder<T> mBuilder;

        private GvBuildReview(Class<T> dataClass, GvDataType dataType, ReviewBuilder builder) {
            super(dataClass, dataType);
            mDataType = dataType;
            mConfig = ConfigGvDataUi.getConfig(dataType);
            mBuilder = (ReviewBuilder.DataBuilder<T>) builder.getDataBuilder(dataType);
            mBuilder.registerGridDataObserver(this);
        }

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
