package com.chdryra.android.reviewer.View.Screens;

import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 27/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DefaultMenus {
    private DefaultMenus() {

    }

    //Static methods
    public static ReviewViewAction.MenuAction getMenu(GvDataType dataType) {
        if (dataType == GvCommentList.GvComment.TYPE) {
            return new ViewScreenComments.CommentsMenu();
        } else {
            return new ReviewViewAction.MenuAction();
        }
    }
}
