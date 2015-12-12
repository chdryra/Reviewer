/*
* Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
* Unauthorized copying of this file, via any medium is strictly prohibited
* Proprietary and confidential
* Author: Rizwan Choudrey
* Date: 24 March, 2015
*/

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.mygenerallibrary.ViewHolderData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataCollection;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDualText;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;

/**
 * Created by: Rizwan Choudrey
 * On: 24/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VhDataCollection implements ViewHolder {
    private Context mContext;
    private ViewGroup mParent;

    private ViewHolder mDataView;
    private ViewHolder mCurrentView;

    //Constructors
    public VhDataCollection() {
        mDataView = new VhDualText();
        mCurrentView = mDataView;
    }

    protected String getUpperString(int number) {
        return String.valueOf(number);
    }

    private void updateDataView(GvDataCollection data) {
        int number = data.size();
        GvDataType dataType = data.getGvDataType();

        String type = number == 1 ? dataType.getDatumName() : dataType.getDataName();
        mDataView.updateView(new GvDualText(getUpperString(number), type));
        mCurrentView = mDataView;
    }

    private void updateDatumView(GvDataCollection data) {
        ViewHolderData datum = data.getItem(0);
        mCurrentView = datum.getViewHolder();
        mCurrentView.inflate(mContext, mParent);
        mCurrentView.updateView(datum);
    }

    //Overridden
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
                dataList.getGvDataType().equals(GvImage.TYPE)) {
            updateDataView(dataList);
        } else {
            updateDatumView(dataList);
        }
    }

    @Override
    public View getView() {
        return mCurrentView.getView();
    }
}
