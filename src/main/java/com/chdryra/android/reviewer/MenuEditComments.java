/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 26 January, 2015
 */

package com.chdryra.android.reviewer;

import android.database.DataSetObserver;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by: Rizwan Choudrey
 * On: 26/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MenuEditComments extends MenuDeleteDone {
    public static final  int MENU_DELETE_ID = R.id.menu_item_delete;
    public static final  int MENU_DONE_ID   = R.id.menu_item_done;
    public static final  int MENU_SPLIT_ID  = R.id.menu_item_split_comment;
    private static final int MENU           = R.menu.fragment_review_comment;

    private boolean mCommentsAreSplit = false;
    private DataSetObserver mObserver;

    public MenuEditComments(ControllerReviewEditable controller) {
        super(controller, GvDataList.GvType.COMMENTS, false, true, MENU);
        mObserver = new DataSetObserver() {
            @Override
            public void onChanged() {
                updateGridDataUi();
            }

            @Override
            public void onInvalidated() {
                super.onInvalidated();
            }
        };
    }

    @Override
    public void onSetViewReview() {
        super.onSetViewReview();
        getViewReview().registerGridDataObserver(mObserver);
    }

    @Override
    public void onUnsetViewReview() {
        super.onUnsetViewReview();
        getViewReview().unregisterGridDataObserver(mObserver);
    }

    @Override
    protected void addMenuItems() {
        addDefaultDeleteActionItem(MENU_DELETE_ID);
        addDefaultDoneActionItem(MENU_DONE_ID);
        addMenuActionItem(getSplitOrUnsplitCommentsAction(), MENU_SPLIT_ID, false);
    }

    private MenuActionItem getSplitOrUnsplitCommentsAction() {
        return new MenuActionItem() {
            @Override
            public void doAction(MenuItem item) {
                splitOrUnsplitComments(item);
            }
        };
    }

    private void updateGridDataUi() {
        //Change grid data
        GvCommentList comments = (GvCommentList) getData();
        if (comments != null) {
            if (mCommentsAreSplit) {
                getViewReview().setGridViewData(comments.getSplitComments());
            } else {
                getViewReview().resetGridViewData();
            }
        }
    }

    private void splitOrUnsplitComments(MenuItem item) {
        mCommentsAreSplit = !mCommentsAreSplit;

        //Change menu icons
        item.setIcon(mCommentsAreSplit ? R.drawable.ic_action_return_from_full_screen : R
                .drawable.ic_action_full_screen);
        if (mCommentsAreSplit) {
            Toast.makeText(getActivity(), R.string.toast_split_comment, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), R.string.toast_unsplit_comment,
                    Toast.LENGTH_SHORT).show();
        }

        updateGridDataUi();
    }
}
