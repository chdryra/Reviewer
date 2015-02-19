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

    private String  mDeleteWhat;
    private boolean mDismissOnDelete;
    private boolean mDismissOnDone;

    private Fragment mListener;
    private boolean  mRatingIsAverage;

    public MenuDeleteDone(String title) {
        this(title, title);
    }

    public MenuDeleteDone(String title, String deleteWhat) {
        this(title, deleteWhat, false, true);
    }

    public MenuDeleteDone(String title, String deleteWhat, boolean dismissOnDelete,
            boolean dismissOnDone) {
        this(title, deleteWhat, dismissOnDelete, dismissOnDone, MENU);
    }

    public MenuDeleteDone(String title, String deleteWhat, boolean dismissOnDelete,
            boolean dismissOnDone, int menuId) {
        super(menuId, title, true);
        mDeleteWhat = deleteWhat;
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

        addMenuItems();
        mListener = new DeleteConfirmListener() {
        };
        registerActionListener(mListener, TAG);
    }

    @Override
    public void onSetViewReview() {
        super.onSetViewReview();
        mRatingIsAverage = getBuilder().isRatingAverage();
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

    protected ReviewBuilderData getBuilder() {
        return (ReviewBuilderData) getAdapter();
    }

    private void doDoneSelected() {
        ViewReview view = getViewReview();
        GvDataList data = getData();
        ReviewBuilderData builder = getBuilder();

        if (data != null) builder.setData(data);
        builder.setSubject(view.getSubject());
        builder.setRatingIsAverage(view.isRatingAverage());
        builder.setRating(view.getRating());
    }

    private void showDeleteConfirmDialog() {
        String deleteWhat = "all " + mDeleteWhat;
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
