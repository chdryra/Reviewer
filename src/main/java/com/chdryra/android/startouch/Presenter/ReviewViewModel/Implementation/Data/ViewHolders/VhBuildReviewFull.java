/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataCollection;

/**
 * Created by: Rizwan Choudrey
 * On: 26/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VhBuildReviewFull extends VhDataCollection {
    private final ViewHolder mDatumView;

    public VhBuildReviewFull(@Nullable ViewHolder datumView) {
        mDatumView = datumView;
    }

    @Override
    protected String getUpperString(int number) {
        return number == 0 ? "+" : super.getUpperString(number);
    }

    @Override
    public View getView() {
        return mDatumView != null ? mDatumView.getView() : super.getView();
    }

    @Override
    public void inflate(Context context, ViewGroup parent) {
        if(mDatumView != null) {
            mDatumView.inflate(context, parent);
        } else {
            super.inflate(context, parent);
        }
    }

    @Override
    protected void updateDataView(GvDataCollection data) {
        if(mDatumView != null && data.size() == 1) {
            mDatumView.updateView(data.get(0));
        } else {
            super.updateDataView(data);
        }
    }
}
