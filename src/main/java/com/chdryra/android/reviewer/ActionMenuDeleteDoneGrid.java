/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 24 January, 2015
 */

package com.chdryra.android.reviewer;

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
public class ActionMenuDeleteDoneGrid extends ReviewView.MenuAction implements
        DialogAlertFragment.DialogAlertListener {
    public static final  int                MENU_DELETE_ID = R.id.menu_item_delete;
    public static final  int                MENU_DONE_ID   = R.id.menu_item_done;
    public static final  ActivityResultCode RESULT_DELETE  = ActivityResultCode.DELETE;
    public static final  ActivityResultCode RESULT_DONE    = ActivityResultCode.DONE;
    private static final int                MENU           = R.menu.menu_delete_done;
    private static final int                DELETE_CONFIRM = 314;

    private boolean mDismissOnDelete = false;
    private boolean mDismissOnDone   = true;

    public ActionMenuDeleteDoneGrid(ControllerReviewEditable controller,
            GvDataList.GvType dataType) {
        super(controller, dataType, MENU);
    }

    public ActionMenuDeleteDoneGrid(ControllerReviewEditable controller,
            GvDataList.GvType dataType, boolean dismissOnDelete, boolean dismissOnDone) {
        super(controller, dataType, MENU);
        mDismissOnDelete = dismissOnDelete;
        mDismissOnDone = dismissOnDone;
    }

    @Override
    public boolean onItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == MENU_DELETE_ID && hasDataToDelete()) {
            showDeleteConfirmDialog();
            return true;
        } else if (itemId == MENU_DONE_ID) {
            doDoneSelected();
            return true;
        } else {
            return super.onItemSelected(item);
        }
    }

    @Override
    public void onAlertNegative(int requestCode, Bundle args) {

    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        if (requestCode == DELETE_CONFIRM) doDeleteSelected();
    }

    private void doDeleteSelected() {
        if (hasDataToDelete()) {
            GvDataList data = getData();
            if (data != null) data.removeAll();
            if (mDismissOnDelete) {
                sendResult(RESULT_DELETE);
                getActivity().finish();
            }
        }
    }

    private void doDoneSelected() {
        GvDataList data = getData();
        ControllerReviewEditable controller = (ControllerReviewEditable) getController();
        if (data != null) controller.setData(data);
        if (mDismissOnDone) {
            sendResult(RESULT_DONE);
            getActivity().finish();
        }
    }

    private void showDeleteConfirmDialog() {
        String deleteWhat = " all " + getDataType().getDataString();
        DialogDeleteConfirm.showDialog(deleteWhat, getReviewView().getParentFragment(),
                DELETE_CONFIRM, getActivity().getFragmentManager());
    }

    private boolean hasDataToDelete() {
        return getData() != null && getData().size() > 0;
    }

    private GvDataList getData() {
        return getReviewView() != null ? getReviewView().getGridData() : null;
    }
}
