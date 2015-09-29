package com.chdryra.android.reviewer.View.Screens;

import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 29/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DefaultGridActions {
    private DefaultGridActions() {

    }

    public static void setGridAction(ReviewView view, GvDataType dataType) {
        if (dataType == GvCommentList.GvComment.TYPE) {
            view.setAction(new ViewScreenComments.CommentsGridItem());
        }
    }
}
