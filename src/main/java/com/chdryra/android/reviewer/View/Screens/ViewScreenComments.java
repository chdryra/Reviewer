package com.chdryra.android.reviewer.View.Screens;

import android.view.View;

import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 29/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewScreenComments {

    public static class CommentsGridItem extends ReviewDataScreen.GridItem {
        @Override
        public void onClickNotExpandable(GvData item, int position, View v) {
            try {
                GvCommentList.GvComment unsplit = ((GvCommentList.GvComment) item)
                        .getUnsplitComment();
                super.onClickNotExpandable(unsplit, position, v);
            } catch (ClassCastException e) {
                super.onClickNotExpandable(item, position, v);
            }
        }
    }

    public static class CommentsMenu extends ReviewViewAction.MenuAction {
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
