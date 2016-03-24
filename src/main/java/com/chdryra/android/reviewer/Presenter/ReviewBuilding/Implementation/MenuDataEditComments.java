/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.reviewer.Presenter.Interfaces.View.DataObservable;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .MaiSplitComments;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 19/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MenuDataEditComments extends MenuDataEdit<GvComment>
        implements DataObservable.DataObserver {
    private static final GvDataType<GvComment> TYPE = GvComment.TYPE;
    private static final int MENU_DELETE_ID = R.id.menu_item_delete;
    private static final int MENU_DONE_ID = R.id.menu_item_done;
    private static final int MENU_SPLIT_ID = R.id.menu_item_split_comment;
    private static final int MENU = R.menu.menu_edit_comments;

    private final MaiSplitComments<GvComment> mSplitter;

    //Constructors
    public MenuDataEditComments() {
        super(TYPE.getDataName(), TYPE.getDataName(), false, true, MENU);
        mSplitter = new MaiSplitComments<>(this);
    }

    @Override
    public void onDataChanged() {
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
        getReviewView().registerDataObserver(this);
    }

    @Override
    public void onUnattachReviewView() {
        getReviewView().unregisterDataObserver(this);
        super.onUnattachReviewView();
    }
}
