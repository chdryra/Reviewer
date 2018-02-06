/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.startouch.Presenter.Interfaces.Actions.MenuActionItem;
import com.chdryra.android.startouch.Presenter.Interfaces.View.DataObservable;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiSplitCommentVals;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.startouch.R;

/**
 * Created by: Rizwan Choudrey
 * On: 19/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MenuEditComments extends MenuEditData<GvComment>
        implements DataObservable.DataObserver {
    private static final GvDataType<GvComment> TYPE = GvComment.TYPE;
    private static final int MENU = R.menu.menu_edit_comments;
    private static final int MENU_DELETE_ID = R.id.menu_item_delete;
    private static final int MENU_PREVIEW_ID = R.id.menu_item_preview;
    private static final int MENU_SPLIT_ID = R.id.menu_item_split_comment;

    private final MaiSplitCommentVals mSplitter;

    public MenuEditComments(MenuActionItem<GvComment> upAction,
                            MenuActionItem<GvComment> deleteAction,
                            MenuActionItem<GvComment> previewAction,
                            MaiSplitCommentVals splitter) {
        super(TYPE.getDataName(), MENU,
                new int[]{MENU_DELETE_ID, MENU_PREVIEW_ID},
                upAction, deleteAction, previewAction);
        mSplitter = splitter;
        bindMenuActionItem(mSplitter, MENU_SPLIT_ID, false);
    }

    @Override
    public void onDataChanged() {
        mSplitter.updateGridData();
    }

    @Override
    public void onAttachReviewView() {
        super.onAttachReviewView();
        getReviewView().registerObserver(this);
    }

    @Override
    public void onDetachReviewView() {
        getReviewView().unregisterObserver(this);
        super.onDetachReviewView();
    }
}
