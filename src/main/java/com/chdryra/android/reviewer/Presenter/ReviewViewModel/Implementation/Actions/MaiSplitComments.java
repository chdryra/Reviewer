/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions;

import android.content.Context;
import android.view.MenuItem;

import com.chdryra.android.reviewer.Application.CurrentScreen;
import com.chdryra.android.reviewer.Application.Strings;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCommentList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.AdapterCommentsAggregate;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 27/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MaiSplitComments<T extends GvData> implements MenuActionNone.MenuActionItem {
    private static final int UNSPLIT_ICON = R.drawable.ic_action_return_from_full_screen;
    private static final int SPLIT_ICON = R.drawable.ic_action_full_screen;

    private boolean mCommentsAreSplit = false;
    private MenuAction<T> mParent;

    //Constructors
    public MaiSplitComments(MenuAction<T> parent) {
        mParent = parent;
    }

    public void updateGridDataUi() {
        ReviewView<T> view = mParent.getReviewView();
        if (view == null) return;

        //Hacky central...
        GvDataList<T> data = view.getGridData();
        GvDataType<T> dataType = data.getGvDataType();
        if (dataType.equals(GvComment.TYPE)) {
            GvCommentList comments = (GvCommentList) data;
            if (mCommentsAreSplit) {
                view.setGridViewData((GvDataList<T>) comments.getSplitComments());
            } else {
                view.setGridViewData(data);
            }
        } else {
            AdapterCommentsAggregate adapter = (AdapterCommentsAggregate) view.getAdapter();
            adapter.setSplit(mCommentsAreSplit);
        }
    }

    //Overridden
    @Override
    public void doAction(MenuItem item) {
        mCommentsAreSplit = !mCommentsAreSplit;

        item.setIcon(mCommentsAreSplit ? UNSPLIT_ICON : SPLIT_ICON);
        CurrentScreen screen = mParent.getReviewView().getScreen();
        if (mCommentsAreSplit) {
            screen.showToast(Strings.Toasts.SPLIT_COMMENT);
        } else {
            screen.showToast(Strings.Toasts.UNSPLIT_COMMENT);
        }

        updateGridDataUi();
    }
}
