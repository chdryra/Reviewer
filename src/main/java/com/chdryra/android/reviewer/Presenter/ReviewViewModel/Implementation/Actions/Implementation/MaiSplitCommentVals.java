/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation;


import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ReviewCommentsEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;

/**
 * Created by: Rizwan Choudrey
 * On: 27/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MaiSplitCommentVals extends MaiSplitCommentsBasic<GvComment> {
    private boolean mSplit = false;

    @Override
    protected void doSplit(boolean doSplit) {
        mSplit = doSplit;
        try {
            ((ReviewCommentsEditor) getReviewView()).setSplit(mSplit);
        } catch (ClassCastException e) {

        }
    }

    public void updateGridData() {
        if(!isAttached() || getReviewView() == null) return;
        doSplit(mSplit);
    }
}
