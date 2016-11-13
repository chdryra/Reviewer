/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.reviewer.Presenter.Interfaces.View.DataObservable;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiSplitCommentVals;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.R;

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
    private static final int MENU_DONE_ID = R.id.menu_item_done;
    private static final int MENU_PREVIEW_ID = R.id.menu_item_preview;
    private static final int MENU_SPLIT_ID = R.id.menu_item_split_comment;

    private final MaiSplitCommentVals mSplitter;

    public MenuEditComments(MaiDataEditor<GvComment>
            deleteAction, MaiDataEditor<GvComment> doneAction, MaiDataEditor<GvComment>
            previewAction, MaiSplitCommentVals splitter) {
        super(MENU, TYPE.getDataName(), deleteAction, doneAction, previewAction);
        mSplitter = splitter;
        mSplitter.setParent(this);
    }

    @Override
    public void onDataChanged() {
        mSplitter.updateGridData();
    }

    @Override
    protected void addMenuItems() {
        bindDeleteActionItem(MENU_DELETE_ID);
        bindDoneActionItem(MENU_DONE_ID);
        bindPreviewActionItem(MENU_PREVIEW_ID);
        bindMenuActionItem(mSplitter, MENU_SPLIT_ID, false);
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
