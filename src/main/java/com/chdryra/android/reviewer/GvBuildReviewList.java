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
    private Activity         mActivity;
    private ControllerReview mController;

    private GvBuildReviewList(Activity activity, ControllerReview controller) {
        super(TYPE);

        mActivity = activity;
        mController = controller;

        add(GvDataList.GvType.TAGS);
        add(GvDataList.GvType.CHILDREN);
        add(GvDataList.GvType.IMAGES);
        add(GvDataList.GvType.COMMENTS);
        add(GvDataList.GvType.LOCATIONS);
        add(GvDataList.GvType.FACTS);
    }

    public static GvBuildReviewList newInstance(Activity activity, ControllerReview controller) {
        return new GvBuildReviewList(activity, controller);
    }

    private void add(GvType dataType) {
        add(new GvBuildReview(dataType));
    }

    private Activity getActivity() {
        return mActivity;
    }

    private ControllerReview getController() {
        return mController;
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
            int size = getController().getData(mDataType).size();

            if (size == 0) return getNoDataView(parent);

            return size > 1 || mDataType == GvDataList.GvType.IMAGES ? getDataView(parent) :
                    getDatumView(parent);
        }

        private View getNoDataView(ViewGroup parent) {
            ViewHolder vh = new VHText();
            vh.inflate(getActivity(), parent);
            vh.updateView(new VHDString(mDataType.getDataString()));
            return vh.getView();
        }

        private View getDataView(ViewGroup parent) {
            int number = getController().getData(mDataType).size();
            String type = number == 1 ? mDataType.getDatumString() : mDataType.getDataString();

            ViewHolder vh = new VHDualText();
            vh.inflate(getActivity(), parent);
            vh.updateView(new VHDDualString(String.valueOf(number), type));
            return vh.getView();
        }

        private View getDatumView(ViewGroup parent) {
            ViewHolderData datum = (ViewHolderData) getController().getData(mDataType)
                    .getItem(0);
            ViewHolder vh = mDataType == GvType.LOCATIONS ? new VHLocation(true) : datum
                    .newViewHolder();
            if (vh.getView() == null) vh.inflate(getActivity(), parent);
            vh.updateView(datum);
            return vh.getView();
        }
    }
}
