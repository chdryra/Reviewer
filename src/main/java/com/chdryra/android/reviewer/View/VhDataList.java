/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 24 March, 2015
 */

package com.chdryra.android.reviewer.View;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.chdryra.android.mygenerallibrary.VHDDualString;
import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.mygenerallibrary.ViewHolderData;

/**
 * Created by: Rizwan Choudrey
 * On: 24/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VhDataList implements ViewHolder {
    private Context   mContext;
    private ViewGroup mParent;

    private ViewHolder mDataView;
    private ViewHolder mCurrentView;

    public VhDataList() {
        mDataView = new VhDualText();
    }

    @Override
    public void inflate(Context context, ViewGroup parent) {
        mContext = context;
        mParent = parent;
        mDataView.inflate(context, parent);
    }

    @Override
    public void updateView(ViewHolderData data) {
        GvDataList dataList = (GvDataList) data;
        if (dataList.size() != 1 || dataList.getGvDataType() == GvImageList.TYPE) {
            updateDataView(dataList);
        } else {
            updateDatumView(dataList);
        }
    }

    @Override
    public View getView() {
        return mCurrentView != null ? mCurrentView.getView() : null;
    }

    private void updateDataView(GvDataList data) {
        int number = data.size();
        GvDataType dataType = data.getGvDataType();

        String type = number == 1 ? dataType.getDatumName() : dataType.getDataName();
        mDataView.updateView(new VHDDualString(String.valueOf(number), type));
        mCurrentView = mDataView;
    }

    private void updateDatumView(GvDataList data) {
        ViewHolderData datum = (ViewHolderData) data.getItem(0);
        GvDataType dataType = data.getGvDataType();

        if (dataType == GvLocationList.TYPE || dataType == GvTagList.TYPE) {
            mCurrentView = dataType == GvLocationList.TYPE ? new VhLocation(true) : new VhTag(true);
        } else {
            mCurrentView = datum.getViewHolder();
        }

        if (mCurrentView.getView() == null) mCurrentView.inflate(mContext, mParent);
        mCurrentView.updateView(datum);
    }

//    private void updateNoDataView(GvDataList data) {
//        mCurrentView = new VhText();
//        if (mCurrentView.getView() == null) mCurrentView.inflate(mContext, mParent);
//        mCurrentView.updateView(new VHDString(data.getGvDataType().getDataName()));
//    }
}
