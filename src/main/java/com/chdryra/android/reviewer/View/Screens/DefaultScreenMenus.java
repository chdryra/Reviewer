package com.chdryra.android.reviewer.View.Screens;

import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 27/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DefaultScreenMenus {
    private DefaultScreenMenus() {

    }

    public static void setMenu(ReviewView view, GvDataType dataType) {
        if (dataType == GvCommentList.GvComment.TYPE) {
            view.setAction(new CommentsMenu());
        }
    }

    private static class CommentsMenu extends ReviewViewAction.MenuAction {
        public static final int MENU_SPLIT_ID = R.id.menu_item_split_comment;
        private static final int MENU = R.menu.menu_view_comments;

        private final MaiSplitComments mSplitter;

        public CommentsMenu() {
            super(MENU, GvCommentList.GvComment.TYPE.getDataName(), true);
            mSplitter = new MaiSplitComments(this);
        }

        @Override
        protected void addMenuItems() {
            bindMenuActionItem(mSplitter, MENU_SPLIT_ID, false);
        }
    }
}
