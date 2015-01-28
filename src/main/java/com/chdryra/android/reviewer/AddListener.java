/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 24 January, 2015
 */

package com.chdryra.android.reviewer;

import android.app.Fragment;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class AddListener extends Fragment implements DialogFragmentGvDataAdd
        .GvDataAddListener {
    private ViewReview    mView;
    private GvDataHandler mHandler;

    public AddListener(ViewReview view) {
        mView = view;
        mHandler = FactoryGvDataHandler.newHandler(mView.getGridData());
    }

    @Override
    public boolean onGvDataAdd(GvDataList.GvData data) {
        boolean added = mHandler.add(data, getActivity());
        mView.updateUi();
        return added;
    }
}
