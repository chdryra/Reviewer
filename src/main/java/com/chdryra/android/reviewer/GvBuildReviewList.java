/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 28 January, 2015
 */

package com.chdryra.android.reviewer;

import android.app.Activity;
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
    private static final GvType TYPE = GvType.BUILD_REVIEW;
    private Activity      mActivity;
    private ReviewBuilder mBuilder;

    private GvBuildReviewList(Activity activity, ReviewBuilder builder) {
        super(TYPE);

        mActivity = activity;
        mBuilder = builder;

        add(GvDataList.GvType.TAGS);
        add(GvDataList.GvType.CHILDREN);
        add(GvDataList.GvType.IMAGES);
        add(GvDataList.GvType.COMMENTS);
        add(GvDataList.GvType.LOCATIONS);
        add(GvDataList.GvType.FACTS);
    }

    public static GvBuildReviewList newInstance(Activity activity, ReviewBuilder adapter) {
        return new GvBuildReviewList(activity, adapter);
    }

    private void add(GvType dataType) {
        add(new GvBuildReview(dataType));
    }

    private Activity getActivity() {
        return mActivity;
    }

    public class GvBuildReview implements GvDataList.GvData {
        private final GvDataList.GvType     mDataType;
        private final ConfigGvDataUi.Config mConfig;

        private GvBuildReview(GvDataList.GvType dataType) {
            mDataType = dataType;
            mConfig = ConfigGvDataUi.getConfig(dataType);
        }

        public GvDataList.GvType getGvType() {
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

            return size > 1 || mDataType == GvDataList.GvType.IMAGES ? getDataView(parent) :
                    getDatumView(parent);
        }

        private View getNoDataView(ViewGroup parent) {
            ViewHolder vh = new VhText();
            vh.inflate(getActivity(), parent);
            vh.updateView(new VHDString(mDataType.getDataString()));
            return vh.getView();
        }

        private View getDataView(ViewGroup parent) {
            int number = mBuilder.getDataSize(mDataType);
            String type = number == 1 ? mDataType.getDatumString() : mDataType.getDataString();

            ViewHolder vh = new VhDualText();
            vh.inflate(getActivity(), parent);
            vh.updateView(new VHDDualString(String.valueOf(number), type));
            return vh.getView();
        }

        private View getDatumView(ViewGroup parent) {
            ViewHolderData datum = (ViewHolderData) mBuilder.getDataBuilder(mDataType)
                    .getGridData().getItem(0);
            ViewHolder vh = mDataType == GvType.LOCATIONS ? new VhLocation(true) : datum
                    .newViewHolder();
            if (vh.getView() == null) vh.inflate(getActivity(), parent);
            vh.updateView(datum);
            return vh.getView();
        }
    }
}
