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

    public static ReviewViewAction.GridItemAction getGridAction(GvDataType dataType) {
        if (dataType == GvCommentList.GvComment.TYPE) {
            return new ViewScreenComments.CommentsGridItem();
        } else {
            return new GiDataLauncher();
        }
    }
}
