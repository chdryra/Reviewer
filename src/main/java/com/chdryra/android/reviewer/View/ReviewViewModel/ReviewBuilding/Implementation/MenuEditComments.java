package com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation;

import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvComment;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDataType;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.MaiSplitComments;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.GridDataObservable;

/**
 * Created by: Rizwan Choudrey
 * On: 19/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MenuEditComments extends MenuDataEdit<GvComment>
        implements GridDataObservable.GridDataObserver {
    private static final GvDataType<GvComment> TYPE = GvComment.TYPE;
    private static final int MENU_DELETE_ID = R.id.menu_item_delete;
    private static final int MENU_DONE_ID = R.id.menu_item_done;
    private static final int MENU_SPLIT_ID = R.id.menu_item_split_comment;
    private static final int MENU = R.menu.menu_edit_comments;

    private final MaiSplitComments<GvComment> mSplitter;

    //Constructors
    public MenuEditComments() {
        super(TYPE, TYPE.getDataName(), TYPE.getDataName(), false, true, MENU);
        mSplitter = new MaiSplitComments<>(this);
    }

    //Overridden
    @Override
    public void onGridDataChanged() {
        mSplitter.updateGridDataUi();
    }

    @Override
    protected void addMenuItems() {
        bindDefaultDeleteActionItem(MENU_DELETE_ID);
        bindDefaultDoneActionItem(MENU_DONE_ID);
        bindMenuActionItem(mSplitter, MENU_SPLIT_ID, false);
    }

    @Override
    public void onAttachReviewView() {
        super.onAttachReviewView();
        getReviewView().registerGridDataObserver(this);
    }

    @Override
    public void onUnattachReviewView() {
        getReviewView().unregisterGridDataObserver(this);
        super.onUnattachReviewView();
    }
}
