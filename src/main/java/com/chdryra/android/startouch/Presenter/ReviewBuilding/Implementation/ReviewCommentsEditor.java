/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation.ReviewViewActions;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCommentList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;


/**
 * Created by: Rizwan Choudrey
 * On: 07/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewCommentsEditor extends ReviewDataEditorDefault<GvComment> {

    public ReviewCommentsEditor(DataBuilderAdapter<GvComment> builder,
                                ReviewViewActions<GvComment> actions,
                                ReviewViewParams params) {
        super(builder, actions, params);
    }

    public void setSplit(boolean doSplit) {
        GvDataList<GvComment> data = getAdapter().getGridData();
        if(doSplit) {
            GvCommentList splitComments = new GvCommentList(data.getGvReviewId());
            for (GvComment comment : data) {
                splitComments.addAll(comment.getSplitComments());
            }
            data = splitComments;
        }

        setGridViewData(data);
    }
}
