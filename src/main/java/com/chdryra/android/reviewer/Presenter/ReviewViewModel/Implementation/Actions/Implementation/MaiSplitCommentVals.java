/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCommentList;

/**
 * Created by: Rizwan Choudrey
 * On: 27/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MaiSplitCommentVals extends MaiSplitCommentsBasic<GvComment> {
    private boolean mCommentsAreSplit;

    @Override
    protected void doSplit(boolean doSplit) {
        mCommentsAreSplit = doSplit;
        ReviewView<GvComment> view = getReviewView();
        GvDataList<GvComment> data = view.getGridData();
        if (doSplit) {
            GvCommentList splitComments = new GvCommentList(data.getGvReviewId());
            for (GvComment comment : data) {
                splitComments.addAll(comment.getSplitComments());
            }

            data = splitComments;
        }

        view.setGridViewData(data);
    }

    public void updateGridData() {
        if(!isAttached() || getReviewView() == null) return;
        doSplit(mCommentsAreSplit);
    }
}
