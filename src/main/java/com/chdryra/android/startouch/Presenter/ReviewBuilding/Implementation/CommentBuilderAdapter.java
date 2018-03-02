/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCommentList;

/**
 * Created by: Rizwan Choudrey
 * On: 02/03/2018
 * Email: rizwan.choudrey@gmail.com
 */

public class CommentBuilderAdapter extends DataBuilderAdapterDefault<GvComment> {
    private boolean mIsSplit = false;
    private GvDataList<GvComment> mGridViewData;

    public CommentBuilderAdapter(ReviewBuilderAdapter<?> parentBuilder) {
        super(GvComment.TYPE, parentBuilder);
    }

    public void setSplitComments(boolean isSplit) {
        if(isSplit) {
            GvDataList<GvComment> data = getGridData();
            GvCommentList splitComments = new GvCommentList(data.getGvReviewId());
            for (GvComment comment : data) {
                splitComments.addAll(comment.getSplitComments());
            }

            mGridViewData = splitComments;
        }

        if(mIsSplit != isSplit) {
            mIsSplit = isSplit;
            onDataChanged();
        }
    }

    @Override
    public GvDataList<GvComment> getGridData() {
        return mIsSplit ? mGridViewData : super.getGridData();
    }
}
