/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 24 January, 2015
 */

package com.chdryra.android.reviewer;

import android.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.mygenerallibrary.DialogDeleteConfirm;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MenuDeleteDone extends ViewReviewAction.MenuAction {
    public static final  int                MENU_DELETE_ID = R.id.menu_item_delete;
    public static final  int                MENU_DONE_ID   = R.id.menu_item_done;
    public static final  ActivityResultCode RESULT_DELETE  = ActivityResultCode.DELETE;
    public static final  ActivityResultCode RESULT_DONE    = ActivityResultCode.DONE;
    private static final int                MENU           = R.menu.menu_delete_done;
    private static final int                DELETE_CONFIRM = 314;
    private static final String             TAG            = "ActionMenuDeleteDoneGridListener";

    private MenuActionItem mDeleteAction;
    private MenuActionItem mDoneAction;

    private boolean mDismissOnDelete;
    private boolean mDismissOnDone;

    private Fragment mListener;
    private boolean  mRatingIsAverage;

    public MenuDeleteDone(ReviewBuilder controller,
            GvDataList.GvType dataType) {
        this(controller, dataType, false, true);
    }

    public MenuDeleteDone(ReviewBuilder controller,
            GvDataList.GvType dataType, boolean dismissOnDelete, boolean dismissOnDone) {
        this(controller, dataType, dismissOnDelete, dismissOnDone, MENU);
    }

    public MenuDeleteDone(ReviewBuilder controller,
            GvDataList.GvType dataType, boolean dismissOnDelete, boolean dismissOnDone,
            int menuId) {
        super(controller, dataType, menuId);
        mDismissOnDelete = dismissOnDelete;
        mDismissOnDone = dismissOnDone;

        mDeleteAction = new MenuActionItem() {
            @Override
            public void doAction(MenuItem item) {
                showDeleteConfirmDialog();
            }
        };

        mDoneAction = new MenuActionItem() {
            @Override
            public void doAction(MenuItem item) {
                doDoneSelected();
                sendResult(RESULT_DONE);
            }
        };

        mRatingIsAverage = getBuilder().isRatingAverage();

        addMenuItems();
        mListener = new DeleteConfirmListener() {
        };
        registerActionListener(mListener, TAG);
    }

    @Override
    protected void addMenuItems() {
        addDefaultDeleteActionItem(MENU_DELETE_ID);
        addDefaultDoneActionItem(MENU_DONE_ID);
    }

    @Override
    protected void doUpSelected() {
        getBuilder().setRatingIsAverage(mRatingIsAverage);
        super.doUpSelected();
    }

    protected void addDefaultDeleteActionItem(int deleteId) {
        addMenuActionItem(getDeleteAction(), deleteId, false);
    }

    protected void addDefaultDoneActionItem(int doneId) {
        addMenuActionItem(getDoneAction(), doneId, mDismissOnDone);
    }

    protected MenuActionItem getDeleteAction() {
        return mDeleteAction;
    }

    protected MenuActionItem getDoneAction() {
        return mDoneAction;
    }

    protected void doDeleteSelected() {
        if (hasDataToDelete()) {
            GvDataList data = getData();
            if (data != null) {
                data.removeAll();
                getViewReview().updateUi();
            }

            if (mDismissOnDelete) {
                sendResult(RESULT_DELETE);
                getActivity().finish();
            }
        }
    }

    protected ReviewBuilder getBuilder() {
        return (ReviewBuilder) getAdapter();
    }

    private void doDoneSelected() {
        ViewReview view = getViewReview();
        GvDataList data = getData();
        ReviewBuilder controller = getBuilder();

        if (data != null) controller.setData(data);
        controller.setSubject(view.getSubject());
        controller.setRating(view.getRating());
    }

    private void showDeleteConfirmDialog() {
        String deleteWhat = " all " + getDataType().getDataString();
        DialogDeleteConfirm.showDialog(deleteWhat, mListener, DELETE_CONFIRM,
                getActivity().getFragmentManager());
    }

    private boolean hasDataToDelete() {
        return getData() != null && getData().size() > 0;
    }

    private abstract class DeleteConfirmListener extends Fragment implements DialogAlertFragment
            .DialogAlertListener {
        @Override
        public void onAlertNegative(int requestCode, Bundle args) {

        }

        @Override
        public void onAlertPositive(int requestCode, Bundle args) {
            if (requestCode == DELETE_CONFIRM) doDeleteSelected();
        }
    }

}
