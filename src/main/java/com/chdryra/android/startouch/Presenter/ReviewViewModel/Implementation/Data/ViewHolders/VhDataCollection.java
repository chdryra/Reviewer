/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderData;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataCollection;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDualText;

/**
 * Created by: Rizwan Choudrey
 * On: 24/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VhDataCollection implements ViewHolder {
    private final ViewHolder mDataView;

    public VhDataCollection() {
        mDataView = new VhDualText();
    }

    @Override
    public void inflate(Context context, ViewGroup parent) {
        mDataView.inflate(context, parent);
    }

    @Override
    public void updateView(ViewHolderData data) {
        GvDataCollection dataList = (GvDataCollection) data;
        updateDataView(dataList);
    }

    @Override
    public View getView() {
        return mDataView.getView();
    }

    String getUpperString(int number) {
        return String.valueOf(number);
    }

    String getLowerString(int number, GvDataType dataType) {
        return number == 1 ? dataType.getDatumName() : dataType.getDataName();
    }

    protected void updateDataView(GvDataCollection data) {
        String upper = getUpperString(data.size());
        String lower = getLowerString(data.size(), data.getGvDataType());
        mDataView.updateView(new GvDualText(upper, lower));
    }
}
