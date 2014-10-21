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
class DialogUI<T extends GVReviewDataList.GVReviewData, D extends DialogCancelActionDoneFragment>
        implements GVReviewDataUI<T> {

    private D                     mDialog;
    private DialogUIManager<T, D> mManager;

    protected DialogUI(D dialog, DialogUIManager<T, D> manager) {
        mDialog = dialog;
        mManager = manager;
    }

    interface DialogUIManager<T extends GVReviewDataList.GVReviewData,
            D extends DialogCancelActionDoneFragment> {
        void initialise(T data, D dialog);

        void update(T data, D dialog);

        T getGVData();
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
