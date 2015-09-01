/*
* Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
* Unauthorized copying of this file, via any medium is strictly prohibited
* Proprietary and confidential
* Author: Rizwan Choudrey
* Date: 24 March, 2015
*/

package com.chdryra.android.reviewer.View.GvDataModel;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.mygenerallibrary.ViewHolderData;

/**
 * Created by: Rizwan Choudrey
 * On: 24/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VhDataCollection implements ViewHolder {
    private Context   mContext;
    private ViewGroup mParent;

    private ViewHolder mDataView;
    private ViewHolder mCurrentView;

    public VhDataCollection() {
        mDataView = new VhDualText();
        mCurrentView = mDataView;
    }

    @Override
    public void inflate(Context context, ViewGroup parent) {
        mContext = context;
        mParent = parent;
        mDataView.inflate(context, parent);
    }

    @Override
    public void updateView(ViewHolderData data) {
        GvDataCollection dataList = (GvDataCollection) data;
        if (dataList.size() != 1 ||
                dataList.getGvDataType().getElementType() == GvImageList.GvImage.TYPE) {
            updateDataView(dataList);
        } else {
            updateDatumView(dataList);
        }
    }

    @Override
    public View getView() {
        return mCurrentView.getView();
    }

    private void updateDataView(GvDataCollection data) {
        int number = data.size();
        GvDataType dataType = data.getGvDataType().getElementType();

        String type = number == 1 ? dataType.getDatumName() : dataType.getDataName();
        mDataView.updateView(new GvDualText(String.valueOf(number), type));
        mCurrentView = mDataView;
    }

    private void updateDatumView(GvDataCollection data) {
        ViewHolderData datum = data.getItem(0);
        GvDataType dataType = data.getGvDataType().getElementType();

        if (dataType == GvLocationList.GvLocation.TYPE || dataType == GvTagList.GvTag.TYPE) {
            mCurrentView = dataType == GvLocationList.GvLocation.TYPE ? new VhLocation(true) :
                    new VhTag(true);
        } else {
            mCurrentView = datum.getViewHolder();
        }

        mCurrentView.inflate(mContext, mParent);
        mCurrentView.updateView(datum);
    }
}
