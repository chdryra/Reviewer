/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 26 January, 2015
 */

package com.chdryra.android.reviewer;

/**
 * Created by: Rizwan Choudrey
 * On: 26/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActionGridItemEditComment extends ActionGridItemEdit {
    public ActionGridItemEditComment(ControllerReviewEditable controller) {
        super(controller, GvDataList.GvType.COMMENTS);
    }

    @Override
    public void onGridItemClick(GvDataList.GvData item) {
        GvCommentList.GvComment split = (GvCommentList.GvComment) item;
        GvCommentList.GvComment unsplit = split.getUnSplitComment();
        super.onGridItemClick(unsplit);
    }
}
