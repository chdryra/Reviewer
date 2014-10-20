/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 17 October, 2014
 */

package com.chdryra.android.reviewer;


import com.chdryra.android.mygenerallibrary.DialogCancelActionDoneFragment;

/**
 * Created by: Rizwan Choudrey
 * On: 17/10/2014
 * Email: rizwan.choudrey@gmail.com
 */
class UIDialog<T extends GVReviewDataList.GVReviewData, D extends DialogCancelActionDoneFragment>
        implements GVReviewDataUI<T> {

    private D                     mDialog;
    private UIDialogManager<T, D> mManager;

    protected UIDialog(D dialog, UIDialogManager<T, D> manager) {
        mDialog = dialog;
        mManager = manager;
    }

    public interface UIDialogManager<T extends GVReviewDataList.GVReviewData,
            D extends DialogCancelActionDoneFragment> {
        public void initialise(T data, D dialog);

        public void update(T data, D dialog);

        public T getGVData();
    }

    @Override
    public void initialiseView(T data) {
        mManager.initialise(data, mDialog);
    }

    @Override
    public void updateView(T data) {
        mManager.update(data, mDialog);
    }

    @Override
    public T getGVData() {
        return mManager.getGVData();
    }
}
