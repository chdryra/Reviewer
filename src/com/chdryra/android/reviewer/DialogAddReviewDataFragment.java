/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.chdryra.android.mygenerallibrary.DialogCancelAddDoneFragment;
import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;

abstract class DialogAddReviewDataFragment extends DialogCancelAddDoneFragment {
    public static final String QUICK_SET = "com.chdryra.android.reviewer.dialog_quick_mode";

    private ControllerReviewNode mController;
    private boolean mQuickSet = false;
    private GVReviewDataList<? extends GVData> mData;

    GVReviewDataList<? extends GVData> setAndInitData(GVType dataType) {
        mData = mController.getData(dataType);
        return mData;
    }

    @Override
    protected void onDoneButtonClick() {
        if (isQuickSet()) {
            mController.setData(mData);
        }
    }

    boolean isQuickSet() {
        return mQuickSet;
    }

    @Override
    protected abstract View createDialogUI(ViewGroup parent);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQuickSet = getArguments().getBoolean(QUICK_SET);
        mController = Administrator.get(getActivity()).unpack(getArguments());
    }
}
