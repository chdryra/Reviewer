/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions;

import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.AdapterComments;


/**
 * Created by: Rizwan Choudrey
 * On: 27/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MaiSplitCommentRefs extends MaiSplitCommentsBasic<GvComment.Reference> {
    @Override
    protected void doSplit(boolean doSplit) {
        ReviewView<GvComment.Reference> view = getParent().getReviewView();
        if (view == null) return;

        AdapterComments adapter = (AdapterComments) view.getAdapter();
        adapter.setSplit(doSplit);
    }
}
