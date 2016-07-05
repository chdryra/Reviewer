/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.os.Bundle;
import android.view.MenuItem;

import com.chdryra.android.mygenerallibrary.Dialogs.AlertListener;
import com.chdryra.android.mygenerallibrary.OtherUtils.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewDataEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MenuActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 10/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MenuEdit<T extends GvData> extends MenuActionNone<T> implements AlertListener {
    private static final int MENU = R.menu.menu_delete_done;
    public static final int MENU_DELETE_ID = R.id.menu_item_delete;
    public static final int MENU_DONE_ID = R.id.menu_item_done;

    public static final ActivityResultCode RESULT_DELETE = ActivityResultCode.DELETE;
    public static final ActivityResultCode RESULT_DONE = ActivityResultCode.DONE;

    private static final int ALERT_DIALOG = RequestCodeGenerator.getCode("DeleteConfirm");

    private final MenuActionItem mDeleteAction;
    private final MenuActionItem mDoneAction;

    private final String mDeleteWhat;
    private final boolean mDismissOnDelete;
    private final boolean mDismissOnDone;

    private ReviewDataEditor<T> mEditor;

    public MenuEdit(GvDataType<T> dataType) {
        this(dataType.getDataName(), dataType.getDataName());
    }

    public MenuEdit(String title, String deleteWhat) {
        this(title, deleteWhat, false);
    }

    public MenuEdit(String title, String deleteWhat, boolean dismissOnDelete) {
        this(title, deleteWhat, dismissOnDelete, true, MENU);
    }

    public MenuEdit(String title, String deleteWhat,
                    boolean dismissOnDelete, boolean dismissOnDone, int menuId) {
        super(menuId, title, true);

        mDeleteWhat = deleteWhat;
        mDismissOnDelete = dismissOnDelete;
        mDismissOnDone = dismissOnDone;

        mDeleteAction = new MenuActionItem() {
            //Overridden
            @Override
            public void doAction(MenuItem item) {
                if (hasDataToDelete()) showDeleteConfirmDialog();
            }
        };

        mDoneAction = new MenuActionItem() {
            //Overridden
            @Override
            public void doAction(MenuItem item) {
                doDoneSelected();
                sendResult(RESULT_DONE);
            }
        };

        addMenuItems();
    }

    public int getAlertRequestCode() {
        return ALERT_DIALOG;
    }

    @Override
    public void onAlertNegative(int requestCode, Bundle args) {

    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        if (requestCode == ALERT_DIALOG) doDeleteSelected();
    }

    //protected methods
    protected MenuActionItem getDeleteAction() {
        return mDeleteAction;
    }

    protected MenuActionItem getDoneAction() {
        return mDoneAction;
    }

    protected DataBuilderAdapter<?> getBuilder() {
        return (DataBuilderAdapter<?>) getAdapter();
    }

    protected ReviewDataEditor<T> getEditor() {
        return mEditor;
    }

    protected void bindDefaultDeleteActionItem(int deleteId) {
        bindMenuActionItem(getDeleteAction(), deleteId, false);
    }

    protected void bindDefaultDoneActionItem(int doneId) {
        bindMenuActionItem(getDoneAction(), doneId, mDismissOnDone);
    }

    protected void doDeleteSelected() {
        if (hasDataToDelete()) {
            getBuilder().deleteAll();
            if (mDismissOnDelete) {
                sendResult(RESULT_DELETE);
                getApp().getCurrentScreen().close();
            }
        }
    }

    protected void doDoneSelected() {
        mEditor.commitEdits();
    }

    private void showDeleteConfirmDialog() {
        String deleteWhat = "all " + mDeleteWhat;
        getApp().getCurrentScreen().showDeleteConfirm(deleteWhat, ALERT_DIALOG);
    }

    private boolean hasDataToDelete() {
        return getGridData() != null && getGridData().size() > 0;
    }

    //Overridden
    @Override
    protected void addMenuItems() {
        bindDefaultDeleteActionItem(MENU_DELETE_ID);
        bindDefaultDoneActionItem(MENU_DONE_ID);
    }

    @Override
    protected void doUpSelected() {
        mEditor.discardEdits();
        super.doUpSelected();
    }

    @Override
    public void onAttachReviewView() {
        super.onAttachReviewView();
        mEditor = (ReviewDataEditor<T>) getReviewView();
    }
}
