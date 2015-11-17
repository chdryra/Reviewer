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
    public static MenuAction getMenu(GvDataType dataType) {
        if (dataType.equals(GvCommentList.GvComment.TYPE)) {
            return new ViewScreenComments.CommentsMenu();
        } else {
            return new MenuAction(dataType.getDataName());
        }
    }
}
