/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation;

import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View.AdapterComments;


/**
 * Created by: Rizwan Choudrey
 * On: 27/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MaiSplitCommentRefs extends MaiSplitCommentsBasic<GvComment.Reference> {
    @Override
    protected void doSplit(boolean doSplit) {
        ((AdapterComments) getReviewView().getAdapter()).setSplit(doSplit);
    }
}
