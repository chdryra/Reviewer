/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation;


import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.CommentBuilderAdapter;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;

/**
 * Created by: Rizwan Choudrey
 * On: 27/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MaiSplitCommentVals extends MaiSplitCommentsBasic<GvComment> {
    private boolean mSplit = false;

    public void updateGridData() {
        if (!isAttached() || getReviewView() == null) return;
        setSplit();
    }

    @Override
    protected void doSplit(boolean doSplit) {
        mSplit = doSplit;
        setSplit();
    }

    @Override
    public void onDetachReviewView() {
        doSplit(false);
        super.onDetachReviewView();
    }

    private void setSplit() {
        try {
            ((CommentBuilderAdapter) getReviewView().getAdapter()).setSplitComments(mSplit);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }
}
