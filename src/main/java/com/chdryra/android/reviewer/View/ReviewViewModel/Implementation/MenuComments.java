package com.chdryra.android.reviewer.View.ReviewViewModel.Implementation;

import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MenuComments extends MenuActionNone<GvCommentList.GvComment> {
    public static final int MENU_SPLIT_ID = R.id.menu_item_split_comment;
    private static final int MENU = R.menu.menu_view_comments;

    private final MaiSplitComments<GvCommentList.GvComment> mSplitter;

    //Constructors
    public MenuComments() {
        super(MENU, GvCommentList.GvComment.TYPE.getDataName(), true);
        mSplitter = new MaiSplitComments<>(this);
    }

    //Overridden
    @Override
    protected void addMenuItems() {
        bindMenuActionItem(mSplitter, MENU_SPLIT_ID, false);
    }
}
