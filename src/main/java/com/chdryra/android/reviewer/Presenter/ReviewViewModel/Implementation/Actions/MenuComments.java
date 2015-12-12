package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MenuComments extends MenuActionNone<GvComment> {
    public static final int MENU_SPLIT_ID = R.id.menu_item_split_comment;
    private static final int MENU = R.menu.menu_view_comments;

    private final MaiSplitComments<GvComment> mSplitter;

    //Constructors
    public MenuComments() {
        super(MENU, GvComment.TYPE.getDataName(), true);
        mSplitter = new MaiSplitComments<>(this);
    }

    //Overridden
    @Override
    protected void addMenuItems() {
        bindMenuActionItem(mSplitter, MENU_SPLIT_ID, false);
    }
}
