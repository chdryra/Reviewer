/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 28 January, 2015
 */

package com.chdryra.android.reviewer;

import android.os.Parcel;
import android.view.View;
import android.view.ViewGroup;

import com.chdryra.android.mygenerallibrary.VHDDualString;
import com.chdryra.android.mygenerallibrary.VHDString;
import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.mygenerallibrary.ViewHolderData;

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
    public static final GvDataType TYPE = new GvDataType("create", "create");
    private ReviewBuilder mBuilder;

    private GvBuildReviewList(ReviewBuilder builder) {
        super(TYPE);

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

    private void add(GvDataType dataType) {
        add(new GvBuildReview(dataType));
    }

    public class GvBuildReview implements GvDataList.GvData {
        private final GvDataType            mDataType;
        private final ConfigGvDataUi.Config mConfig;

        private GvBuildReview(GvDataType dataType) {
            mDataType = dataType;
            mConfig = ConfigGvDataUi.getConfig(dataType);
        }

        public GvDataType getGvDataType() {
            return mDataType;
        }

        public ConfigGvDataUi.Config getConfig() {
            return mConfig;
        }

        public int getDataSize() {
            return mBuilder.getDataSize(mDataType);
        }

        @Override
        public ViewHolder newViewHolder() {
            return null;
        }

        @Override
        public boolean isValidForDisplay() {
            return true;
        }

        @Override
        public String getStringSummary() {
            return mDataType.getDataName();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeSerializable(mDataType);
        }

        public View updateView(ViewGroup parent) {
            int size = mBuilder.getDataSize(mDataType);

            if (size == 0) return getNoDataView(parent);

            return size > 1 || mDataType == GvImageList.TYPE ? getDataView(parent) :
                    getDatumView(parent);
        }

        private View getNoDataView(ViewGroup parent) {
            ViewHolder vh = new VhText();
            vh.inflate(mBuilder.getContext(), parent);
            vh.updateView(new VHDString(mDataType.getDataName()));
            return vh.getView();
        }

        private View getDataView(ViewGroup parent) {
            int number = mBuilder.getDataSize(mDataType);
            String type = number == 1 ? mDataType.getDatumName() : mDataType.getDataName();

            ViewHolder vh = new VhDualText();
            vh.inflate(mBuilder.getContext(), parent);
            vh.updateView(new VHDDualString(String.valueOf(number), type));
            return vh.getView();
        }

        private View getDatumView(ViewGroup parent) {
            ViewHolderData datum = (ViewHolderData) mBuilder.getDataBuilder(mDataType)
                    .getGridData().getItem(0);

            ViewHolder vh;
            if (mDataType == GvLocationList.TYPE || mDataType == GvTagList.TYPE) {
                vh = mDataType == GvLocationList.TYPE ? new VhLocation(true) : new VhTag(true);
            } else {
                vh = datum.newViewHolder();
                ;
            }

            if (vh.getView() == null) vh.inflate(mBuilder.getContext(), parent);
            vh.updateView(datum);
            return vh.getView();
        }
    }
}
